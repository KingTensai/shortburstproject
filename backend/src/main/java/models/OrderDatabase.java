package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDatabase {

//TODO FIX DB SOURCE
//private static final String URL =
  //      "jdbc:mysql://dt5.ehb.be:3306/2526JAVAADV015?useSSL=false&serverTimezone=UTC";
    //private static final String USER = "2526JAVAADV015";
    //private static final String PASSWORD = "18725463";
private static final String URL = "jdbc:mysql://localhost:3306/localdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "testpass";

    public static void createTablesIfNotExists() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String dbProductTable = """
            CREATE TABLE IF NOT EXISTS product (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                sku VARCHAR(255) NOT NULL UNIQUE,
                name VARCHAR(255) NOT NULL,
                description VARCHAR(1024),
                price DOUBLE NOT NULL,
                category VARCHAR(255),
                location VARCHAR(255),
                created_at TIMESTAMP,
                updated_at TIMESTAMP
            )
        """;

            String dbStockTable = """
            CREATE TABLE IF NOT EXISTS stock (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                product_id BIGINT NOT NULL UNIQUE,
                quantity INT NOT NULL,
                CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
            )
        """;

            String dbOrdersTable = """
            CREATE TABLE IF NOT EXISTS orders (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                price DOUBLE,
                order_location VARCHAR(255) NOT NULL
            )
        """;

            String dbOrderProductTable = """
            CREATE TABLE IF NOT EXISTS order_product (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                quantity INT NOT NULL,
                price DOUBLE NOT NULL,
                order_id BIGINT NOT NULL,
                product_id BIGINT NOT NULL,
                CONSTRAINT fk_orderproduct_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                CONSTRAINT fk_orderproduct_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
            )
        """;

            stmt.executeUpdate(dbProductTable);
            stmt.executeUpdate(dbStockTable);
            stmt.executeUpdate(dbOrdersTable);
            stmt.executeUpdate(dbOrderProductTable);

            System.out.println("Tables created successfully (if they did not exist).");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //For testing purposes only, now using panache repos
    public static void main(String[] args) {
        createTablesIfNotExists();
    }

}
