
public class StaticBlock {
	static int pack(short yLength, short xLength, short y, short x) {
//		return (((((yLength << 8) | xLength) << 8) | y) << 8) |  x;
		int retVal = yLength;
		retVal = (retVal << 8) | xLength;
		retVal = (retVal << 8) | y;
		retVal = (retVal << 8) | x;
		return retVal;
	}
	static short getHeight(int packedBlock) {
		byte height = (byte) (packedBlock >>> 24);
		return asShort(height);
	}
	static short getWidth(int packedBlock) {
		byte width = (byte) (packedBlock >>> 16);
		return asShort(width);
	}
	static short getY(int packedBlock) {
		byte y = (byte) (packedBlock >>> 8);
		return asShort(y);
	}
	static short getX(int packedBlock) {
		byte y = (byte) packedBlock;
		return asShort(y);
	}
	static short asShort(byte x) {
		return (x < 0 ? (short)(x + 256) : x);
	}
}
