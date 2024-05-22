import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Book class to manage book information
class Book {
    private String id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowBook() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println("Book borrowed: " + title);
        } else {
            System.out.println("Book is already borrowed: " + title);
        }
    }

    public void returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println("Book returned: " + title);
        } else {
            System.out.println("Book was not borrowed: " + title);
        }
    }
}

// Member class to manage member information
class Member {
    private String id;
    private String name;
    private List<Book> borrowedBooks;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            book.borrowBook();
            borrowedBooks.add(book);
        } else {
            System.out.println("Book is already borrowed by someone else: " + book.getTitle());
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("This book was not borrowed by you: " + book.getTitle());
        }
    }
}

// Library class to manage library operations
class Library {
    private List<Book> books;
    private Map<String, Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void deleteBook(String bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
        System.out.println("Book deleted with ID: " + bookId);
    }

    public void updateBook(String bookId, String newTitle, String newAuthor) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book = new Book(bookId, newTitle, newAuthor); // Creating a new book object with updated details
                System.out.println("Book updated: " + newTitle);
                break;
            }
        }
    }

    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        System.out.println("Book not found: " + title);
        return null;
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
        System.out.println("Member added: " + member.getName());
    }

    public void deleteMember(String memberId) {
        members.remove(memberId);
        System.out.println("Member deleted with ID: " + memberId);
    }

    public void borrowBook(String memberId, String bookId) {
        Member member = members.get(memberId);
        if (member != null) {
            Book book = books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
            if (book != null) {
                member.borrowBook(book);
            } else {
                System.out.println("Book not found with ID: " + bookId);
            }
        } else {
            System.out.println("Member not found with ID: " + memberId);
        }
    }

    public void returnBook(String memberId, String bookId) {
        Member member = members.get(memberId);
        if (member != null) {
            Book book = books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
            if (book != null) {
                member.returnBook(book);
            } else {
                System.out.println("Book not found with ID: " + bookId);
            }
        } else {
            System.out.println("Member not found with ID: " + memberId);
        }
    }
}

// Main class to demonstrate the functionality
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Add books to the library
        Book book1 = new Book("B001", "who am i", "Ramana mahasri");
        Book book2 = new Book("B002", "I am that", "Sri nisargadatta Maharaj");
        library.addBook(book1);
        library.addBook(book2);

        // Add members to the library
        Member member1 = new Member("M001", "Apurva");
        Member member2 = new Member("M002", "Saanvika");
        library.addMember(member1);
        library.addMember(member2);

        // Borrow and return books
        library.borrowBook("M001", "B001");
        library.returnBook("M001", "B001");

        // Search for a book
        Book searchedBook = library.searchBook("1984");
        if (searchedBook != null) {
            System.out.println("Book found: " + searchedBook.getTitle() + " by " + searchedBook.getAuthor());
        }

        // Delete a book
        library.deleteBook("B002");

        // Update a book
        library.updateBook("B001", "Invest like a Billionaire", "Warren Buffet");
    }
}