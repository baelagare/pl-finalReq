package system_pack;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import controllers.InventoryController;
import models.Product;
import models.Supplier;

import java.awt.*;
import java.util.List;

@SuppressWarnings("unused")
public class InventoryUI {
    private JFrame frmPoscarthos;
    private JTable productsTable, suppliersTable;
    private DefaultTableModel productsTableModel, suppliersTableModel;
    private JComboBox<String> categoryComboBox;

    public InventoryUI() {
        initialize();
        loadSuppliers();
    }

    private void initialize() {
        frmPoscarthos = new JFrame();
        frmPoscarthos.setSize(901, 602);
        frmPoscarthos.setTitle("Inventory");
        frmPoscarthos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPoscarthos.getContentPane().setLayout(null);

        // Product Panel
        JPanel productsPanel = new JPanel();
        productsPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        productsPanel.setBounds(10, 54, 251, 243);
        productsPanel.setLayout(null);
        frmPoscarthos.getContentPane().add(productsPanel);

        JButton addProductBtn = createButton("Add Product", 46, 34);
        addProductBtn.addActionListener(e -> openProductDialog(null));
        productsPanel.add(addProductBtn);

        JButton updateProductBtn = createButton("Update Product", 46, 94);
        updateProductBtn.addActionListener(e -> openProductDialog(getSelectedProduct()));
        productsPanel.add(updateProductBtn);

        JButton deleteProductBtn = createButton("Delete Product", 46, 154);
        deleteProductBtn.addActionListener(e -> deleteProduct());
        productsPanel.add(deleteProductBtn);

        // Category ComboBox
        JLabel lblCategory = new JLabel("Product Category:");
        lblCategory.setFont(new Font("Montserrat", Font.PLAIN, 14));
        lblCategory.setBounds(271, 15, 130, 30);
        frmPoscarthos.getContentPane().add(lblCategory);

        categoryComboBox = new JComboBox<>();
        categoryComboBox.setBounds(411, 15, 163, 30);
        frmPoscarthos.getContentPane().add(categoryComboBox);
        loadProductCategories();
        categoryComboBox.addActionListener(e -> filterProductsByCategory());

        // Products Table
        JPanel productsTblPanel = createTitledPanel("Products", 265, 50, 616, 248);
        frmPoscarthos.getContentPane().add(productsTblPanel);
        productsTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Stock", "Price"}, 0);
        productsTable = createTable(productsTableModel);
        productsTblPanel.add(new JScrollPane(productsTable));

        // Suppliers Panel
        JPanel suppliersPanel = new JPanel();
        suppliersPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        suppliersPanel.setBounds(10, 308, 251, 243);
        frmPoscarthos.getContentPane().add(suppliersPanel);
        suppliersPanel.setLayout(null);

        JButton addSupplierBtn = createButton("Add Supplier", 46, 36);
        addSupplierBtn.addActionListener(e -> openSupplierDialog(null));
        suppliersPanel.add(addSupplierBtn);

        JButton updateSupplierBtn = createButton("Update Supplier", 46, 96);
        updateSupplierBtn.addActionListener(e -> openSupplierDialog(getSelectedSupplier()));
        suppliersPanel.add(updateSupplierBtn);

        JButton deleteSupplierBtn = createButton("Delete Supplier", 46, 156);
        deleteSupplierBtn.addActionListener(e -> deleteSupplier());
        suppliersPanel.add(deleteSupplierBtn);

        // Suppliers Table
        JPanel suppliersTblPanel = createTitledPanel("Suppliers", 265, 302, 616, 255);
        frmPoscarthos.getContentPane().add(suppliersTblPanel);
        suppliersTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Contact", "Address"}, 0);
        suppliersTable = createTable(suppliersTableModel);
        suppliersTblPanel.add(new JScrollPane(suppliersTable));

        // Main Menu Button
        JButton mainMenuBtn = createButton("Main Menu", 710, 8);
        mainMenuBtn.addActionListener(e -> goToMainMenu());
        frmPoscarthos.getContentPane().add(mainMenuBtn);
        

        loadAllProducts();
        loadSuppliers();
    }

/********************************************************************************/    
    
