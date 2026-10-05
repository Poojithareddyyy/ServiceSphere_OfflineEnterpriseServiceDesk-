package servicesphere_UI;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import servicesphere_Service.AdminDashboardService;

import java.util.List;

public class AdminDashboardFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;

    public AdminDashboardFrame() {

    
        setTitle("ServiceSphere - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setLayout(new BorderLayout());


        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(new Color(25, 35, 55));
        sidebarPanel.setPreferredSize(new Dimension(240, 0));

        JLabel sidebarTitle = new JLabel(
                "<html><center>SERVICE<br>SPHERE</center></html>"
        );

        sidebarTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sidebarTitle.setBorder(
                BorderFactory.createEmptyBorder(30, 10, 30, 10)
        );

        sidebarPanel.add(sidebarTitle, BorderLayout.NORTH);

      
        JPanel menuPanel = new JPanel(new GridLayout(8, 1, 0, 8));
        menuPanel.setBackground(new Color(25, 35, 55));
        menuPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        JButton dashboardButton = createMenuButton("Dashboard");
        
        dashboardButton.addActionListener(e -> {
            dispose();
            new AdminDashboardFrame();
        });
        
        JButton ticketsButton = createMenuButton("Tickets");
        
        ticketsButton.addActionListener(e -> {

            dispose();

            new TicketManagementFrame();

        });
        
        JButton technicianButton = createMenuButton("Technicians");
        
        technicianButton.addActionListener(e -> {

            dispose();

            new TechnicianManagementFrame();

        });
        
        JButton assetsButton = createMenuButton("Assets");
        JButton usersButton = createMenuButton("Users");
        
        assetsButton.addActionListener(e -> {

            dispose();

            new AssetManagementFrame();

        });
        
        usersButton.addActionListener(e -> {

            dispose();

            new UserManagementFrame();
        });
        
        JButton slaButton = createMenuButton("SLA Monitor");
        
        slaButton.addActionListener(e -> {

            dispose();

            new SLAMonitorFrame();

        });
        
        JButton reportsButton = createMenuButton("Reports");
        
        reportsButton.addActionListener(e -> {
            dispose();
            new ReportsFrame();
        });
        
        
        JButton knowledgeBaseButton =
                createMenuButton("Knowledge Base");

        knowledgeBaseButton.addActionListener(e -> {
            dispose();
            new KnowledgeBaseFrame();
        });

        menuPanel.add(dashboardButton);
        menuPanel.add(ticketsButton);
        menuPanel.add(technicianButton);
        menuPanel.add(assetsButton);
        menuPanel.add(usersButton);
        menuPanel.add(slaButton);
        menuPanel.add(reportsButton);
        menuPanel.add(knowledgeBaseButton);
        

        sidebarPanel.add(menuPanel, BorderLayout.CENTER);

        // Logout
        JButton logoutButton = createMenuButton("Logout");
        
        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                new LoginFrame();
            }
        });

        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setBackground(new Color(25, 35, 55));
        logoutPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 20, 15)
        );

        logoutPanel.add(logoutButton, BorderLayout.CENTER);

        sidebarPanel.add(logoutPanel, BorderLayout.SOUTH);


        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JLabel heading = new JLabel("Admin Dashboard");

        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(new Color(25, 35, 55));

        JLabel adminLabel = new JLabel("Administrator");

        adminLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        adminLabel.setForeground(new Color(90, 100, 115));

        topPanel.add(heading, BorderLayout.WEST);
        topPanel.add(adminLabel, BorderLayout.EAST);

        contentPanel.add(topPanel, BorderLayout.NORTH);


        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(new Color(245, 247, 250));
        dashboardPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );


     AdminDashboardService dashboardService =
             new AdminDashboardService();

     

        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        statsPanel.setBackground(new Color(245, 247, 250));

        statsPanel.add(
                createStatCard(
                        String.valueOf(
                                dashboardService.getTotalTickets()
                        ),
                        "Total Tickets"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(
                                dashboardService.getActiveTickets()
                        ),
                        "Active Tickets"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(
                                dashboardService.getHighPriorityTickets()
                        ),
                        "High Priority"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(
                                dashboardService.getSLABreachedTickets()
                        ),
                        "SLA Breached"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(
                                dashboardService.getResolvedTickets()
                        ),
                        "Resolved Tickets"
                )
        );

        statsPanel.add(
                createStatCard(
                        String.valueOf(
                                dashboardService.getTechnicianCount()
                        ),
                        "Technicians"
                )
        );

        dashboardPanel.add(statsPanel, BorderLayout.NORTH);


        JPanel recentPanel = new JPanel(new BorderLayout());

        recentPanel.setBackground(Color.WHITE);

        recentPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 232)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 25, 20, 25
                        )
                )
        );

        JLabel recentHeading = new JLabel("Recent Tickets");

        recentHeading.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        recentHeading.setForeground(
                new Color(25, 35, 55)
        );

        recentPanel.add(
                recentHeading,
                BorderLayout.NORTH
        );


        List<Object[]> recentTickets =
                dashboardService.getRecentTickets();

        JPanel ticketList =
                new JPanel(
                        new GridLayout(
                                Math.max(recentTickets.size(), 1),
                                1,
                                0,
                                10
                        )
                );

        ticketList.setBackground(Color.WHITE);

        if (recentTickets.isEmpty()) {

            ticketList.add(
                    createTicketRow(
                            "-",
                            "No tickets found",
                            "-"
                    )
            );

        } else {

            for (Object[] ticket : recentTickets) {

                String ticketNumber =
                        (String) ticket[0];

                String issueTitle =
                        (String) ticket[1];

                String status =
                        (String) ticket[2];

                ticketList.add(
                        createTicketRow(
                                ticketNumber,
                                issueTitle,
                                status
                        )
                );
            }
        }

        recentPanel.add(
                ticketList,
                BorderLayout.CENTER
        );

        dashboardPanel.add(
                recentPanel,
                BorderLayout.CENTER
        );

        contentPanel.add(
                dashboardPanel,
                BorderLayout.CENTER
        );


        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(35, 48, 72));

        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 15, 12, 15
                )
        );

        return button;
    }


    private JPanel createStatCard(
            String number,
            String title
    ) {

        JPanel card = new JPanel(new GridBagLayout());

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 232)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel numberLabel =
                new JLabel(number);

        numberLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        32
                )
        );

        numberLabel.setForeground(
                new Color(25, 35, 55)
        );

        card.add(numberLabel, gbc);

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

        card.add(titleLabel, gbc);

        return card;
    }


    private JPanel createTicketRow(
            String ticketId,
            String description,
            String status
    ) {

        JPanel row =
                new JPanel(new BorderLayout());

        row.setBackground(
                new Color(248, 249, 251)
        );

        row.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 15, 12, 15
                )
        );

        JLabel ticketLabel =
                new JLabel(
                        "<html><b>"
                        + ticketId
                        + "</b><br>"
                        + description
                        + "</html>"
                );

        ticketLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JLabel statusLabel =
                new JLabel(status);

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        row.add(
                ticketLabel,
                BorderLayout.CENTER
        );

        row.add(
                statusLabel,
                BorderLayout.EAST
        );

        return row;
    }


   
}
