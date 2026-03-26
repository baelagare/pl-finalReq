package controllers;

import models.Product;
import models.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import db.DatabaseConnection;

@SuppressWarnings("unused")
public class InventoryController {

    // Fetch all products from the database
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection()) {           
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");

                products.add(new Product(product_id, name, category, stock, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Add a product to the database
    public static boolean addProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO products (product_id, name, category, stock, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getCategory());
            stmt.setInt(4, product.getStock());
            stmt.setDouble(5, product.getPrice());
            
            return stmt.executeUpdate() > 0;
            //return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing product
    public static boolean updateProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE products SET name = ?, category = ?, stock = ?, price = ? WHERE product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getStock());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getProductId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // update a product stock
    public static void updateProductStock(int productId, int quantity) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Delete a product based on its ID
    public static boolean deleteProduct(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failure
        }
    }
    
    public static String getCategoryByProductId(String productId) {
        String category = null;
        
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT category FROM products WHERE product_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                category = resultSet.getString("category");
            }
            
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return category;
    }

    public static Product getProductById(String productId) { 
    	Product product = null; 
    	
    	try { 
    		Connection connection = DatabaseConnection.getConnection(); 
    		String query = "SELECT * FROM products WHERE product_id = ?"; 
    		PreparedStatement preparedStatement = connection.prepareStatement(query); 
    		preparedStatement.setString(1, productId); 
    		ResultSet resultSet = preparedStatement.executeQuery(); 
    		
    		if (resultSet.next()) { 
    			int id = resultSet.getInt("product_id"); 
    			String name = resultSet.getString("name"); 
    			String category = resultSet.getString("category"); 
    			int stock = resultSet.getInt("stock"); 
    			double price = resultSet.getDouble("price"); 
    			product = new Product(id, name, category, stock, price); 
    		} 
    		
    		resultSet.close(); 
    		preparedStatement.close(); 
    		connection.close(); 
    	} catch (Exception e) { 
    		e.printStackTrace(); } 
    	return product; 
    }
    
    public static Product getProductByName(String productName) throws Exception { 
        Product product = null; 
        try (Connection connection = DatabaseConnection.getConnection()) { 
            String query = "SELECT * FROM products WHERE name = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(query); 
            preparedStatement.setString(1, productName);  // Set parameter at index 1 instead of 2
            ResultSet resultSet = preparedStatement.executeQuery(); 
            
            if (resultSet.next()) { 
                // Create a product object based on the database result 
                int id = resultSet.getInt("product_id"); 
                String name = resultSet.getString("name"); 
                String category = resultSet.getString("category"); 
                int stock = resultSet.getInt("stock"); 
                double price = resultSet.getDouble("price");             
                product = new Product(id, name, category, stock, price); 
            } 
            resultSet.close(); 
            preparedStatement.close(); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
        return product; 
    }


    // Fetch products filtered by category
    public static List<Product> getProductsByCategory(String category) {
        List<Product> filteredProducts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE category = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String name = rs.getString("name");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");

                filteredProducts.add(new Product(product_id, name, category, stock, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredProducts;
    }

    // Fetch distinct product categories
    public static List<String> getProductCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT DISTINCT category FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
    
/*********************************************************************************************************************/    

    // Fetch all suppliers from the database
    public static List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM suppliers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int supplier_id = rs.getInt("supplier_id");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String address = rs.getString("address");

                suppliers.add(new Supplier(supplier_id, name, contact, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Add a new supplier to the database
    public static boolean addSupplier(Supplier supplier) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO suppliers (supplier_id, name, contact, address) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getContact());
            stmt.setString(4, supplier.getAddress());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 // Update an existing supplier's details
    public static boolean updateSupplier(Supplier supplier) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE suppliers SET name = ?, contact = ?, address = ? WHERE supplier_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContact());
            stmt.setString(3, supplier.getAddress());
            stmt.setInt(4, supplier.getSupplierId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteSupplier(int supplier_id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, supplier_id);
            stmt.executeUpdate();
            return true; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failure
        }
    }

}
