package com.employeeManagement;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewEmployeeById extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldEmployeeId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewEmployeeById frame = new ViewEmployeeById();
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
    public ViewEmployeeById() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        // Title Label
        JLabel lblTitle = new JLabel("Employee Details");
        lblTitle.setFont(new Font("Segoe UI Historic", Font.BOLD, 30));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, lblTitle, 10, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        contentPane.add(lblTitle);

        // Employee ID Label
        JLabel lblEmployeeId = new JLabel("Employee ID:");
        lblEmployeeId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, lblEmployeeId, 20, SpringLayout.SOUTH, lblTitle);
        layout.putConstraint(SpringLayout.WEST, lblEmployeeId, 20, SpringLayout.WEST, contentPane);
        contentPane.add(lblEmployeeId);

        // Employee ID Input Field
        textFieldEmployeeId = new JTextField();
        layout.putConstraint(SpringLayout.NORTH, textFieldEmployeeId, -2, SpringLayout.NORTH, lblEmployeeId);
        layout.putConstraint(SpringLayout.WEST, textFieldEmployeeId, 10, SpringLayout.EAST, lblEmployeeId);
        layout.putConstraint(SpringLayout.EAST, textFieldEmployeeId, -20, SpringLayout.EAST, contentPane);
        contentPane.add(textFieldEmployeeId);
        textFieldEmployeeId.setColumns(20);

        // Checkboxes
        JCheckBox chkId = new JCheckBox("Id");
        layout.putConstraint(SpringLayout.NORTH, chkId, 20, SpringLayout.SOUTH, textFieldEmployeeId);
        layout.putConstraint(SpringLayout.WEST, chkId, 20, SpringLayout.WEST, contentPane);
        contentPane.add(chkId);

        JCheckBox chkName = new JCheckBox("Name");
        layout.putConstraint(SpringLayout.NORTH, chkName, 0, SpringLayout.NORTH, chkId);
        layout.putConstraint(SpringLayout.WEST, chkName, 20, SpringLayout.EAST, chkId);
        contentPane.add(chkName);

        JCheckBox chkSalary = new JCheckBox("Salary");
        layout.putConstraint(SpringLayout.NORTH, chkSalary, 0, SpringLayout.NORTH, chkId);
        layout.putConstraint(SpringLayout.WEST, chkSalary, 20, SpringLayout.EAST, chkName);
        contentPane.add(chkSalary);

        JCheckBox chkEmail = new JCheckBox("Email");
        layout.putConstraint(SpringLayout.NORTH, chkEmail, 20, SpringLayout.SOUTH, chkId);
        layout.putConstraint(SpringLayout.WEST, chkEmail, 20, SpringLayout.WEST, contentPane);
        contentPane.add(chkEmail);

        JCheckBox chkPhone = new JCheckBox("Phone");
        layout.putConstraint(SpringLayout.NORTH, chkPhone, 0, SpringLayout.NORTH, chkEmail);
        layout.putConstraint(SpringLayout.WEST, chkPhone, 20, SpringLayout.EAST, chkEmail);
        contentPane.add(chkPhone);

        JCheckBox chkAddress = new JCheckBox("Address");
        layout.putConstraint(SpringLayout.NORTH, chkAddress, 0, SpringLayout.NORTH, chkEmail);
        layout.putConstraint(SpringLayout.WEST, chkAddress, 20, SpringLayout.EAST, chkPhone);
        contentPane.add(chkAddress);

        // Find Button
        JButton btnFind = new JButton("Find");
        layout.putConstraint(SpringLayout.NORTH, btnFind, 20, SpringLayout.SOUTH, chkEmail);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnFind, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        contentPane.add(btnFind);
        
        // Result Area
        JTextArea textAreaResult = new JTextArea();
        textAreaResult.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textAreaResult.setEditable(false);
        layout.putConstraint(SpringLayout.NORTH, textAreaResult, 20, SpringLayout.SOUTH, btnFind);
        layout.putConstraint(SpringLayout.WEST, textAreaResult, 20, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, textAreaResult, -20, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, textAreaResult, -20, SpringLayout.SOUTH, contentPane);
        contentPane.add(textAreaResult);

        // Home Button
     
        JButton btnHome = new JButton("Home");
        layout.putConstraint(SpringLayout.NORTH, btnHome, 10, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, btnHome, -10, SpringLayout.EAST, contentPane); // Move to the right edge
        contentPane.add(btnHome);


        // Add Action Listener to Find Button
        btnFind.addActionListener(e -> {
            String empId = textFieldEmployeeId.getText();
            if (empId.isEmpty()) {
                textAreaResult.setText("Please enter a valid Employee ID.");
                return;
            }

            // Start building SQL query based on selected checkboxes
            StringBuilder queryBuilder = new StringBuilder("SELECT ");
            boolean isFirst = true;

            if (chkId.isSelected()) {
                queryBuilder.append("emp_id");
                isFirst = false;
            }
            if (chkName.isSelected()) {
                if (!isFirst) queryBuilder.append(", ");
                queryBuilder.append("name");
                isFirst = false;
            }
            if (chkSalary.isSelected()) {
                if (!isFirst) queryBuilder.append(", ");
                queryBuilder.append("salary");
                isFirst = false;
            }
            if (chkEmail.isSelected()) {
                if (!isFirst) queryBuilder.append(", ");
                queryBuilder.append("email");
                isFirst = false;
            }
            if (chkPhone.isSelected()) {
                if (!isFirst) queryBuilder.append(", ");
                queryBuilder.append("phone");
                isFirst = false;
            }
            if (chkAddress.isSelected()) {
                if (!isFirst) queryBuilder.append(", ");
                queryBuilder.append("address");
            }

            queryBuilder.append(" FROM employee WHERE emp_id = ?");

            // Execute query and display result
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "22122002");
                 PreparedStatement stmt = con.prepareStatement(queryBuilder.toString())) {
                stmt.setString(1, empId); // Set employee ID in the query
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    StringBuilder result = new StringBuilder();
                    if (chkId.isSelected()) result.append("ID: ").append(rs.getString("emp_id")).append("\n");
                    if (chkName.isSelected()) result.append("Name: ").append(rs.getString("name")).append("\n");
                    if (chkSalary.isSelected()) result.append("Salary: ").append(rs.getString("salary")).append("\n");
                    if (chkEmail.isSelected()) result.append("Email: ").append(rs.getString("email")).append("\n");
                    if (chkPhone.isSelected()) result.append("Phone: ").append(rs.getString("phone")).append("\n");
                    if (chkAddress.isSelected()) result.append("Address: ").append(rs.getString("address")).append("\n");

                    textAreaResult.setText(result.toString());
                } else {
                    textAreaResult.setText("No employee found with this ID.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                textAreaResult.setText("An error occurred while fetching data.");
            }
        });

        // Action Listener for Home Button
        btnHome.addActionListener(e -> {
            Home homePage = new Home(); // Assuming Home is the main page
            homePage.setVisible(true);
            ViewEmployeeById.this.dispose(); // Close current frame
        });
    }
}
