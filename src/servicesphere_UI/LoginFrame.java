package servicesphere_UI;

import servicesphere_Service.LoginService;

import javax.swing.JOptionPane;

import java.awt.*;

import javax.swing.*;

public class LoginFrame extends JFrame  {

    private JLabel titleLabel;
    private JLabel subtitleLabel;

    private JLabel loginHeading;
    private JLabel roleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JComboBox<String> roleComboBox;

    private JTextField usernameField;

    private JPasswordField passwordField;

    private JButton loginButton;

    public LoginFrame() {


        setTitle("ServiceSphere - Login");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(1000, 700));

        setLayout(new BorderLayout());



        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(new Color(245, 247, 250));



        JPanel headerPanel = new JPanel(
                new GridBagLayout()
        );

        headerPanel.setBackground(
                new Color(245, 247, 250)
        );

        GridBagConstraints header = new GridBagConstraints();

        header.gridx = 0;

        header.anchor = GridBagConstraints.CENTER;



        titleLabel = new JLabel("SERVICE SPHERE");

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        40
                )
        );

        titleLabel.setForeground(
                new Color(25, 35, 55)
        );

        header.gridy = 0;

        header.insets = new Insets(
                35, 20, 8, 20
        );

        headerPanel.add(
                titleLabel,
                header
        );



        subtitleLabel = new JLabel(
                "Offline Enterprise Service Desk & Incident Management System"
        );

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        18
                )
        );

        subtitleLabel.setForeground(
                new Color(80, 90, 105)
        );

        header.gridy = 1;

        header.insets = new Insets(
                0, 20, 30, 20
        );

        headerPanel.add(
                subtitleLabel,
                header
        );



        JPanel loginCard = new JPanel(
                new GridBagLayout()
        );

        loginCard.setBackground(Color.WHITE);

        loginCard.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(220, 225, 232),
                                1
                        ),

                        BorderFactory.createEmptyBorder(
                                35,
                                50,
                                35,
                                50
                        )
                )
        );

        GridBagConstraints form =
                new GridBagConstraints();

        form.fill =
                GridBagConstraints.HORIZONTAL;

        form.weightx = 1.0;

        form.insets =
                new Insets(10, 10, 10, 10);



        loginHeading =
                new JLabel(
                        "SIGN IN TO SERVICE SPHERE"
                );

        loginHeading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        loginHeading.setForeground(
                new Color(25, 35, 55)
        );

        loginHeading.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        form.gridx = 0;

        form.gridy = 0;

        form.gridwidth = 2;

        form.insets =
                new Insets(5, 10, 25, 10);

        loginCard.add(
                loginHeading,
                form
        );



        roleLabel =
                new JLabel("Login as");

        roleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        form.gridx = 0;

        form.gridy = 1;

        form.gridwidth = 1;

        form.insets =
                new Insets(10, 10, 6, 10);

        loginCard.add(
                roleLabel,
                form
        );



        roleComboBox =
                new JComboBox<>(
                        new String[] {
                                "Employee",
                                "Admin"
                        }
                );

        roleComboBox.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        roleComboBox.setPreferredSize(
                new Dimension(360, 42)
        );

        form.gridx = 1;

        loginCard.add(
                roleComboBox,
                form
        );



        usernameLabel =
                new JLabel(
                        "Username / Email"
                );

        usernameLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        form.gridx = 0;

        form.gridy = 2;

        loginCard.add(
                usernameLabel,
                form
        );



        usernameField =
                new JTextField();

        usernameField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        usernameField.setPreferredSize(
                new Dimension(360, 42)
        );

        form.gridx = 1;

        loginCard.add(
                usernameField,
                form
        );



        passwordLabel =
                new JLabel("Password");

        passwordLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        form.gridx = 0;

        form.gridy = 3;

        loginCard.add(
                passwordLabel,
                form
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
                new Dimension(360, 42)
        );

        form.gridx = 1;

        loginCard.add(
                passwordField,
                form
        );



        loginButton =
                new JButton("SIGN IN");

        loginButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        loginButton.setPreferredSize(
                new Dimension(220, 45)
        );

        form.gridx = 0;

        form.gridy = 4;

        form.gridwidth = 2;

        form.insets =
                new Insets(25, 10, 10, 10);

        loginCard.add(
                loginButton,
                form
        );
        
        loginButton.addActionListener(e -> {

            String username =
                    usernameField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword()
                    );

            String role =
                    roleComboBox
                            .getSelectedItem()
                            .toString()
                            .toUpperCase();

            // Check empty fields
            if (username.isEmpty() ||
                password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Create LoginService object
            LoginService loginService =
                    new LoginService();

            // Authenticate using MySQL
            String authenticatedRole =
                    loginService.authenticate(
                            username,
                            password,
                            role
                    );



            if ("ADMIN".equals(authenticatedRole)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!",
                        "Welcome",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

                new AdminDashboardFrame();

            }


            else if ("EMPLOYEE".equals(authenticatedRole)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!",
                        "Welcome",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

                new EmployeeDashboardFrame(username);

            }



            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username, password, or role.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });




        JLabel accessLabel =
                new JLabel(
                        "Authorized access only"
                );

        accessLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        accessLabel.setForeground(
                new Color(100, 105, 115)
        );

        accessLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        form.gridx = 0;

        form.gridy = 5;

        form.gridwidth = 2;

        form.insets =
                new Insets(5, 10, 0, 10);

        loginCard.add(
                accessLabel,
                form
        );




        JPanel centerPanel =
                new JPanel(
                        new GridBagLayout()
                );

        centerPanel.setBackground(
                new Color(245, 247, 250)
        );

        GridBagConstraints center =
                new GridBagConstraints();

        center.gridx = 0;

        center.gridy = 0;

        center.weightx = 1;

        center.weighty = 1;

        center.anchor =
                GridBagConstraints.CENTER;

        centerPanel.add(
                loginCard,
                center
        );



        JLabel footerLabel =
                new JLabel(
                        "ServiceSphere  •  Offline Service Desk"
                );

        footerLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        footerLabel.setForeground(
                new Color(100, 105, 115)
        );

        footerLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        footerLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 10, 15, 10
                )
        );



        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                footerLabel,
                BorderLayout.SOUTH
        );

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


    public static void main(String[] args) {

        new LoginFrame();
    }
}
