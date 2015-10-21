package book;

public class Book {
	
	private String title;
	private String author;
	private String publisher;
	private int copydate;

	public Book(String title, String author, String 
						publisher, int copydate) {
			
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.copydate = copydate;
			
	}
	
	
	
	public String toString() {
		
		return ("The book " + title + ", writen by " + author + ",\n" +
						"was published by " + publisher +" in " +
						copydate);
		
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCopydate() {
		return copydate;
	}

	public void setCopydate(int copydate) {
		this.copydate = copydate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
