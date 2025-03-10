package com.employeeManagement;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemoveEmployee extends JFrame {

    private JPanel contentPane;
    private JTextField employeeIdField;  // Declare the text field for Employee ID
    private JTextArea displayArea;       // Declare the text area for display

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RemoveEmployee frame = new RemoveEmployee();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RemoveEmployee() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);  // Adjust size to fit all components
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);
        
        // Title Label
        JLabel lblNewLabel = new JLabel("Remove Employee");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 132, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, -137, SpringLayout.EAST, contentPane);
        lblNewLabel.setFont(new Font("Segoe UI Historic", Font.BOLD, 30));
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel);
        
        // Home Button (top-right corner)
        JButton homeButton = new JButton("Home");
        sl_contentPane.putConstraint(SpringLayout.NORTH, homeButton, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, homeButton, -10, SpringLayout.EAST, contentPane);
        contentPane.add(homeButton);
        
        // Employee ID Label
        JLabel lblEmployeeId = new JLabel("Employee ID:");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblEmployeeId, 20, SpringLayout.SOUTH, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblEmployeeId, 40, SpringLayout.WEST, contentPane);
        contentPane.add(lblEmployeeId);
        
        // Employee ID Input Field
        employeeIdField = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.NORTH, employeeIdField, 6, SpringLayout.SOUTH, lblEmployeeId);
        sl_contentPane.putConstraint(SpringLayout.WEST, employeeIdField, 40, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, employeeIdField, -40, SpringLayout.EAST, contentPane);
        employeeIdField.setColumns(10);
        contentPane.add(employeeIdField);
        
        // Remove Button
        JButton removeButton = new JButton("Remove");
        sl_contentPane.putConstraint(SpringLayout.NORTH, removeButton, 20, SpringLayout.SOUTH, employeeIdField);
        sl_contentPane.putConstraint(SpringLayout.WEST, removeButton, 40, SpringLayout.WEST, contentPane);
        contentPane.add(removeButton);
        
        // Display Area (Text Area) - Adjust size to make it bigger and remove JScrollPane
        displayArea = new JTextArea();
        displayArea.setEditable(false);  // Make it non-editable
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Optional: make font monospaced for better readability
        displayArea.setLineWrap(true);   // Allow text wrapping
        displayArea.setWrapStyleWord(true);
        sl_contentPane.putConstraint(SpringLayout.NORTH, displayArea, 20, SpringLayout.SOUTH, removeButton);
        sl_contentPane.putConstraint(SpringLayout.WEST, displayArea, 40, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, displayArea, -40, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, displayArea, -20, SpringLayout.SOUTH, contentPane);
        contentPane.add(displayArea);
        
        // ActionListener for the "Remove" button
        removeButton.addActionListener(e -> {
            String employeeId = employeeIdField.getText();
            
            if (employeeId.isEmpty()) {
                displayArea.setText("Please enter a valid Employee ID.");
                return;
            }
            
            // Call the method to remove the employee
            removeEmployee(employeeId);
        });
        
        // ActionListener for the "Home" button
        homeButton.addActionListener(e -> {
            // Action to go back to the home screen or main window
            // For example, if you want to dispose the current window and show a new window:
            dispose();  // Close current window
            // You can open the home screen here
            
             new Home().setVisible(true);
        });
    }

    // Method to remove employee from the database
    private void removeEmployee(String employeeId) {
        String url = "jdbc:mysql://localhost:3306/jdbcproject"; // Database URL
        String username = "root"; // Database username
        String password = "22122002"; // Database password

        String sqlFetch = "SELECT emp_id, name, salary, phone FROM employee WHERE emp_id = ?";
        String sqlDelete = "DELETE FROM employee WHERE emp_id = ?";

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            // Fetch employee details first
            try (PreparedStatement pstmtFetch = con.prepareStatement(sqlFetch)) {
                pstmtFetch.setString(1, employeeId); // Set the employee ID in the query
                ResultSet rs = pstmtFetch.executeQuery();

                if (rs.next()) {
                    // Display employee information
                    String employeeName = rs.getString("name");
                    double salary = rs.getDouble("salary");
                    String phone = rs.getString("phone");

                    displayArea.setText("Employee Found:\n" +
                                         "ID: " + employeeId + "\n" +
                                         "Name: " + employeeName + "\n" +
                                         "Salary: " + salary + "\n" +
                                         "Phone: " + phone + "\n\n" 
                                         );

                    // Now delete the employee
                    try (PreparedStatement pstmtDelete = con.prepareStatement(sqlDelete)) {
                        pstmtDelete.setString(1, employeeId);
                        int rowsAffected = pstmtDelete.executeUpdate();

                        if (rowsAffected > 0) {
                            displayArea.append("\nEmployee with ID " + employeeId + " has been successfully removed.");
                        } else {
                            displayArea.append("\nNo employee found with ID " + employeeId + ".");
                        }
                    }

                } else {
                    displayArea.setText("No employee found with ID " + employeeId + ".");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            displayArea.setText("An error occurred while removing the employee.");
        }
    }
}
