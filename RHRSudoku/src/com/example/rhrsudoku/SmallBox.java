package com.example.rhrsudoku;

import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

import com.example.rhrsudoku.GameActivity.StateInfo;

public class SmallBox extends View {	
	
	private static final int POSSIBLE_VALUES = 1;
	private static final int FINAL_VALUE = 2;
	private static final int NONE = 3;
	private static final int TOP = 4;
	private static final int BOTTOM = 5;
	private static final int RIGHT = 6;
	private static final int LEFT = 7;
	private static final int HORIZONTAL = 8;
	private static final int VERTICAL = 9;
		
	final static float finalValueTextSize1 = 36f;
	final static float generatedValueTextSize1 = 42f;
	final static float possibleValueTextSize1 = 16f;
	static float scale;
	
	private int displayState = NONE;
	int size1 = 60;
	int row, col;
	SudokuPuzzleCell cell1;
	private SortedSet<Integer> possibleValues = new TreeSet<Integer>();
	String possibleValuesS = new String();
	private static Paint[] paints = new Paint[16];
	private static Paint[] gridLinePaintList = new Paint[10];
	GameActivity.StateInfo state1;
	
	public SmallBox(Context context, GameActivity.StateInfo state1, 
			SudokuPuzzleCell cell1, int row, int col, int size) {
		super(context);
		this.state1 = state1;
		this.cell1 = cell1;
		this.row = row;
		this.col = col;
		this.size1 = size;
		//setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		createPaints();
		createGridLinePaintList();
		if (row<0 || row>8 || col<0 || col>8) {
			System.err.println("ERRONEOUS ROW/COL");
			System.exit(1);
		}
		if (cell1.hasValue)
			displayState = FINAL_VALUE;
		scale = getResources().getDisplayMetrics().density;
		cell1.setSmallBox(this);
	}
	
	/*
	 * BEGIN CALLBACKS
	 */
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * The width and height of each cell should be specified by the activity
		 * because each cell needs to be exactly the same size
		 */
		int width = setDimension(widthMeasureSpec);
		int height = setDimension(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		/*
		 * paints[0]	default text colour, user inputted numbers
		 * paints[1]	text colour, solver generated numbers
		 * paints[2]	text colour, generator generated numbers
		 * paints[3]	text colour, conflicting number
		 * paints[4]	text, possible values
		 * paints[5]	text colour, selected, final value
		 * paints[6]	text colour, selecting, possible value
		 * 
		 * paints[7]	generator generated background colour
		 * paints[8]	default background colour, for user input
		 * paints[9]	solver generated background colour	
		 * paints[10]	conflicting cells background colour
		 * paints[11]	selected and entering final value, background colour
		 * paints[12]	selected and entering possible value, background colour
		 * 
		 * paints[13]	minor gridLine
		 * paints[14]	major gridLine
		 * paints[15]	edge gridLine
		 */
		
		onDrawBackground(canvas);
		onDrawText(canvas);
		onDrawBorder(canvas);
	}
	
	private void onDrawBackground(Canvas canvas) {
		/*
		 * priorities for drawing background:
		 * 1) selected
		 * 2) conflicting
		 * 3) user generated
		 * 4) generator generated
		 * 5) solver generated
		 */
		if (state1.hasSelectedSmallBox && state1.selectedSmallBox == this) {
			if (state1.selectingState == StateInfo.SELECTING_FINAL_VALUE)
				canvas.drawPaint(paints[11]);
			else if (state1.selectingState == StateInfo.SELECTING_POSSIBLE_VALUE)
				canvas.drawPaint(paints[12]);
		}
		else if (cell1.isConflicting())
			canvas.drawPaint(paints[10]);
		else if (cell1.inputMethod == SudokuPuzzleCell.USER_INPUT)
			canvas.drawPaint(paints[8]);
		else if (cell1.inputMethod == SudokuPuzzleCell.GENERATED)
			canvas.drawPaint(paints[7]);
		else if (cell1.inputMethod == SudokuPuzzleCell.SOLVER_GENERATED)
			canvas.drawPaint(paints[9]);
		else
			canvas.drawPaint(paints[8]);
	}
	
