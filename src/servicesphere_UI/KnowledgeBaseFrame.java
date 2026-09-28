package servicesphere_UI;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import servicesphere_Service.KnowledgeBaseService;

public class KnowledgeBaseFrame extends JFrame {

    private KnowledgeBaseService knowledgeBaseService;

    private JTable articleTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JTextArea contentArea;

    public KnowledgeBaseFrame() {

        knowledgeBaseService =
                new KnowledgeBaseService();

        setTitle("ServiceSphere - Knowledge Base");

        setSize(1100, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );


        // ================= MAIN PANEL =================

        JPanel mainPanel =
                new JPanel(new BorderLayout(20, 20));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );


        // ================= HEADER =================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        JLabel titleLabel =
                new JLabel("Knowledge Base");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Troubleshooting & Service Desk Solutions"
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JPanel titlePanel =
                new JPanel(new GridLayout(2, 1));

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // ================= SEARCH =================

        JPanel searchPanel =
                new JPanel(new BorderLayout(10, 10));

        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        JButton searchButton =
                new JButton("Search");

        JButton showAllButton =
                new JButton("Show All");

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );

        searchPanel.add(
                showAllButton,
                BorderLayout.WEST
        );


        // ================= TABLE =================

        String[] columns = {
                "ID",
                "Title",
                "Category"
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

        articleTable =
                new JTable(tableModel);

        articleTable.setRowHeight(30);

        articleTable.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane tableScrollPane =
                new JScrollPane(articleTable);


        // ================= CONTENT =================

        contentArea =
                new JTextArea();

        contentArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        contentArea.setLineWrap(true);

        contentArea.setWrapStyleWord(true);

        contentArea.setEditable(false);

        contentArea.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JScrollPane contentScrollPane =
                new JScrollPane(contentArea);


        JPanel centerPanel =
                new JPanel(new BorderLayout(15, 15));

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );


        JPanel detailsPanel =
                new JPanel(new BorderLayout());

        JLabel detailsLabel =
                new JLabel("Article Details");

        detailsLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        detailsPanel.add(
                detailsLabel,
                BorderLayout.NORTH
        );

        detailsPanel.add(
                contentScrollPane,
                BorderLayout.CENTER
        );


        centerPanel.add(
                detailsPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        // ================= BOTTOM =================

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        JButton refreshButton =
                new JButton("Refresh");

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


        // ================= ACTIONS =================

        searchButton.addActionListener(e -> {

            String keyword =
                    searchField.getText().trim();

            if (keyword.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a search keyword.",
                        "Search",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            loadArticles(
                    knowledgeBaseService
                            .searchArticles(keyword)
            );
        });


        showAllButton.addActionListener(e -> {

            searchField.setText("");

            loadArticles(
                    knowledgeBaseService
                            .getAllArticles()
            );
        });


        refreshButton.addActionListener(e -> {

            searchField.setText("");

            loadArticles(
                    knowledgeBaseService
                            .getAllArticles()
            );
        });


        articleTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        showSelectedArticle();
                    }
                });


        backButton.addActionListener(e -> {

            dispose();

            new AdminDashboardFrame();
        });


        add(mainPanel);

        loadArticles(
                knowledgeBaseService
                        .getAllArticles()
        );

        setVisible(true);
    }


    // ================= LOAD ARTICLES =================

    private void loadArticles(
            List<Object[]> articles) {

        tableModel.setRowCount(0);

        contentArea.setText("");

        for (Object[] article : articles) {

            Object[] row = {
                    article[0],
                    article[1],
                    article[2]
            };

            tableModel.addRow(row);
        }
    }


    // ================= SHOW ARTICLE =================

    private void showSelectedArticle() {

        int selectedRow =
                articleTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int articleId =
                (Integer) tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        List<Object[]> articles =
                knowledgeBaseService
                        .getAllArticles();

        for (Object[] article : articles) {

            int id =
                    (Integer) article[0];

            if (id == articleId) {

                String title =
                        (String) article[1];

                String category =
                        (String) article[2];

                String content =
                        (String) article[3];

                contentArea.setText(
                        "Title: " + title
                        + "\n\n"
                        + "Category: " + category
                        + "\n\n"
                        + content
                );

                contentArea.setCaretPosition(0);

                break;
            }
        }
    }


    // ================= MAIN =================


}