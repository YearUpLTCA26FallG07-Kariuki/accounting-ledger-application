Studio Ledger
Creative Work. Clear Books.

So, What's Studio Ledger?
Studio Ledger is a little java project I created with creatives, freelancers, and independent work in mind. 
If you think about it: you get paid for a performance, work with a client, purchase equipment, or pay for another subscription. There always transactions coming in and going out, and sometimes keeping track of everything can get a little messy.

I wanted to create something that could make managing those transactions a little easier. Something simple, organized, and easy to navigate without making finaces feel like a whole other job.

And that's where Studio Ledger comes in!

What Can You Do With It?
A few things Studio Ledger can help you with:

- Log Income: Keep track of money coming in from clients, projects, performances, and other work.
- Add Expenses: Record purchases, subscriptions, services and other business expenses.
- View Your Ledger: See your transaction history, with the newest transactions showing first.
- Separate Income & Expenses: Easily view deposits and payments separately.
- Check Your Reports: Get a look at your monthly and yearly financial activity.
- Search by Vendor: Looking for a particular client or business? Search for it by name.
- Save Your Transactions: Your records are saved to a CSV file, so your information is still there when you come back.

                                          
                                          Behind the Scenes

Studio Ledger is built with Java, and I separated the project into four classes so each part has its own job.

Here's a little breakdown:

                                  "AccountingLedger.java"

This is where everything starts! It handles the menus, collects user input, and connects the different parts of the application.

                                        "Ledger.java"

Think of this as the memory bank. It keeps track of transactions, loads saved records, organizes them, and separates income from expenses.

                                      "Transaction.java"

Every transaction needs its own details. This class holds the date, time, description, vendor, and amount.

                                        "Reports.java"

This is where the numbers come together. It handles monthly and yearly summaries, along with searches by vendor.

                    
                    What I Used

A few of the tools that helped bring Studio Ledger together:

~ Java

~ IntelliJ IDEA

~ Maven

~ GitHub

~ CSV File Storage


                Want to Give It a Try?

Here's how to get started:

1. Clone or download the repository.

2. Open the project in IntelliJ IDEA.

3. Let Maven load the project.

4. Open AccountingLedger.java.

5. Run the application using its main() method.


                Where Does the Information Go?

Studio Ledger uses a CSV file to save transactions.

              
              You'll find it here:

src/main/resources/transactions.csv

               Each transaction is stored with five pieces of information:

date|time|description|vendor|amount

For example:

2026-11-05|14:30|Studio microphone|Sweetwater|-129.99


Positive amounts represent income, while negative amounts represent expenses.

The application also loads those saved transactions into memory while it's running, so it can work with the records without having to reread the file every single time.


                            Project Structure

accounting-ledger-application/
│
├── src/
│   └── main/
│       ├── java/com/pluralsight/
│       │   ├── AccountingLedger.java
│       │   ├── Ledger.java
│       │   ├── Reports.java
│       │   └── Transaction.java
│       │
│       └── resources/
│           └── transactions.csv
│
├── pom.xml
├── .gitignore
└── README.md


                             A Little Note From Me

This started as a Java capstone project, but I didn't want to just build something to check off an assignment.

I wanted to take what I was learning and turn it into something that felt like mine. Something practical, something creative, and something I could actually imagine using outside of the classroom.

Throughout this project, I've been learning how to work with classes, objects, methods, collections, file handling, input validation, and more. I've also learned that building something doesn't mean getting everything perfect on the first try. Sometimes it's about figuring out what works, making changes, and learning along the way.

                       Thanks for stopping by Studio Ledger!

Studio Ledger — Creative Work. Clear Books.
