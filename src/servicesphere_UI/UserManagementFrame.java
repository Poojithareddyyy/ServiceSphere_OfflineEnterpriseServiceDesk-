package servicesphere_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import servicesphere_Service.UserService;

public class UserManagementFrame extends JFrame {

    private JTextField fullNameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField departmentField;
    private JTextField phoneField;

    private JButton createButton;
    private JButton clearButton;
    private JButton backButton;
    private JButton logoutButton;

    public UserManagementFrame() {


        setTitle("ServiceSphere - User Management");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setLayout(
                new BorderLayout()
        );


        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                new Color(245, 247, 250)
        );


        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(Color.WHITE);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        // BACK BUTTON

        backButton =
                new JButton("← Back");

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        backButton.setFocusPainted(false);

        // HEADING

        JLabel heading =
                new JLabel("User Management");

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        heading.setForeground(
                new Color(25, 35, 55)
        );

        heading.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // LOGOUT

        logoutButton =
                new JButton("Logout");

        logoutButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        logoutButton.setFocusPainted(false);

        headerPanel.add(
                backButton,
                BorderLayout.WEST
        );

        headerPanel.add(
                heading,
                BorderLayout.CENTER
        );

        headerPanel.add(
                logoutButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        JPanel centerPanel =
                new JPanel(new GridBagLayout());

        centerPanel.setBackground(
                new Color(245, 247, 250)
        );


        JPanel formCard =
                new JPanel(new GridBagLayout());

        formCard.setBackground(Color.WHITE);

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 232)
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 45, 30, 45
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(
                        8, 10, 8, 10
                );


        JLabel formTitle =
                new JLabel("Create New Employee");

        formTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        formTitle.setForeground(
                new Color(25, 35, 55)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(
                        5, 10, 25, 10
                );

        formCard.add(
                formTitle,
                gbc
        );


        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.insets =
                new Insets(
                        8, 10, 5, 10
                );

        formCard.add(
                createLabel("Full Name"),
                gbc
        );

        fullNameField =
                createTextField();

        gbc.gridx = 1;

        formCard.add(
                fullNameField,
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 2;

        formCard.add(
                createLabel("Username"),
                gbc
        );

        usernameField =
                createTextField();

        gbc.gridx = 1;

        formCard.add(
                usernameField,
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 3;

        formCard.add(
                createLabel("Email"),
                gbc
        );

        emailField =
                createTextField();

        gbc.gridx = 1;

        formCard.add(
                emailField,
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 4;

        formCard.add(
                createLabel("Password"),
                gbc
        );

        passwordField =
                new JPasswordField();

        passwordField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        passwordField.setPreferredSize(
                new Dimension(400, 40)
        );

        gbc.gridx = 1;

        formCard.add(
                passwordField,
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 5;

        formCard.add(
                createLabel("Department"),
                gbc
        );

        departmentField =
                createTextField();

        gbc.gridx = 1;

        formCard.add(
                departmentField,
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 6;

        formCard.add(
                createLabel("Phone"),
                gbc
        );

        phoneField = new JTextField();

        phoneField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c) || phoneField.getText().length() >= 10) {
                    e.consume();
                }
            }
        });

        phoneField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        phoneField.setPreferredSize(
                new Dimension(400, 40)
        );

        gbc.gridx = 1;
        formCard.add(phoneField, gbc);


        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(Color.WHITE);

        createButton =
                new JButton("CREATE EMPLOYEE");

        createButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        createButton.setPreferredSize(
                new Dimension(190, 42)
        );

        createButton.setFocusPainted(false);

        clearButton =
                new JButton("CLEAR");

        clearButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        clearButton.setPreferredSize(
                new Dimension(110, 42)
        );

        clearButton.setFocusPainted(false);

        buttonPanel.add(createButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(
                        22, 10, 5, 10
                );

        formCard.add(
                buttonPanel,
                gbc
        );


        JLabel informationLabel =
                new JLabel(
                        "New employees will be created with the EMPLOYEE role."
                );

        informationLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        informationLabel.setForeground(
                new Color(100, 105, 115)
        );

        informationLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(
                        8, 10, 5, 10
                );

        formCard.add(
                informationLabel,
                gbc
        );


        GridBagConstraints centerGbc =
                new GridBagConstraints();

        centerGbc.gridx = 0;
        centerGbc.gridy = 0;

        centerGbc.weightx = 1.0;
        centerGbc.weighty = 1.0;

        centerGbc.anchor =
                GridBagConstraints.CENTER;

        centerPanel.add(
                formCard,
                centerGbc
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        createButton.addActionListener(e ->
                createEmployee()
        );

        clearButton.addActionListener(e ->
                clearForm()
        );

        backButton.addActionListener(e -> {

            dispose();

            new AdminDashboardFrame();
        });

        logoutButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Confirm Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice ==
                    JOptionPane.YES_OPTION) {

                dispose();

                new LoginFrame();
            }
        });


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void createEmployee() {

        String fullName =
                fullNameField
                        .getText()
                        .trim();

        String username =
                usernameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String department =
                departmentField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();


        if (fullName.isEmpty() ||
            username.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty() ||
            department.isEmpty() ||
            phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }
        
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!email.contains("@")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }



        UserService userService =
                new UserService();

        boolean created =
                userService.addEmployee(
                        fullName,
                        username,
                        email,
                        password,
                        department,
                        phone
                );

        if (created) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to create employee.\n"
                    + "Username or email may already exist.",
                    "Creation Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void clearForm() {

        fullNameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        departmentField.setText("");
        phoneField.setText("");

        fullNameField.requestFocus();
    }

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        field.setPreferredSize(
                new Dimension(400, 40)
        );

        return field;
    }


    private JLabel createLabel(
            String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        label.setForeground(
                new Color(45, 55, 70)
        );

        return label;
    }

}
