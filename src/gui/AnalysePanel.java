package gui;

import app.AppState;
import model.Dimension;
import model.Scenario;
import service.ScenarioRepository;
import service.ScoreCalculator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;

public class AnalysePanel extends WizardPanel {
    private final JPanel scorePanel;
    private final JLabel gapLabel;
    private final ScoreCalculator scoreCalculator;

    public AnalysePanel(AppState appState, ScenarioRepository scenarioRepository) {
        super(appState, scenarioRepository);
        this.scoreCalculator = new ScoreCalculator();

        JLabel title = new JLabel("Step 5 - Analyse (V1 preview)");
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        gapLabel = new JLabel("Gap analysis will appear here.");
        gapLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        scorePanel.add(gapLabel);

        add(scorePanel, BorderLayout.CENTER);
    }

    @Override
    public void onEnterStep() {
        scorePanel.removeAll();
        Scenario scenario = appState.getSelectedScenario();
        if (scenario == null) {
            gapLabel.setText("No scenario selected.");
            scorePanel.add(gapLabel);
            return;
        }

        Dimension lowestDimension = null;
        double lowestScore = Double.MAX_VALUE;

        for (Dimension dimension : scenario.getDimensions()) {
            double score = scoreCalculator.calculateDimensionScore(dimension);
            if (score < lowestScore) {
                lowestScore = score;
                lowestDimension = dimension;
            }

            JLabel label = new JLabel(dimension.getName() + " - " + String.format("%.2f", score));
            JProgressBar progressBar = new JProgressBar(0, 50);
            progressBar.setValue((int) Math.round(score * 10));
            progressBar.setStringPainted(true);
            progressBar.setString(String.format("%.2f / 5.00", score));

            scorePanel.add(label);
            scorePanel.add(progressBar);
            scorePanel.add(Box.createVerticalStrut(10));
        }

        if (lowestDimension != null) {
            double gap = 5.0 - lowestScore;
            String level = qualityLevel(lowestScore);
            gapLabel.setText("Lowest dimension: " + lowestDimension.getName()
                    + " | Score: " + String.format("%.2f", lowestScore)
                    + " | Gap: " + String.format("%.2f", gap)
                    + " | Level: " + level
                    + " | This dimension requires the most improvement.");
        }
        scorePanel.add(Box.createVerticalStrut(10));
        scorePanel.add(gapLabel);
        scorePanel.revalidate();
        scorePanel.repaint();
    }

    private String qualityLevel(double score) {
        if (score >= 4.5) return "Excellent";
        if (score >= 3.5) return "Good";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor";
    }

    @Override
    public boolean validateStep() {
        return true;
    }
}
