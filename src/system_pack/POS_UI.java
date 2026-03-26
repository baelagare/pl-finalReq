package system_pack;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import models.Product;
import controllers.InventoryController;

public class POS_UI {

    private JFrame frmPoscarthos;
    private JTextField prdctIdTextField;
    private JTextField prdctNameTextField;
    private JTextField priceTextField;
    private JTextField totalTextField;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private JTextArea receiptTextArea;
    private JTextField totalCalField;
    private JTextField cashField;
    private JTextField changeField;
    private JSpinner qtySpinner;
	//private Object product;
	//private int quantity;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                POS_UI window = new POS_UI();
                window.frmPoscarthos.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public POS_UI() {
        initialize();
    }

    private void initialize() {
        frmPoscarthos = new JFrame();
        frmPoscarthos.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmPoscarthos.setTitle("POS System");
        frmPoscarthos.setPreferredSize(new Dimension(1400, 730));
        frmPoscarthos.getContentPane().setLayout(null);

        JPanel sellPanel = createSellPanel();
        frmPoscarthos.getContentPane().add(sellPanel);

        JPanel itemsPanel = createItemsPanel();
        frmPoscarthos.getContentPane().add(itemsPanel);

        JPanel receiptPanel = createReceiptPanel();
        frmPoscarthos.getContentPane().add(receiptPanel);

        JPanel calculatePanel = createCalculatePanel();
        frmPoscarthos.getContentPane().add(calculatePanel);

        JButton mainMenuButton = createMainMenuButton();
        frmPoscarthos.getContentPane().add(mainMenuButton);
    }

    private JPanel createSellPanel() {
        JPanel sellPanel = new JPanel();
        sellPanel.setBounds(10, 11, 944, 202);
        sellPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Product"));
        sellPanel.setLayout(null);

        addProductComponents(sellPanel);
        addActionButtons(sellPanel);

        return sellPanel;
    }

    private void addProductComponents(JPanel sellPanel) {
        JLabel productIdLabel = new JLabel("Product ID");
        productIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productIdLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        productIdLabel.setBounds(67, 39, 115, 32);
        sellPanel.add(productIdLabel);

        prdctIdTextField = new JTextField();
        prdctIdTextField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        prdctIdTextField.setBounds(34, 74, 184, 32);
        sellPanel.add(prdctIdTextField);

        JLabel productNameLabel = new JLabel("Product Name");
        productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productNameLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        productNameLabel.setBounds(239, 39, 162, 32);
        sellPanel.add(productNameLabel);

        prdctNameTextField = new JTextField();
        prdctNameTextField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        prdctNameTextField.setBounds(228, 74, 184, 32);
        sellPanel.add(prdctNameTextField);

        JLabel qtyLabel = new JLabel("Quantity");
        qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qtyLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        qtyLabel.setBounds(422, 39, 101, 32);
        sellPanel.add(qtyLabel);

        qtySpinner = new JSpinner();
        qtySpinner.setFont(new Font("Montserrat", Font.PLAIN, 15));
        qtySpinner.setBounds(422, 74, 101, 32);
        qtySpinner.addChangeListener(e -> qtySpinnerStateChanged());
        sellPanel.add(qtySpinner);

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        priceLabel.setBounds(584, 39, 78, 32);
        sellPanel.add(priceLabel);

        priceTextField = new JTextField();
        priceTextField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        priceTextField.setBounds(533, 74, 184, 32);
        sellPanel.add(priceTextField);

        JLabel totalLabel = new JLabel("Total");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        totalLabel.setBounds(777, 39, 78, 32);
        sellPanel.add(totalLabel);

        totalTextField = new JTextField();
        totalTextField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        totalTextField.setBounds(727, 74, 184, 32);
        totalTextField.setEditable(false);
        sellPanel.add(totalTextField);
    }

    private void addActionButtons(JPanel sellPanel) {
        JButton buyButton = new JButton("BUY");
        buyButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        buyButton.setBounds(675, 129, 137, 42);
        buyButton.addActionListener(e -> {
            processPurchase((int) qtySpinner.getValue());
            clearFields();                   
            
        });        
        sellPanel.add(buyButton);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        deleteButton.setBounds(525, 129, 137, 42);
        deleteButton.addActionListener(e -> removeSelectedItem());
        sellPanel.add(deleteButton);
        
        JButton searchButton = new JButton("SEARCH");
        searchButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        searchButton.setBounds(34, 129, 137, 42);
        searchButton.addActionListener(e -> searchProduct());
        sellPanel.add(searchButton);
    }

