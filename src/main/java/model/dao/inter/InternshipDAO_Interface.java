package model.dao.inter;

import model.Internship;

import java.sql.Timestamp;
import java.util.List;

public interface InternshipDAO_Interface {

    int insert(Internship internship);

    Internship getInternshipByDate(Timestamp timestamp);

    Internship getInternshipByTwoDate(Timestamp timestamp1, Timestamp timestamp2);

    Internship getInternshipFromID(int id);

    List<Internship>  getIntershipByCompanyID(int id);
}
