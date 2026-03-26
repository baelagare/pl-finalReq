# POScarthos (POS & Inventory Management System)

A simple Java Swing **Point-of-Sale (POS)** and **Inventory Management System** created as a course requirement.

The app uses **MySQL** for persistence and is structured as a plain **Eclipse Java project** (no Maven/Gradle).

## Features

- Login screen (users stored in MySQL)
- Main menu navigation
- POS screen (search product, add to cart, print receipt)
- Inventory management (CRUD for products and suppliers, category filtering)

## Tech Stack

- Java (Swing / AWT)
- MySQL
- JDBC (MySQL Connector/J)

## Project Structure

- `src/main/Main.java` — main entry point (opens the Login UI)
- `src/system_pack/*` — Swing UIs (`LoginUI`, `MainMenuUI`, `POS_UI`, `InventoryUI`, `SettingsUI`)
- `src/controllers/*` — database operations for users/products/suppliers
- `src/db/DatabaseConnection.java` — JDBC connection
- `posandinventory_db.sql` — schema + sample data
- `logo/*` — images used by the UI

## Requirements

### 1) Java

This repository contains `src/module-info.java` and requires:
- `java.desktop`
- `java.sql`

### 2) MySQL

- MySQL running locally
- A database named `posandinventory_db`

### 3) MySQL Connector/J

This project depends on the MySQL JDBC driver (`com.mysql.cj.jdbc.Driver`).

> Note: The current `.classpath` references a local Windows path for the connector jar, so you will likely need to re-add the jar on your machine.

## Database Setup

1. Start your MySQL server.
2. Create the database + tables using the provided SQL dump (`posandinventory_db.sql`).

Example (inside the MySQL client):

```sql
SOURCE posandinventory_db.sql;
```

This creates and seeds:
- `users`
- `products`
- `suppliers`
- `sales`

### Default DB Connection (hardcoded)

The application currently connects using the settings in `src/db/DatabaseConnection.java`:

- URL: `jdbc:mysql://localhost:3306/posandinventory_db`
- User: `root`
- Password: `1234`

If your MySQL credentials differ, update `DatabaseConnection.java`.

## Running in Eclipse (recommended)

1. Import the project into Eclipse: **File → Import → Existing Projects into Workspace**
2. Add MySQL Connector/J to the build path:
   - **Project → Properties → Java Build Path → Libraries → Add External JARs…**
3. Run the application:
   - Open `src/main/Main.java`
   - **Run As → Java Application**

This launches the login window (`system_pack.LoginUI`).

## Building / Running as a Runnable JAR

Because this is not a Maven/Gradle project, the easiest way is exporting a Runnable JAR from Eclipse.

1. In Eclipse: **Right click project → Export…**
2. Choose: **Java → Runnable JAR file**
3. Select the launch configuration that runs `main.Main`
4. Choose a destination, e.g. `POScarthos.jar`
5. For “Library handling”, choose an option that packages dependencies (so the MySQL connector is included), or ensure the connector jar is otherwise on the runtime classpath.

Run:

```bash
java -jar POScarthos.jar
```

## Troubleshooting

### `java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver`

You are missing the MySQL Connector/J jar at runtime.
- Add it to Eclipse build path, and/or
- Re-export the runnable jar with the dependency packaged.

### Logo/image not showing

The UI loads the image as a classpath resource:

- `getResource("/logo.png")`

Ensure `logo.png` is available on the runtime classpath (often the root of the jar) when exporting.

## Notes

- Passwords are stored and checked as plain text in the database.
- This project was built using Eclipse’s Java builder (compiled output in `bin/`).

## License

[![License](https://img.shields.io/github/license/baelagare/pl-finalReq.svg?style=flat-square)](LICENSE)   
