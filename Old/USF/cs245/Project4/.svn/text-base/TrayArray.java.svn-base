import java.util.Arrays;

public class TrayArray {
	short[] tray;
	short numCols;
	short[] empty;
	
	TrayArray(int numRows, int numCols){
		this.numCols = (short) numCols;
		tray = new short[numRows * numCols];
		Arrays.fill(tray, (short) -1);
	}
	void buildTray(int[] blocksTray){
		Arrays.sort(blocksTray);
		Arrays.fill(tray, (short) -1);
		// look at each block in tray
		for(short i = 0; i < blocksTray.length; i++){
			int block = blocksTray[i];
			// set array values
			for(int j = 0; j < StaticBlock.getWidth(block); j++)
				for(int k = 0; k < StaticBlock.getHeight(block); k++){
					int index = (j + StaticBlock.getX(block)) + 
							    ((k + StaticBlock.getY(block)) * numCols);
					assert tray[index] == -1;
					tray[index] = i;
				}
		}
		findEmptySpaces();
	}
	
	void findEmptySpaces(){
		int count = 0;
		if(empty == null)
			empty = new short[tray.length];
		for(int i = 0; i < tray.length / numCols; i++){ // Go through each row
			for(int j = 0; j < numCols; j++){ // Go through each col in this row
				if(tray[(i*numCols) + j] == -1)
					empty[count++] = (short) ((i << 8) | j);
			}
		}
		
		short[] tmp = new short[count];
		for(int i = 0; i < count; i++)
			tmp[i] = empty[i];
		empty = tmp;
	}
}