	private void onDrawText(Canvas canvas) {
		Paint selectedPaint = null;
		int xpos = 0;
		int ypos = 0;
		String s = null;
		if (displayState == SmallBox.NONE) {
			return;
		}
		else if (displayState == SmallBox.FINAL_VALUE) {
			if (cell1.inputMethod == SudokuPuzzleCell.GENERATED)
				selectedPaint = paints[2];
			else if (cell1.inputMethod == SudokuPuzzleCell.SOLVER_GENERATED)
				selectedPaint = paints[1];
			else if (cell1.isConflicting())
				selectedPaint = paints[3];
			else if (state1.hasSelectedSmallBox && state1.selectedSmallBox == this)
				selectedPaint = paints[5];
			else
				selectedPaint = paints[0];
			xpos = canvas.getWidth()/2;
			ypos = (int) ((canvas.getHeight() / 2) - ((selectedPaint.descent() + selectedPaint.ascent()) / 2)) ;
			s = Integer.toString(cell1.getValue());
			canvas.drawText(s, xpos, ypos, selectedPaint);
		}
		else if (displayState == SmallBox.POSSIBLE_VALUES) {
			if (state1.hasSelectedSmallBox && state1.selectedSmallBox == this)
				selectedPaint = paints[6];
			else
				selectedPaint = paints[4];
			s = possibleValuesS;
			xpos = canvas.getWidth()/2;
			ypos = (int) ((canvas.getHeight() / 2) - ((selectedPaint.descent() + selectedPaint.ascent()) / 2)) ;

			String[] lines1 = s.split("\n");
			Rect bounds = new Rect();
			int yoff = 0;
			for (int i=0;i<lines1.length;i++) {
				canvas.drawText(lines1[i], xpos, ypos + yoff, selectedPaint);
				selectedPaint.getTextBounds(lines1[i], 0, lines1[i].length(), bounds);
				yoff += bounds.height() + 2;
			}
		}
	}
	
	private void onDrawBorder(Canvas canvas) {
		drawBorder(canvas);
		//border.draw(canvas);
	}
	
	private void drawBorder(Canvas canvas) {
		Paint minor = paints[13];
		Paint major = paints[14];
		Paint edge = paints[15];
		
		drawBorder2(canvas, minor);
		drawBorder2(canvas, major);
		drawBorder2(canvas, edge);
	}
	private void drawBorder2(Canvas canvas, Paint paint) {
		drawBorder3(canvas, row, VERTICAL, paint);
		drawBorder3(canvas, col, HORIZONTAL, paint);
	}
	private void drawBorder3(Canvas canvas, int rowcol, int orientation, Paint p) {
		int head, tail;
		if (orientation == VERTICAL) {
			head = TOP;
			tail = BOTTOM;
		}
		else {
			head = LEFT;
			tail = RIGHT;
		}
		if (p==gridLinePaintList[rowcol])
			drawBorderLine(canvas, head, gridLinePaintList[rowcol]);
		if (p==gridLinePaintList[rowcol+1])
			drawBorderLine(canvas, tail, gridLinePaintList[rowcol + 1]);
	}
	private void drawBorderLine(Canvas canvas, int side, Paint paint) {
		if (side == TOP)
			canvas.drawLine(0, 0, size1, 0, paint);
		else if (side == BOTTOM)
			canvas.drawLine(0, size1, size1, size1, paint);
		else if (side == RIGHT)
			canvas.drawLine(size1, 0, size1, size1, paint);
		else if (side == LEFT)
			canvas.drawLine(0, 0, 0, size1, paint);
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		if (gainFocus) {
			state1.hasSelectedSmallBox = true;
			state1.selectedSmallBox = this;
		}
		
		else {
		}
	}
	
	/*
	 * END CALLBACKS
	 * BEGIN BUILDER METHODS
	 */
	
	private ShapeDrawable createBorder() {
		// UNUSED
		ShapeDrawable border = new ShapeDrawable();
		border.setBounds(0,0,size1,size1);
		Paint paint = border.getPaint();
		paint.setStyle(Paint.Style.STROKE);
		if (row == 0 || row == 3 || row == 6) {
			//TODO
			// add a thick border to the top
		}
		
		return border;		
	}
	
