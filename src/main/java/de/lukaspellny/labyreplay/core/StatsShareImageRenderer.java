package de.lukaspellny.labyreplay.core;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class StatsShareImageRenderer {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 675;

    public BufferedImage render(RoundStats stats, String playerName, String serverName) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            paintBackground(graphics);
            paintHeader(graphics, playerName, serverName);
            paintStats(graphics, stats);
            paintFooter(graphics);
        } finally {
            graphics.dispose();
        }
        return image;
    }

    public byte[] toPng(BufferedImage image) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "png", output);
        return output.toByteArray();
    }

    public void copyToClipboard(BufferedImage image) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new ImageTransferable(image), null);
    }

    private static void paintBackground(Graphics2D graphics) {
        graphics.setColor(new Color(16, 18, 24));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        graphics.setColor(new Color(43, 111, 143));
        graphics.fillRect(0, 0, 32, HEIGHT);

        graphics.setColor(new Color(245, 189, 80));
        graphics.fillRect(32, 0, 8, HEIGHT);

        graphics.setColor(new Color(31, 35, 44));
        graphics.fillRoundRect(96, 116, 1008, 410, 18, 18);

        graphics.setStroke(new BasicStroke(2f));
        graphics.setColor(new Color(78, 86, 101));
        graphics.drawRoundRect(96, 116, 1008, 410, 18, 18);
    }

    private static void paintHeader(Graphics2D graphics, String playerName, String serverName) {
        graphics.setColor(new Color(238, 241, 246));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 58));
        graphics.drawString("LabyReplay", 128, 86);

        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        graphics.setColor(new Color(184, 191, 204));
        graphics.drawString(playerName + " auf " + serverName, 130, 154);
    }

    private static void paintStats(Graphics2D graphics, RoundStats stats) {
        int startX = 132;
        int y = 236;
        int cardWidth = 222;
        int gap = 24;

        paintStat(graphics, startX, y, cardWidth, "Kills", stats.kills(), new Color(220, 86, 79));
        paintStat(graphics, startX + cardWidth + gap, y, cardWidth, "Beds", stats.beds(), new Color(245, 189, 80));
        paintStat(graphics, startX + (cardWidth + gap) * 2, y, cardWidth, "Hits", stats.hits(), new Color(83, 166, 113));
        paintStat(graphics, startX + (cardWidth + gap) * 3, y, cardWidth, "Deaths", stats.deaths(), new Color(107, 129, 235));
    }

    private static void paintStat(Graphics2D graphics, int x, int y, int width, String label, int value, Color accent) {
        graphics.setColor(new Color(24, 27, 34));
        graphics.fillRoundRect(x, y, width, 210, 12, 12);

        graphics.setColor(accent);
        graphics.fillRoundRect(x, y, width, 8, 8, 8);

        graphics.setColor(new Color(190, 197, 210));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        graphics.drawString(label, x + 24, y + 58);

        graphics.setColor(new Color(248, 250, 252));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
        String valueText = String.valueOf(value);
        FontMetrics metrics = graphics.getFontMetrics();
        graphics.drawString(valueText, x + width - 24 - metrics.stringWidth(valueText), y + 150);
    }

    private static void paintFooter(Graphics2D graphics) {
        graphics.setColor(new Color(140, 148, 164));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 21));
        graphics.drawString("Local highlight, no upload required", 130, 584);

        if (Desktop.isDesktopSupported()) {
            graphics.drawString("Ready for Discord clipboard sharing", 130, 616);
        }
    }
}
