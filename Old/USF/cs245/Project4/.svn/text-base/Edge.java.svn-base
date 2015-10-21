import java.util.Arrays;

public class Edge {
	BlockTray tray;
	int index;
	Block block;
	boolean moved = false;
	enum BlankSpace {ABOVE, LEFT, RIGHT, BELOW}
	BlankSpace blank;
	
	Edge(BlockTray tray, int index, int blankRow, int blankCol){
		this.tray = tray;
		this.index = index;
		block = new Block(tray.tray[index]);
		if(block.col > blankCol)
			blank = BlankSpace.LEFT;
		else if(block.col + block.row - 1 < blankCol)
			blank = BlankSpace.RIGHT;
		else if(block.row > blankRow)
			blank = BlankSpace.ABOVE;
		else
			blank = BlankSpace.BELOW;
	}
	Edge(BlockTray tray, int index, BlankSpace location){
		this.index = index;
		block = new Block(tray.tray[index]);
		this.tray = tray;
		blank = location;
	}
	void move(boolean pack) {
		if(!moved){
			moveBlock(pack);
		}else{
			moveBack(pack);
		}
	}
	void moveBlock(boolean pack){
		if(blank == BlankSpace.ABOVE)
			block.row--;
		else if(blank == BlankSpace.BELOW)
			block.row++;
		else if(blank == BlankSpace.LEFT)
			block.col--;
		else
			block.col++;
		if(pack)
			tray.tray[index] = block.pack();
		moved = true;
		assert block.height > 0 && block.width > 0;
	}
	void moveBack(boolean pack){
		int oldValue = block.pack();
		if(blank == BlankSpace.ABOVE)
			block.row++;
		else if(blank == BlankSpace.BELOW)
			block.row--;
		else if(blank == BlankSpace.LEFT)
			block.col++;
		else
			block.col--;
		if(pack)
			tray.tray[Arrays.binarySearch(tray.tray, oldValue)] = block.pack();
		moved = false;
		assert block.height > 0 && block.width > 0;
	}
	String printSolution(){
		String oldPosition = block.getPosition();
		moveBack(true);
		return block.getPosition() + " " + oldPosition + "\n";
	}
	void printMove() {
		if(moved){
			moveBack(false);
			System.out.print(block.getPosition() + " ");
			moveBlock(false);
			System.out.println(block.getPosition());
		}else{
			moveBlock(false);
			System.out.print(block.getPosition() + " ");
			moveBack(false);
			System.out.println(block.getPosition());
		}
	}
	public String toString() {
		String tmp = "";
		if(moved){
			moveBack(false);
			tmp += block.getPosition() + " ";
			moveBlock(false);
			tmp += block.getPosition();
		}else{
			moveBlock(false);
			tmp += block.getPosition() + " ";
			moveBack(false);
			tmp += block.getPosition();
		}
		return tmp + " " + blank;
	}
}
