package de.lukaspellny.labyreplay.core;

import de.lukaspellny.labyreplay.api.RoundStats;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class StatsShareImageRenderer {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 675;
    private static final Color INK = new Color(241, 244, 248);
    private static final Color MUTED = new Color(157, 167, 181);
    private static final Color PANEL = new Color(24, 27, 34);
    private static final Color LINE = new Color(64, 72, 87);

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
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new ImageTransferable(image), null);
        } catch (HeadlessException | IllegalStateException ignored) {
            // Clipboard access is optional; the image is still saved to disk.
        }
    }

    private static void paintBackground(Graphics2D graphics) {
        graphics.setPaint(new GradientPaint(0, 0, new Color(13, 18, 25), WIDTH, HEIGHT, new Color(31, 34, 44)));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        graphics.setColor(new Color(78, 176, 153));
        graphics.fillRect(0, 0, WIDTH, 10);

        graphics.setColor(new Color(236, 185, 92));
        graphics.fillRect(0, 10, WIDTH, 4);

        graphics.setColor(new Color(255, 255, 255, 10));
        graphics.fillOval(886, -220, 520, 520);
        graphics.fillOval(-160, 418, 340, 340);

        graphics.setStroke(new BasicStroke(2f));
        graphics.setColor(new Color(255, 255, 255, 18));
        graphics.drawLine(104, 182, 1096, 182);
    }

    private static void paintHeader(Graphics2D graphics, String playerName, String serverName) {
        graphics.setColor(MUTED);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        graphics.drawString("LABYREPLAY HIGHLIGHT", 104, 78);

        graphics.setColor(INK);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 62));
        graphics.drawString(playerName, 104, 145);

        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 27));
        graphics.setColor(new Color(196, 204, 216));
        graphics.drawString(serverName + " round summary", 106, 224);

        graphics.setColor(new Color(78, 176, 153));
        graphics.fillRoundRect(918, 76, 178, 46, 12, 12);
        graphics.setColor(new Color(9, 20, 22));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        graphics.drawString("LOCAL ONLY", 947, 106);
    }

    private static void paintStats(Graphics2D graphics, RoundStats stats) {
        int startX = 104;
        int y = 286;
        int cardWidth = 234;
        int gap = 22;

        paintStat(graphics, startX, y, cardWidth, "Kills", stats.kills(), new Color(220, 86, 79));
        paintStat(graphics, startX + cardWidth + gap, y, cardWidth, "Beds", stats.beds(), new Color(245, 189, 80));
        paintStat(graphics, startX + (cardWidth + gap) * 2, y, cardWidth, "Hits", stats.hits(), new Color(83, 166, 113));
        paintStat(graphics, startX + (cardWidth + gap) * 3, y, cardWidth, "Deaths", stats.deaths(), new Color(107, 129, 235));
    }

    private static void paintStat(Graphics2D graphics, int x, int y, int width, String label, int value, Color accent) {
        graphics.setColor(PANEL);
        graphics.fillRoundRect(x, y, width, 224, 16, 16);

        graphics.setColor(LINE);
        graphics.setStroke(new BasicStroke(2f));
        graphics.drawRoundRect(x, y, width, 224, 16, 16);

        graphics.setColor(accent);
        graphics.fillRoundRect(x + 18, y + 18, 46, 8, 8, 8);

        graphics.setColor(MUTED);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        graphics.drawString(label.toUpperCase(), x + 24, y + 68);

        graphics.setColor(INK);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 86));
        String valueText = String.valueOf(value);
        FontMetrics metrics = graphics.getFontMetrics();
        graphics.drawString(valueText, x + width - 24 - metrics.stringWidth(valueText), y + 166);
    }

    private static void paintFooter(Graphics2D graphics) {
        graphics.setColor(MUTED);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        graphics.drawString("Saved locally. No tracking. No upload.", 104, 590);

        graphics.setColor(new Color(236, 185, 92));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        graphics.drawString("Ready for Discord", 104, 626);

        graphics.setColor(new Color(255, 255, 255, 30));
        graphics.setStroke(new BasicStroke(1.5f));
        graphics.drawRoundRect(888, 574, 208, 48, 14, 14);
        graphics.setColor(new Color(202, 210, 222));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        graphics.drawString("labyreplay", 944, 605);
    }
}
