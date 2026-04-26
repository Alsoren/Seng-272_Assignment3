ISO 15939 Measurement Process Simulator - Implementation V2

Student Name: Ahmet Alp Keleş
Student ID: 202328038
Course: Software Project II
Project Type: Individual Java Swing GUI Application

V2 Scope
- Java Swing desktop application with CardLayout wizard structure
- Separate JPanel implementation for Profile, Define, Plan, Collect, and Analyse steps
- Step indicator with active and completed step status
- Profile validation with user-friendly warning messages
- Define step with JRadioButton/ButtonGroup single-selection for quality type and mode
- Scenario selection based on selected mode
- ScenarioRepository with two modes: Education and Health
- At least two scenarios for each mode
- Plan step with read-only JTable for dimensions and metrics
- Collect step with raw values and calculated metric scores
- ScoreCalculator service for metric score and dimension score calculations
- GapAnalyzer service for lowest dimension, gap value, quality level, and improvement message
- Analyse step with progress bars, improved gap analysis area, and RadarChartPanel bonus preview
- Model, GUI, service, and app state separation
- No external libraries

V2 Improvements Compared to V1
- Added service/GapAnalyzer.java
- Added service/GapAnalysisResult.java
- Moved gap analysis logic out of AnalysePanel
- Fixed progress bar scaling to use 0-100 percentage values
- Replaced long single-line gap label with a multiline JTextArea
- Added gui/RadarChartPanel.java for radar chart visualization
- Updated mode selection to use JRadioButton and ButtonGroup
- Updated application title and README for V2

Compile Instructions
Open a terminal in the project root folder and run:

javac -d out src/Main.java src/app/*.java src/model/*.java src/service/*.java src/gui/*.java

Run Instructions
After compiling, run:

java -cp out Main

Submission Notes
- This is the V2 GitHub submission version.
- The project uses Java SE standard library only.
- The scenario data is hard-coded inside ScenarioRepository as required.
- Student name and student ID fields are completed for submission. Add a screenshot if requested by your instructor.
