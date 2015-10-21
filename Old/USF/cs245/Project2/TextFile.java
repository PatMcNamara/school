import java.io.*;
import java.util.Iterator;
class TextFile implements Iterable<Character> {
    
    public TextFile(String filename, char readOrWrite) {
	try {
	    if (readOrWrite == 'w' || readOrWrite == 'W') {
		inputFile = false;
		file = new RandomAccessFile(filename, "rw");
	    } else if (readOrWrite == 'r' || readOrWrite == 'R') {  
		inputFile = true;
		file = new RandomAccessFile(filename, "r");
	    }
	} catch(Exception e) {
	    System.out.println(e.getMessage());
	    System.exit(0);
	}
	position = 0;

    }

    public boolean EndOfFile() {
	Assert.notFalse(inputFile,"EndOfFile only relevant for input files");
	try {
	    return position == file.length();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    System.exit(0);
	}
	return true;
    }

    
    public char readChar() {
	char returnchar = 0;
	try {
	    Assert.notFalse(inputFile,"Can only read from input files!");
	    Assert.notFalse(!EndOfFile(),"Read past end of file!");
	    position++;
	    returnchar = (char) file.read();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    System.exit(0);
	}

	return returnchar;
    }

    public void writeChar(char c) {
	try {
	    Assert.notFalse(!inputFile,"Can only write to output files!");
	    file.write((byte) c);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    System.exit(0);
	}
    }

    public void close() {
	try {
	    file.close();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    System.exit(0);
	}

    }

    public void rewind() {
	try {
	    Assert.notFalse(inputFile,"Can only rewind input files!");
	    file.seek(0);
	    position = 0;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    System.exit(0);
	}
    }

    private boolean inputFile;
    private RandomAccessFile file;
    private long position;
    
    
    /*  All of the above code is as provided, the following code is new  */
    public Iterator<Character> iterator() {
    	return new TextIterator();
    }
    class TextIterator implements Iterator<Character> {
    	public TextIterator() {
    		rewind();
    	}
    	public boolean hasNext() {
    		return !EndOfFile();
    	}
    	public Character next() {
    		return readChar();
    	}
    	public void remove() {
    		throw new UnsupportedOperationException();
    	}
    }
}