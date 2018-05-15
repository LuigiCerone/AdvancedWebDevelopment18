package model.dao.inter;

import model.Internship;

import java.sql.Timestamp;

public interface InternshipDAO_Interface {

    int insert(Internship internship);

    Internship getInternshipByDate(Timestamp timestamp);

    Internship getInternshipByTwoDate(Timestamp timestamp1, Timestamp timestamp2);

}
