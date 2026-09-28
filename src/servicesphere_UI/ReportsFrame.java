package servicesphere_UI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import servicesphere_Service.ReportService;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportsFrame extends JFrame {

    private ReportService reportService;

    private JLabel totalLabel;
    private JLabel openLabel;
    private JLabel assignedLabel;
    private JLabel progressLabel;
    private JLabel resolvedLabel;
    private JLabel closedLabel;
    private JLabel slaLabel;
    private DefaultTableModel workloadModel;

    public ReportsFrame() {

        reportService = new ReportService();

        setTitle("ServiceSphere - Reports & Analytics");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ================= HEADER =================

        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel =
                new JLabel("Reports & Analytics");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        JLabel subtitleLabel =
                new JLabel("Service Desk Performance Overview");

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);


        // ================= STAT CARDS =================

        JPanel cardsPanel =
                new JPanel(new GridLayout(2, 4, 20, 20));

        totalLabel = new JLabel("0", SwingConstants.CENTER);
        openLabel = new JLabel("0", SwingConstants.CENTER);
        assignedLabel = new JLabel("0", SwingConstants.CENTER);
        progressLabel = new JLabel("0", SwingConstants.CENTER);
        resolvedLabel = new JLabel("0", SwingConstants.CENTER);
        closedLabel = new JLabel("0", SwingConstants.CENTER);
        slaLabel = new JLabel("0", SwingConstants.CENTER);

        cardsPanel.add(
                createCard(
                        "TOTAL TICKETS",
                        totalLabel
                )
        );

        cardsPanel.add(
                createCard(
                        "OPEN",
                        openLabel
                )
        );

        cardsPanel.add(
                createCard(
                        "ASSIGNED",
                        assignedLabel
                )
        );

        cardsPanel.add(
                createCard(
                        "IN PROGRESS",
                        progressLabel
                )
        );

        cardsPanel.add(
                createCard(
                        "RESOLVED",
                        resolvedLabel
                )
        );

        cardsPanel.add(
                createCard(
                        "CLOSED",
                        closedLabel
                )
        );

        cardsPanel.add(
                createCard(
                        "SLA BREACHED",
                        slaLabel
                )
        );

        JPanel centerPanel =
                new JPanel(new BorderLayout(15, 15));

        centerPanel.add(
                cardsPanel,
                BorderLayout.NORTH
        );
        
        
     // ================= TECHNICIAN WORKLOAD =================

        JLabel workloadTitle =
                new JLabel("Technician Workload");

        workloadTitle.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        String[] columns = {
                "Technician",
                "Total Tickets",
                "Resolved",
                "Active"
        };

        workloadModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {
                        return false;
                    }
                };

        JTable workloadTable =
                new JTable(workloadModel);

        workloadTable.setRowHeight(30);

        List<Object[]> workload =
                reportService.getTechnicianWorkload();

        for (Object[] row : workload) {

            workloadModel.addRow(row);
        }

        JScrollPane workloadScrollPane =
                new JScrollPane(workloadTable);

        JPanel workloadPanel =
                new JPanel(new BorderLayout(10, 10));

        workloadPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        0,
                        0,
                        0
                )
        );

        workloadPanel.add(
                workloadTitle,
                BorderLayout.NORTH
        );

        workloadPanel.add(
                workloadScrollPane,
                BorderLayout.CENTER
        );

        centerPanel.add(
                workloadPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        // ================= BOTTOM =================

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        JButton refreshButton =
                new JButton("Refresh Reports");

        JButton backButton =
                new JButton("Back");

        bottomPanel.add(
                refreshButton,
                BorderLayout.WEST
        );

        bottomPanel.add(
                backButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ================= BUTTON ACTIONS =================

        refreshButton.addActionListener(e -> {

            loadReports();

            workloadModel.setRowCount(0);

            List<Object[]> refreshedWorkload =
                    reportService.getTechnicianWorkload();

            for (Object[] row : refreshedWorkload) {

                workloadModel.addRow(row);
            }
        });

        backButton.addActionListener(e -> {

            dispose();

            new AdminDashboardFrame();

        });


        add(mainPanel);

        loadReports();

        setVisible(true);
    }


    // ================= CREATE CARD =================

    private JPanel createCard(
            String title,
            JLabel valueLabel) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                java.awt.Color.LIGHT_GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                15,
                                20,
                                15
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }


    // ================= LOAD REPORTS =================

    private void loadReports() {

        totalLabel.setText(
                String.valueOf(
                        reportService.getTotalTickets()
                )
        );

        openLabel.setText(
                String.valueOf(
                        reportService.getOpenTickets()
                )
        );

        assignedLabel.setText(
                String.valueOf(
                        reportService.getAssignedTickets()
                )
        );

        progressLabel.setText(
                String.valueOf(
                        reportService.getInProgressTickets()
                )
        );

        resolvedLabel.setText(
                String.valueOf(
                        reportService.getResolvedTickets()
                )
        );

        closedLabel.setText(
                String.valueOf(
                        reportService.getClosedTickets()
                )
        );

        slaLabel.setText(
                String.valueOf(
                        reportService.getSLABreachedTickets()
                )
        );
    }


    // ================= MAIN =================


}