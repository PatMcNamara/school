import java.util.Arrays;
import java.util.Scanner;

//This is a tray where each block is is a single element
public class BlockTray {
	int[] tray;
	BlockTray(Scanner s, int traySize){
		tray = new int[traySize];
		int i = 0;
		while(s.hasNextInt())
			tray[i++] = StaticBlock.pack(s.nextShort(), s.nextShort(), s.nextShort(), s.nextShort());
		s.close();

		int[] tmp = new int[i];
		for(int j = 0; j < i; j++)
			tmp[j] = tray[j];
		tray = tmp;
	}
	BlockTray(int[] tray){
		this.tray = tray;
	}
	public BlockTray clone() {
		return new BlockTray(tray.clone());
	}
	public int hashCode(){
		return Arrays.hashCode(tray);
	}
	public boolean equals(Object o){
		BlockTray list = (BlockTray) o;
		return Arrays.equals(tray, list.tray);
	}
}
