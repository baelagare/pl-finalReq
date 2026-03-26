package models;

public class Supplier {
    private int supplier_id;
    private String name;
    private String contact;
    private String address;

    // Constructor
    public Supplier(int supplier_id, String name, String contact, String address) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    // Overloaded Constructor (without id for new suppliers)
    public Supplier(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    // Default Constructor
    public Supplier() {
    }

    // Getters and Setters
    public int getSupplierId() {
        return supplier_id;
    }

    public void setSupplierId(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
