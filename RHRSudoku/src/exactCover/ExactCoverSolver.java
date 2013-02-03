package exactCover;

import java.util.*;
import java.util.Random;

import com.google.common.base.Optional;
import com.google.common.collect.*;

public class ExactCoverSolver<E> {	
	private final static int SETX_NOT_COVERED = 1;
	private final static int SETS_NOT_COVERED = 2;
	private final static int IS_VALID = 3;
	private final static int SETS_EMPTY = 4;
	private final static int SETX_EMPTY = 5;
	private final boolean PRINT_WORKING = true;
	private static final boolean PRINT_MATRIX = false;
	private static final boolean PRINT_3 = true;

	public Set<Set<E>> solve(ExactCoverProblem<E> p) {
			/*
			 * we will use the dancing links implementation.
			 * columns represent elements
			 * rows represent subsets of S
			 * 
			 * this function tries to solve the problem,
			 * if a solution is found it is returned
			 * if no solution is found, null is returned
			 * status
			 * 1) each element in SetX is given an identifier
			 * 2) each element in Sets is given an identifier
			 * 3) a matrix is created using the identifiers
			 * 4) a solution is found using dancing links
			 * 5) the identifiers are using to identify the subsets to return
			 * 
			 */
		int validity1 = validateProblem(p);
		switch (validity1) {
		case SETS_NOT_COVERED:
			System.out.println("ERROR: setS contains subsets containing elements not in setX");
			return null;
		case SETX_NOT_COVERED:
			System.out.println("ERROR: setX contains elements not covered by setS");
			return null;
		case SETS_EMPTY:
			System.out.println("ERROR: setS is empty");
			return null;
		case SETX_EMPTY:
			System.out.println("ERROR: setX is empty");
			return null;
		case IS_VALID:
			break;
		}
		
		
		
		int id = 1;
		BiMap<Integer, E> mapX = HashBiMap.create(p.setX.size());
		BiMap<Integer, Set<E>> mapS = HashBiMap.create(p.setS.size());

		Iterator<E> it = p.setX.iterator();
		while (it.hasNext()) {
			E element = it.next();
			mapX.put(id, element );
			if (PRINT_WORKING)
				System.out.println("ID: " +id +"     Element:  " + element.toString());
			id++;

		}
		it = (Iterator<E>) p.setS.iterator();
		while (it.hasNext()) {
			Set<E> subset = (Set<E>) it.next();
			mapS.put(id, subset);
			if (PRINT_WORKING)
				System.out.println("ID: " + id + "     Subset:   "+subset.toString());
			id++;
		}
		
		Matrix matrix = buildMatrix(p, mapX, mapS);
		processRowInclusions1(matrix, p, mapS);
		
		Set<Integer> result1 = solveMatrix(matrix);
		if (result1 == null)
			return null;
		Set<Set<E>> result2 = new HashSet<Set<E>>();
		it = (Iterator<E>) result1.iterator();
		while(it.hasNext()) {
			
			result2.add(mapS.get(it.next()));
		}
		return result2;
	}
	
	private int validateProblem(ExactCoverProblem<E> p) {
		Set<E> unionS = new HashSet<E>();
		for (Set<E> subset : p.setS)
			unionS.addAll(subset);
		if (unionS.isEmpty())
			return SETS_EMPTY;
		if (p.setX.isEmpty())
			return SETX_EMPTY;
		if (unionS.equals(p.setX))
			return IS_VALID;
		if (!p.setX.containsAll(unionS))
			return SETS_NOT_COVERED;
		else if (!unionS.containsAll(p.setX)) {
			Set<E> s3 = new HashSet<E>();
			s3.addAll(p.setX);
			s3.removeAll(unionS);
			System.out.println("Elements in setX not covered: "+s3.toString());
			return SETX_NOT_COVERED;
		}
		else
			return IS_VALID;
	}

	Set<Integer> solveMatrix(Matrix matrix) {
		if (PRINT_3)
			System.out.println("Solving Matrix...");
		Set<Integer> solutions = new HashSet<Integer>();
		/*				return null;

		 * try to solve the matrix. if solvable, return the set of integers
		 * identifying the subsets/rows
		 * if unsolvable, return null
		 */
		
		boolean isSolved = solveMatrix1(matrix, solutions);
		if (isSolved)
			return solutions;
		
		return null;
	}
	
