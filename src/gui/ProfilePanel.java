package gui;

import app.AppState;
import model.UserProfile;
import service.ScenarioRepository;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class ProfilePanel extends WizardPanel {
    private final JTextField usernameField;
    private final JTextField schoolField;
    private final JTextField sessionNameField;

    public ProfilePanel(AppState appState, ScenarioRepository scenarioRepository) {
        super(appState, scenarioRepository);

        JLabel title = new JLabel("Step 1 - Profile");
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        usernameField = new JTextField();
        schoolField = new JTextField();
        sessionNameField = new JTextField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("School:"));
        formPanel.add(schoolField);
        formPanel.add(new JLabel("Session Name:"));
        formPanel.add(sessionNameField);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(formPanel);
        wrapper.add(Box.createVerticalGlue());
        add(wrapper, BorderLayout.CENTER);
    }

    @Override
    public boolean validateStep() {
        String username = usernameField.getText().trim();
        String school = schoolField.getText().trim();
        String sessionName = sessionNameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username to continue.");
            return false;
        }
        if (school.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your school to continue.");
            return false;
        }
        if (sessionName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a session name to continue.");
            return false;
        }

        appState.setUserProfile(new UserProfile(username, school, sessionName));
        return true;
    }
}
