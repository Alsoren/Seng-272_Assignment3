ISO 15939 Measurement Process Simulator - Implementation V1

Student Name: Ahmet Alp Keleş
Student ID: 202328038
Course: Software Project II
Project Type: Individual Java Swing GUI Application

V1 Scope
- Java Swing desktop application foundation
- CardLayout wizard structure
- Separate JPanel for each step
- Step indicator with active and completed steps
- Profile step with validation
- Define step with single quality type selection, mode selection, and scenario selection
- ScenarioRepository with two modes: Education and Health
- At least two scenarios for each mode
- Plan step with read-only JTable
- Initial Collect and Analyse preview screens for score calculation demonstration
- Model, GUI, service, and app state separation
- No external libraries

Compile Instructions
Open a terminal in the project root folder and run:

javac -d out src/Main.java src/app/*.java src/model/*.java src/service/*.java src/gui/*.java

Run Instructions
After compiling, run:

java -cp out Main

Notes
- This is the V1 GitHub submission version.
- The project uses Java SE standard library only.
- The scenario data is hard-coded inside ScenarioRepository as required.
- For the final submission, add/complete screenshot documentation and optionally improve Analyse with radar chart.