	boolean solveMatrix1(Matrix matrix, Set<Integer> solutions) {
		if (PRINT_3)
			System.out.println("Entered solveMatrix1()");
		if(matrix.isEmpty())
			return true;
		return solveMatrix2(matrix, solutions);
		}
	
	boolean solveMatrix2(Matrix matrix, Set<Integer> solutions) {
		if (PRINT_3)
			System.out.println("Entered solveMatrix2()");
		MatrixCellHeader chosenColumn = matrix.columnStarter.findSmallestColumn();
//		MatrixCellHeader chosenColumn = matrix.columnStarter.starter;
//		Random randGen = new Random();
//		int rand1 = randGen.nextInt(matrix.getColumnsM());
//		for (int i=0; i<rand1; i++)
//			chosenColumn = chosenColumn.next;
		if (PRINT_WORKING)
			System.out.println("chosenColumn with ID: " + chosenColumn.id);
		return solveMatrix3(matrix, solutions, chosenColumn);
		
	}
	boolean solveMatrix3(Matrix matrix, Set<Integer> solutions, MatrixCellHeader chosenColumn) {
		if (PRINT_3)
			System.out.println("Entered solveMatrix3()");
		if (chosenColumn.isEmpty())
			return false;
		Set<MatrixCellHeader> rowSet2 = chosenColumn.getIntersectingHeaders();
		Set<Integer> intersectingHeaderIDS = new HashSet<Integer>();
		Iterator<MatrixCellHeader> rowSet2IT = rowSet2.iterator();
		while (rowSet2IT.hasNext())
			intersectingHeaderIDS.add(rowSet2IT.next().id);
		rowSet2IT = rowSet2.iterator();
		while(rowSet2IT.hasNext()) {
			MatrixCellHeader chosenRow = rowSet2IT.next();
			if(PRINT_WORKING) {
				matrix.printMatrix();
				System.out.println("chosenRow with ID: " + chosenRow.id + " out " +
						"of " + intersectingHeaderIDS.toString());
			}
			boolean value = solveMatrix4(matrix, solutions, chosenColumn, chosenRow);
			if (value == false) {
				if (PRINT_WORKING)
					System.out.println("Branch failed with chosenRow ID: "+ chosenRow.id + " out " +
							"of " + intersectingHeaderIDS.toString());
				else
					continue;
				
			}
			else if (value == true)
				return true;
		}
		if (PRINT_WORKING)
			System.out.println("No more intersecting rows left to choose from for " +
					"chosenColumn: " + chosenColumn.id);
		return false;	
	}

	boolean solveMatrix4(Matrix matrix, Set<Integer> solutions, 
			MatrixCellHeader chosenColumn, MatrixCellHeader chosenRow) {
		if (PRINT_3)
			System.out.println("Entered solveMatrix4()");
		solutions.add(chosenRow.id);
		LinkedList<MatrixCellHeader> removedHeaders = new LinkedList<MatrixCellHeader>();
		
		for (MatrixCellHeader columnHeader : chosenRow.getIntersectingHeaders()) {
			for (MatrixCellHeader rowHeader2 : columnHeader.getIntersectingHeaders()) {
				if (rowHeader2.isRemoved)
					continue;
				matrix.rowStarter.removeCellHeader(rowHeader2);
				removedHeaders.add(rowHeader2);
			}
			if (columnHeader.isRemoved)
				continue;
			matrix.columnStarter.removeCellHeader(columnHeader);
			removedHeaders.add(columnHeader);
		}
		boolean value = solveMatrix1(matrix, solutions);
		if (value == false) {
			if (PRINT_WORKING)
				System.out.println("Branch failed..........backtracing");
			while(!removedHeaders.isEmpty()) {
				MatrixCellHeader removedHeader1 = removedHeaders.removeLast();
				if (removedHeader1.orientation == Matrix.HORIZONTAL)
					matrix.rowStarter.restoreCellHeader(removedHeader1);
				else if (removedHeader1.orientation == Matrix.VERTICAL)
					matrix.columnStarter.restoreCellHeader(removedHeader1);
			}
			solutions.remove(chosenRow.id);
			return false;
		}
		else
			return true;
		
	}
	boolean solveMatrix5() {
		if (PRINT_3)
			System.out.println("Entered solveMatrix5()");
		return false;
		
	}
	boolean solveMatrix6() {
		if (PRINT_3)
			System.out.println("Entered solveMatrix6()");
		return false;
		
	}
	
