package model.dao.inter;

import model.Student;

public interface StudentDAO_Interface {
    boolean insert(Student student);

    Student getStudentById(int id);
}
