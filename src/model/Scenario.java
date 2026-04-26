package model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String name;
    private String mode;
    private QualityType qualityType;
    private ArrayList<Dimension> dimensions;

    public Scenario(String name, String mode, QualityType qualityType) {
        this.name = name;
        this.mode = mode;
        this.qualityType = qualityType;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public String getName() { return name; }
    public String getMode() { return mode; }
    public QualityType getQualityType() { return qualityType; }
    public List<Dimension> getDimensions() { return dimensions; }

    @Override
    public String toString() {
        return name;
        // JComboBox uses this method to display a readable scenario name.
    }
}
