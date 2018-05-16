package controller;

import model.Student;
import model.dao.CredentialDAO;
import model.dao.StudentDAO;
import org.apache.log4j.Logger;

/**
 * Controller used to manage all the student's related stuff such as insertion, update etc.
 */
public class StudentController {
    final static Logger logger = Logger.getLogger(StudentController.class);

    CredentialDAO credentialDAO;
    StudentDAO studentDAO;

    public StudentController() {
        credentialDAO = new CredentialDAO();
        studentDAO = new StudentDAO();
    }

    /**
     * Method used to insert a new student into the DB with the relative instance in the credentials table.
     *
     * @param student student to insert.
     * @return id of the inserted student, -1 otherwise.
     */
    public int insertStudent(Student student) {
        // In order to insert a student first we need to check if the email is available, then we register the credential,
        // then the we get the last inserted id and insert a student instance in the table with the returned id.
        if (credentialDAO.checkEmailAvailable(student.getCredential().getEmail())) {
            // Email is available, then insert credential.
            int id = credentialDAO.insert(student.getCredential(), student.getCredential().getPassword());
            if (id != -1) {
                // Credential inserted correctly.
                student.setId(id);
                // Then insert the student.
                student.getCredential().setUserType(0); // 0 means student.
                if (studentDAO.insert(student)) {
                    // OK.
                    return student.getId();
                } else {
                    logger.error("Error while inserting credential object.");
                }
            } else {
                logger.error("Error while inserting student object.");
            }
        } else {
            logger.error("Email is not available.");
        }
        return -1;
    }

    /**
     * Method used to select information about a specific information. The returned info vary according to userType.
     *
     * @param userType     userType which is requesting information. 0=student, 1=company, 2=admin.
     * @param idStudent    student's ID.
     * @param idLoggedUser requesting user's ID.
     * @return student instance, null otherwise.
     */
    public Student selectiveSelect(int userType, int idStudent, int idLoggedUser) {
        switch (userType) {
            case 0: {
                // Student can get information only if id is his id.
                if (idStudent != idLoggedUser) return null;
                return studentDAO.getStudentById(idStudent);
            }
            case 1: {
                // A company can see information only if student is doing an internship with them.
                if (new InternshipController().checkUserInternshipInCompany(idStudent, idLoggedUser)) {
                    return studentDAO.getStudentById(idStudent);
                }
                break;
            }
            case 2: {
                // Admin can always see infomation.
                return studentDAO.getStudentById(idStudent);
            }
            default:
                logger.error("userType value is not correct.");
                return null;
        }
        return null;
    }
}
