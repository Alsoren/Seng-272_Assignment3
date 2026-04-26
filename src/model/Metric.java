package model;

public class Metric {
    private String name;
    private int coefficient;
    private Direction direction;
    private double minValue;
    private double maxValue;
    private String unit;
    private double rawValue;
    private double score;

    public Metric(String name, int coefficient, Direction direction, double minValue, double maxValue, String unit, double rawValue) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.rawValue = rawValue;
    }

    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public Direction getDirection() { return direction; }
    public double getMinValue() { return minValue; }
    public double getMaxValue() { return maxValue; }
    public String getUnit() { return unit; }
    public double getRawValue() { return rawValue; }
    public double getScore() { return score; }

    public void setScore(double score) {
        this.score = score;
    }
}
