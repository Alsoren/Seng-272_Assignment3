package gui;

import app.AppState;
import model.Dimension;
import model.Metric;
import model.Scenario;
import service.ScenarioRepository;
import service.ScoreCalculator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class CollectPanel extends WizardPanel {
    private final DefaultTableModel tableModel;
    private final ScoreCalculator scoreCalculator;

    public CollectPanel(AppState appState, ScenarioRepository scenarioRepository) {
        super(appState, scenarioRepository);
        this.scoreCalculator = new ScoreCalculator();

        JLabel title = new JLabel("Step 4 - Collect Data (V1 preview)");
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Metric", "Direction", "Range", "Value", "Score", "Coeff / Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    @Override
    public void onEnterStep() {
        tableModel.setRowCount(0);
        Scenario scenario = appState.getSelectedScenario();
        if (scenario == null) {
            return;
        }

        for (Dimension dimension : scenario.getDimensions()) {
            for (Metric metric : dimension.getMetrics()) {
                double score = scoreCalculator.calculateMetricScore(metric);
                tableModel.addRow(new Object[]{
                        metric.getName(),
                        metric.getDirection().getDisplayName(),
                        removeTrailingZero(metric.getMinValue()) + " - " + removeTrailingZero(metric.getMaxValue()),
                        removeTrailingZero(metric.getRawValue()),
                        String.format("%.1f", score),
                        metric.getCoefficient() + " / " + metric.getUnit()
                });
            }
        }
    }

    private String removeTrailingZero(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    @Override
    public boolean validateStep() {
        return true;
    }
}