    // Utility Methods
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Montserrat", Font.PLAIN, 12));
        button.setBounds(x, y, 153, 45);
        return button;
    }

    private JPanel createTitledPanel(String title, int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(), title));
        panel.setBounds(x, y, width, height);
        panel.setLayout(new BorderLayout());
        return panel;
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Montserrat", Font.PLAIN, 12));
        table.setRowHeight(20);
        return table;
    }
    
 /****************************************************************************/      
    
    private void loadAllProducts() {
        productsTableModel.setRowCount(0); // Clear the table
        InventoryController.getAllProducts().forEach(product -> 
            productsTableModel.addRow(new Object[]{
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getStock(),
                product.getPrice()
            })
        );
    }
    
    private void loadProductsByCategory(String category) {
        productsTableModel.setRowCount(0); // Clear the table
        InventoryController.getProductsByCategory(category).forEach(product -> 
            productsTableModel.addRow(new Object[]{
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getStock(),
                product.getPrice()
            })
        );
    }

    private void loadProductCategories() {
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem("All");
        InventoryController.getProductCategories().forEach(categoryComboBox::addItem);
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            
            if ("All".equalsIgnoreCase(selectedCategory)) {
                loadAllProducts(); // Load all products if "All" is chosen
            } else {
                loadProductsByCategory(selectedCategory); // Load filtered products
            }
        });
    }

