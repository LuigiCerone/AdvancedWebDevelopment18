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

    public int insertStudent(Student student) {
        // In order to insert a student first we need to check if the email is available, then we register the student,
        // then the we get the last inserted id and insert a credential instance in the table.
        if (credentialDAO.checkEmailAvailable(student.getCredential().getEmail())) {
            // Email is available, then insert student.
            int id = studentDAO.insert(student);
            if (id != -1) {
                // Student inserted correctly.
                student.setId(id);
                // Then insert the credential.
                student.getCredential().setUserType(0); // 0 means student.
                student.getCredential().setUserFk(id);
                if (credentialDAO.insert(student.getCredential(), student.getPassword())) {
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

}
