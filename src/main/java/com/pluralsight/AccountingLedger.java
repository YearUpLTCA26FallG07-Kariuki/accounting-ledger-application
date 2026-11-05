package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Main class: starts Studio Ledger and connects all supporting classes.
public class AccountingLedger {

    // Formats transaction times using hours and minutes only.
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {

        // One Scanner is shared throughout the application.
        Scanner scanner = new Scanner(System.in);

        // Create the objects that handle transactions, the ledger, and reports.
        Ledger ledger = new Ledger();
        Reports reports = new Reports(ledger);

        String choice = "";

        // Keep displaying the main menu until the user chooses Y to exit.
        while (!choice.equalsIgnoreCase("Y")) {

            System.out.println("==============================");
            System.out.println("         STUDIO LEDGER");
            System.out.println("    Creative Work. Clear Books.");
            System.out.println("==============================");
            System.out.println();
            System.out.println("L) Log Income");
            System.out.println("A) Add Expense");
            System.out.println("D) Display Ledger");
            System.out.println("Y) Your Exit");
            System.out.println();
            System.out.print("Select an option: ");

            choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("L")) {
                addIncome(scanner, ledger);

            } else if (choice.equalsIgnoreCase("A")) {
                addExpense(scanner, ledger);

            } else if (choice.equalsIgnoreCase("D")) {
                displayLedgerMenu(scanner, ledger, reports);

            } else if (choice.equalsIgnoreCase("Y")) {
                System.out.println();
                System.out.println("Thank you for using Studio Ledger!");

            } else {
                System.out.println("Invalid option. Please try again.");
            }

            System.out.println();
        }

        // Close the Scanner when the application is finished.
        scanner.close();
    }

    // Collects income information and saves it as a positive transaction.
    private static void addIncome(Scanner scanner, Ledger ledger) {

        System.out.println();
        System.out.println("========== RECORD INCOME ==========");

        String date = getDate(scanner);
        String time = getTime(scanner);

        System.out.print("Income description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Client / Vendor: ");
        String vendor = scanner.nextLine().trim();

        double amount = getAmount(scanner);

        // Positive amounts represent money coming into the studio.
        Transaction transaction = new Transaction(
                date, time, description, vendor, amount
        );

        ledger.addTransaction(transaction);
    }

    // Collects expense information and saves it as a negative transaction.
    private static void addExpense(Scanner scanner, Ledger ledger) {

        System.out.println();
        System.out.println("========== RECORD EXPENSE ==========");

        String date = getDate(scanner);
        String time = getTime(scanner);

        System.out.print("Expense description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();

        double amount = getAmount(scanner);

        // Negative amounts represent money leaving the studio.
        Transaction transaction = new Transaction(
                date, time, description, vendor, -amount
        );

        ledger.addTransaction(transaction);
    }

    // Displays the ledger menu and its available filters and reports.
    private static void displayLedgerMenu(
            Scanner scanner, Ledger ledger, Reports reports) {

        String choice = "";

        // Continue showing the ledger menu until H returns to the home menu.
        while (!choice.equalsIgnoreCase("H")) {

            System.out.println();
            System.out.println("========== STUDIO LEDGER ==========");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits / Income");
            System.out.println("P) Payments / Expenses");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.println();
            System.out.print("Select an option: ");

            choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("A")) {
                ledger.displayTransactions(ledger.getAllTransactions());

            } else if (choice.equalsIgnoreCase("D")) {
                ledger.displayTransactions(ledger.getDeposits());

            } else if (choice.equalsIgnoreCase("P")) {
                ledger.displayTransactions(ledger.getPayments());

            } else if (choice.equalsIgnoreCase("R")) {
                displayReportsMenu(scanner, reports);

            } else if (choice.equalsIgnoreCase("H")) {
                System.out.println("Returning to Studio Ledger home...");

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Displays the five required reports and vendor search.
    private static void displayReportsMenu(
            Scanner scanner, Reports reports) {

        String choice = "";

        // Keep the reports menu open until the user selects 0 to go back.
        while (!choice.equals("0")) {

            System.out.println();
            System.out.println("========== FINANCIAL REPORTS ==========");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger");
            System.out.println();
            System.out.print("Select a report: ");

            choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                reports.monthToDate();

            } else if (choice.equals("2")) {
                reports.previousMonth();

            } else if (choice.equals("3")) {
                reports.yearToDate();

            } else if (choice.equals("4")) {
                reports.previousYear();

            } else if (choice.equals("5")) {
                System.out.print("Enter vendor name: ");
                String vendorName = scanner.nextLine().trim();
                reports.searchByVendor(vendorName);

            } else if (choice.equals("0")) {
                System.out.println("Returning to ledger...");

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Requests a date and checks that it follows YYYY-MM-DD format.
    private static String getDate(Scanner scanner) {

        while (true) {
            System.out.print("Date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();

            try {
                LocalDate.parse(date);
                return date;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please use YYYY-MM-DD.");
            }
        }
    }

    // Requests a time and checks that it follows the HH:mm format.
    private static String getTime(Scanner scanner) {

        while (true) {
            System.out.print("Time (HH:mm, 24-hour format): ");
            String time = scanner.nextLine().trim();

            try {
                LocalTime.parse(time, TIME_FORMAT);
                return time;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Please use HH:mm, such as 14:30.");
            }
        }
    }

    // Makes sure the amount entered is a valid positive number.
    private static double getAmount(Scanner scanner) {

        while (true) {
            System.out.print("Amount: $");
            String input = scanner.nextLine().trim();

            try {
                double amount = Double.parseDouble(input);

                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }

                System.out.println("Amount must be greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric amount.");
            }
        }
    }
}