package com.routesync.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * EntityPanel
 * -----------
 * A reusable "module" screen: a title, a table, and a toolbar (Load / Add / Edit / Delete).
 *
 * On purpose, this class does NOT contain any SQL. It just gives you a nice-looking,
 * ready-to-wire screen. A live connection is always one line away:
 *
 *      Connection conn = DatabaseConnection.getConnection();
 *
 * Fill in the TODO methods at the bottom of this file with your own queries —
 * everything above them (layout, styling, table model) you can leave untouched.
 */
public class EntityPanel extends JPanel {

    protected final DefaultTableModel tableModel;
    protected final JTable table;
    protected final String entityName;

    public EntityPanel(String entityName, String[] columnNames) {
        this.entityName = entityName;

        setLayout(new BorderLayout(0, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        setBackground(UITheme.CONTENT_BG);

        // ---- Page title ----
        JLabel title = new JLabel(entityName);
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_DARK);
        add(title, BorderLayout.NORTH);

        // ---- Table (starts empty — loadData() is where you fill it) ----
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // read-only grid; edits happen through the Edit button/dialog
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(UITheme.FONT_BODY);
        table.getTableHeader().setFont(UITheme.FONT_HEADER);
        table.setSelectionBackground(UITheme.ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(UITheme.BORDER);
        table.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));

        // Wrap toolbar + table in a white "card" so it pops off the light background
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(UITheme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.add(buildToolbar(), BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    private JToolBar buildToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBackground(UITheme.CARD_BG);

        JButton loadBtn   = makeButton("Load");
        JButton addBtn    = makeButton("Add");
        JButton editBtn   = makeButton("Edit");
        JButton deleteBtn = makeButton("Delete");

        // Wire each button straight to the matching TODO stub below.
        loadBtn.addActionListener(e -> loadData());
        addBtn.addActionListener(e -> addRecord());
        editBtn.addActionListener(e -> editRecord());
        deleteBtn.addActionListener(e -> deleteRecord());

        toolbar.add(loadBtn);
        toolbar.add(addBtn);
        toolbar.add(editBtn);
        toolbar.add(deleteBtn);
        return toolbar;
    }

    private JButton makeButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UITheme.FONT_BODY);
        btn.setFocusPainted(false);
        btn.setBackground(UITheme.ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // =========================================================================================
    // TODO — YOUR DATABASE CODE GOES HERE.
    //
    // A live connection is always available via DatabaseConnection.getConnection().
    // Use tableModel.setRowCount(0) to clear the grid and tableModel.addRow(Object[])
    // to add a row to what's displayed. Nothing else in this class needs to change.
    // =========================================================================================

    /** TODO: run a SELECT for this entity and populate tableModel with the results. */
    protected void loadData() {
        // Example skeleton — uncomment and adapt to your table's real columns:
        //
        // try (Connection conn = DatabaseConnection.getConnection();
        //      PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + entityName);
        //      ResultSet rs = ps.executeQuery()) {
        //
        //     tableModel.setRowCount(0);
        //     while (rs.next()) {
        //         tableModel.addRow(new Object[]{ rs.getInt("id"), rs.getString("name") });
        //     }
        // } catch (SQLException ex) {
        //     JOptionPane.showMessageDialog(this, "Load failed: " + ex.getMessage());
        // }

        JOptionPane.showMessageDialog(this, entityName + ": hook up your SELECT query in loadData().");
    }

    /** TODO: collect input (e.g. a small dialog) and run an INSERT. */
    protected void addRecord() {
        JOptionPane.showMessageDialog(this, entityName + ": hook up your INSERT query in addRecord().");
    }

    /** TODO: run an UPDATE for the currently selected row. */
    protected void editRecord() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }
        JOptionPane.showMessageDialog(this, entityName + ": hook up your UPDATE query in editRecord().");
    }

    /** TODO: run a DELETE for the currently selected row. */
    protected void deleteRecord() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }
        JOptionPane.showMessageDialog(this, entityName + ": hook up your DELETE query in deleteRecord().");
    }
}
