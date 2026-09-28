package servicesphere_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import servicesphere_Service.AssetService;
import JarJdbc.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AssetManagementFrame extends JFrame {

    private JTable assetTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> employeeCombo;

    private List<Integer> employeeIds =
            new ArrayList<>();

    private AssetService assetService =
            new AssetService();

    public AssetManagementFrame() {

        // ==========================================
        // FRAME SETTINGS
        // ==========================================

        setTitle("ServiceSphere - Asset Management");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1100, 700)
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
                new JLabel("Asset Management");

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
                        "Manage and assign enterprise assets"
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


        // ==========================================
        // TABLE
        // ==========================================

        String[] columns = {
                "Asset ID",
                "Asset Code",
                "Asset Name",
                "Type",
                "Serial Number",
                "Status",
                "Assigned Employee"
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

        assetTable =
                new JTable(tableModel);

        assetTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        assetTable.setRowHeight(32);

        assetTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

        assetTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(assetTable);

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
        // ASSIGNMENT PANEL
        // ==========================================

        JPanel assignmentPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                15
                        )
                );

        assignmentPanel.setBackground(
                new Color(245, 247, 250)
        );

        JLabel employeeLabel =
                new JLabel("Assign To:");

        employeeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );


        employeeCombo =
                new JComboBox<>();

        employeeCombo.setPreferredSize(
                new Dimension(260, 35)
        );


        JButton assignButton =
                new JButton("Assign Asset");

        assignButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        assignButton.setFocusPainted(false);


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


        assignmentPanel.add(
                employeeLabel
        );

        assignmentPanel.add(
                employeeCombo
        );

        assignmentPanel.add(
                assignButton
        );

        assignmentPanel.add(
                refreshButton
        );

        assignmentPanel.add(
                backButton
        );


        add(
                assignmentPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // LOAD DATA
        // ==========================================

        loadAssets();

        loadEmployees();


        // ==========================================
        // ASSIGN BUTTON
        // ==========================================

        assignButton.addActionListener(e -> {

            int selectedRow =
                    assetTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an asset first.",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            int employeeIndex =
                    employeeCombo.getSelectedIndex();

            if (employeeIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an employee.",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            int assetId =
                    (int) tableModel.getValueAt(
                            selectedRow,
                            0
                    );

            int employeeId =
                    employeeIds.get(
                            employeeIndex
                    );


            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Assign this asset to the selected employee?",
                            "Confirm Assignment",
                            JOptionPane.YES_NO_OPTION
                    );


            if (choice != JOptionPane.YES_OPTION) {
                return;
            }


            boolean success =
                    assetService.assignAsset(
                            assetId,
                            employeeId
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Asset assigned successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadAssets();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to assign the asset.",
                        "Assignment Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        });


        // ==========================================
        // REFRESH
        // ==========================================

        refreshButton.addActionListener(e -> {

            loadAssets();
            loadEmployees();

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
    // LOAD ALL ASSETS
    // ==========================================

    private void loadAssets() {

        tableModel.setRowCount(0);

        List<Object[]> assets =
                assetService.getAllAssets();

        for (Object[] asset : assets) {

            String assignedEmployee =
                    asset[6] == null
                            ? "Unassigned"
                            : asset[6].toString();

            tableModel.addRow(
                    new Object[] {
                            asset[0],
                            asset[1],
                            asset[2],
                            asset[3],
                            asset[4],
                            asset[5],
                            assignedEmployee
                    }
            );
        }
    }


    // ==========================================
    // LOAD EMPLOYEES
    // ==========================================

    private void loadEmployees() {

        employeeCombo.removeAllItems();

        employeeIds.clear();

        String sql =
                "SELECT user_id, full_name " +
                "FROM users " +
                "WHERE role = 'EMPLOYEE' " +
                "AND status = 'ACTIVE' " +
                "ORDER BY full_name";


        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {

                int userId =
                        rs.getInt("user_id");

                String fullName =
                        rs.getString("full_name");


                employeeIds.add(
                        userId
                );

                employeeCombo.addItem(
                        fullName
                );
            }


            rs.close();
            pst.close();
            con.close();


        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load employees.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}