package com.example.rhrsudoku;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class HardCodedPuzzles3 {
	final static boolean calculatePuzzlesMdynamically = false;
	static int[] puzzlesMhandWritten = {300,300,300,300};
	static Resources resources;
	final static int difficultiesM = 4;
	static int[] puzzlesM = new int[difficultiesM];
	static XmlPullParserFactory factory;
	static Random randGen;
	static XmlResourceParser xpp;

	public HardCodedPuzzles3(Resources resources) {
		this.resources = resources;
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		factory.setNamespaceAware(true);
		try {
			if (this.calculatePuzzlesMdynamically)
				calculatePuzzlesM();
			else
				this.puzzlesM = this.puzzlesMhandWritten;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		randGen = new Random();
	}
	
	
	public SudokuPuzzle getRandomPuzzle(int difficulty) {
		xpp = resources.getXml(R.xml.db);
		int eventType = -43243;
		try {
			eventType = xpp.getEventType();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		int count = 0;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("puzzleBundles")) {
				if (difficulty == xpp.getAttributeIntValue(0, -1)) {
					int randomID = randGen.nextInt(puzzlesM[difficulty]);
					try {
						return getSpecificPuzzle(difficulty, randomID);
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
			try {
				eventType = xpp.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}
	
	public SudokuPuzzle getSpecificPuzzle(int difficulty, int id) throws XmlPullParserException, IOException {
		int eventType = xpp.next();
		int count=0;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("bundle")) {
				if (count==id)
					return createNext1();
				else
					count++;
			}
			if (eventType == XmlPullParser.END_TAG && xpp.getName().equals("puzzleBundles")) {
				System.err.println("Error: didnt find enough bundles to match this id,\n" +
						"Perhaps you have entered the wrong numbers for puzzlesMhandWritten\n" +
						"Or try setting calculatePuzzlesMdynamically=true");
				System.exit(1);
			}
			eventType = xpp.next();
		}
		return null;
	}
	
	private SudokuPuzzle createNext1() throws XmlPullParserException, IOException {
		int eventType = xpp.next();
		SudokuPuzzle puzzle = null;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("puzzle")) {
				puzzle = createPuzOrSol();
				break;
			}
			eventType = xpp.next();
		}
		if (puzzle == null) {
			System.err.println("ERROR: puzzle and/or solution are null");
			System.exit(1);
		}
		
		return puzzle;
	}
	
	private static SudokuPuzzle createPuzOrSol() throws XmlPullParserException, IOException {
		SudokuPuzzle puzzle = new SudokuPuzzle();
		int eventType = xpp.next();
		boolean gettingSolution = false;
		while(eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.END_TAG && xpp.getName().equals("solution")) {
				eventType = xpp.next();
				break;
			}
			if (eventType == XmlPullParser.END_TAG && xpp.getName().equals("puzzle")) {
				gettingSolution = true;
			}
			if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("cell")) {
				if (xpp.getAttributeCount() != 3) {
					System.err.println("Error: not enough attributes for cell");
					System.exit(1);
				}
				if ((!xpp.getAttributeName(0).equals("row")) || (!xpp.getAttributeName(1).equals("col")) ||
						(!xpp.getAttributeName(2).equals("val"))) {
					System.err.println("Error: Attributes are in wrong order:" +
							xpp.getAttributeName(0)+ ":"+xpp.getAttributeName(1)+ ":"+xpp.getAttributeName(2));
					System.exit(1);
					} 
				int row = xpp.getAttributeIntValue(0, -1);
				int col = xpp.getAttributeIntValue(1, -1);
				int value = xpp.getAttributeIntValue(2, -1);
				if (row == -1 || col == -1 || value == -1) {
					System.err.println("Error: Invalid values: row:"+ row + " col:"+col+" value:"+value);
					System.exit(1);
				}
				if (!gettingSolution) {
					puzzle.puzzle[row][col].setValue(value);
					puzzle.puzzle[row][col].setInput(SudokuPuzzleCell.GENERATED);
				}
				else {
					puzzle.puzzle[row][col].setSolution(value);
				}
				//System.out.println("Added row="+row+",col="+col+",value="+value);
			}
			eventType = xpp.next();
		}
		if (!puzzle.solutionIsFilled()) {
			System.err.println("Error: solution is not filled!");
			System.exit(1);
		}
		return puzzle;
	}
	
	private static void calculatePuzzlesM() throws XmlPullParserException, IOException {
		XmlResourceParser xpp = resources.getXml(R.xml.db);
		int eventType = xpp.getEventType();
		for (int i=0;i<difficultiesM;i++) {
			int count=0;
			while(eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.END_TAG && xpp.getName().equals("puzzleBundles")) {
					eventType = xpp.next();
					break;
				}
				if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("bundle")) {
					count++;
				}
				eventType = xpp.next();
			}
			puzzlesM[i] = count;
			System.out.println("Found " + count + " puzzles for difficulty " + i);
		}
	}

}
