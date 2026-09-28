package servicesphere_UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import servicesphere_Service.TicketService;

public class MyTicketsFrame extends JFrame {

    private String username;

    private JTable ticketTable;
    private DefaultTableModel tableModel;

    public MyTicketsFrame(String username) {

        this.username = username;

        setTitle("ServiceSphere - My Tickets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 600));
        setSize(1200, 700);
        setLocationRelativeTo(null);

        buildUI();

        loadTickets();
        setVisible(true);
    }

    private void buildUI() {

        setLayout(new BorderLayout(15, 15));

        // =========================
        // TOP HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 10, 20
                )
        );

        JLabel titleLabel =
                new JLabel("My Tickets");

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 26)
        );

        JLabel userLabel =
                new JLabel("Logged in as: " + username);

        userLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                userLabel,
                BorderLayout.EAST
        );

        add(headerPanel, BorderLayout.NORTH);


        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Ticket Number",
                "Issue",
                "Category",
                "Priority",
                "Status",
                "Created",
                "SLA Due",
                "SLA Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        ticketTable = new JTable(tableModel);

        ticketTable.setRowHeight(30);
        ticketTable.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        ticketTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        ticketTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        JScrollPane scrollPane =
                new JScrollPane(ticketTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 20, 0, 20
                )
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM BUTTONS
        // =========================

        JPanel bottomPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        10
                ));

        JButton refreshButton =
                new JButton("Refresh");

        JButton backButton =
                new JButton("Back");

        JButton logoutButton =
                new JButton("Logout");

        refreshButton.addActionListener(e -> {
            loadTickets();
        });

        backButton.addActionListener(e -> {

            dispose();

            new EmployeeDashboardFrame(username);
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

        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);
        bottomPanel.add(logoutButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }


    // =========================
    // LOAD TICKETS
    // =========================

    private void loadTickets() {

        tableModel.setRowCount(0);

        TicketService ticketService =
                new TicketService();

        List<Object[]> tickets =
                ticketService.getMyTickets(username);

        for (Object[] ticket : tickets) {

            tableModel.addRow(ticket);
        }

        if (tickets.isEmpty()) {

            // No popup here.
            // Empty table is cleaner UX.
        }
    }

    
}