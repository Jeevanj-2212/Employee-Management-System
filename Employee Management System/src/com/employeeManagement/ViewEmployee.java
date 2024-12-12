package com.employeeManagement;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class ViewEmployee extends JFrame {

    private JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewEmployee frame = new ViewEmployee();
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
    public ViewEmployee() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);

        JLabel lblNewLabel = new JLabel("View Employee");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 150, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, -150, SpringLayout.EAST, contentPane);
        lblNewLabel.setFont(new Font("Segoe UI Historic", Font.BOLD, 30));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel);

        // Define column names
        String[] columnNames = { "Employee ID", "Employee Name", "Salary" };

        // Initialize table with no data and defined columns
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            // Override isCellEditable to prevent editing
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };
        
        table = new JTable(model);

        // Add a scroll pane to the table
        JScrollPane scrollPane = new JScrollPane(table);
        sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.SOUTH, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, contentPane);
        contentPane.add(scrollPane);

        // Add the Home button at the top-right corner
        JButton homeButton = new JButton("Home");
        sl_contentPane.putConstraint(SpringLayout.NORTH, homeButton, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, homeButton, -10, SpringLayout.EAST, contentPane);
        contentPane.add(homeButton);
        homeButton.addActionListener(e -> {
            // Open the home page
            Home homePage = new Home();
            homePage.setVisible(true);
            ViewEmployee.this.dispose(); // Close the current window (ViewEmployee)
        });

        try {
            // STEP 1: Loading the driver (optional)
            // Class.forName("com.mysql.cj.jdbc.Driver");
            // System.out.println("Driver Loaded");

            // STEP 2: Establish the connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "22122002");
            System.out.println("Connection Established");

            // STEP 3: Statement creation
            Statement stmt = con.createStatement();
            System.out.println("Statement Created");

            // STEP 4: Querying
            String sql = "SELECT * FROM employee"; // Your table name
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Query executed");

            // STEP 5: Populating the JTable with data from ResultSet
            while (rs.next()) {
                // Fetching data from ResultSet
                int employeeId = rs.getInt("emp_id"); // Make sure column name is correct
                String employeeName = rs.getString("name"); // Make sure column name is correct
                double salary = rs.getDouble("salary"); // Make sure column name is correct

                // Add row to the table model
                model.addRow(new Object[] { employeeId, employeeName, salary });
            }

            // STEP 6: Close resources
            con.close();
            stmt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
