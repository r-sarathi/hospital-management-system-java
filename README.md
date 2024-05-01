# Hospital Management System

This project is a Java-based Hospital Management System that utilizes MySQL database for storing data. It allows users to manage patients, doctors, and appointments efficiently.

## Database Setup

1. Create Database: This query creates a database named "hospital_management" to store all the necessary data.

```sql
   CREATE DATABASE hospital_management;
```

2. Use Database: This query selects the "hospital_management" database for executing further operations.

```sql
   USE hospital_management;
```

3. Create Patients Table: This query creates a table named "patients" with columns for patient ID, name, age, and gender.

```sql
   CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);
```

4. Create Doctors Table: This query creates a table named "doctors" with columns for doctor ID, name, and specialization.

```sql
   CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL
);
```

5. Create Appointments Table: This query creates a table named "appointments" with columns for appointment ID, patient ID, doctor ID, and appointment date. It also establishes foreign key constraints to maintain referential integrity.

```sql
    CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
```

## Option:

* Add Patient: Allows users to add a new patient to the system.
* View Patients: Displays a list of all patients in the system.
* View Doctors: Displays a list of all doctors in the system along with their specializations.
* Book Appointment: Enables users to book an appointment by selecting a patient and a doctor from the available lists.
* Exit: Terminates the program and exits the system.

