package com.pluralsight;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

// Generates financial reports using transactions stored in the Ledger.
public class Reports {

    // Connects this class to the Ledger so it can retrieve transactions.
    private final Ledger ledger;

    // Constructor: receives the Ledger object when Reports is created.
    public Reports(Ledger ledger) {
        this.ledger = ledger;

    }

    // Report 1: Finds transactions from the beginning of this month through today.
    public void monthToDate() {

        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : ledger.getAllTransactions()) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate());

                if (YearMonth.from(date).equals(currentMonth)
                        && !date.isAfter(today)) {
                    results.add(transaction);
                }

            } catch (DateTimeParseException e) {
                System.out.println("Skipping transaction with invalid date.");
            }
        }

        displayReport("MONTH TO DATE", results);
    }

    // Report 2: Finds transactions from the entire previous month.
    public void previousMonth() {

        YearMonth previousMonth = YearMonth.now().minusMonths(1);

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : ledger.getAllTransactions()) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate());

                if (YearMonth.from(date).equals(previousMonth)) {
                    results.add(transaction);
                }

            } catch (DateTimeParseException e) {
                System.out.println("Skipping transaction with invalid date.");
            }
        }

        displayReport("PREVIOUS MONTH", results);
    }

    // Report 3: Finds transactions from January 1 through today this year.
    public void yearToDate() {

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : ledger.getAllTransactions()) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate());

                if (date.getYear() == currentYear
                        && !date.isAfter(today)) {
                    results.add(transaction);
                }

            } catch (DateTimeParseException e) {
                System.out.println("Skipping transaction with invalid date.");
            }
        }

        displayReport("YEAR TO DATE", results);
    }

    // Report 4: Finds transactions from the entire previous calendar year.
    public void previousYear() {

        int previousYear = LocalDate.now().getYear() - 1;

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : ledger.getAllTransactions()) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate());

                if (date.getYear() == previousYear) {
                    results.add(transaction);
                }

            } catch (DateTimeParseException e) {
                System.out.println("Skipping transaction with invalid date.");
            }
        }

        displayReport("PREVIOUS YEAR", results);
    }

    // Report 5: Finds transactions associated with a specific vendor.
    public void searchByVendor(String vendorName) {

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : ledger.getAllTransactions()) {

            if (transaction.getVendor().trim()
                    .equalsIgnoreCase(vendorName.trim())) {
                results.add(transaction);
            }
        }

        displayReport("VENDOR SEARCH: " + vendorName, results);
    }

    // Displays matching transactions and calculates their combined net amount.
    private void displayReport(String title, List<Transaction> results) {

        System.out.println();
        System.out.println("========== " + title + " ==========");

        ledger.displayTransactions(results);

        double total = 0;

        for (Transaction transaction : results) {
            total += transaction.getAmount();
        }

        System.out.println("-----------------------------------");
        System.out.printf("Net Total: $%.2f%n", total);
    }
}