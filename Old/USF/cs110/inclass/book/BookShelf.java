package book;

public class BookShelf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Book book1 = new Book("The First Book",	"The first author",
				"The first publisher", 1994);
		Book book2 = new Book("The second book",
				"The Second author", "The second publisher", 1908);
		System.out.println(book1.toString());
		System.out.println(book2.toString());
		book1.setAuthor("A differant author");
		System.out.println(book1.toString());
		book2.setPublisher("a new publisher");
		book2.setCopydate(1956);
		System.out.println(book2.toString());

	}

}
