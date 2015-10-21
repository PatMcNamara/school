import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Solver {
	int count = 0;
	BlockTray blocksInTray;
	BlockTray solution;
	TrayArray tray;
	HashTable table = new HashTable();
	StringBuffer path = new StringBuffer();
	
	Solver(Scanner initialConfig, Scanner solutionFile){
		int numRows = initialConfig.nextInt();
		int numCols = initialConfig.nextInt();
		initialConfig.nextLine();
		blocksInTray = new BlockTray(initialConfig, numRows * numCols);
		tray = new TrayArray(numRows, numCols);
		tray.buildTray(blocksInTray.tray);
		this.solution = new BlockTray(solutionFile, numRows * numCols);
	}
	
	boolean solve() {
		if(isSolution())
			return true;
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		for(int i: tray.empty.clone()){//TODO
			int blankIndex = StaticBlock.getY(i)*tray.numCols + StaticBlock.getX(i);
			boolean top = false, bottom = false, left = false, right = false;
			
			// check if blank is on top, bottom, left or right edge of tray
			if(blankIndex % tray.numCols == 0)
				left = true;
			if(blankIndex - tray.numCols < 0)
				top = true;
			if(blankIndex + tray.numCols >= tray.tray.length)
				bottom = true;
			if((blankIndex+1) % tray.numCols == 0)
				right = true;
			
			int blockIndex, value;
			
			// LOOK RIGHT
			blockIndex = blankIndex + 1;
			if(!right && (value = tray.tray[blockIndex]) != -1){
				int block = blocksInTray.tray[value];
				int count = 0;
				while(blockIndex < tray.tray.length && 
						tray.tray[blockIndex] == value && 
						tray.tray[blockIndex-1] == -1) {
					count++;
					blockIndex += tray.numCols;
				}
				if(count >= StaticBlock.getHeight(block)){
					Arrays.sort(blocksInTray.tray);
					assert Arrays.binarySearch(blocksInTray.tray, block) >= 0;
					Edge e = new Edge(blocksInTray, 
							Arrays.binarySearch(blocksInTray.tray, block), 
							Edge.BlankSpace.LEFT);
					e.moveBlock(true);
					tray.buildTray(blocksInTray.tray);
					if(table.put(blocksInTray.clone()))
						edgeList.add(e);
					e.moveBack(true);
					tray.buildTray(blocksInTray.tray);
				}
			}
			
			// LOOK DOWN
			blockIndex = blankIndex + tray.numCols;
			if(!bottom && (value = tray.tray[blockIndex]) != -1){
				int block = blocksInTray.tray[value];
				int count = 0;//TODO
				while(blockIndex < tray.tray.length && 
						tray.tray[blockIndex] == value && 
						tray.tray[blockIndex-tray.numCols] == -1){
					count++;
					blockIndex += 1;
				}
				if(count >= StaticBlock.getWidth(block)){
					Arrays.sort(blocksInTray.tray);
					assert Arrays.binarySearch(blocksInTray.tray, block) >= 0;
					Edge e = new Edge(blocksInTray, 
							Arrays.binarySearch(blocksInTray.tray, block), 
							Edge.BlankSpace.ABOVE);
					e.moveBlock(true);
					tray.buildTray(blocksInTray.tray);
					if(table.put(blocksInTray.clone()))
						edgeList.add(e);
					e.moveBack(true);
					tray.buildTray(blocksInTray.tray);
				}
			}
			
			// LOOK LEFT
			blockIndex = blankIndex - 1;
			if(!left && (value = tray.tray[blockIndex]) != -1){ 
				int block = blocksInTray.tray[value];
				int count = 0;
				while(blockIndex < tray.tray.length && 
						tray.tray[blockIndex] == value && 
						tray.tray[blockIndex+1] == -1) {
					count++;
					blockIndex += tray.numCols;
				}
				if(count >= StaticBlock.getHeight(block)) {
					Arrays.sort(blocksInTray.tray);
					assert Arrays.binarySearch(blocksInTray.tray, block) >= 0;
					Edge e = new Edge(blocksInTray, 
							Arrays.binarySearch(blocksInTray.tray, block), 
							Edge.BlankSpace.RIGHT);
					e.moveBlock(true);
					tray.buildTray(blocksInTray.tray);
					if(table.put(blocksInTray.clone()))
						edgeList.add(e);
					e.moveBack(true);
					tray.buildTray(blocksInTray.tray);
				}
			}
			
			// LOOK UP
			blockIndex = blankIndex - tray.numCols;
			if(!top && (value = tray.tray[blockIndex]) != -1){
				int block = blocksInTray.tray[value];
				int count = 0;
				while(count < StaticBlock.getWidth(block) && 
						tray.tray[blockIndex] == value && 
						tray.tray[blockIndex+tray.numCols] == -1) {
					count++;
					blockIndex += 1;
				}
				if(count >= StaticBlock.getWidth(block)){
					Arrays.sort(blocksInTray.tray);
					assert Arrays.binarySearch(blocksInTray.tray, block) >= 0;
					Edge e = new Edge(blocksInTray, 
							Arrays.binarySearch(blocksInTray.tray, block), 
							Edge.BlankSpace.BELOW);
					e.moveBlock(true);
					tray.buildTray(blocksInTray.tray);
					if(table.put(blocksInTray.clone()))
						edgeList.add(e);
					e.moveBack(true);
					tray.buildTray(blocksInTray.tray);
				}
			}
		}

		for(Edge e: edgeList){
			e.moveBlock(true);
			tray.buildTray(blocksInTray.tray);
			if(solve()){
				path.insert(0, e.printSolution());
				tray.buildTray(blocksInTray.tray);
				return true;
			}
			e.moveBack(true);
			tray.buildTray(blocksInTray.tray);
		}
		return false;
	}
	
	boolean checkEdges(Edge e) {
		e.moveBlock(true);
		tray.buildTray(blocksInTray.tray); 
		if(!table.containsKey(blocksInTray)){
			table.put(blocksInTray.clone());
			if(solve()){
				path.insert(0, e.printSolution());
				tray.buildTray(blocksInTray.tray);
				return true;
			}
		}
		e.moveBack(true);
		tray.buildTray(blocksInTray.tray);
		return false;
	}
	
	boolean checkEdges(ArrayList<Edge> edgeList) {
		for(Edge e: edgeList){
			e.moveBlock(true);
			tray.buildTray(blocksInTray.tray);
			if(solve()){
				path.insert(0, e.printSolution());
				tray.buildTray(blocksInTray.tray);
				return true;
			}
			e.moveBack(true);
			tray.buildTray(blocksInTray.tray);
		}
		return false;
	}

	boolean isSolution(){
		boolean retVal = true;
		for(int i: solution.tray)
			retVal &= Arrays.binarySearch(blocksInTray.tray, i) >= 0;
		return retVal;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner initialConfig, solution;
		if(args.length != 2)
			throw new IllegalArgumentException();
		try {
			initialConfig = new Scanner(new File(args[0]));
			solution = new Scanner(new File(args[1]));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Invalid input file");
		}
		Solver solver = new Solver(initialConfig, solution);
		solver.table.put(solver.blocksInTray.clone());
		if(solver.solve())
			System.out.println(solver.path.toString());
		else
			System.out.println("No solution");
	}
}
