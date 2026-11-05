package com.pluralsight;

// Represents one financial transaction in Studio Ledger.
public class Transaction {

    // These fields store the five required transaction details.
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    // Constructor: creates a transaction using the information provided.
    public Transaction(String date, String time, String description,
                       String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getter methods allow other classes to access transaction details.
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    // Converts the transaction into the required CSV storage format.
    // Each field is separated by a vertical bar.
    public String toCsv() {
        return date + "|" + time + "|" + description + "|"
                + vendor + "|" + amount;
    }
}