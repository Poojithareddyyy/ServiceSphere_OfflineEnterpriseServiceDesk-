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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import servicesphere_Service.TechnicianService;

public class TechnicianManagementFrame extends JFrame {

    private JTable technicianTable;
    private DefaultTableModel tableModel;

    private TechnicianService technicianService =
            new TechnicianService();

    public TechnicianManagementFrame() {


        setTitle("ServiceSphere - Technician Management");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setLayout(new BorderLayout());



        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(Color.WHITE);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        JLabel heading =
                new JLabel("Technician Management");

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

        headerPanel.add(
                heading,
                BorderLayout.WEST
        );


        JLabel subHeading =
                new JLabel(
                        "Manage service desk technicians"
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
                subHeading,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );



        String[] columns = {
                "ID",
                "Technician Name",
                "Email",
                "Specialization",
                "Phone",
                "Status"
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


        technicianTable =
                new JTable(tableModel);

        technicianTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        technicianTable.setRowHeight(32);

        technicianTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

        technicianTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        technicianTable
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


        JButton addButton =
                new JButton("Add Technician");

        addButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        addButton.setFocusPainted(false);


        JButton activateButton =
                new JButton("Set Available");

        activateButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        activateButton.setFocusPainted(false);


        JButton unavailableButton =
                new JButton("Set Unavailable");

        unavailableButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        unavailableButton.setFocusPainted(false);


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

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        backButton.setFocusPainted(false);


        buttonPanel.add(addButton);
        buttonPanel.add(activateButton);
        buttonPanel.add(unavailableButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );



        loadTechnicians();



        addButton.addActionListener(e -> {

            showAddTechnicianDialog();

        });


        activateButton.addActionListener(e -> {

            updateSelectedTechnicianStatus(
                    "AVAILABLE"
            );

        });



        unavailableButton.addActionListener(e -> {

            updateSelectedTechnicianStatus(
                    "UNAVAILABLE"
            );

        });



        refreshButton.addActionListener(e -> {

            loadTechnicians();

        });

        backButton.addActionListener(e -> {

            dispose();

            new AdminDashboardFrame();

        });


        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setVisible(true);
    }


    private void loadTechnicians() {

        tableModel.setRowCount(0);

        List<Object[]> technicians =
                technicianService.getAllTechnicians();

        for (Object[] technician : technicians) {

            tableModel.addRow(
                    new Object[] {
                            technician[0],
                            technician[1],
                            technician[2],
                            technician[3],
                            technician[4],
                            technician[5]
                    }
            );
        }
    }



    private void showAddTechnicianDialog() {

        javax.swing.JTextField nameField =
                new javax.swing.JTextField();

        javax.swing.JTextField emailField =
                new javax.swing.JTextField();

        javax.swing.JTextField specializationField =
                new javax.swing.JTextField();

        javax.swing.JTextField phoneField =
                new javax.swing.JTextField();


        phoneField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    @Override
                    public void keyTyped(
                            java.awt.event.KeyEvent e) {

                        char c =
                                e.getKeyChar();

                        if (!Character.isDigit(c)
                                || phoneField.getText().length() >= 10) {

                            e.consume();
                        }
                    }
                }
        );


        JPanel panel =
                new JPanel();

        panel.setLayout(
                new java.awt.GridLayout(
                        4,
                        2,
                        10,
                        10
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        panel.add(
                new JLabel("Technician Name:")
        );

        panel.add(nameField);


        panel.add(
                new JLabel("Email:")
        );

        panel.add(emailField);


        panel.add(
                new JLabel("Specialization:")
        );

        panel.add(specializationField);


        panel.add(
                new JLabel("Phone:")
        );

        panel.add(phoneField);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Technician",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (result != JOptionPane.OK_OPTION) {
            return;
        }


        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String specialization =
                specializationField
                        .getText()
                        .trim();

        String phone =
                phoneField.getText().trim();


        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter technician name.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        if (specialization.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter specialization.",
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



        boolean success =
                technicianService.addTechnician(
                        name,
                        email,
                        specialization,
                        phone
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Technician added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadTechnicians();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add technician.\n"
                    + "Email may already exist.",
                    "Creation Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    private void updateSelectedTechnicianStatus(
            String status) {

        int selectedRow =
                technicianTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a technician first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int technicianId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        boolean success =
                technicianService
                        .updateTechnicianStatus(
                                technicianId,
                                status
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Technician status updated.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadTechnicians();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update technician status.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



}