	void processRowInclusions1(Matrix matrix, ExactCoverProblem<E> p, BiMap<Integer,Set<E>> mapS ) {
		if (PRINT_3)
			System.out.println("Processing Row Inclusions...");
		if (p.setW == null)
			return;
		if (!p.setS.containsAll(p.setW)) {
			System.err.println("ERROR: setW is not a strict subset of setS");
			return;
		}
		for (Set<E> subset: p.setW) {
			int rowID = mapS.inverse().get(subset);
			Optional<MatrixCellHeader> row2 = matrix.rowStarter.findHeader(rowID);
			if (row2.isPresent())
				processRowInclusions2(matrix, row2.get());
		}
	}
	
	
	void processRowInclusions2(Matrix matrix, MatrixCellHeader chosenRow) {
		for (MatrixCellHeader columnHeader : chosenRow.getIntersectingHeaders()) {
			for (MatrixCellHeader rowHeader2 : columnHeader.getIntersectingHeaders()) {
				if (rowHeader2.isRemoved)
					continue;
				matrix.rowStarter.removeCellHeader(rowHeader2);
			}
			if (columnHeader.isRemoved)
				continue;
			matrix.columnStarter.removeCellHeader(columnHeader);
		}
	}
	
	
	Matrix buildMatrix(ExactCoverProblem<E> p, BiMap<Integer, E> mapX,
			BiMap<Integer, Set<E>> mapS ) {
		if (PRINT_3)
			System.out.println("Building Matrix...");
		/*
		 * 1) build the headers (rows and columns)
		 * 2) build the cells, linking with each other and the headers
		 * 3) build the Matrix
		 */
		MatrixHeaderStarter columnStarter, rowStarter;
		columnStarter = new MatrixHeaderStarter(null, Matrix.VERTICAL);
		rowStarter = new MatrixHeaderStarter(null, Matrix.HORIZONTAL);
		initializeHeaders(columnStarter, mapX);
		initializeHeaders(rowStarter, mapS);
		Matrix matrix = new Matrix(columnStarter, rowStarter);
		initializeMatrixCells(p, mapX, mapS, matrix);
		if (PRINT_WORKING)
			matrix.printMatrix();
		return matrix;
		}
	
	void initializeHeaders(MatrixHeaderStarter headersStarter, BiMap<Integer, ?> map) {
		boolean initializedStarter = false;
		for (Map.Entry<Integer, ?> entry : map.entrySet()) {
			
			if (!initializedStarter) {
				MatrixCellHeader header = new MatrixCellHeader(entry.getKey(),
						null, null, headersStarter.orientation);
				headersStarter.starter = header;
				headersStarter.starter.next = headersStarter.starter;
				headersStarter.starter.previous = headersStarter.starter;
				initializedStarter = true;
				continue;
			}
			
			MatrixCellHeader header2 = new MatrixCellHeader(entry.getKey(), headersStarter.starter,
					headersStarter.starter.next, headersStarter.orientation);
			
			header2.previous.next = header2;
			header2.next.previous = header2;
		}
	}
	
	void initializeMatrixCells(ExactCoverProblem<E> p, BiMap<Integer, E> mapX,
			BiMap<Integer, Set<E>> mapS, Matrix matrix) {
		/*
		 * iterate through each subset.
		 * for each element, create a new MatrixCell
		 */
		Iterator<Set<E>> it = p.setS.iterator();
		while (it.hasNext()) {
			Set<E> subset = (Set<E>) it.next();
			for (E element : subset) {
				int rowID = mapS.inverse().get(subset);
				int columnID = mapX.inverse().get(element);
				MatrixCellHeader rowHeader = matrix.rowStarter.findHeader(rowID).get();
				MatrixCellHeader columnHeader = matrix.columnStarter.findHeader(columnID).get();
				MatrixCell cell = new MatrixCell(columnHeader, rowHeader);
				rowHeader.addCell(cell);
				columnHeader.addCell(cell);
			}
		}
	}
	
	class Matrix {
		final static int VERTICAL = 1;
		final static int HORIZONTAL = 2;
		MatrixHeaderStarter columnStarter, rowStarter;
		public Matrix( MatrixHeaderStarter columnStarter, MatrixHeaderStarter rowStarter) {
			this.columnStarter = columnStarter;
			this.rowStarter = rowStarter;
		}
		boolean isEmpty() {
			return columnStarter.isEmpty();		
		}
		
