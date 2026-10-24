package com.routesync;

import com.routesync.ui.MainFrame;

import javax.swing.*;

/**
 * Main
 * ----
 * Entry point. Sets a cleaner look-and-feel (Nimbus, built into the JDK — no extra
 * dependency needed) and opens the main window.
 */
public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // If Nimbus isn't available for some reason, just fall back to the default look.
        }

        // Swing UI must be built/updated on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
