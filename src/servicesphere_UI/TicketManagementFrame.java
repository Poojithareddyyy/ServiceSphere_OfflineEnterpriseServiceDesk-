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

public class TicketManagementFrame extends JFrame {

    private JTable ticketTable;
    private DefaultTableModel tableModel;

    private TicketService ticketService =
            new TicketService();

    public TicketManagementFrame() {

        // ==========================================
        // FRAME SETTINGS
        // ==========================================

        setTitle("ServiceSphere - Ticket Management");

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
                new JLabel("Ticket Management");

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
                        "View and manage all service tickets"
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
                "ID",
                "Ticket Number",
                "Created By",
                "Issue",
                "Category",
                "Priority",
                "Status",
                "Created At",
                "Asset",
                "Technician",
                "SLA"
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


        ticketTable =
                new JTable(tableModel);

        ticketTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        ticketTable.setRowHeight(32);

        ticketTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        ticketTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        ticketTable
                );

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
        // BOTTOM BUTTON PANEL
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


        JButton viewButton =
                new JButton("View Details");

        viewButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        viewButton.setFocusPainted(false);


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


        JButton backButton =
                new JButton("Back");
        
        JButton assignButton =
                new JButton("Assign Technician");
        
        JButton updateStatusButton = new JButton("Update Status");
        
        
        updateStatusButton.addActionListener(e -> {

            int selectedRow = ticketTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please select a ticket first.",
                    "No Ticket Selected",
                    JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int ticketId =
                (Integer) tableModel.getValueAt(selectedRow, 0);

            String currentStatus =
                tableModel.getValueAt(selectedRow, 6).toString();

            String[] statuses = {
                "OPEN",
                "ASSIGNED",
                "IN PROGRESS",
                "WAITING",
                "RESOLVED",
                "CLOSED"
            };

            String newStatus = (String) JOptionPane.showInputDialog(
                this,
                "Select new status:",
                "Update Ticket Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statuses,
                currentStatus
            );

            if (newStatus == null) {
                return;
            }

            boolean updated =
                ticketService.updateTicketStatus(
                    ticketId,
                    newStatus
                );

            if (updated) {

                JOptionPane.showMessageDialog(
                    this,
                    "Ticket status updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                loadTickets();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Unable to update ticket status.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        assignButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        assignButton.setFocusPainted(false);

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        backButton.setFocusPainted(false);


        buttonPanel.add(viewButton);
        buttonPanel.add(assignButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        buttonPanel.add(updateStatusButton);


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // LOAD TICKETS
        // ==========================================

        loadTickets();


        // ==========================================
        // VIEW DETAILS
        // ==========================================

        viewButton.addActionListener(e -> {

            viewSelectedTicket();

        });
        
        assignButton.addActionListener(e -> {

            assignSelectedTicket();

        });


        // ==========================================
        // REFRESH
        // ==========================================

        refreshButton.addActionListener(e -> {

            loadTickets();

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
    // LOAD ALL TICKETS
    // ==========================================

    private void loadTickets() {

        tableModel.setRowCount(0);

        List<Object[]> tickets =
                ticketService.getAllTickets();


        for (Object[] ticket : tickets) {

            String asset =
                    ticket[8] == null
                            ? "None"
                            : ticket[8].toString();


            String technician =
                    ticket[10] == null
                            ? "Unassigned"
                            : ticket[10].toString();

            String slaStatus =
                    Boolean.TRUE.equals(ticket[9])
                            ? "BREACHED"
                            : "Within SLA";


            tableModel.addRow(
                    new Object[] {

                            ticket[0],
                            ticket[1],
                            ticket[2],
                            ticket[3],
                            ticket[4],
                            ticket[5],
                            ticket[6],
                            ticket[7],
                            asset,
                            technician,
                            slaStatus
                    }
            );
        }
    }


    // ==========================================
    // VIEW SELECTED TICKET
    // ==========================================

    private void viewSelectedTicket() {

        int selectedRow =
                ticketTable.getSelectedRow();


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


        String issue =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                3
                        )
                );


        String category =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                4
                        )
                );


        String priority =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                5
                        )
                );


        String status =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                6
                        )
                );


