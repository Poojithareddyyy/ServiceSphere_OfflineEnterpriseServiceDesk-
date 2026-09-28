package servicesphere_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import servicesphere_Service.TicketService;

public class EmployeeDashboardFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    
    private String username;
    
    private JTextField titleField;

    public EmployeeDashboardFrame(String username) {
    	
    	this.username = username;

        // =========================
        // FRAME SETTINGS
        // =========================

        setTitle("ServiceSphere - Employee Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setLayout(new BorderLayout());

        // =========================
        // SIDEBAR
        // =========================

        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(new Color(25, 35, 55));
        sidebarPanel.setPreferredSize(new Dimension(240, 0));

        JLabel sidebarTitle = new JLabel(
                "<html><center>SERVICE<br>SPHERE</center></html>"
        );

        sidebarTitle.setFont(
                new Font("SansSerif", Font.BOLD, 25)
        );

        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        sidebarTitle.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 10, 30, 10
                )
        );

        sidebarPanel.add(
                sidebarTitle,
                BorderLayout.NORTH
        );

        // =========================
        // MENU
        // =========================

        JPanel menuPanel =
                new JPanel(
                        new GridLayout(4, 1, 0, 10)
                );

        menuPanel.setBackground(
                new Color(25, 35, 55)
        );

        menuPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        JButton dashboardButton =
                createMenuButton("Dashboard");
        
        dashboardButton.addActionListener(e -> {
            titleField.requestFocusInWindow();
        });

        JButton raiseTicketButton =
                createMenuButton("Raise Ticket");
        
        raiseTicketButton.addActionListener(e -> {
            titleField.requestFocusInWindow();
        });

        JButton myTicketsButton =
                createMenuButton("My Tickets");
        
        myTicketsButton.addActionListener(e -> {
            dispose();
            new MyTicketsFrame(username);
        });

        JButton myAssetsButton =
                createMenuButton("My Assets");
        
        
        myAssetsButton.addActionListener(e -> {
            dispose();
            new MyAssetsFrame(username);
        });

        menuPanel.add(dashboardButton);
        menuPanel.add(raiseTicketButton);
        menuPanel.add(myTicketsButton);
        menuPanel.add(myAssetsButton);

        sidebarPanel.add(
                menuPanel,
                BorderLayout.CENTER
        );

        // =========================
        // LOGOUT
        // =========================

        JButton logoutButton =
                createMenuButton("Logout");
        
        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                new LoginFrame();
            }
        });

        JPanel logoutPanel =
                new JPanel(new BorderLayout());

        logoutPanel.setBackground(
                new Color(25, 35, 55)
        );

        logoutPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 15, 20, 15
                )
        );

        logoutPanel.add(
                logoutButton,
                BorderLayout.CENTER
        );

        sidebarPanel.add(
                logoutPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // MAIN CONTENT
        // =========================

        contentPanel =
                new JPanel(new BorderLayout());

        contentPanel.setBackground(
                new Color(245, 247, 250)
        );

        // =========================
        // TOP HEADER
        // =========================

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(Color.WHITE);

        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        JLabel heading =
                new JLabel("Employee Dashboard");

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        heading.setForeground(
                new Color(25, 35, 55)
        );

        JLabel employeeLabel =
                new JLabel("Logged in as: " + username);

        employeeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        employeeLabel.setForeground(
                new Color(90, 100, 115)
        );

        topPanel.add(
                heading,
                BorderLayout.WEST
        );

        topPanel.add(
                employeeLabel,
                BorderLayout.EAST
        );

        contentPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =========================
        // MAIN EMPLOYEE AREA
        // =========================

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                new Color(245, 247, 250)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        // =========================
        // STAT CARDS
        // =========================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(1, 3, 20, 20)
                );

        statsPanel.setBackground(
                new Color(245, 247, 250)
        );
        
        TicketService ticketService =
                new TicketService();

        int myTickets =
                ticketService.getMyTicketCount(username);

        int openTickets =
                ticketService.getMyOpenTicketCount(username);

        int resolvedTickets =
                ticketService.getMyResolvedTicketCount(username);

        statsPanel.add(
                createStatCard(
                        String.valueOf(myTickets),
                        "My Tickets"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(openTickets),
                        "Open Tickets"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(resolvedTickets),
                        "Resolved Tickets"
                )
        );

        mainPanel.add(
                statsPanel,
                BorderLayout.NORTH
        );

        // =========================
        // RAISE TICKET FORM
        // =========================

        JPanel ticketPanel =
                new JPanel(new GridBagLayout());

        ticketPanel.setBackground(Color.WHITE);

        ticketPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 232)
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 30, 25, 30
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(8, 8, 8, 8);

        // =========================
        // FORM HEADING
        // =========================

        JLabel formHeading =
                new JLabel("Raise a New Service Ticket");

        formHeading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        formHeading.setForeground(
                new Color(25, 35, 55)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(
                        5, 8, 20, 8
                );

        ticketPanel.add(
                formHeading,
                gbc
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                createFormLabel("Issue Title");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        gbc.insets = new Insets(8, 8, 5, 8 );

        ticketPanel.add(titleLabel,gbc);

        titleField = new JTextField();

        titleField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        titleField.setPreferredSize(
                new Dimension(400, 40)
        );

        gbc.gridx = 1;

        ticketPanel.add(
                titleField,
                gbc
        );

        // =========================
        // CATEGORY
        // =========================

        JLabel categoryLabel =
                createFormLabel("Category");

        gbc.gridx = 0;
        gbc.gridy = 2;

        ticketPanel.add(
                categoryLabel,
                gbc
        );

        JComboBox<String> categoryCombo =
                new JComboBox<>(
                        new String[] {
                                "Hardware",
                                "Software",
                                "Network",
                                "Access / Account",
                                "Other"
                        }
                );

        categoryCombo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        categoryCombo.setPreferredSize(
                new Dimension(400, 40)
        );

        gbc.gridx = 1;

        ticketPanel.add(
                categoryCombo,
                gbc
        );

        // =========================
        // PRIORITY
        // =========================

        JLabel priorityLabel =
                createFormLabel("Priority");

        gbc.gridx = 0;
        gbc.gridy = 3;

        ticketPanel.add(
                priorityLabel,
                gbc
        );

        JComboBox<String> priorityCombo =
                new JComboBox<>(
                        new String[] {
                                "Low",
                                "Medium",
                                "High",
                                "Critical"
                        }
                );

        priorityCombo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        priorityCombo.setPreferredSize(
                new Dimension(400, 40)
        );

        gbc.gridx = 1;

        ticketPanel.add(
                priorityCombo,
                gbc
        );

        // =========================
        // ASSET
        // =========================

        JLabel assetLabel =
                createFormLabel("Related Asset");

        gbc.gridx = 0;
        gbc.gridy = 4;

        ticketPanel.add(
                assetLabel,
                gbc
        );

        JComboBox<String> assetCombo =
                new JComboBox<>(
                        new String[] {
                                "Select Asset",
                                "Laptop - LAP001",
                                "Desktop - DESK001",
                                "Monitor - MON001",
                                "Other"
                        }
                );

        assetCombo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        assetCombo.setPreferredSize(
                new Dimension(400, 40)
        );

        gbc.gridx = 1;

        ticketPanel.add(
                assetCombo,
                gbc
        );

        // =========================
        // DESCRIPTION
        // =========================

        JLabel descriptionLabel =
                createFormLabel("Description");

        gbc.gridx = 0;
        gbc.gridy = 5;

        ticketPanel.add(
                descriptionLabel,
                gbc
        );

        JTextArea descriptionArea =
                new JTextArea(5, 30);

        descriptionArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );

        descriptionScroll.setPreferredSize(
                new Dimension(400, 110)
        );

        gbc.gridx = 1;

        ticketPanel.add(
                descriptionScroll,
                gbc
        );

        // =========================
        // SUBMIT BUTTON
        // =========================

        JButton submitButton =
                new JButton("SUBMIT TICKET");

        submitButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        submitButton.setPreferredSize(
                new Dimension(220, 45)
        );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(
                        20, 8, 5, 8
                );

        ticketPanel.add(
                submitButton,
                gbc
        );
        
        submitButton.addActionListener(e -> {

            String issueTitle =
                    titleField.getText().trim();

            String category =
                    (String) categoryCombo.getSelectedItem();

            String priority =
                    (String) priorityCombo.getSelectedItem();

            String asset =
                    (String) assetCombo.getSelectedItem();

            String description =
                    descriptionArea.getText().trim();

            // =========================
            // VALIDATION
            // =========================

            if (issueTitle.isEmpty()) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Please enter the issue title.",
                        "Validation Error",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (description.isEmpty()) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Please describe the issue.",
                        "Validation Error",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // =========================
            // CREATE TICKET
            // =========================

            

            String ticketNumber =
                    ticketService.createTicket(
                            username,
                            issueTitle,
                            category,
                            priority,
                            asset,
                            description
                    );

            if (ticketNumber != null) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Ticket created successfully!\n\n"
                        + "Ticket Number: " + ticketNumber
                        + "\nStatus: OPEN",
                        "Ticket Created",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                // Clear form

                titleField.setText("");
                categoryCombo.setSelectedIndex(0);
                priorityCombo.setSelectedIndex(0);
                assetCombo.setSelectedIndex(0);
                descriptionArea.setText("");

            } else {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Unable to create ticket.\n"
                        + "Please try again.",
                        "Creation Failed",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        

        // =========================
        // ADD FORM
        // =========================

        JPanel formContainer =
                new JPanel(
                        new GridBagLayout()
                );

        formContainer.setBackground(
                new Color(245, 247, 250)
        );

        GridBagConstraints formContainerGbc =
                new GridBagConstraints();

        formContainerGbc.gridx = 0;
        formContainerGbc.gridy = 0;

        formContainerGbc.weightx = 1;
        formContainerGbc.weighty = 1;

        formContainerGbc.fill =
                GridBagConstraints.BOTH;

        formContainerGbc.insets =
                new Insets(
                        20, 0, 0, 0
                );

        formContainer.add(
                ticketPanel,
                formContainerGbc
        );

        mainPanel.add(
                formContainer,
                BorderLayout.CENTER
        );

        contentPanel.add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =========================
        // ADD TO FRAME
        // =========================

        add(
                sidebarPanel,
                BorderLayout.WEST
        );

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        // =========================
        // DISPLAY
        // =========================

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setVisible(true);
    }

    // =====================================================
    // MENU BUTTON
    // =====================================================

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(
                new Color(35, 48, 72)
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 15, 12, 15
                )
        );

        return button;
    }

    // =====================================================
    // STAT CARD
    // =====================================================

    private JPanel createStatCard(
            String number,
            String title
    ) {

        JPanel card =
                new JPanel(
                        new GridBagLayout()
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 232)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel numberLabel =
                new JLabel(number);

        numberLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        numberLabel.setForeground(
                new Color(25, 35, 55)
        );

        card.add(
                numberLabel,
                gbc
        );

        gbc.gridy = 1;

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        titleLabel.setForeground(
                new Color(90, 100, 115)
        );

        card.add(
                titleLabel,
                gbc
        );

        return card;
    }

    // =====================================================
    // FORM LABEL
    // =====================================================

    private JLabel createFormLabel(
            String text
    ) {

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