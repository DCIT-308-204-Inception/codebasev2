public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String shelfLocation; // e.g. "Balme Library - Section C"
    private boolean isAvailable;
    private int urgencyLevel; // 1=low, 2=medium, 3=high (for reservations)

    public Book(String bookId, String title, String author,
            String category, String shelfLocation) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.shelfLocation = shelfLocation;
        this.isAvailable = true;
        this.urgencyLevel = 1;
    }

    // Getters and settes
    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setUrgencyLevel(int level) {
        this.urgencyLevel = level;
    }

    @Override
    public String toString() {
        return "[" + bookId +
                "] " + title + " by " + author
                + " | " + shelfLocation
                + " | " + (isAvailable ? "Available" : "Checked Out");
    }
}
