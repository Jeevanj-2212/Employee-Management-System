package com.employeeManagement;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddEmployee extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldEmpId;
    private JTextField textFieldName;
    private JTextField textFieldSalary;
    private JTextField textFieldEmail;
    private JTextField textFieldPhone;
    private JTextField textFieldAddress;

    // Database connection parameters
    private String url = "jdbc:mysql://localhost:3306/jdbcproject";
    private String username = "root";
    private String password = "22122002";

    // Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddEmployee frame = new AddEmployee();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Create the frame
    public AddEmployee() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);

        // Label for Add Employee
        JLabel lblNewLabel = new JLabel("Add Employee");
        lblNewLabel.setFont(new Font("Segoe UI Historic", Font.BOLD, 30));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel);

        // Label and TextField for Employee ID
        JLabel lblEmpId = new JLabel("Employee ID");
        textFieldEmpId = new JTextField();
        contentPane.add(lblEmpId);
        contentPane.add(textFieldEmpId);
        textFieldEmpId.setColumns(10);

        // Label and TextField for Name
        JLabel lblName = new JLabel("Name");
        textFieldName = new JTextField();
        contentPane.add(lblName);
        contentPane.add(textFieldName);
        textFieldName.setColumns(10);

        // Label and TextField for Salary
        JLabel lblSalary = new JLabel("Salary");
        textFieldSalary = new JTextField();
        contentPane.add(lblSalary);
        contentPane.add(textFieldSalary);
        textFieldSalary.setColumns(10);

        // Label and TextField for Email
        JLabel lblEmail = new JLabel("Email");
        textFieldEmail = new JTextField();
        contentPane.add(lblEmail);
        contentPane.add(textFieldEmail);
        textFieldEmail.setColumns(10);

        // Label and TextField for Phone
        JLabel lblPhone = new JLabel("Phone");
        textFieldPhone = new JTextField();
        contentPane.add(lblPhone);
        contentPane.add(textFieldPhone);
        textFieldPhone.setColumns(10);

        // Label and TextField for Address
        JLabel lblAddress = new JLabel("Address");
        textFieldAddress = new JTextField();
        contentPane.add(lblAddress);
        contentPane.add(textFieldAddress);
        textFieldAddress.setColumns(10);

        // Add Employee Button
        JButton btnAddEmployee = new JButton("Add Employee");
        contentPane.add(btnAddEmployee);

        // Home Button
        JButton btnHome = new JButton("Home");
        contentPane.add(btnHome);

        // ActionListener for Add Employee Button
        btnAddEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the employee details from the text fields
                    String empIdStr = textFieldEmpId.getText();  // Get Employee ID (String)
                    int empId = Integer.parseInt(empIdStr);      // Convert to int

                    String name = textFieldName.getText();
                    String salaryStr = textFieldSalary.getText();
                    int salary = Integer.parseInt(salaryStr);    // Convert Salary to int
                    String email = textFieldEmail.getText();
                    String phone = textFieldPhone.getText();
                    String address = textFieldAddress.getText();

                    // Call the insert method with the correct parameters
                    insertEmployeeData(empId, name, salary, email, phone, address);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddEmployee.this, "Please enter valid data.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener for Home Button
        btnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home homePage = new Home(); // Assuming Home is the main page
                homePage.setVisible(true);
                AddEmployee.this.dispose(); // Close current frame
            }
        });

        // SpringLayout constraints for positioning components
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblNewLabel, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, lblEmpId, 60, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblEmpId, 30, SpringLayout.WEST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldEmpId, 60, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, textFieldEmpId, 120, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textFieldEmpId, -30, SpringLayout.EAST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, lblName, 100, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblName, 30, SpringLayout.WEST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldName, 100, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, textFieldName, 120, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textFieldName, -30, SpringLayout.EAST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, lblSalary, 140, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblSalary, 30, SpringLayout.WEST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldSalary, 140, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, textFieldSalary, 120, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textFieldSalary, -30, SpringLayout.EAST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, lblEmail, 180, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblEmail, 30, SpringLayout.WEST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldEmail, 180, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, textFieldEmail, 120, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textFieldEmail, -30, SpringLayout.EAST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, lblPhone, 220, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblPhone, 30, SpringLayout.WEST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldPhone, 220, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, textFieldPhone, 120, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textFieldPhone, -30, SpringLayout.EAST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, lblAddress, 260, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblAddress, 30, SpringLayout.WEST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, textFieldAddress, 260, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, textFieldAddress, 120, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textFieldAddress, -30, SpringLayout.EAST, contentPane);

        sl_contentPane.putConstraint(SpringLayout.NORTH, btnAddEmployee, 300, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnAddEmployee, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

        // Home Button position at top right corner
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnHome, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnHome, -10, SpringLayout.EAST, contentPane);
    }

    // Method to insert employee data into the database
    private void insertEmployeeData(int empId, String name, int salary, String email, String phone, String address) {
        String sql = "INSERT INTO employee (emp_id, name, salary, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Set the values for the statement
            pstmt.setInt(1, empId);  // Set emp_id as integer
            pstmt.setString(2, name);
            pstmt.setInt(3, salary);  // Set salary as integer
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, address);

            // Execute the query
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Employee added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while adding the employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
