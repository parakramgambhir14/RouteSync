package com.routesync.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * NavButton
 * ---------
 * A flat sidebar button that highlights on hover and stays highlighted while
 * its module is the one currently shown. Purely cosmetic — no database code here.
 */
public class NavButton extends JButton {

    private boolean selected = false;

    public NavButton(String text) {
        super(text);
        setFont(UITheme.FONT_NAV);
        setForeground(UITheme.TEXT_LIGHT);
        setBackground(UITheme.SIDEBAR_BG);
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) setBackground(UITheme.SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) setBackground(UITheme.SIDEBAR_BG);
            }
        });
    }

    /** Marks this button as the active module (or not), swapping its background accordingly. */
    public void setSelected(boolean selected) {
        this.selected = selected;
        setBackground(selected ? UITheme.ACCENT : UITheme.SIDEBAR_BG);
    }
}
