package controller;

import model.Candidacy;
import model.dao.CandidacyDAO;
import model.dao.InternshipDAO;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class InternshipController {
    private InternshipDAO internshipDAO;
    private CandidacyDAO candidacyDAO;

    InternshipController() {
        this.internshipDAO = new InternshipDAO();
        this.candidacyDAO = new CandidacyDAO();
    }

    public boolean checkUserInternshipInCompany(int idStudent, int idLoggedUser) {
        // First we need to get all the company internships running in this period.
        Calendar calendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        List<Candidacy> list = candidacyDAO.getAllActiveCandidacyForCompany(timestamp, idLoggedUser);
        for (Candidacy c : list) {
            if (c.getStudentFk() == idStudent) {
                return true;
            }
        }
        return false;
    }
}
