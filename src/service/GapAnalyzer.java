package service;

import model.Dimension;
import model.Scenario;

public class GapAnalyzer {
    private final ScoreCalculator scoreCalculator;

    public GapAnalyzer() {
        this.scoreCalculator = new ScoreCalculator();
    }

    public GapAnalysisResult analyze(Scenario scenario) {
        if (scenario == null || scenario.getDimensions().isEmpty()) {
            return null;
        }

        Dimension lowestDimension = null;
        double lowestScore = Double.MAX_VALUE;

        for (Dimension dimension : scenario.getDimensions()) {
            double dimensionScore = scoreCalculator.calculateDimensionScore(dimension);
            if (dimensionScore < lowestScore) {
                lowestScore = dimensionScore;
                lowestDimension = dimension;
            }
        }

        double gap = 5.0 - lowestScore;
        String qualityLevel = getQualityLevel(lowestScore);
        String message = "This dimension has the lowest score and requires the most improvement.";

        return new GapAnalysisResult(lowestDimension, lowestScore, gap, qualityLevel, message);
    }

    public String getQualityLevel(double score) {
        if (score >= 4.5) {
            return "Excellent";
        }
        if (score >= 3.5) {
            return "Good";
        }
        if (score >= 2.5) {
            return "Needs Improvement";
        }
        return "Poor";
    }
}
