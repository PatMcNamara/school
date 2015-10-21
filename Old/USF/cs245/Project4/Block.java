
public class Block {
	short width, height, row, col;
	
	Block(int value) {
		this.width = StaticBlock.getWidth(value);
		this.height = StaticBlock.getHeight(value);
		this.row = StaticBlock.getY(value);
		this.col = StaticBlock.getX(value);
	}
	int pack() {
		return StaticBlock.pack(height, width, row, col);
	}
	public String getPosition(){
		return row + " " + col;
	}
}
