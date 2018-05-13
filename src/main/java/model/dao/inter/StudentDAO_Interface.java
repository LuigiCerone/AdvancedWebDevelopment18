package model.dao.inter;

import model.Student;
import model.dao.StudentDAO;

public interface StudentDAO_Interface {
    int insert(Student student);

    Student getStudentById(int id);
}