        String createdAt =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                7
                        )
                );


        String asset =
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


        String sla =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                10
                        )
                );


        String message =
                "Ticket Number: " + ticketNumber
                + "\nCreated By: " + createdBy
                + "\nIssue: " + issue
                + "\nCategory: " + category
                + "\nPriority: " + priority
                + "\nStatus: " + status
                + "\nCreated At: " + createdAt
                + "\nAsset: " + asset
                + "\nTechnician: " + technician
                + "\nSLA: " + sla;


        JOptionPane.showMessageDialog(
                this,
                message,
                "Ticket Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
 // ==========================================
 // ASSIGN SELECTED TICKET
 // ==========================================

 private void assignSelectedTicket() {

     int selectedRow =
             ticketTable.getSelectedRow();

     if (selectedRow == -1) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please select a ticket first.",
                 "Selection Required",
                 JOptionPane.WARNING_MESSAGE
         );

         return;
     }


     int ticketId =
             (int) tableModel.getValueAt(
                     selectedRow,
                     0
             );


     String ticketNumber =
             String.valueOf(
                     tableModel.getValueAt(
                             selectedRow,
                             1
                     )
             );


     List<Object[]> technicians =
             ticketService.getAvailableTechnicians();


     if (technicians.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "No available technicians found.",
                 "No Technicians",
                 JOptionPane.WARNING_MESSAGE
         );

         return;
     }


     String[] technicianNames =
             new String[technicians.size()];


     for (int i = 0;
          i < technicians.size();
          i++) {

         Object[] technician =
                 technicians.get(i);

         technicianNames[i] =
                 technician[1].toString()
                 + " - "
                 + technician[2].toString();
     }


     String selectedTechnician =
             (String) JOptionPane.showInputDialog(
                     this,
                     "Select technician for "
                     + ticketNumber + ":",
                     "Assign Technician",
                     JOptionPane.QUESTION_MESSAGE,
                     null,
                     technicianNames,
                     technicianNames[0]
             );


     if (selectedTechnician == null) {
         return;
     }


     int selectedTechnicianIndex = -1;


     for (int i = 0;
          i < technicianNames.length;
          i++) {

         if (technicianNames[i]
                 .equals(selectedTechnician)) {

             selectedTechnicianIndex = i;

             break;
         }
     }


     if (selectedTechnicianIndex == -1) {
         return;
     }


     int technicianId =
             ((Number)
                     technicians
                             .get(selectedTechnicianIndex)[0])
                     .intValue();


     /*
      * Admin user ID.
      *
      * Our current system has one fixed
      * administrator account.
      */
     int adminUserId =
    	        ticketService.getAdminUserId();

    	if (adminUserId == -1) {

    	    JOptionPane.showMessageDialog(
    	            this,
    	            "Administrator account could not be found.",
    	            "Assignment Failed",
    	            JOptionPane.ERROR_MESSAGE
    	    );

    	    return;
    	}


     int choice =
             JOptionPane.showConfirmDialog(
                     this,
                     "Assign ticket "
                     + ticketNumber
                     + " to "
                     + selectedTechnician
                     + "?",
                     "Confirm Assignment",
                     JOptionPane.YES_NO_OPTION
             );


     if (choice != JOptionPane.YES_OPTION) {
         return;
     }


     boolean success =
             ticketService.assignTechnician(
                     ticketId,
                     technicianId,
                     adminUserId
             );


     if (success) {

         JOptionPane.showMessageDialog(
                 this,
                 "Technician assigned successfully.",
                 "Assignment Successful",
                 JOptionPane.INFORMATION_MESSAGE
         );

     } else {

         JOptionPane.showMessageDialog(
                 this,
                 "Unable to assign technician.",
                 "Assignment Failed",
                 JOptionPane.ERROR_MESSAGE
         );
     }
 }


}