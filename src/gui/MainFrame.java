package gui;

import app.AppState;
import service.ScenarioRepository;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final WizardPanel[] wizardPanels;
    private final StepIndicatorPanel stepIndicatorPanel;
    private final JButton backButton;
    private final JButton nextButton;
    private int currentStep;

    public MainFrame() {
        super("ISO 15939 Measurement Process Simulator - V1");

        AppState appState = new AppState();
        ScenarioRepository scenarioRepository = new ScenarioRepository();

        wizardPanels = new WizardPanel[]{
                new ProfilePanel(appState, scenarioRepository),
                new DefinePanel(appState, scenarioRepository),
                new PlanPanel(appState, scenarioRepository),
                new CollectPanel(appState, scenarioRepository),
                new AnalysePanel(appState, scenarioRepository)
        };

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        for (int i = 0; i < wizardPanels.length; i++) {
            cardPanel.add(wizardPanels[i], "step" + i);
        }

        stepIndicatorPanel = new StepIndicatorPanel();
        stepIndicatorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backButton = new JButton("Back");
        nextButton = new JButton("Next");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        setLayout(new BorderLayout());
        add(stepIndicatorPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> previousStep());
        nextButton.addActionListener(e -> nextStep());

        currentStep = 0;
        updateStepView();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
    }

    private void nextStep() {
        if (!wizardPanels[currentStep].validateStep()) {
            return;
        }

        if (currentStep < wizardPanels.length - 1) {
            currentStep++;
            updateStepView();
        }
    }

    private void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            updateStepView();
        }
    }

    private void updateStepView() {
        wizardPanels[currentStep].onEnterStep();
        cardLayout.show(cardPanel, "step" + currentStep);
        stepIndicatorPanel.updateIndicator(currentStep);
        backButton.setEnabled(currentStep > 0);
        nextButton.setEnabled(currentStep < wizardPanels.length - 1);
        nextButton.setText(currentStep == wizardPanels.length - 1 ? "Finish" : "Next");
    }
}