		int getColumnsM() {
			return columnStarter.getSize();
		}
		void printMatrix() {
			if (!PRINT_MATRIX)
				return;
			System.out.println();
			System.out.println("==========================================");
			int starterID = columnStarter.starter.id;
			MatrixCellHeader header1= columnStarter.starter;
			System.out.print("    ");
			while(true) {
				System.out.print(header1.id + "  ");
				header1 = header1.next;
				if (header1.id == starterID)
					break;
			}
			System.out.println("\n-------------------------------------------");
			MatrixCellHeader header2 = rowStarter.starter;
			starterID = header2.id;
			while(true) {
				System.out.print(header2.id+"|  ") ;
				Set<MatrixCellHeader> intersects = header2.getIntersectingHeaders();
				// for each columnHeader, if it intersects print 1, otherwise print 0
				MatrixCellHeader columnHeader3 = columnStarter.starter;
				int startingID2 = columnHeader3.id;
				while(true) {
					if(intersects.contains(columnHeader3))
						System.out.print("1  ");
					else
						System.out.print("0  ");
					columnHeader3 = columnHeader3.next;
					if(columnHeader3.id == startingID2)
						break;
				}
				System.out.println();
				header2 = header2.next;
				if (header2.id == starterID)
					break;
				
			}
			System.out.println("=======================================");
		}
	}
	class MatrixCell {
		MatrixCell north, east, south, west;
		{ north = east = south = west = this; }