    private JPanel createItemsPanel() {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBounds(10, 224, 944, 445);
        itemsPanel.setBorder(new TitledBorder(null, "Items"));
        itemsPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 23, 924, 411);
        itemsPanel.add(scrollPane);

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{
            "Product ID", "Product Name", "Category", "Quantity", "Price", "Total"
        });
        itemsTable = new JTable(tableModel);
        itemsTable.setFont(new Font("Montserrat", Font.PLAIN, 15));
        scrollPane.setViewportView(itemsTable);

        return itemsPanel;
    }

    private JPanel createReceiptPanel() {
        JPanel receiptPanel = new JPanel();
        receiptPanel.setBounds(964, 273, 392, 396);
        receiptPanel.setBorder(new TitledBorder(null, "Receipt"));
        receiptPanel.setLayout(null);

        receiptTextArea = new JTextArea();
        receiptTextArea.setBounds(10, 22, 372, 363);
        receiptPanel.add(receiptTextArea);

        return receiptPanel;
    }

    private JPanel createCalculatePanel() {
        JPanel calculatePanel = new JPanel();
        calculatePanel.setBorder(new TitledBorder(null, "Calculate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        calculatePanel.setBounds(964, 11, 392, 253);
        calculatePanel.setLayout(null);
        
        JLabel totalCalLabel = new JLabel("Total");
        totalCalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalCalLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        totalCalLabel.setBounds(43, 39, 78, 32);
        calculatePanel.add(totalCalLabel);
        
        totalCalField = new JTextField();
        totalCalField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        totalCalField.setBounds(131, 39, 212, 32);
        calculatePanel.add(totalCalField);
        totalCalField.setColumns(10);
        
        JLabel cashLabel = new JLabel("Cash");
        cashLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cashLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        cashLabel.setBounds(43, 82, 78, 32);
        calculatePanel.add(cashLabel);
        
        cashField = new JTextField();
        cashField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        cashField.setColumns(10);
        cashField.setBounds(131, 82, 212, 32);
        calculatePanel.add(cashField);
        
        JLabel changeLabel = new JLabel("Change");
        changeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        changeLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        changeLabel.setBounds(43, 125, 78, 32);
        calculatePanel.add(changeLabel);
        
        changeField = new JTextField();
        changeField.setFont(new Font("Montserrat", Font.PLAIN, 15));
        changeField.setColumns(10);
        changeField.setBounds(131, 125, 212, 32);
        calculatePanel.add(changeField);
        
        JButton btnPrintBill = new JButton("Print Bill");
        btnPrintBill.setFont(new Font("Montserrat", Font.PLAIN, 15));
        btnPrintBill.setBounds(120, 181, 137, 42);
        btnPrintBill.addActionListener(e -> {
        	calculateChange();
        	printReceipt();
        });
        calculatePanel.add(btnPrintBill);

        return calculatePanel;
    }

    private JButton createMainMenuButton() {
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        mainMenuButton.setBounds(10, 680, 191, 49);
        mainMenuButton.addActionListener(e -> {
            frmPoscarthos.dispose();
            MainMenuUI mainMenu = new MainMenuUI();
            mainMenu.setVisible(true);
        });
        return mainMenuButton;
    }
    
    private void clearFields() {
        prdctIdTextField.setText("");
        prdctNameTextField.setText("");
        qtySpinner.setValue(0);
        priceTextField.setText("");
        totalTextField.setText("");
    }

    private void searchProduct() {
        try {
            String productName = prdctNameTextField.getText();
            Product product = InventoryController.getProductByName(productName);
            if (product != null) {
                prdctIdTextField.setText(String.valueOf(product.getProductId()));
                priceTextField.setText(String.valueOf(product.getPrice()));
                qtySpinnerStateChanged();   
                                
				
                
            } else {
                JOptionPane.showMessageDialog(frmPoscarthos, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    private void addProductToTable(Product product, int quantity) { 
//    	double total = product.getPrice() * quantity; 
//    	tableModel.addRow(new Object[]{ 
//    			product.getProductId(), 
//    			product.getName(), 
//    			product.getCategory(), 
//    			quantity, product.
//    			getPrice(), total }); }

    private void processPurchase(int quantity) {
        String productIdText = prdctIdTextField.getText();
        Product product = InventoryController.getProductById(productIdText);
        
        // Check if the product is null
        if (product == null) {
            JOptionPane.showMessageDialog(frmPoscarthos, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String category = product.getCategory(); // Using the category from the product
        String productName = product.getName();
        double price = product.getPrice();
        double total = quantity * price;

        // Check if the quantity spinner is set to 0
        int qty = (int) qtySpinner.getValue();
        if (qty == 0) {
            JOptionPane.showMessageDialog(frmPoscarthos, "Please input a quantity", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add the product to the table
        tableModel.addRow(new Object[]{productIdText, productName, category, quantity, price, total});
        
        // Update the inventory in the database
        InventoryController.updateProductStock(product.getProductId(), quantity);

        // Calculate total
        calculateTotal();
        
        // Print receipt
        //printReceipt();

        // Clear input fields
        clearFields();
    }



    private void removeSelectedItem() {
        try {
            int selectedRow = itemsTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(frmPoscarthos, "Deleted na!.");
                calculateTotal();
            } else {
                JOptionPane.showMessageDialog(frmPoscarthos, "Wala man kay selected nga item. Palihug select sa item nga gusto idelete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(frmPoscarthos, "Error removing selected item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (double) tableModel.getValueAt(i, 5);
        }
        totalCalField.setText(String.valueOf(total));
        //calculateChange();
    }

    private void calculateChange() {
    	String cashText = cashField.getText(); 
    	if (cashText.isEmpty()) { 
    		JOptionPane.showMessageDialog(frmPoscarthos, "Please input cash.", "Warning", JOptionPane.WARNING_MESSAGE); 
    		return; // Exit the method if cash is not provided }
    	}
    	
        double total = Double.parseDouble(totalCalField.getText());
        double cash = Double.parseDouble(cashField.getText());
        double change = cash - total;
        changeField.setText(String.valueOf(change));       
        
    }

    private void printReceipt() {
        try {
            receiptTextArea.setText("                         	     POScarthos \n");
            receiptTextArea.setText(receiptTextArea.getText() + "                         	     Bert Lagare, \n");
            receiptTextArea.setText(receiptTextArea.getText() + "                         	     Intermediate Programming, \n");
            receiptTextArea.setText(receiptTextArea.getText() + "                         	    +63 981 536 3596 \n");
            receiptTextArea.setText(receiptTextArea.getText() + "----------------------------------------------------------------------------------------------\n");
            receiptTextArea.setText(receiptTextArea.getText() + "  Item \t\tQty \tPrice" + "\n");
            receiptTextArea.setText(receiptTextArea.getText() + "----------------------------------------------------------------------------------------------\n");
            
            DefaultTableModel df = (DefaultTableModel) itemsTable.getModel();
            
            for (int i = 0; i < df.getRowCount(); i++) {
                String name = df.getValueAt(i, 1).toString();
                String qty = df.getValueAt(i, 3).toString();
                String price = df.getValueAt(i, 4).toString();
                
                receiptTextArea.setText(receiptTextArea.getText() + name + "\t\t" + qty + "\t" + price + "\n");
            }
            
            receiptTextArea.setText(receiptTextArea.getText() + "---------------------------------------------------------------------------------------------\n");
            receiptTextArea.setText(receiptTextArea.getText() + "Sub Total : " + totalCalField.getText() + "\n");
            receiptTextArea.setText(receiptTextArea.getText() + "Cash      : " + cashField.getText() + "\n");
            receiptTextArea.setText(receiptTextArea.getText() + "Balance   : " + changeField.getText() + "\n");
            receiptTextArea.setText(receiptTextArea.getText() + "---------------------------------------------------------------------------------------------\n");
            receiptTextArea.setText(receiptTextArea.getText() + "                     		Thanks For Your Business...!" + "\n");
            receiptTextArea.setText(receiptTextArea.getText() + "---------------------------------------------------------------------------------------------\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frmPoscarthos, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void qtySpinnerStateChanged() {
        int quantity = (int) qtySpinner.getValue();
        double price = Double.parseDouble(priceTextField.getText());
        double total = quantity * price;
        totalTextField.setText(String.valueOf(total));
    }

    public void setVisible(boolean visible) {
        frmPoscarthos.setVisible(visible);
    }
}
