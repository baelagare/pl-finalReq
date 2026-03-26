package models;

public class Product {
    private int product_id;
    private String name;
    private String category;
    private int stock;
    private double price;

    // Constructor
    public Product(int product_id, String name, String category, int stock, double price) {
        this.product_id = product_id;
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }

    // Overloaded Constructor (without id for new products)
    public Product(String name, String category, int stock, double price) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }

    // Default Constructor
    public Product() {
    }

    // Getters and Setters
    public int getProductId() {
        return product_id;
    }

    public void setId(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
