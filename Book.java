package com.example.demo20;

public class Book {
    private int inventoryNumber;
    private String author;
    private String title;
    private int publicationYear;

    public Book(int inventoryNumber, String author, String title, int publicationYear) {
        this.inventoryNumber = inventoryNumber;
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public int getInventoryNumber() { return inventoryNumber; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public int getPublicationYear() { return publicationYear; }
}
