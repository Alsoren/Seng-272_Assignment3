package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class StepIndicatorPanel extends JPanel {
    private final JLabel[] stepLabels;
    private final String[] stepNames = {"Profile", "Define", "Plan", "Collect", "Analyse"};

    public StepIndicatorPanel() {
        setLayout(new GridLayout(1, stepNames.length, 8, 8));
        stepLabels = new JLabel[stepNames.length];

        for (int i = 0; i < stepNames.length; i++) {
            stepLabels[i] = new JLabel((i + 1) + ". " + stepNames[i], JLabel.CENTER);
            stepLabels[i].setOpaque(true);
            stepLabels[i].setBackground(new Color(230, 230, 230));
            stepLabels[i].setForeground(Color.DARK_GRAY);
            stepLabels[i].setFont(stepLabels[i].getFont().deriveFont(Font.PLAIN));
            add(stepLabels[i]);
        }
    }

    public void updateIndicator(int currentStep) {
        for (int i = 0; i < stepLabels.length; i++) {
            if (i < currentStep) {
                stepLabels[i].setText("✓ " + stepNames[i]);
                stepLabels[i].setBackground(new Color(210, 235, 210));
                stepLabels[i].setFont(stepLabels[i].getFont().deriveFont(Font.BOLD));
            } else if (i == currentStep) {
                stepLabels[i].setText((i + 1) + ". " + stepNames[i]);
                stepLabels[i].setBackground(new Color(190, 215, 245));
                stepLabels[i].setFont(stepLabels[i].getFont().deriveFont(Font.BOLD));
            } else {
                stepLabels[i].setText((i + 1) + ". " + stepNames[i]);
                stepLabels[i].setBackground(new Color(230, 230, 230));
                stepLabels[i].setFont(stepLabels[i].getFont().deriveFont(Font.PLAIN));
            }
        }
    }
}
