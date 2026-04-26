package service;

import model.Dimension;
import model.Direction;
import model.Metric;
import model.QualityType;
import model.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioRepository {
    private final HashMap<String, ArrayList<Scenario>> scenariosByMode;

    public ScenarioRepository() {
        scenariosByMode = new HashMap<>();
        loadScenarios();
    }

    public List<String> getModes() {
        return new ArrayList<>(scenariosByMode.keySet());
    }

    public List<Scenario> getScenariosByMode(String mode) {
        return scenariosByMode.getOrDefault(mode, new ArrayList<>());
    }

    private void loadScenarios() {
        ArrayList<Scenario> educationScenarios = new ArrayList<>();
        educationScenarios.add(createEducationTeamAlpha());
        educationScenarios.add(createEducationTeamBeta());
        scenariosByMode.put("Education", educationScenarios);

        ArrayList<Scenario> healthScenarios = new ArrayList<>();
        healthScenarios.add(createHospitalSystem());
        healthScenarios.add(createClinicSystem());
        scenariosByMode.put("Health", healthScenarios);
    }

    private Scenario createEducationTeamAlpha() {
        Scenario scenario = new Scenario("Scenario C - Team Alpha", "Education", QualityType.PRODUCT_QUALITY);

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, Direction.HIGHER_IS_BETTER, 0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding time", 50, Direction.LOWER_IS_BETTER, 0, 60, "min", 5));

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, Direction.LOWER_IS_BETTER, 0, 15, "sec", 3));
        performance.addMetric(new Metric("Concurrent exams", 50, Direction.HIGHER_IS_BETTER, 0, 600, "users", 480));

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance", 50, Direction.HIGHER_IS_BETTER, 0, 100, "%", 92));
        accessibility.addMetric(new Metric("Screen reader score", 50, Direction.HIGHER_IS_BETTER, 0, 100, "%", 86));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, Direction.HIGHER_IS_BETTER, 95, 100, "%", 99));
        reliability.addMetric(new Metric("MTTR", 50, Direction.LOWER_IS_BETTER, 0, 120, "min", 24));

        Dimension functionality = new Dimension("Functional Suitability", 15);
        functionality.addMetric(new Metric("Feature completion", 50, Direction.HIGHER_IS_BETTER, 0, 100, "%", 91));
        functionality.addMetric(new Metric("Assignment submit rate", 50, Direction.HIGHER_IS_BETTER, 0, 100, "%", 88));

        scenario.addDimension(usability);
        scenario.addDimension(performance);
        scenario.addDimension(accessibility);
        scenario.addDimension(reliability);
        scenario.addDimension(functionality);
        return scenario;
    }

    private Scenario createEducationTeamBeta() {
        Scenario scenario = new Scenario("Scenario D - Team Beta", "Education", QualityType.PRODUCT_QUALITY);

        Dimension usability = new Dimension("Usability", 30);
        usability.addMetric(new Metric("SUS score", 50, Direction.HIGHER_IS_BETTER, 0, 100, "points", 74));
        usability.addMetric(new Metric("Onboarding time", 50, Direction.LOWER_IS_BETTER, 0, 60, "min", 18));

        Dimension reliability = new Dimension("Reliability", 25);
        reliability.addMetric(new Metric("Uptime", 50, Direction.HIGHER_IS_BETTER, 95, 100, "%", 97.6));
        reliability.addMetric(new Metric("MTTR", 50, Direction.LOWER_IS_BETTER, 0, 120, "min", 42));

        scenario.addDimension(usability);
        scenario.addDimension(reliability);
        return scenario;
    }

    private Scenario createHospitalSystem() {
        Scenario scenario = new Scenario("Scenario A - Hospital System", "Health", QualityType.PROCESS_QUALITY);

        Dimension service = new Dimension("Service Continuity", 35);
        service.addMetric(new Metric("System availability", 50, Direction.HIGHER_IS_BETTER, 90, 100, "%", 98));
        service.addMetric(new Metric("Incident response time", 50, Direction.LOWER_IS_BETTER, 0, 120, "min", 30));

        Dimension safety = new Dimension("Data Safety", 30);
        safety.addMetric(new Metric("Backup success rate", 50, Direction.HIGHER_IS_BETTER, 0, 100, "%", 94));
        safety.addMetric(new Metric("Security incidents", 50, Direction.LOWER_IS_BETTER, 0, 20, "count", 2));

        scenario.addDimension(service);
        scenario.addDimension(safety);
        return scenario;
    }

    private Scenario createClinicSystem() {
        Scenario scenario = new Scenario("Scenario B - Clinic System", "Health", QualityType.PROCESS_QUALITY);

        Dimension efficiency = new Dimension("Process Efficiency", 40);
        efficiency.addMetric(new Metric("Appointment delay", 50, Direction.LOWER_IS_BETTER, 0, 60, "min", 12));
        efficiency.addMetric(new Metric("Daily completed visits", 50, Direction.HIGHER_IS_BETTER, 0, 200, "visits", 140));

        Dimension satisfaction = new Dimension("User Satisfaction", 30);
        satisfaction.addMetric(new Metric("Patient satisfaction", 50, Direction.HIGHER_IS_BETTER, 0, 100, "%", 83));
        satisfaction.addMetric(new Metric("Complaint count", 50, Direction.LOWER_IS_BETTER, 0, 30, "count", 6));

        scenario.addDimension(efficiency);
        scenario.addDimension(satisfaction);
        return scenario;
    }
}
