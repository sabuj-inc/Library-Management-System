package librarymanagementsystem;
import java.util.*;

class Book {
    String name;
    String author;
    double price;

    public Book(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book Name: " + name + ", Author: " + author + ", Price: " + price;
    }
}

public class LibraryManagementSystem {
    private static List<Book> books = new ArrayList<>();
    private static List<Book> soldBooks = new ArrayList<>();
    private static double totalSellPrice = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Remove Book");
            System.out.println("4. View All Books");
            System.out.println("5. Sort Books by Price");
            System.out.println("6. Sell Book");
            System.out.println("7. View Sold Books and Total Sales");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    searchBook(scanner);
                    break;
                case 3:
                    removeBook(scanner);
                    break;
                case 4:
                    viewAllBooks();
                    break;
                case 5:
                    sortBooksByPrice();
                    viewAllBooks(); // Display sorted books
                    break;
                case 6:
                    sellBook(scanner);
                    break;
                case 7:
                    viewSoldBooksAndTotalSales();
                    break;
                case 8:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter Book Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        System.out.print("Enter Book Price: ");
        double price = scanner.nextDouble();
        books.add(new Book(name, author, price));
        System.out.println("Book added successfully.");
    }

    private static void searchBook(Scanner scanner) {
        System.out.print("Enter Book Name to Search: ");
        String name = scanner.nextLine();
        books.sort(Comparator.comparing(book -> book.name)); // Ensure the list is sorted
        int index = binarySearch(books, name);
        if (index != -1) {
            System.out.println("Book Found: " + books.get(index));
        } else {
            System.out.println("Book not found.");
        }
    }

    private static int binarySearch(List<Book> books, String name) {
        int low = 0, high = books.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = books.get(mid).name.compareToIgnoreCase(name);
            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private static void removeBook(Scanner scanner) {
        System.out.print("Enter Book Name to Remove: ");
        String name = scanner.nextLine();
        Iterator<Book> iterator = books.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            if (iterator.next().name.equalsIgnoreCase(name)) {
                iterator.remove();
                removed = true;
                System.out.println("Book removed successfully.");
                break;
            }
        }
        if (!removed) {
            System.out.println("Book not found.");
        }
    }

    private static void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("\nBooks in Library:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void sortBooksByPrice() {
        quickSort(books, 0, books.size() - 1);
        System.out.println("Books sorted by price successfully.");
    }

    private static void quickSort(List<Book> books, int low, int high) {
        if (low < high) {
            int pi = partition(books, low, high);
            quickSort(books, low, pi - 1);
            quickSort(books, pi + 1, high);
        }
    }

    private static int partition(List<Book> books, int low, int high) {
        double pivot = books.get(high).price;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (books.get(j).price <= pivot) {
                i++;
                Collections.swap(books, i, j);
            }
        }
        Collections.swap(books, i + 1, high);
        return i + 1;
    }

    private static void sellBook(Scanner scanner) {
        System.out.print("Enter Book Name to Sell: ");
        String name = scanner.nextLine();
        Iterator<Book> iterator = books.iterator();
        boolean sold = false;
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.name.equalsIgnoreCase(name)) {
                soldBooks.add(book);
                totalSellPrice += book.price;
                iterator.remove();
                System.out.println("Book sold successfully.");
                sold = true;
                break;
            }
        }
        if (!sold) {
            System.out.println("Book not found.");
        }
    }

    private static void viewSoldBooksAndTotalSales() {
        if (soldBooks.isEmpty()) {
            System.out.println("No books have been sold yet.");
        } else {
            System.out.println("\nSold Books:");
            for (Book book : soldBooks) {
                System.out.println(book);
            }
            System.out.println("\nTotal Sales: " + totalSellPrice);
        }
    }
}

