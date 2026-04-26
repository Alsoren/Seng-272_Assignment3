package service;

import model.Direction;
import model.Dimension;
import model.Metric;

public class ScoreCalculator {
    public double calculateMetricScore(Metric metric) {
        double min = metric.getMinValue();
        double max = metric.getMaxValue();
        double value = metric.getRawValue();
        double score;

        if (metric.getDirection() == Direction.HIGHER_IS_BETTER) {
            score = 1 + ((value - min) / (max - min)) * 4;
        } else {
            score = 5 - ((value - min) / (max - min)) * 4;
        }

        score = clamp(score, 1.0, 5.0);
        score = roundToNearestHalf(score);
        metric.setScore(score);
        return score;
    }

    public double calculateDimensionScore(Dimension dimension) {
        double weightedTotal = 0;
        double coefficientTotal = 0;

        for (Metric metric : dimension.getMetrics()) {
            double metricScore = calculateMetricScore(metric);
            weightedTotal += metricScore * metric.getCoefficient();
            coefficientTotal += metric.getCoefficient();
        }

        double dimensionScore = coefficientTotal == 0 ? 0 : weightedTotal / coefficientTotal;
        dimension.setDimensionScore(dimensionScore);
        return dimensionScore;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double roundToNearestHalf(double value) {
        return Math.round(value * 2.0) / 2.0;
    }
}
