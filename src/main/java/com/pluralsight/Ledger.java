package com.pluralsight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Handles saving, retrieving, sorting, and displaying transactions.
public class Ledger {

   // public ArrayList<Transaction> transactions = new ArrayList<>();

    // Defines where Studio Ledger stores its financial records.
    private final Path filePath =
            Path.of("src", "main", "resources", "transactions.csv");

    // Saves a new transaction without deleting existing records.
    public void addTransaction(Transaction transaction) {

        try {
            Files.writeString(
                    filePath,
                    transaction.toCsv() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            System.out.println("Transaction successfully saved!");

        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Reads all previously saved transactions from the CSV file.
    public List<Transaction> getAllTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        try {
            // If the file does not exist yet, return an empty list.
            if (!Files.exists(filePath)) {
                return transactions;
            }

            List<String> lines = Files.readAllLines(filePath);

            // Process each saved transaction one line at a time.
            for (String line : lines) {

                // Split the line into its five transaction fields.
                String[] parts = line.split("\\|", -1);

                if (parts.length == 5) {
                    try {
                        String date = parts[0];
                        String time = parts[1];
                        String description = parts[2];
                        String vendor = parts[3];
                        double amount = Double.parseDouble(parts[4]);

                        // Reconstruct the transaction as a Java object.
                        Transaction transaction = new Transaction(
                                date, time, description, vendor, amount
                        );

                        transactions.add(transaction);

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Skipping invalid transaction: " + line
                        );
                    }
                } else {
                    System.out.println("Skipping incorrectly formatted record.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        // Sort by date and time, placing the newest transaction first.
        // HH:mm uses a consistent 24-hour format, so alphabetical sorting works.
        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );

        return transactions;
    }

    // Displays a collection of transactions in a readable format.
    public void displayTransactions(List<Transaction> transactions) {

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println();
        System.out.printf(
                "%-12s %-7s %-30s %-22s %12s%n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT"
        );

        System.out.println(
                "--------------------------------------------------------------------------------------"
        );

        for (Transaction transaction : transactions) {

            System.out.printf(
                    "%-12s %-7s %-30s %-22s $%10.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount()
            );
        }
    }

    // Returns transactions with positive amounts (income).
    public List<Transaction> getDeposits() {

        List<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : getAllTransactions()) {

            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    // Returns transactions with negative amounts (expenses/payments).
    public List<Transaction> getPayments() {

        List<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : getAllTransactions()) {

            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }

        return payments;
    }
}