import java.sql.*;
import java.util.*;

class Student {
    int ID;
    String Name;
    String dpt;
    int Age;

    public Student(int ID, String Name, String dpt, int Age) {
        this.ID = ID;
        this.Name = Name;
        this.dpt = dpt;
        this.Age = Age;
    }
}

public class StudentManagement {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Santhosh@1936";
    private static final String ADMIN_PASSWORD = "admin123";
    private static boolean isAuthenticated = false;

    public static void authenticateAdmin() {
        Scanner ss = new Scanner(System.in);
        System.out.print("Enter Admin Password: ");
        String password = ss.nextLine();

        if (password.equals(ADMIN_PASSWORD)) {
            isAuthenticated = true;
            System.out.println("Authentication successful! ✅");
        } else {
            System.out.println("Authentication failed! ❌ Exiting...");
            System.exit(0);
        }
    }

    public static void addStudent() {
        if (!isAuthenticated) return;
        Scanner ss = new Scanner(System.in);
        System.out.println("Enter the ID of the student: ");
        int id = ss.nextInt();
        ss.nextLine();
        System.out.println("Enter the name of the student: ");
        String name = ss.nextLine();
        System.out.println("Enter the department of the student: ");
        String dpt = ss.nextLine();
        System.out.println("Enter the age of the student: ");
        int age = ss.nextInt();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?)") ) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, dpt);
            stmt.setInt(4, age);
            stmt.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public static void viewStudents() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            System.out.printf("%-8s %-20s %-15s %-5s%n", "ID", "Name", "Department", "Age");
            System.out.println("--------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-8d %-20s %-15s %-5d%n",
                        rs.getInt("ID"), rs.getString("Name"), rs.getString("Department"), rs.getInt("Age"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
    }

    public static void deleteStudent() {
        Scanner ss = new Scanner(System.in);
        System.out.print("Enter student ID to delete: ");
        int id = ss.nextInt();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM students WHERE ID = ?");
             PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO deleted_students (ID, Name, Department, Age) VALUES (?, ?, ?, ?)");
             PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM students WHERE ID = ?")) {

            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                insertStmt.setInt(1, rs.getInt("ID"));
                insertStmt.setString(2, rs.getString("Name"));
                insertStmt.setString(3, rs.getString("Department"));
                insertStmt.setInt(4, rs.getInt("Age"));
                insertStmt.executeUpdate();

                deleteStmt.setInt(1, id);
                deleteStmt.executeUpdate();
                System.out.println("Student deleted successfully and stored in deleted_students!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    public static void searchStudent() {
        Scanner ss = new Scanner(System.in);
        System.out.println("Search by:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.print("Enter your choice: ");
        int choice = ss.nextInt();
        ss.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = choice == 1 ? conn.prepareStatement("SELECT * FROM students WHERE ID = ?") :
                     conn.prepareStatement("SELECT * FROM students WHERE Name LIKE ?")) {

            if (choice == 1) {
                System.out.print("Enter student ID: ");
                int id = ss.nextInt();
                stmt.setInt(1, id);
            } else if (choice == 2) {
                System.out.print("Enter student Name: ");
                String name = ss.nextLine();
                stmt.setString(1, "%" + name + "%");
            } else {
                System.out.println("Invalid choice!");
                return;
            }

            ResultSet rs = stmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("Name") + ", Department: " + rs.getString("Department") + ", Age: " + rs.getInt("Age"));
                found = true;
            }
            if (!found) {
                System.out.println("No matching students found!");
            }
        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }


    public static void updateStudent() {
        Scanner ss = new Scanner(System.in);
        System.out.print("Enter student ID to update: ");
        int id = ss.nextInt();
        ss.nextLine();
        System.out.print("Enter new name (or press Enter to keep unchanged): ");
        String name = ss.nextLine();
        System.out.print("Enter new department (or press Enter to keep unchanged): ");
        String dpt = ss.nextLine();
        System.out.print("Enter new age (or press Enter to keep unchanged): ");
        String ageStr = ss.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE students SET Name = ?, Department = ?, Age = ? WHERE ID = ?")) {
            if (!name.isEmpty()) stmt.setString(1, name);
            if (!dpt.isEmpty()) stmt.setString(2, dpt);
            if (!ageStr.isEmpty()) stmt.setInt(3, Integer.parseInt(ageStr));
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner ss = new Scanner(System.in);
        authenticateAdmin();

        while (true) {
            System.out.println("\n📚 Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Update Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = ss.nextInt();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> deleteStudent();
                case 4 -> updateStudent();
                case 5 -> searchStudent();
                case 6 -> {
                    System.out.println("Exiting... 👋");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
