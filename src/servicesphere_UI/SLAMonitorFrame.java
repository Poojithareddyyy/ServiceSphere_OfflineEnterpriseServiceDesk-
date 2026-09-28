package servicesphere_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import servicesphere_Service.TicketService;
import servicesphere_Service.SLAService;

public class SLAMonitorFrame extends JFrame {

    private JTable slaTable;
    private DefaultTableModel tableModel;

    private TicketService ticketService =
            new TicketService();

    public SLAMonitorFrame() {

        // ==========================================
        // FRAME SETTINGS
        // ==========================================

        setTitle("ServiceSphere - SLA Monitor");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1200, 700)
        );

        setLayout(new BorderLayout());


        // ==========================================
        // HEADER
        // ==========================================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(Color.WHITE);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );


        JLabel heading =
                new JLabel("SLA Monitor");

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


        JLabel subHeading =
                new JLabel(
                        "Monitor service-level deadlines"
                );

        subHeading.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        subHeading.setForeground(
                new Color(90, 100, 115)
        );


        headerPanel.add(
                heading,
                BorderLayout.WEST
        );

        headerPanel.add(
                subHeading,
                BorderLayout.EAST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // ==========================================
        // TABLE
        // ==========================================

        String[] columns = {
                "Ticket ID",
                "Ticket Number",
                "Created By",
                "Priority",
                "Status",
                "Started At",
                "Due At",
                "Remaining",
                "SLA Status",
                "Technician"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        slaTable =
                new JTable(tableModel);

        slaTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        slaTable.setRowHeight(32);

        slaTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        slaTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(slaTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 10, 30
                )
        );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                15
                        )
                );

        buttonPanel.setBackground(
                new Color(245, 247, 250)
        );


        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        refreshButton.setFocusPainted(false);


        JButton detailsButton =
                new JButton("View Details");

        detailsButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        detailsButton.setFocusPainted(false);


        JButton backButton =
                new JButton("Back");

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        backButton.setFocusPainted(false);


        buttonPanel.add(refreshButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(backButton);


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // LOAD SLA DATA
        // ==========================================

        loadSLAData();


        // ==========================================
        // REFRESH
        // ==========================================

        refreshButton.addActionListener(e -> {

            loadSLAData();

        });


        // ==========================================
        // VIEW DETAILS
        // ==========================================

        detailsButton.addActionListener(e -> {

            viewSelectedSLA();

        });


        // ==========================================
        // BACK
        // ==========================================

        backButton.addActionListener(e -> {

            dispose();

            new AdminDashboardFrame();

        });


        // ==========================================
        // DISPLAY
        // ==========================================

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setVisible(true);
    }


    // ==========================================
    // LOAD SLA DATA
    // ==========================================

    private void loadSLAData() {
    	
    	SLAService slaService = new SLAService();
    	slaService.processSLABreaches();

        tableModel.setRowCount(0);

        List<Object[]> slaList =
                ticketService.getSLAMonitorData();


        for (Object[] sla : slaList) {

            String technician =
                    sla[9] == null
                            ? "Unassigned"
                            : sla[9].toString();


            long remainingMinutes =
                    ((Number) sla[7]).longValue();


            String remainingText;


            if (remainingMinutes < 0) {

                remainingText =
                        Math.abs(remainingMinutes)
                        + " min overdue";

            } else {

                remainingText =
                        remainingMinutes
                        + " min";
            }


            tableModel.addRow(
                    new Object[] {

                            sla[0],

                            sla[1],

                            sla[2],

                            sla[3],

                            sla[4],

                            sla[5],

                            sla[6],

                            remainingText,

                            sla[8],

                            technician
                    }
            );
        }
    }


    // ==========================================
    // VIEW SLA DETAILS
    // ==========================================

    private void viewSelectedSLA() {

        int selectedRow =
                slaTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a ticket first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String ticketNumber =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                1
                        )
                );


        String createdBy =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                2
                        )
                );


        String priority =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                3
                        )
                );


        String status =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                4
                        )
                );


        String startedAt =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                5
                        )
                );


        String dueAt =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                6
                        )
                );


        String remaining =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                7
                        )
                );


        String slaStatus =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                8
                        )
                );


        String technician =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                9
                        )
                );


        String message =
                "Ticket Number: " + ticketNumber
                + "\nCreated By: " + createdBy
                + "\nPriority: " + priority
                + "\nTicket Status: " + status
                + "\nStarted At: " + startedAt
                + "\nDue At: " + dueAt
                + "\nRemaining: " + remaining
                + "\nSLA Status: " + slaStatus
                + "\nTechnician: " + technician;


        JOptionPane.showMessageDialog(
                this,
                message,
                "SLA Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ==========================================
    // MAIN
    // ==========================================


}