package gui;

import model.Dimension;
import model.Scenario;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.List;

public class RadarChartPanel extends JPanel {
    private Scenario scenario;

    public RadarChartPanel() {
        setPreferredSize(new java.awt.Dimension(360, 300));
        setBackground(Color.WHITE);
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (scenario == null || scenario.getDimensions().isEmpty()) {
            g2.drawString("Radar chart will appear after scenario analysis.", 20, 30);
            g2.dispose();
            return;
        }

        List<Dimension> dimensions = scenario.getDimensions();
        int count = dimensions.size();
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2 + 10;
        int radius = Math.min(width, height) / 3;

        g2.setColor(new Color(230, 230, 230));
        for (int level = 1; level <= 5; level++) {
            double levelRadius = radius * (level / 5.0);
            Path2D grid = createPolygonPath(count, centerX, centerY, levelRadius, null);
            g2.draw(grid);
        }

        g2.setColor(new Color(180, 180, 180));
        for (int i = 0; i < count; i++) {
            double angle = getAngle(i, count);
            int x = centerX + (int) Math.round(Math.cos(angle) * radius);
            int y = centerY + (int) Math.round(Math.sin(angle) * radius);
            g2.drawLine(centerX, centerY, x, y);
        }

        double[] values = new double[count];
        for (int i = 0; i < count; i++) {
            values[i] = dimensions.get(i).getDimensionScore();
        }

        Path2D scoreShape = createPolygonPath(count, centerX, centerY, radius, values);
        g2.setColor(new Color(70, 130, 180, 70));
        g2.fill(scoreShape);
        g2.setColor(new Color(30, 90, 150));
        g2.setStroke(new BasicStroke(2f));
        g2.draw(scoreShape);

        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(Color.DARK_GRAY);
        for (int i = 0; i < count; i++) {
            Dimension dimension = dimensions.get(i);
            double angle = getAngle(i, count);
            int labelX = centerX + (int) Math.round(Math.cos(angle) * (radius + 35));
            int labelY = centerY + (int) Math.round(Math.sin(angle) * (radius + 35));
            String text = shorten(dimension.getName()) + " (" + String.format("%.1f", dimension.getDimensionScore()) + ")";
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, labelX - textWidth / 2, labelY);
        }

        g2.dispose();
    }

    private Path2D createPolygonPath(int count, int centerX, int centerY, double radius, double[] values) {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < count; i++) {
            double ratio = values == null ? 1.0 : Math.max(0.0, Math.min(5.0, values[i])) / 5.0;
            double angle = getAngle(i, count);
            double x = centerX + Math.cos(angle) * radius * ratio;
            double y = centerY + Math.sin(angle) * radius * ratio;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();
        return path;
    }

    private double getAngle(int index, int count) {
        return -Math.PI / 2 + (2 * Math.PI * index / count);
    }

    private String shorten(String text) {
        if (text.length() <= 16) {
            return text;
        }
        return text.substring(0, 13) + "...";
    }
}
