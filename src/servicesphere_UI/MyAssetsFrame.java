package servicesphere_UI;

import java.awt.BorderLayout;
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
import javax.swing.table.DefaultTableModel;

import servicesphere_Service.AssetService;

public class MyAssetsFrame extends JFrame {

    private String username;

    private JTable assetTable;
    private DefaultTableModel tableModel;

    public MyAssetsFrame(String username) {

        this.username = username;

        setTitle("ServiceSphere - My Assets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 550));
        setSize(1100, 650);
        setLocationRelativeTo(null);

        buildUI();

        loadAssets();

        setVisible(true);
    }

    private void buildUI() {

        setLayout(new BorderLayout(15, 15));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 10, 20
                )
        );

        JLabel titleLabel =
                new JLabel("My Assets");

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        JLabel userLabel =
                new JLabel(
                        "Logged in as: " + username
                );

        userLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                userLabel,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Asset Code",
                "Asset Name",
                "Type",
                "Serial Number",
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

        assetTable =
                new JTable(tableModel);

        assetTable.setRowHeight(32);

        assetTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        assetTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        assetTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        JScrollPane scrollPane =
                new JScrollPane(assetTable);

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
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        JButton refreshButton =
                new JButton("Refresh");

        JButton backButton =
                new JButton("Back");

        JButton logoutButton =
                new JButton("Logout");


        // Refresh

        refreshButton.addActionListener(e -> {

            loadAssets();

        });


        // Back

        backButton.addActionListener(e -> {

            dispose();

            new EmployeeDashboardFrame(
                    username
            );

        });


        // Logout

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


        bottomPanel.add(
                refreshButton
        );

        bottomPanel.add(
                backButton
        );

        bottomPanel.add(
                logoutButton
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }


    // =========================
    // LOAD ASSETS
    // =========================

    private void loadAssets() {

        tableModel.setRowCount(0);

        AssetService assetService =
                new AssetService();

        List<Object[]> assets =
                assetService.getMyAssets(
                        username
                );

        for (Object[] asset : assets) {

            tableModel.addRow(asset);

        }
    }


    // =========================
    // TEST LAUNCHER
    // =========================


}