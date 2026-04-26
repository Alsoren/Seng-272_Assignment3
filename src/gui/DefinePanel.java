package gui;

import app.AppState;
import model.QualityType;
import model.Scenario;
import service.ScenarioRepository;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

public class DefinePanel extends WizardPanel {
    private final JRadioButton productQualityButton;
    private final JRadioButton processQualityButton;
    private final JComboBox<String> modeComboBox;
    private final JComboBox<Scenario> scenarioComboBox;

    public DefinePanel(AppState appState, ScenarioRepository scenarioRepository) {
        super(appState, scenarioRepository);

        JLabel title = new JLabel("Step 2 - Define Quality Dimensions");
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        productQualityButton = new JRadioButton(QualityType.PRODUCT_QUALITY.getDisplayName());
        processQualityButton = new JRadioButton(QualityType.PROCESS_QUALITY.getDisplayName());
        ButtonGroup qualityTypeGroup = new ButtonGroup();
        qualityTypeGroup.add(productQualityButton);
        qualityTypeGroup.add(processQualityButton);

        modeComboBox = new JComboBox<>();
        for (String mode : scenarioRepository.getModes()) {
            modeComboBox.addItem(mode);
        }

        scenarioComboBox = new JComboBox<>();
        modeComboBox.addActionListener(e -> refreshScenarios());
        refreshScenarios();

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Quality Type:"), gbc);
        gbc.gridx = 1;
        JPanel radioPanel = new JPanel();
        radioPanel.add(productQualityButton);
        radioPanel.add(processQualityButton);
        formPanel.add(radioPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Mode:"), gbc);
        gbc.gridx = 1;
        formPanel.add(modeComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Scenario:"), gbc);
        gbc.gridx = 1;
        formPanel.add(scenarioComboBox, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void refreshScenarios() {
        scenarioComboBox.removeAllItems();
        String selectedMode = (String) modeComboBox.getSelectedItem();
        if (selectedMode == null) {
            return;
        }
        List<Scenario> scenarios = scenarioRepository.getScenariosByMode(selectedMode);
        for (Scenario scenario : scenarios) {
            scenarioComboBox.addItem(scenario);
        }
    }

    @Override
    public boolean validateStep() {
        if (!productQualityButton.isSelected() && !processQualityButton.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select one quality type to continue.");
            return false;
        }

        String selectedMode = (String) modeComboBox.getSelectedItem();
        Scenario selectedScenario = (Scenario) scenarioComboBox.getSelectedItem();

        if (selectedMode == null) {
            JOptionPane.showMessageDialog(this, "Please select one measurement mode to continue.");
            return false;
        }
        if (selectedScenario == null) {
            JOptionPane.showMessageDialog(this, "Please select one scenario to continue.");
            return false;
        }

        QualityType selectedQualityType = productQualityButton.isSelected()
                ? QualityType.PRODUCT_QUALITY
                : QualityType.PROCESS_QUALITY;

        appState.setQualityType(selectedQualityType);
        appState.setMode(selectedMode);
        appState.setSelectedScenario(selectedScenario);
        return true;
    }
}
