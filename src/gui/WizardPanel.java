package gui;

import app.AppState;
import service.ScenarioRepository;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public abstract class WizardPanel extends JPanel {
    protected final AppState appState;
    protected final ScenarioRepository scenarioRepository;

    public WizardPanel(AppState appState, ScenarioRepository scenarioRepository) {
        this.appState = appState;
        this.scenarioRepository = scenarioRepository;
        setLayout(new BorderLayout(10, 10));
    }

    public abstract boolean validateStep();

    public void onEnterStep() {
        // Optional hook for panels that need to refresh their data when opened.
    }
}
