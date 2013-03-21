package rhrstudios.rhrsudoku;

import java.util.SortedSet;
import java.util.TreeSet;

import rhrstudios.rhrsudoku.GameActivity.StateInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;


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
	final static float generatedValueTextSize1 = 36f;
	final static float possibleValueTextSize1 = 16f;
	final static float scale = 1.4f;
	static float density;
	final static boolean areUsingAIC = false;
	boolean areIndicatingConflicts = false;
	
	/*
	 * BEGIN STATE INFO TO SAVE
	 */
	private int displayState = NONE;
	SortedSet<Integer> possibleValues = new TreeSet<Integer>();
	/*
	 * END STATE INFO TO SAVE
	 */
	static int size1 = 60;
	//int row, col;
	SudokuPuzzleCell cell1;
	String possibleValuesS = new String();
	private static Paint[] paints = new Paint[16];
	private static Paint[] gridLinePaintList = new Paint[10];
	GameActivity.StateInfo state1;
	
	public SmallBox(Context context, GameActivity.StateInfo state1, 
			SudokuPuzzleCell cell1, int row, int col, int size) {
		super(context);
		this.state1 = state1;
		this.cell1 = cell1;
//		this.row = row;
//		this.col = col;
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
		density = getResources().getDisplayMetrics().density;
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
		 * paints[1]	text colour, hint generated numbers
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
		else if (cell1.inputMethod == SudokuPuzzleCell.HINT_GENERATED)
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
			if (cell1.getValue() == null)
				return;
			if (cell1.inputMethod == SudokuPuzzleCell.GENERATED)
				selectedPaint = paints[2];
			else if (cell1.inputMethod == SudokuPuzzleCell.HINT_GENERATED)
				selectedPaint = paints[1];
			else if ((areUsingAIC && areIndicatingConflicts && cell1.isConflicting()) ||
					!areUsingAIC && cell1.isConflicting()) {
				selectedPaint = paints[3];
			}
			else if (state1.hasSelectedSmallBox && state1.selectedSmallBox == this)
				selectedPaint = paints[5];
			else
				selectedPaint = paints[0];
			xpos = View.MeasureSpec.getSize(getWidth())/2;
			ypos = (int) ((View.MeasureSpec.getSize(getHeight()) / 2) - ((selectedPaint.descent() + selectedPaint.ascent()) / 2)) ;
			
			s = Integer.toString(cell1.getValue());
			canvas.drawText(s, xpos, ypos, selectedPaint);
		}
		else if (displayState == SmallBox.POSSIBLE_VALUES) {
			if (possibleValues.isEmpty())
				return;
			if (state1.hasSelectedSmallBox && state1.selectedSmallBox == this)
				selectedPaint = paints[6];
			else
				selectedPaint = paints[4];
			s = possibleValuesS;
			String[] lines1 = s.split("\n");
			xpos = View.MeasureSpec.getSize(getWidth())/2;
			
			if (lines1.length == 1)
				ypos = (int) ((View.MeasureSpec.getSize(getHeight()) / 2) - 
						((selectedPaint.descent() + selectedPaint.ascent()) / 2)) ;
			else
				ypos = (int) ((View.MeasureSpec.getSize(getHeight())/2));
			

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
		drawBorder3(canvas, cell1.row, VERTICAL, paint);
		drawBorder3(canvas, cell1.col, HORIZONTAL, paint);
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
			if (this.areIndicatingConflicts && !cell1.isConflicting())
				this.areIndicatingConflicts = false;
		}
	}
	

	  @Override
	  public Parcelable onSaveInstanceState() {
	    Parcelable superState = super.onSaveInstanceState();
	    SavedState ss = new SavedState(superState);
	    ss.solution = this.cell1.solution;
	    ss.hasSolution = this.cell1.hasSolution;
	    ss.row = this.cell1.row;
	    ss.col = this.cell1.col;
	    ss.hasValue = this.cell1.hasValue;
	    ss.isEditable = this.cell1.isEditable;
	    if (this.cell1.hasValue)
	    	ss.value = this.cell1.getValue();
	    ss.inputMethod = this.cell1.inputMethod;
	    ss.displayState = this.displayState;
	    String possibleValuesS2 = new String();
	    for (int i : this.possibleValues) {
	    	possibleValuesS2 = possibleValuesS2.concat(Integer.toString(i));
	    }
	    ss.possibleValuesS = possibleValuesS2;
	    return ss;
	  }

	  @Override
	  public void onRestoreInstanceState(Parcelable state) {
	    //begin boilerplate code so parent classes can restore state
	    if(!(state instanceof SavedState)) {
	      super.onRestoreInstanceState(state);
	      return;
	    }

	    SavedState ss = (SavedState)state;
	    super.onRestoreInstanceState(ss.getSuperState());
	    //end

	    this.cell1.col = ss.col;
	    this.cell1.row = ss.row;
	    this.cell1.isEditable = true;
	    this.cell1.setValue(ss.value);
	    this.cell1.hasValue = ss.hasValue;
	    this.cell1.setInput(ss.inputMethod);
	    this.cell1.isEditable = ss.isEditable;
	    this.displayState = ss.displayState;
	    this.possibleValues.clear();
	    this.cell1.hasSolution = ss.hasSolution;
	    this.cell1.solution = ss.solution;
	    
	    for (int i=0;i<ss.possibleValuesS.length();i++) {
	    	int pv = Integer.parseInt(String.valueOf(ss.possibleValuesS.charAt(i)));
	    //	int pv = Integer.parseInt(possibleValuesS.substring(i, i+1));
	    	this.possibleValues.add(pv);
	    }
	    this.calculatePossibleValuesS();
	  }

	  static class SavedState extends BaseSavedState {
		  final static int TRUE = 0;
		  final static int FALSE = 1;
	   int row, col;
	   boolean hasValue;
	   boolean isEditable;
	   int value;
	   int inputMethod;
	   int displayState;
	   String possibleValuesS;
	   boolean hasSolution;
	   int solution;


	    SavedState(Parcelable superState) {
	      super(superState);
	    }

	    private SavedState(Parcel in) {
	      super(in);
	      this.row = in.readInt();
	      this.col = in.readInt();
	      if (in.readInt() == TRUE)
	    	  this.hasValue = true;
	      else
	    	  this.hasValue = false;
	      if (in.readInt() == TRUE)
	    	  this.isEditable = true;
	      else
	    	  this.isEditable = false;
	      this.value = in.readInt();
	      this.inputMethod = in.readInt();
	      this.displayState = in.readInt();
	      this.possibleValuesS = in.readString();
	      if (in.readInt() == TRUE)
	    	  this.hasSolution = true;
	      else
	    	  this.hasSolution = false;
	      this.solution = in.readInt();
	    }

	    @Override
	    public void writeToParcel(Parcel out, int flags) {
	      super.writeToParcel(out, flags);
	      out.writeInt(this.row);
	      out.writeInt(this.col);
	      if (this.hasValue)
	    	  out.writeInt(SavedState.TRUE);
	      else
	    	  out.writeInt(SavedState.FALSE);
	      if (this.isEditable)
	    	  out.writeInt(SavedState.TRUE);
	      else
	    	  out.writeInt(SavedState.FALSE);
	      out.writeInt(this.value);
	      out.writeInt(this.inputMethod);
	      out.writeInt(this.displayState);
	      out.writeString(this.possibleValuesS);
	      if (this.hasSolution)
	    	  out.writeInt(TRUE);
	      else
	    	  out.writeInt(FALSE);
	      out.writeInt(solution);
	      
	    }

	    //required field that makes Parcelables from a Parcel
	    public static final Parcelable.Creator<SavedState> CREATOR =
	        new Parcelable.Creator<SavedState>() {
	          public SavedState createFromParcel(Parcel in) {
	            return new SavedState(in);
	          }
	          public SavedState[] newArray(int size) {
	            return new SavedState[size];
	          }
	    };
	  }
	
	
	/*
	 * END CALLBACKS
	 * BEGIN BUILDER METHODS
	 */
	
	private static void createPaints() {
		/*
		 * paints[0]	default text colour, user inputted numbers
		 * paints[1]	text colour, hint generated numbers
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
		int finalValueTextSize2 = (int) (finalValueTextSize1 * density * scale * (size1/100f) + 0.5f);
		int generatedValueTextSize2 = (int) (generatedValueTextSize1 * density * scale * (size1/100f) + 0.5f);
		int possibleValueTextSize2 = (int) (possibleValueTextSize1 * density * scale * (size1/100f) + 0.5f);
		
		for (int i=0;i<paints.length;i++) {
			paints[i] = new Paint();
			paints[i].setStyle(Style.FILL);
			paints[i].setTextAlign(Paint.Align.CENTER);
			paints[i].setTextSize(finalValueTextSize2);
		}
		
		paints[0].setColor(Color.BLACK);
		paints[1].setColor(Color.GREEN);
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
		
		paints[0].setTextSize(finalValueTextSize2);
		paints[2].setTextSize(generatedValueTextSize2);
		paints[4].setTextSize(possibleValueTextSize2);
		paints[6].setTextSize(possibleValueTextSize2);
		
		//paints[0].setTypeface(Typeface.DEFAULT_BOLD);
		paints[2].setTypeface(Typeface.DEFAULT_BOLD);
		
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
	
	public void removePossibleValues() {
		if (!possibleValues.isEmpty()) {
			possibleValues.clear();
			displayState = NONE;
			calculatePossibleValuesS();
			invalidate();
		}
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
