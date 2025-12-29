/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gradeanalyzer1;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jenniferobach
 */
public class GradeAnalyzer {
    
    // Constants for grade thresholds
    private static final int A_THRESHOLD = 90;
    private static final int B_THRESHOLD = 80;
    private static final int C_THRESHOLD = 70;
    private static final int D_THRESHOLD = 60;
    
    // Class attributes
    private ArrayList<Double> grades;
    private int[] gradeDistribution;
    
    /**
     * Constructor - Initializes the GradeAnalyzer
     */
    public GradeAnalyzer() {
        grades = new ArrayList<>();
        gradeDistribution = new int[5]; // Indexes: 0=A, 1=B, 2=C, 3=D, 4=F
    }
    
    /**
     * Main method - Program entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        GradeAnalyzer analyzer = new GradeAnalyzer();
        Scanner scanner = new Scanner(System.in);
        
        displayWelcomeMessage();
        
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getMenuChoice(scanner);
            
            // Switch statement for menu selection
            switch (choice) {
                case 1 -> inputGrades(scanner, analyzer);
                case 2 -> analyzer.displayAllGrades();
                case 3 -> analyzer.calculateAndDisplayStatistics();
                case 4 -> analyzer.displayGradeDistribution();
                case 5 -> analyzer.displayLetterGrades();
                case 6 -> analyzer.displayCompleteReport();
                case 7 -> {
                    running = false;
                    System.out.println("\nThank you for using GradeAnalyzer!");
                }
                default -> System.out.println("\nInvalid choice. Please enter 1-7.");
            }
            
            if (running && choice != 1 && choice != 7) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
        System.out.println("Program terminated.");
    }
    
    // =============== STATIC HELPER METHODS ===============
    
    /**
     * Displays the welcome message
     */
    private static void displayWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         GRADE ANALYZER v2.0          ║");
        System.out.println("║      NetBeans Console Edition        ║");
        System.out.println("╚══════════════════════════════════════╝\n");
        System.out.println("Welcome to GradeAnalyzer!");
        System.out.println("A comprehensive tool for educational grade analysis.\n");
    }
    
    /**
     * Displays the main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n════════════════ MAIN MENU ════════════════");
        System.out.println("  1. 📝 Input New Grades");
        System.out.println("  2. 📄 View All Entered Grades");
        System.out.println("  3. 📊 Calculate Statistics");
        System.out.println("  4. 📈 Show Grade Distribution");
        System.out.println("  5. 🔤 Show Letter Grade Conversion");
        System.out.println("  6. 📋 Display Complete Report");
        System.out.println("  7. 🚪 Exit Program");
        System.out.println("═══════════════════════════════════════════");
    }
    
    /**
     * Gets and validates menu choice from user
     * @param scanner Scanner object for input
     * @return validated menu choice
     */
    private static int getMenuChoice(Scanner scanner) {
        System.out.print("\nEnter your choice (1-7): ");
        
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number (1-7): ");
            scanner.next(); // Clear invalid input
        }
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear newline character
        return choice;
    }
    
    /**
     * Handles grade input from user
     * @param scanner Scanner object for input
     * @param analyzer GradeAnalyzer instance
     */
    private static void inputGrades(Scanner scanner, GradeAnalyzer analyzer) {
        System.out.println("\n════════════════ INPUT GRADES ════════════════");
        System.out.println("Enter grades between 0 and 100.");
        System.out.println("Type '-1' when finished.");
        System.out.println("══════════════════════════════════════════════\n");
        
        analyzer.clearAllData();
        
        int gradeCount = 0;
        boolean inputActive = true;
        
        while (inputActive) {
            System.out.print("Enter grade #" + (gradeCount + 1) + " (or -1 to finish): ");
            
            if (!scanner.hasNextDouble()) {
                System.out.println("❌ Error: Please enter a valid number.");
                scanner.next(); // Clear invalid input
                continue;
            }
            
            double grade = scanner.nextDouble();
            scanner.nextLine(); // Clear newline
            
            // Check for exit condition
            if (grade == -1) {
                inputActive = false;
                continue;
            }
            
            // Validate grade range
            if (grade < 0 || grade > 100) {
                System.out.println("❌ Error: Grade must be between 0 and 100.");
                continue;
            }
            
            // Add grade to analyzer
            if (analyzer.addGrade(grade)) {
                System.out.println("✅ Grade " + grade + " added successfully.");
                gradeCount++;
            }
        }
        
        if (gradeCount > 0) {
            System.out.println("\n" + gradeCount + " grades entered successfully.");
        } else {
            System.out.println("\nNo grades were entered.");
        }
    }
    
    // =============== INSTANCE METHODS ===============
    
    /**
     * Adds a grade to the collection
     * @param grade The grade to add
     * @return true if grade was added successfully
     */
    public boolean addGrade(double grade) {
        return grades.add(grade);
    }
    
    /**
     * Clears all grades and resets distribution
     */
    public void clearAllData() {
        grades.clear();
        resetDistribution();
        System.out.println("All previous data has been cleared.");
    }
    
    /**
     * Resets the grade distribution array
     */
    private void resetDistribution() {
        for (int i = 0; i < gradeDistribution.length; i++) {
            gradeDistribution[i] = 0;
        }
    }
    
    /**
     * Gets the number of grades entered
     * @return count of grades
     */
    public int getGradeCount() {
        return grades.size();
    }
    
    /**
     * Displays all entered grades
     */
    public void displayAllGrades() {
        System.out.println("\n═════════════ ENTERED GRADES ═════════════");
        
        if (grades.isEmpty()) {
            System.out.println("No grades have been entered yet.");
            return;
        }
        
        System.out.print("Grades: [");
        for (int i = 0; i < grades.size(); i++) {
            System.out.printf("%.1f", grades.get(i));
            if (i < grades.size() - 1) {
                System.out.print(", ");
            }
            // Format for readability with many grades
            if ((i + 1) % 10 == 0 && i < grades.size() - 1) {
                System.out.print("\n        ");
            }
        }
        System.out.println("]");
        
        System.out.println("Total grades: " + grades.size());
        System.out.println("══════════════════════════════════════════");
    }
    
    /**
     * Calculates and displays statistical analysis
     */
    public void calculateAndDisplayStatistics() {
        System.out.println("\n══════════════ STATISTICS ══════════════");
        
        if (grades.isEmpty()) {
            System.out.println("No grades to analyze. Please enter grades first.");
            return;
        }
        
        double average = calculateAverage();
        double highest = findHighestGrade();
        double lowest = findLowestGrade();
        double median = calculateMedian();
        double range = highest - lowest;
        
        System.out.printf("📊 Average (Mean): %.2f\n", average);
        System.out.printf("🏆 Highest Score:  %.1f\n", highest);
        System.out.printf("📉 Lowest Score:   %.1f\n", lowest);
        System.out.printf("📐 Median Score:   %.1f\n", median);
        System.out.printf("📏 Score Range:    %.1f\n", range);
        System.out.printf("👥 Total Students: %d\n", grades.size());
        System.out.println("══════════════════════════════════════════");
    }
    
    /**
     * Calculates the average grade
     * @return average of all grades
     */
    private double calculateAverage() {
        if (grades.isEmpty()) return 0;
        
        double sum = 0;
        // Using enhanced for loop
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }
    
    /**
     * Finds the highest grade
     * @return highest grade value
     */
    private double findHighestGrade() {
        if (grades.isEmpty()) return 0;
        
        double highest = grades.get(0);
        // Using traditional for loop
        for (int i = 1; i < grades.size(); i++) {
            if (grades.get(i) > highest) {
                highest = grades.get(i);
            }
        }
        return highest;
    }
    
    /**
     * Finds the lowest grade
     * @return lowest grade value
     */
    private double findLowestGrade() {
        if (grades.isEmpty()) return 0;
        
        double lowest = grades.get(0);
        // Using traditional for loop
        for (int i = 1; i < grades.size(); i++) {
            if (grades.get(i) < lowest) {
                lowest = grades.get(i);
            }
        }
        return lowest;
    }
    
    /**
     * Calculates the median grade
     * @return median grade value
     */
    private double calculateMedian() {
        if (grades.isEmpty()) return 0;
        
        ArrayList<Double> sortedGrades = new ArrayList<>(grades);
        Collections.sort(sortedGrades);
        
        int size = sortedGrades.size();
        if (size % 2 == 0) {
            // Even number of grades
            double mid1 = sortedGrades.get(size / 2 - 1);
            double mid2 = sortedGrades.get(size / 2);
            return (mid1 + mid2) / 2.0;
        } else {
            // Odd number of grades
            return sortedGrades.get(size / 2);
        }
    }
    
    /**
     * Calculates grade distribution counts
     */
    private void calculateDistribution() {
        resetDistribution();
        
        for (double grade : grades) {
            char letter = getLetterGrade(grade);
            
            // Switch statement for distribution counting
            switch (letter) {
                case 'A' -> gradeDistribution[0]++;
                case 'B' -> gradeDistribution[1]++;
                case 'C' -> gradeDistribution[2]++;
                case 'D' -> gradeDistribution[3]++;
                case 'F' -> gradeDistribution[4]++;
            }
        }
    }
    
    /**
     * Determines letter grade for a numerical grade
     * @param grade numerical grade
     * @return letter grade (A-F)
     */
    private char getLetterGrade(double grade) {
        if (grade >= A_THRESHOLD) {
            return 'A';
        } else if (grade >= B_THRESHOLD) {
            return 'B';
        } else if (grade >= C_THRESHOLD) {
            return 'C';
        } else if (grade >= D_THRESHOLD) {
            return 'D';
        } else {
            return 'F';
        }
    }
    
    /**
     * Displays grade distribution as a table
     */
    public void displayGradeDistribution() {
        System.out.println("\n════════════ GRADE DISTRIBUTION ════════════");
        
        if (grades.isEmpty()) {
            System.out.println("No grades to display. Please enter grades first.");
            return;
        }
        
        calculateDistribution();
        
        System.out.println("┌─────────────┬─────────┬───────┬─────────────┐");
        System.out.println("│ Grade Range │ Letter  │ Count │ Percentage  │");
        System.out.println("├─────────────┼─────────┼───────┼─────────────┤");
        
        String[] ranges = {"90-100", "80-89", "70-79", "60-69", "0-59"};
        String[] letters = {"A", "B", "C", "D", "F"};
        
        int total = grades.size();
        for (int i = 0; i < 5; i++) {
            double percentage = total > 0 ? (gradeDistribution[i] * 100.0 / total) : 0;
            System.out.printf("│ %-11s │    %-4s │ %5d │ %10.1f%% │\n", 
                            ranges[i], letters[i], gradeDistribution[i], percentage);
        }
        
        System.out.println("└─────────────┴─────────┴───────┴─────────────┘");
        System.out.println("═══════════════════════════════════════════════");
    }
    
    /**
     * Displays letter grade conversion for all grades
     */
    public void displayLetterGrades() {
        System.out.println("\n════════════ LETTER GRADE CONVERSION ════════════");
        
        if (grades.isEmpty()) {
            System.out.println("No grades to display. Please enter grades first.");
            return;
        }
        
        System.out.println("┌─────────────┬─────────┬─────────────┐");
        System.out.println("│ Numerical   │ Letter  │ Description │");
        System.out.println("├─────────────┼─────────┼─────────────┤");
        
        for (double grade : grades) {
            char letter = getLetterGrade(grade);
            String description = getGradeDescription(letter);
            System.out.printf("│ %11.1f │    %-4s │ %-11s │\n", grade, letter, description);
        }
        
        System.out.println("└─────────────┴─────────┴─────────────┘");
        System.out.println("═════════════════════════════════════════════════");
    }
    
    /**
     * Gets description for a letter grade
     * @param letter grade letter
     * @return description string
     */
    private String getGradeDescription(char letter) {
        return switch (letter) {
            case 'A' -> "Excellent";
            case 'B' -> "Good";
            case 'C' -> "Average";
            case 'D' -> "Below Avg";
            case 'F' -> "Fail";
            default -> "Unknown";
        };
    }
    
    /**
     * Displays a complete analysis report
     */
    public void displayCompleteReport() {
        System.out.println("\n════════════════ COMPLETE REPORT ════════════════");
        
        if (grades.isEmpty()) {
            System.out.println("No grades to report. Please enter grades first.");
            return;
        }
        
        // Header
        System.out.println("GRADE ANALYZER - COMPREHENSIVE REPORT");
        System.out.println("Generated on: " + java.time.LocalDate.now());
        System.out.println("══════════════════════════════════════════════════\n");
        
        // Section 1: Grades Summary
        System.out.println("1. GRADES SUMMARY");
        System.out.println("─────────────────");
        displayAllGrades();
        
        // Section 2: Statistics
        System.out.println("\n2. STATISTICAL ANALYSIS");
        System.out.println("───────────────────────");
        calculateAndDisplayStatistics();
        
        // Section 3: Distribution
        System.out.println("\n3. GRADE DISTRIBUTION");
        System.out.println("─────────────────────");
        displayGradeDistribution();
        
        // Section 4: Performance Analysis
        System.out.println("\n4. PERFORMANCE ANALYSIS");
        System.out.println("───────────────────────");
        double average = calculateAverage();
        System.out.println("Overall Performance: " + getPerformanceComment(average));
        
        // Section 5: Recommendations
        System.out.println("\n5. RECOMMENDATIONS");
        System.out.println("──────────────────");
        System.out.println(getRecommendations());
        
        System.out.println("\n════════════════════ END OF REPORT ════════════════════");
    }
    
    /**
     * Generates performance comment
     * @param average average grade
     * @return performance comment
     */
    private String getPerformanceComment(double average) {
        if (average >= 90) {
            return "⭐ EXCELLENT - Outstanding performance!";
        } else if (average >= 80) {
            return "👍 GOOD - Above average performance";
        } else if (average >= 70) {
            return "↔ AVERAGE - Meets expectations";
        } else if (average >= 60) {
            return "⚠ NEEDS IMPROVEMENT - Below average";
        } else {
            return "❌ POOR - Significant improvement needed";
        }
    }
    
    /**
     * Generates recommendations based on grade analysis
     * @return recommendations string
     */
    private String getRecommendations() {
        if (grades.isEmpty()) return "No data for recommendations.";
        
        calculateDistribution();
        
        StringBuilder recommendations = new StringBuilder();
        
        if (gradeDistribution[4] > 0) { // F grades
            recommendations.append("- Consider additional support for students failing\n");
        }
        
        if (gradeDistribution[0] + gradeDistribution[1] > grades.size() / 2) {
            recommendations.append("- Excellent overall performance. Consider advanced material\n");
        }
        
        if (calculateAverage() < 70) {
            recommendations.append("- Review teaching methods and assessment difficulty\n");
        }
        
        if (findLowestGrade() < 50) {
            recommendations.append("- Individual attention needed for lowest performers\n");
        }
        
        return recommendations.toString();
    }
}