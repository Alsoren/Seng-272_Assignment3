package gui;

import app.AppState;
import model.Dimension;
import model.Metric;
import model.Scenario;
import service.ScenarioRepository;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class PlanPanel extends WizardPanel {
    private final DefaultTableModel tableModel;
    private final JLabel scenarioLabel;

    public PlanPanel(AppState appState, ScenarioRepository scenarioRepository) {
        super(appState, scenarioRepository);

        scenarioLabel = new JLabel("Step 3 - Plan Measurement");
        scenarioLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scenarioLabel, BorderLayout.NORTH);

        String[] columns = {"Dimension", "Dim. Coeff", "Metric", "Metric Coeff", "Direction", "Range", "Unit"};
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
            scenarioLabel.setText("Step 3 - Plan Measurement");
            return;
        }

        scenarioLabel.setText("Step 3 - Plan Measurement | " + scenario.getName());

        for (Dimension dimension : scenario.getDimensions()) {
            for (Metric metric : dimension.getMetrics()) {
                tableModel.addRow(new Object[]{
                        dimension.getName(),
                        dimension.getCoefficient(),
                        metric.getName(),
                        metric.getCoefficient(),
                        metric.getDirection().getDisplayName(),
                        formatRange(metric.getMinValue(), metric.getMaxValue()),
                        metric.getUnit()
                });
            }
        }
    }

    private String formatRange(double min, double max) {
        return removeTrailingZero(min) + " - " + removeTrailingZero(max);
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