//    private void viewProducts() {
//        productsTableModel.setRowCount(0);
//        InventoryController.getAllProducts().forEach(product ->
//                productsTableModel.addRow(new Object[]{
//                        product.getId(), product.getName(), product.getCategory(), product.getStock(), product.getPrice()
//                }));
//    }

    private void filterProductsByCategory() {
        String category = (String) categoryComboBox.getSelectedItem();
        productsTableModel.setRowCount(0);
        InventoryController.getProductsByCategory(category).forEach(product ->
                productsTableModel.addRow(new Object[]{
                        product.getProductId(), product.getName(), product.getCategory(), product.getStock(), product.getPrice()
                }));
    }
      

    private Product getSelectedProduct() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow != -1) {
        	
            return new Product(
                    (int) productsTableModel.getValueAt(selectedRow, 0),
                    (String) productsTableModel.getValueAt(selectedRow, 1),
                    (String) productsTableModel.getValueAt(selectedRow, 2),
                    (int) productsTableModel.getValueAt(selectedRow, 3),
                    (double) productsTableModel.getValueAt(selectedRow, 4)
            );
        }
        return null;
    }

    private void deleteProduct() {
        try {
            Product product = getSelectedProduct(); // Get the selected product
            if (product == null) {
                JOptionPane.showMessageDialog(frmPoscarthos, "Please select a product to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(frmPoscarthos, 
                    "Are you sure you want to delete the selected product?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean isDeleted = InventoryController.deleteProduct(product.getProductId());
                if (isDeleted) {
                    JOptionPane.showMessageDialog(frmPoscarthos, "Product deleted successfully.");
                    loadAllProducts(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(frmPoscarthos, "Failed to delete product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmPoscarthos, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void openProductDialog(Product product) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField idField = new JTextField(product != null ? String.valueOf(product.getProductId()) : "");
        JTextField nameField = new JTextField(product != null ? String.valueOf(product.getName()) : "");
        JTextField categoryField = new JTextField(product != null ? String.valueOf(product.getCategory()) : "");
        JTextField stockField = new JTextField(product != null ? String.valueOf(product.getStock()) : "");
        JTextField priceField = new JTextField(product != null ? String.valueOf(product.getPrice()) : "");

        panel.add(new JLabel("ID:")); panel.add(idField);
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Category:")); panel.add(categoryField);
        panel.add(new JLabel("Stock:")); panel.add(stockField);
        panel.add(new JLabel("Price:")); panel.add(priceField);

        String dialogTitle = (product == null) ? "Add Product" : "Update Product";
        int result = JOptionPane.showConfirmDialog(frmPoscarthos, panel, dialogTitle, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int product_id = Integer.parseInt(idField.getText());
                String name = nameField.getText().trim();
                String category = categoryField.getText();
                int stock = Integer.parseInt(stockField.getText());
                double price = Double.parseDouble(priceField.getText());

                if (product == null) { // Add New Product
                    if (InventoryController.addProduct(new Product(product_id, name, category, stock, price))) {
                        JOptionPane.showMessageDialog(frmPoscarthos, "Product Added Successfully!");
                        loadAllProducts();
                    }
                } else { // Update Existing Product
                    if (InventoryController.updateProduct(new Product(product_id, name, category, stock, price))) {
                        JOptionPane.showMessageDialog(frmPoscarthos, "Product Updated Successfully!");
                        loadAllProducts();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmPoscarthos, "Invalid Input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

/**********************************************************************************/
    
    private void loadSuppliers() {
    	suppliersTableModel.setRowCount(0); // Clear the table
        InventoryController.getAllSuppliers().forEach(supplier -> 
            suppliersTableModel.addRow(new Object[]{
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getAddress(),               
            })
        );
    }
    
    private Supplier getSelectedSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow != -1) {
        	
        	return new Supplier (
        			(int) suppliersTableModel.getValueAt(selectedRow, 0),
                    (String) suppliersTableModel.getValueAt(selectedRow, 1),
                    (String) suppliersTableModel.getValueAt(selectedRow, 2),
                    (String) suppliersTableModel.getValueAt(selectedRow, 3)
            );       	        			
        }
        //return (selectedRow != -1) ? (Supplier) suppliersTableModel.getValueAt(selectedRow, 1) : null;
		return null;
    }

    private void openSupplierDialog(Supplier supplier) {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField idField = new JTextField(supplier != null ? String.valueOf(supplier.getSupplierId()) : "");
        JTextField nameField = new JTextField(supplier != null ? String.valueOf(supplier.getName()) : "");
        JTextField contactField = new JTextField(supplier != null ? String.valueOf(supplier.getContact()) : "");
        JTextField addressField = new JTextField(supplier != null ? String.valueOf(supplier.getAddress()) : "");

        panel.add(new JLabel("ID:")); panel.add(idField);
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Contact:")); panel.add(contactField);
        panel.add(new JLabel("Address:")); panel.add(addressField);

        String dialogTitle = (supplier == null) ? "Add Supplier" : "Update Supplier";
        int result = JOptionPane.showConfirmDialog(frmPoscarthos, panel, dialogTitle, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int supplier_id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String contact = contactField.getText();
                String address = addressField.getText();

                if (supplier == null) { // Add New Supplier
                    if (InventoryController.addSupplier(new Supplier(supplier_id, name, contact, address))) {
                        JOptionPane.showMessageDialog(frmPoscarthos, "Supplier Added Successfully!");
                        loadSuppliers();
                    }
                } else { // Update Supplier
                    if (InventoryController.updateSupplier(new Supplier(supplier_id, name, contact, address))) {
                        JOptionPane.showMessageDialog(frmPoscarthos, "Supplier Updated Successfully!");
                        loadSuppliers(); // Refresh the supplier list
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmPoscarthos, "Invalid Input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void deleteSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow != -1) {
            int supplierId = (int) suppliersTableModel.getValueAt(selectedRow, 0);
            boolean isDeleted = InventoryController.deleteSupplier(supplierId);
            if (isDeleted) {
                suppliersTableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(frmPoscarthos, "Supplier deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(frmPoscarthos, "Failed to delete supplier.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frmPoscarthos, "Please select a supplier to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    private void viewSuppliers() {
//        suppliersTableModel.setRowCount(0); // Clear existing rows
//        
//        try {
//            // Call the InventoryController to fetch suppliers from the database
//            List<Supplier> suppliers = InventoryController.getAllSuppliers();
//            
//            // Add each supplier's data to the table model
//            for (Supplier supplier : suppliers) {
//                suppliersTableModel.addRow(new Object[]{
//                    supplier.getId(), 
//                    supplier.getName(), 
//                    supplier.getContact(),
//                    supplier.getAddress()
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error loading suppliers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void goToMainMenu() {
        //JOptionPane.showMessageDialog(frmPoscarthos, "Returning to Main Menu...");
        frmPoscarthos.dispose();
        MainMenuUI mainMenu = new MainMenuUI();
        mainMenu.setVisible(true);
        
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new InventoryUI().frmPoscarthos.setVisible(true));
    }

	public void setVisible(boolean visible) {
		frmPoscarthos.setVisible(visible);
		
	}
}
