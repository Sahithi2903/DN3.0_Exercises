import java.util.Arrays;
import java.util.Comparator;
class Book {
    private String bookId;
    private String title;
    private String author;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}



class Library {
    private Book[] books;
    private int size;

    public Library(int capacity) {
        books = new Book[capacity];
        size = 0;
    }

    public void addBook(Book book) {
        if (size < books.length) {
            books[size++] = book;
        } else {
            System.out.println("Library is full. Cannot add more books.");
        }
    }

    public Book linearSearch(String title) {
        for (int i = 0; i < size; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return books[i];
            }
        }
        return null;
    }

    public Book binarySearch(String title) {
        int left = 0, right = size - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = books[mid].getTitle().compareToIgnoreCase(title);
            if (cmp == 0) {
                return books[mid];
            }
            if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public void sortBooksByTitle() {
        Arrays.sort(books, 0, size, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Library library = new Library(10);

        Book book1 = new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald");
        Book book2 = new Book("B002", "To Kill a Mockingbird", "Harper Lee");
        Book book3 = new Book("B003", "1984", "George Orwell");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        System.out.println("Library books (unsorted):");
        System.out.println(library.linearSearch("The Great Gatsby"));
        System.out.println(library.linearSearch("To Kill a Mockingbird"));
        System.out.println(library.linearSearch("1984"));

        library.sortBooksByTitle();

        System.out.println("\nLibrary books (sorted):");
        System.out.println(library.binarySearch("The Great Gatsby"));
        System.out.println(library.binarySearch("To Kill a Mockingbird"));
        System.out.println(library.binarySearch("1984"));
    }
}
