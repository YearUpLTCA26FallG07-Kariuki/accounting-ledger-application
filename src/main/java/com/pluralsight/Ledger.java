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

    // MEMORY BANK: Holds transactions while Studio Ledger is running.
    private final List<Transaction> transactions = new ArrayList<>();

    // Tracks whether transactions have already been loaded from the CSV.
    private boolean transactionsLoaded = false;

    // Defines where Studio Ledger stores its financial records.
    private final Path filePath =
            Path.of("src", "main", "resources", "transactions.csv");

    // Saves a new transaction and adds it to the memory bank.
    public void addTransaction(Transaction transaction) {

        try {
            // Load existing records before adding a new one.
            loadTransactions();

            // Save the transaction permanently in the CSV file.
            Files.writeString(
                    filePath,
                    transaction.toCsv() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            // Update the memory bank immediately.
            transactions.add(transaction);
            sortTransactions();

            System.out.println("Transaction successfully saved!");

        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Loads saved transactions into memory only once.
    private void loadTransactions() throws IOException {

        if (transactionsLoaded) {
            return;
        }

        if (Files.exists(filePath)) {

            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {

                String[] parts = line.split("\\|", -1);

                if (parts.length == 5) {
                    try {
                        String date = parts[0];
                        String time = parts[1];
                        String description = parts[2];
                        String vendor = parts[3];
                        double amount = Double.parseDouble(parts[4]);

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
                    System.out.println(
                            "Skipping incorrectly formatted record."
                    );
                }
            }
        }

        // Prevents the CSV file from being loaded repeatedly.
        transactionsLoaded = true;

        sortTransactions();
    }

    // Returns all transactions from the memory bank, newest first.
    public List<Transaction> getAllTransactions() {

        try {
            loadTransactions();

        } catch (IOException e) {
            System.out.println(
                    "Error reading transactions: " + e.getMessage()
            );
        }

        // Returns a copy so outside code cannot accidentally change the memory bank.
        return new ArrayList<>(transactions);
    }

    // Sorts the memory bank by date and time, newest first.
    private void sortTransactions() {

        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );
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