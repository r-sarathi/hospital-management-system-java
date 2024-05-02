import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url =  "jdbc:mysql://localhost:3306/hospital_management";
    private static final String username = "root";
    private static final String password = "your_database_root_password";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while(true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> {
                        patient.AddPatient();
                        System.out.println();
                    }
                    case 2 -> {
                        patient.ViewPatients();
                        System.out.println();
                    }
                    case 3 -> {
                        doctor.ViewDoctors();
                        System.out.println();
                    }
                    case 4 -> {
                        BookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                    }
                    case 5 -> {
                        System.out.println("|-------------------------------------------------|");
                        return;
                    }
                    default -> System.out.println("Enter valid choice!!!");
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void BookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient Id: ");
        int patientID = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorID = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if(patient.GetPatientByID(patientID) && doctor.GetDoctorByID(doctorID)){
            if(CheckDoctorAvailability(doctorID, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientID);
                    preparedStatement.setInt(2, doctorID);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked!");
                    }else{
                        System.out.println("Failed to Book Appointment!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }

    public static boolean CheckDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
