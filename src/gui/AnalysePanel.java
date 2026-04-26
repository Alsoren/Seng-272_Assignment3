package gui;

import app.AppState;
import model.Dimension;
import model.Scenario;
import service.GapAnalysisResult;
import service.GapAnalyzer;
import service.ScenarioRepository;
import service.ScoreCalculator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class AnalysePanel extends WizardPanel {
    private final JPanel scorePanel;
    private final JTextArea gapTextArea;
    private final RadarChartPanel radarChartPanel;
    private final ScoreCalculator scoreCalculator;
    private final GapAnalyzer gapAnalyzer;

    public AnalysePanel(AppState appState, ScenarioRepository scenarioRepository) {
        super(appState, scenarioRepository);
        this.scoreCalculator = new ScoreCalculator();
        this.gapAnalyzer = new GapAnalyzer();

        JLabel title = new JLabel("Step 5 - Analyse");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Dimension-Based Weighted Averages"));

        radarChartPanel = new RadarChartPanel();
        radarChartPanel.setBorder(BorderFactory.createTitledBorder("Radar Chart"));

        contentPanel.add(new JScrollPane(scorePanel));
        contentPanel.add(radarChartPanel);

        gapTextArea = new JTextArea();
        gapTextArea.setEditable(false);
        gapTextArea.setLineWrap(true);
        gapTextArea.setWrapStyleWord(true);
        gapTextArea.setRows(5);
        gapTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane gapScrollPane = new JScrollPane(gapTextArea);
        gapScrollPane.setBorder(BorderFactory.createTitledBorder("Gap Analysis"));

        add(contentPanel, BorderLayout.CENTER);
        add(gapScrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void onEnterStep() {
        scorePanel.removeAll();
        Scenario scenario = appState.getSelectedScenario();

        if (scenario == null) {
            gapTextArea.setText("No scenario selected. Please complete the Define step first.");
            radarChartPanel.setScenario(null);
            scorePanel.revalidate();
            scorePanel.repaint();
            return;
        }

        for (Dimension dimension : scenario.getDimensions()) {
            double score = scoreCalculator.calculateDimensionScore(dimension);
            addDimensionScoreRow(dimension, score);
        }

        GapAnalysisResult result = gapAnalyzer.analyze(scenario);
        showGapAnalysis(result);
        radarChartPanel.setScenario(scenario);

        scorePanel.revalidate();
        scorePanel.repaint();
    }

    private void addDimensionScoreRow(Dimension dimension, double score) {
        JLabel label = new JLabel(dimension.getName() + " - " + String.format("%.2f / 5.00", score));
        label.setFont(label.getFont().deriveFont(Font.BOLD));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) Math.round((score / 5.0) * 100));
        progressBar.setStringPainted(true);
        progressBar.setString(String.format("%.0f%%", (score / 5.0) * 100));

        JPanel rowPanel = new JPanel(new BorderLayout(5, 5));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        rowPanel.add(label, BorderLayout.NORTH);
        rowPanel.add(progressBar, BorderLayout.CENTER);

        scorePanel.add(rowPanel);
        scorePanel.add(Box.createVerticalStrut(8));
    }

    private void showGapAnalysis(GapAnalysisResult result) {
        if (result == null || result.getLowestDimension() == null) {
            gapTextArea.setText("Gap analysis could not be calculated because no dimension data exists.");
            return;
        }

        gapTextArea.setText(
                "Lowest Dimension: " + result.getLowestDimension().getName() + "\n" +
                "Score: " + String.format("%.2f", result.getScore()) + " / 5.00\n" +
                "Gap Value: " + String.format("%.2f", result.getGap()) + "\n" +
                "Quality Level: " + result.getQualityLevel() + "\n" +
                "Improvement Message: " + result.getImprovementMessage()
        );
    }

    @Override
    public boolean validateStep() {
        return true;
    }
}