		MatrixCellHeader columnHeader, rowHeader;
		public MatrixCell(MatrixCellHeader columnHeader, MatrixCellHeader rowHeader) {
			this.columnHeader = columnHeader;
			this.rowHeader = rowHeader;
		}		
	}
	class MatrixCellHeader {
		final int id;
		final int orientation;
		boolean isRemoved = false;
		MatrixCellHeader next, previous;
		public MatrixCellHeader(int id, MatrixCellHeader previous, MatrixCellHeader next,
				int orientation) {
			this.id = id;
			this.next = next;
			this.previous = previous;
			this.orientation = orientation;
		}
		MatrixCell starter;
		boolean starterInitialized = false;
		void addCell(MatrixCell c) {
			if (!starterInitialized) {
				starter = c;
				starterInitialized = true;
				return;
			}
			if (orientation == Matrix.VERTICAL) {
				c.north = starter;
				c.south = starter.south;
				c.north.south = c;
				c.south.north = c;
			}
			else if (orientation == Matrix.HORIZONTAL) {
				c.west = starter;
				c.east = starter.east;
				c.west.east = c;
				c.east.west = c;
			}
		}
		boolean isEmpty() {
			if (starter == null)
				return true;
			return false;
		}
		void removeCells() {
			if (isEmpty()) {
				return;
			}
			if (orientation == Matrix.HORIZONTAL) {
				int startingColumnID = starter.columnHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if (cell1.columnHeader.starter == cell1)
						cell1.columnHeader.starter = cell1.south;
					cell1.north.south = cell1.south;
					cell1.south.north = cell1.north;
					if (cell1.columnHeader.starter == cell1)
						cell1.columnHeader.starter = null;
					
					cell1 = cell1.east;
					if(cell1.columnHeader.id == startingColumnID)
						break;
				}
			}
			else if (orientation == Matrix.VERTICAL) {
				int startingRowID = starter.rowHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if (cell1.rowHeader.starter == cell1)
						cell1.rowHeader.starter = cell1.east;
					cell1.east.west = cell1.west;
					cell1.west.east = cell1.east;
					if (cell1.rowHeader.starter == cell1)
						cell1.rowHeader.starter = null;
					cell1 = cell1.south;
					if (cell1.rowHeader.id == startingRowID)
						break;
				}
			}
		}
		
		void restoreCells() {
			if(!isRemoved)
				return;
			if (isEmpty())
				return;
			if (orientation == Matrix.HORIZONTAL) {
				int startingID = starter.columnHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if(cell1.columnHeader.starter == null)
						cell1.columnHeader.starter = cell1;
					cell1.north.south = cell1;
					cell1.south.north = cell1;
					cell1 = cell1.east;
					if (cell1.columnHeader.id == startingID)
						break;
				}
			}
			else if (orientation == Matrix.VERTICAL) {
				int startingID = starter.rowHeader.id;
				MatrixCell cell1 = starter;
				while(true) {
					if(cell1.rowHeader.starter == null)
						cell1.rowHeader.starter = cell1;
					cell1.west.east = cell1;
					cell1.east.west = cell1;
					cell1 = cell1.south;
					if (cell1.rowHeader.id == startingID)
						break;
				}
			}
		}
		Set<MatrixCellHeader> getIntersectingHeaders() {
			Set<MatrixCellHeader> headers = new HashSet<MatrixCellHeader>();
			if (isEmpty())
				return headers;
			MatrixCell cell = starter;
			if (orientation == Matrix.HORIZONTAL) {
				int startingID = cell.columnHeader.id;
				while(true) {
					headers.add(cell.columnHeader);
					cell = cell.east;
					if(cell.columnHeader.id == startingID)
						break;
				}
			}
			else if (orientation == Matrix.VERTICAL) {
				int startingID = cell.rowHeader.id;
				while(true) {
					headers.add(cell.rowHeader);
					cell = cell.south;
					if (cell.rowHeader.id == startingID)
						break;
				}
				
			}
			return headers;
		}
	}
	class MatrixHeaderStarter {
		MatrixCellHeader starter;
		final int orientation;
		public MatrixHeaderStarter (MatrixCellHeader starter, int orientation) {
			this.starter = starter;
			this.orientation = orientation;
		}
		public MatrixCellHeader findSmallestColumn() {
			if (orientation != Matrix.VERTICAL) {
				System.err.println("ERROR: Not a column header starter");
				return null;
			}
			Set<MatrixCellHeader> headers1 = getCellHeaders();
			MatrixCellHeader smallest;
			int smallestSize;
			Iterator<MatrixCellHeader> it = headers1.iterator();
			smallest = it.next();
			smallestSize = smallest.getIntersectingHeaders().size();
			while(it.hasNext()) {
				MatrixCellHeader header2 = it.next();
				int header2size = header2.getIntersectingHeaders().size();
				if ((header2size == smallestSize) && (Math.random() > 0.5))
					smallest = header2;
				else if (header2size < smallestSize) {
					smallest = header2;
					smallestSize = header2size;
				}
			}
			return smallest;
		}
		int getSize() {
			return getCellHeaders().size();
		}
		boolean isEmpty() {
			if(starter == null)
				return true;
			return false;
		}
		
		Set<MatrixCellHeader> getCellHeaders() {
			Set<MatrixCellHeader> headers = new HashSet<MatrixCellHeader>();
			if (isEmpty())
				return headers;
			MatrixCellHeader header1 = starter;
			while(true) {
				headers.add(header1);
				header1 = header1.next;
				if (header1 == starter)
					break;
			}
			return headers;
		}
		
		Optional<MatrixCellHeader> findHeader(int ID) {
			int startingID = starter.id;
			MatrixCellHeader header1 = starter;
			while(true) {
				if (header1.id == ID)
					return Optional.of(header1);
				else
					header1 = header1.next;
				if (header1.id == startingID)
					return Optional.absent();
			}
		}
		
		void removeCellHeader(MatrixCellHeader header) {
			if (header.isRemoved)
				return;
			String rowheader = "header";
			switch(orientation) {
			case Matrix.HORIZONTAL: rowheader = "row"; break;
			case Matrix.VERTICAL: rowheader = "column"; break;
			}
			if (PRINT_WORKING)
				System.out.println("Removing "+rowheader+" with ID: "+ header.id);
			header.removeCells();
			if (header == starter)
				starter = header.next;
			header.previous.next = header.next;
			header.next.previous = header.previous;
			if (header == starter) {
				starter = null;
				if (PRINT_WORKING)
					System.out.println("Detected there are no "+ rowheader+ "s left");
			}
			header.isRemoved = true;
		}
		
		void restoreCellHeader(MatrixCellHeader header) {
			if(!header.isRemoved)
				return;
			if (header.orientation != orientation)
				return;
			String rowheader = "header";
			switch(orientation) {
			case Matrix.HORIZONTAL: rowheader = "row"; break;
			case Matrix.VERTICAL: rowheader = "column"; break;
			}
			if (PRINT_WORKING)
				System.out.println("Restoring "+rowheader+" with ID: "+ header.id);
			header.restoreCells();
			if (isEmpty()) {
				starter = header;
				header.isRemoved = false;
				return;
			}
			header.previous.next = header;
			header.next.previous = header;
			header.isRemoved = false;
		}
	}
}