	private static void createPaints() {
		/*
		 * paints[0]	default text colour, user inputted numbers
		 * paints[1]	text colour, solver generated numbers
		 * paints[2]	text colour, generator generated numbers
		 * paints[3]	text colour, conflicting number
		 * paints[4]	text, possible values
		 * paints[5]	text colour, selected, final value
		 * paints[6]	text colour, selecting, possible value
		 * 
		 * paints[7]	generator generated background colour
		 * paints[8]	default background colour, for user input
		 * paints[9]	solver generated background colour	
		 * paints[10]	conflicting cells background colour
		 * paints[11]	selected and entering final value, background colour
		 * paints[12]	selected and entering possible value, background colour 
		 * 
		 * paints[13]	minor gridLine
		 * paints[14]	major gridLine
		 * paints[15]	edge gridLine
		 */
		
		// Get the screen's density scale
		int finalValueTextSize2 = (int) (finalValueTextSize1 * scale + 0.5f);
		int generatedValueTextSize2 = (int) (generatedValueTextSize1 * scale + 0.5f);
		int possibleValueTextSize2 = (int) (possibleValueTextSize1 * scale + 0.5f);
		
		for (int i=0;i<paints.length;i++) {
			paints[i] = new Paint();
			paints[i].setStyle(Style.FILL);
			paints[i].setTextAlign(Paint.Align.CENTER);
			paints[i].setTextSize(finalValueTextSize2);
		}
		
		paints[0].setColor(Color.BLACK);
		paints[1].setColor(Color.BLACK);
		paints[2].setColor(Color.BLACK);
		paints[3].setColor(Color.RED);
		paints[4].setColor(Color.BLACK);
		paints[5].setColor(Color.BLACK);
		paints[6].setColor(Color.BLACK);
		paints[7].setColor(Color.WHITE);
		paints[8].setColor(Color.WHITE);
		paints[9].setColor(Color.WHITE);
		paints[10].setColor(Color.WHITE);
		paints[11].setColor(0xFFCCE3FF);
		paints[12].setColor(Color.YELLOW);
		paints[13].setColor(Color.LTGRAY);
		paints[14].setColor(Color.BLACK);
		paints[15].setColor(Color.BLACK);
		
		paints[2].setTextSize(generatedValueTextSize2);
		paints[4].setTextSize(possibleValueTextSize2);
		paints[6].setTextSize(possibleValueTextSize2);
		
		paints[13].setStyle(Paint.Style.STROKE);
		paints[14].setStyle(Paint.Style.STROKE);
		paints[15].setStyle(Paint.Style.STROKE);
		paints[13].setStrokeWidth(2f);
		paints[14].setStrokeWidth(3f);
		paints[15].setStrokeWidth(12f);
		paints[13].setAlpha(250);
		paints[14].setAlpha(250);
		paints[15].setAlpha(250);
	}
	
	static void createGridLinePaintList() {
		Paint minor = paints[13];
		Paint major = paints[14];
		Paint edge = paints[15];
		gridLinePaintList[0] = edge; gridLinePaintList[1] = minor; 
		gridLinePaintList[2] = minor; gridLinePaintList[3] = major; 
		gridLinePaintList[4] = minor; gridLinePaintList[5] = minor;
		gridLinePaintList[6] = major; gridLinePaintList[7] = minor; 
		gridLinePaintList[8] = minor; gridLinePaintList[9] = edge;
	}
	
	/*
	 * END BUILDER METHODS
	 * BEGIN GETTER/SETTER METHODS
	 */
	
	private void calculatePossibleValuesS() {
		/*
		 * Creates a string of numbers from the set of possible values
		 */
		possibleValuesS = new String();
		for (Integer i : possibleValues) {
			if (possibleValuesS.length() == 4)
				possibleValuesS = possibleValuesS.concat("\n");
			possibleValuesS = possibleValuesS.concat(Integer.toString(i));
		}
	}
	
	public SortedSet<Integer> getPossibleValues() {
		return possibleValues;
	}
	
	public void setFinalValue(int i, int inputMethod) {
		if (!cell1.isEditable) {
			System.err.println("ERROR: This cell is not editable");
			return;
		}
		if (!boundsCheck(i))
			return;
		cell1.setValue(i); 
		cell1.setInput(inputMethod);
		displayState = FINAL_VALUE;
		invalidate();
	}
	
	public void clearFinalValue() {
		if (!cell1.isEditable) {
			System.err.println("ERROR: This cell is not editable");
			return;
		}
		cell1.hasValue = false;
		if (!possibleValues.isEmpty())
			displayState = SmallBox.POSSIBLE_VALUES;
		else
			displayState = SmallBox.NONE;
		invalidate();
	}
	public void addPossibleValue(int i) {
		if (!cell1.isEditable) {
			System.err.println("Error: this cell is not editable");
			return;
		}
		if(!boundsCheck(i))
			return;
		displayState = POSSIBLE_VALUES;
		cell1.hasValue = false;
		possibleValues.add(i);
		calculatePossibleValuesS();
		invalidate();
	}
	
	public boolean containsPossibleValue(int i) {
		return possibleValues.contains(i);
	}
	public void removePossibleValue(int i) {
		possibleValues.remove(i);
		displayState = SmallBox.POSSIBLE_VALUES;
		if (possibleValues.isEmpty())
			displayState = NONE;
		calculatePossibleValuesS();
		invalidate();
	}
	
	private boolean boundsCheck(int i) {
		/*
		 * bounds checking on numbers entered into finalValue and possibleValues
		 */
		if (i<1 || i>9) {
			System.out.println("ERRONEOUS VALUE: " + i);
			return false;
		}
		return true;
	}
	
	private int setDimension(int measureSpec) {
		int mode = View.MeasureSpec.getMode(measureSpec);
		int size2 = View.MeasureSpec.getSize(measureSpec);
		if (mode == View.MeasureSpec.EXACTLY)
			return size2;
		else if (mode == View.MeasureSpec.AT_MOST) {
			if (size2>size1)
				return size1;
			else 
				return size2;
		}
		else if (mode == View.MeasureSpec.UNSPECIFIED)
			return size1;
		return size1;
	}
	
	/*
	 * END GETTER/SETTER METHODS
	 */
}
