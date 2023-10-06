package com.github;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class School {
	
	private static void createStudent(Connection connection, Scanner scanner) throws SQLException {
	    System.out.print("Enter student's full name: ");
	    String fullName = scanner.nextLine();
	    System.out.print("Enter student's age: ");
	    int age = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline character
	    System.out.print("Enter student's gender: ");
	    String gender = scanner.nextLine();
	    System.out.print("Enter student's date of birth (yyyy-mm-dd): ");
	    String dateOfBirth = scanner.nextLine();
	    System.out.print("Enter student's email: ");
	    String email = scanner.nextLine();
	    System.out.print("Enter student's phone: ");
	    String phone = scanner.nextLine();

	    String insertQuery = "INSERT INTO students (FullName, Age, Gender, DateOfBirth, Email, Phone) " +
	            "VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	        preparedStatement.setString(1, fullName);
	        preparedStatement.setInt(2, age);
	        preparedStatement.setString(3, gender);
	        preparedStatement.setString(4, dateOfBirth);
	        preparedStatement.setString(5, email);
	        preparedStatement.setString(6, phone);

	        int rowsInserted = preparedStatement.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("New student inserted successfully.");
	        } else {
	            System.out.println("Failed to insert student.");
	        }
	    }
	}
	
	private static void readStudent(Connection connection, Scanner scanner) throws SQLException {
	    System.out.print("Enter student's ID: ");
	    int studentID = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline character

	    String selectQuery = "SELECT * FROM students WHERE Student_ID = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	        preparedStatement.setInt(1, studentID);

	        try (
	            ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                System.out.println("Student ID: " + resultSet.getInt("Student_ID"));
	                System.out.println("Full Name: " + resultSet.getString("FullName"));
	                System.out.println("Age: " + resultSet.getInt("Age"));
	                System.out.println("Gender: " + resultSet.getString("Gender"));
	                System.out.println("Date of Birth: " + resultSet.getString("DateOfBirth"));
	                System.out.println("Email: " + resultSet.getString("Email"));
	                System.out.println("Phone: " + resultSet.getString("Phone"));
	            } else {
	                System.out.println("Student not found.");
	            }
	        }
	    }
	}

	
	private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
	    System.out.print("Enter student's ID: ");
	    int studentID = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline character

	    String selectQuery = "SELECT * FROM students WHERE Student_ID = ?";
	    String updateQuery = "UPDATE students SET Age = ?, Email = ?, Phone = ? WHERE Student_ID = ?";

	    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	         PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

	        selectStatement.setInt(1, studentID);

	        try (ResultSet resultSet = selectStatement.executeQuery()) {
	            if (resultSet.next()) {
	                System.out.print("Enter updated age: ");
	                int updatedAge = scanner.nextInt();
	                scanner.nextLine(); // Consume the newline character
	                System.out.print("Enter updated email: ");
	                String updatedEmail = scanner.nextLine();
	                System.out.print("Enter updated phone: ");
	                String updatedPhone = scanner.nextLine();

	                updateStatement.setInt(1, updatedAge);
	                updateStatement.setString(2, updatedEmail);
	                updateStatement.setString(3, updatedPhone);
	                updateStatement.setInt(4, studentID);

	                int rowsUpdated = updateStatement.executeUpdate();
	                if (rowsUpdated > 0) {
	                    System.out.println("Student data updated successfully.");
	                } else {
	                    System.out.println("Failed to update student data.");
	                }
	            } else {
	                System.out.println("Student not found.");
	            }
	        }
	    }
	}

	
	private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
	    System.out.print("Enter student's ID: ");
	    int studentID = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline character

	    String deleteQuery = "DELETE FROM students WHERE Student_ID = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
	        preparedStatement.setInt(1, studentID);

	        int rowsDeleted = preparedStatement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Student deleted successfully.");
	        } else {
	            System.out.println("Failed to delete student.");
	        }
	    }
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jdbcUrl = "jdbc:mysql://localhost:3306/schoolmanagement?serverTimezone=Europe/Stockholm";
		String username = "root";
		String password = "";

		try (
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
		    System.out.println("Database connection established.");

		    // Create a Scanner for user input
		    Scanner scanner = new Scanner(System.in);

		    while (true) {
		        // Display menu options
		        System.out.println("\nChoose an operation:");
		        System.out.println("1. Create (Insert) a new student");
		        System.out.println("2. Read (Select) student data");
		        System.out.println("3. Update student data");
		        System.out.println("4. Delete a student");
		        System.out.println("5. Exit");
		        System.out.print("Enter your choice: ");

		        int choice = scanner.nextInt();
		        scanner.nextLine(); // Consume the newline character

		        switch (choice) {
		            case 1:
		                // Implement createStudent method
		            	createStudent(connection,scanner);
		                break;
		            case 2:
		                // Implement readStudent method
		            	readStudent(connection,scanner);
		                break;
		            case 3:
		                // Implement updateStudent method
		            	updateStudent(connection,scanner);
		                break;
		            case 4:
		                // Implement deleteStudent method
		            	deleteStudent(connection,scanner);
		                break;
		            case 5:
		                System.out.println("Exiting program.");
		                return;
		            default:
		                System.out.println("Invalid choice. Please enter a valid option.");
		        }
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}

}
