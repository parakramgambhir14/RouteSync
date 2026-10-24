package com.routesync.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * UITheme
 * -------
 * One place for every color and font used in the app.
 * Change a value here and it updates everywhere — that's the whole point.
 */
public class UITheme {

    // ----- Colors -----
    public static final Color SIDEBAR_BG    = new Color(0x1E293B); // dark navy
    public static final Color SIDEBAR_HOVER = new Color(0x334155); // lighter navy on hover
    public static final Color ACCENT        = new Color(0x14B8A6); // teal accent (buttons, selection)
    public static final Color CONTENT_BG    = new Color(0xF8FAFC); // light page background
    public static final Color CARD_BG       = Color.WHITE;
    public static final Color TEXT_DARK     = new Color(0x1E293B);
    public static final Color TEXT_LIGHT    = Color.WHITE;
    public static final Color BORDER        = new Color(0xE2E8F0);
    public static final Color SUCCESS       = new Color(0x22C55E); // connected indicator
    public static final Color DANGER        = new Color(0xEF4444); // disconnected indicator

    // ----- Fonts -----
    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_NAV    = new Font("Segoe UI", Font.PLAIN, 14);
}
