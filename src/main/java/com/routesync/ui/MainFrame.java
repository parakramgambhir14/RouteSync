package com.routesync.ui;

import com.routesync.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MainFrame
 * ---------
 * The application window: a dark sidebar for navigation, a header bar with a live
 * "connected to database" indicator, and a content area that swaps between modules
 * (Dashboard, Customers, Vehicles, ...) using CardLayout.
 *
 * No SQL lives here either — this file is purely about assembling the frontend.
 */
public class MainFrame extends JFrame {

    // One label per sidebar module. Add/remove a module by editing just this array —
    // the sidebar buttons and content cards are both built from it below.
    private static final String[] MODULES = {
            "Dashboard", "Customers", "Vehicles", "Drivers",
            "Routes", "Service Orders", "Schedule", "Maintenance", "Feedback"
    };

    private final JPanel contentPanel;
    private final CardLayout cardLayout;
    private final JLabel statusDot;
    private final JLabel statusText;

    public MainFrame() {
        setTitle("RouteSync — Fleet & Scheduling Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());

        // ---------------- Sidebar ----------------
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(220, 0));

        JLabel logo = new JLabel("  RouteSync");
        logo.setFont(UITheme.FONT_TITLE);
        logo.setForeground(UITheme.TEXT_LIGHT);
        logo.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(logo);

        // ---------------- Content area ----------------
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UITheme.CONTENT_BG);

        List<NavButton> navButtons = new ArrayList<>();

        for (String module : MODULES) {
            JPanel panel = createPanelFor(module);
            contentPanel.add(panel, module);

            NavButton navBtn = new NavButton(module);
            navBtn.addActionListener(e -> {
                cardLayout.show(contentPanel, module);
                for (NavButton b : navButtons) {
                    b.setSelected(b.getText().equals(module));
                }
            });
            navButtons.add(navBtn);
            sidebar.add(navBtn);
        }
        navButtons.get(0).setSelected(true); // Dashboard is selected on launch

        sidebar.add(Box.createVerticalGlue());

        // ---------------- Header bar (page chrome + DB status indicator) ----------------
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER));
        header.setPreferredSize(new Dimension(0, 55));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        statusDot = new JLabel("\u25CF"); // a filled circle used as a status light
        statusText = new JLabel("Checking database connection...");
        statusText.setFont(UITheme.FONT_BODY);

        statusPanel.add(statusDot);
        statusPanel.add(statusText);
        header.add(statusPanel, BorderLayout.EAST);

        add(sidebar, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        refreshConnectionStatus();
    }

    /** Builds the screen shown for a given sidebar module. */
    private JPanel createPanelFor(String module) {
        switch (module) {
            case "Dashboard":
                return buildDashboard();
            case "Customers":
                return new EntityPanel("Customers",
                        new String[]{"ID", "Name", "Type", "Phone", "Email", "City"});
            case "Vehicles":
                return new EntityPanel("Vehicles",
                        new String[]{"ID", "Registration No", "Category", "Make", "Model", "Status"});
            case "Drivers":
                return new EntityPanel("Drivers",
                        new String[]{"ID", "Name", "License No", "Phone", "Status"});
            case "Routes":
                return new EntityPanel("Routes",
                        new String[]{"ID", "Route Name", "Depot", "Distance (KM)"});
            case "Service Orders":
                return new EntityPanel("Service Orders",
                        new String[]{"ID", "Customer", "Service Type", "Preferred Date", "Status"});
            case "Schedule":
                return new EntityPanel("Schedule",
                        new String[]{"ID", "Order", "Vehicle", "Driver", "Route", "Date", "Status"});
            case "Maintenance":
                return new EntityPanel("Maintenance",
                        new String[]{"ID", "Vehicle", "Type", "Date", "Cost"});
            case "Feedback":
                return new EntityPanel("Feedback",
                        new String[]{"ID", "Order", "Rating", "Date"});
            default:
                return new JPanel();
        }
    }

    /** A simple welcome screen — no database calls, just orientation for whoever opens the app. */
    private JPanel buildDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.CONTENT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel welcome = new JLabel(
                "<html><h1 style='margin:0'>Welcome to RouteSync</h1>" +
                "<p style='color:#475569;font-weight:normal;width:500px'>" +
                "Use the sidebar to open a module. Each one gives you a ready-made table " +
                "and toolbar — plug your SQL into the TODO methods in EntityPanel.java " +
                "(or override them per-module) to bring it to life.</p></html>");
        welcome.setFont(UITheme.FONT_BODY);
        panel.add(welcome, BorderLayout.NORTH);
        return panel;
    }

    /** Pings the database once at startup and updates the status dot/text in the header. */
    private void refreshConnectionStatus() {
        boolean connected = DatabaseConnection.testConnection();
        statusDot.setForeground(connected ? UITheme.SUCCESS : UITheme.DANGER);
        statusText.setText(connected ? "Connected to database" : "Not connected \u2014 check db.properties");
    }
}
