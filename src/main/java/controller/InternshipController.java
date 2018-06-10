package controller;

import model.Candidacy;
import model.Internship;
import model.dao.CandidacyDAO;
import model.dao.InternshipDAO;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class InternshipController {
    final static Logger logger = Logger.getLogger(InternshipController.class);

    private InternshipDAO internshipDAO;
    private CandidacyDAO candidacyDAO;

    public InternshipController() {
        this.internshipDAO = new InternshipDAO();
        this.candidacyDAO = new CandidacyDAO();
    }

    /**
     * Method used to check if the student with id = idStudent is currently doing an internship with company
     * (id = idLoggedUser).
     *
     * @param idStudent    student's ID
     * @param idLoggedUser currently logged in user ID
     * @return true if student is doing an internship with company identified by idLoggedUser, false otherwise.
     */
    public boolean checkUserInternshipInCompany(int idStudent, int idLoggedUser) {
        // First we need to get all the company internships running in this period.
        Calendar calendar = Calendar.getInstance();

        List<Candidacy> list = candidacyDAO.getAllActiveCandidacyForCompany(new Date(calendar.getTime().getTime()), idLoggedUser);
        for (Candidacy c : list) {
            if (c.getStudentFk() == idStudent) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to check if the candidacy with id = candidacyId is related to an internship made by company with id =
     * companyId.
     *
     * @param candidacyId student's ID
     * @param companyId   currently logged in user ID
     * @return true if it's its, false otherwise.
     */
    public boolean checkCandidacyForCompany(int candidacyId, int companyId) {
        // First we need to get all the company internships running in this period.
        Calendar calendar = Calendar.getInstance();

        List<Candidacy> list = candidacyDAO.getAllActiveCandidacyForCompany(new Date(calendar.getTime().getTime()), companyId);
        for (Candidacy c : list) {
            if (c.getId() == candidacyId) {
                return true;
            }
        }
        return false;
    }


    /**
     * Method used to finalize a candidacy for a specific candidacy (id = candidacyId).
     *
     * @param companyId   comapany ID.
     * @param candidacyId candidacy ID.
     * @param candidacy   candidacy with information to update.
     * @return 1 if ok, 0 if forbidden, -1 otherwise.
     */
    public int addCandidacyToInternship(int companyId, int candidacyId, Candidacy candidacy) {
        if (this.checkCandidacyForCompany(candidacyId, companyId)) {
            // Then add the information in the DB.
            Candidacy c = candidacyDAO.getCandidacyFromId(candidacyId);
            if (c != null) {
                if (candidacy.getStartDate() != null) c.setStartDate(candidacy.getStartDate());
                if (candidacy.getEndDate() != null) c.setEndDate(candidacy.getEndDate());
                if (candidacyDAO.update(c)) {
                    return 1;
                } else {
                    logger.error("Error while updating candidacy.");
                    return -1;
                }
            } else {
                logger.error("Error while getting candidacy from id.");
                return -1;
            }
        } else return 0;
    }

    /**
     * Method used to delete a candidacy for a specific candidacy (id = candidacyId).
     *
     * @param companyId   comapany ID.
     * @param candidacyId candidacy ID.
     * @return 1 if deleted, 0 if forbidden, -1 otherwise.
     */
    public int deleteCandidacyToInternship(int companyId, int candidacyId) {
        if (this.checkCandidacyForCompany(candidacyId, companyId)) {
            // Then set the status to rejected of the candidacy in the DB.
            if (candidacyDAO.rejectCandidacyById(candidacyId)) {
                return 1;
            } else {
                logger.error("Error while rejecting candidacy.");
                return -1;
            }
        } else {
            logger.error("Error while getting candidacy from id.");
            return -1;
        }
    }

    public int checkIfAllowed(int idLoggedUser, int candidacyId, int userType) {
        // userType contains an integer 0=student, 1=company, 2=admin.
        switch (userType) {
            case 0: {
                // Check if candidacy is related to user.
                Candidacy candidacy = candidacyDAO.getCandidacyFromId(candidacyId);
                if (candidacy.getStudentFk() == idLoggedUser) {
                    return 1;
                } else
                    return 0;
            }
            case 1: {
                if (checkCandidacyForCompany(candidacyId, idLoggedUser))
                    return 1;
                else
                    return 0;
            }
            case 2: {
                // Admin can also download it.
            }
            default: {
                logger.error("Unkown error.");
                return -1;
            }
        }
    }

    public File getPdfForCandidacy(int candidacyId) {
        // TODO Get the PDF somehow.
        File file = new File("test.pdf"); // Initialize this to the File path you want to serve.
        return file;
    }

    public Internship getInternshipByID(int id) {
        return internshipDAO.getInternshipFromID(id);
    }

    public LinkedList<Internship> getListInternshipByCompanyID(int companyId, int first, int last) {
        LinkedList<Internship> list = null;
        if (first != 0 && last != 0) {
            // Both limits are set.
            list = internshipDAO.getInternshipByRangeByCompany(companyId, first, last);
        } else if (first != 0 && last == 0) {
            // Only first limit is set, so return all the internships with id>=first.
            list = internshipDAO.getInternshipByRangeByCompany(companyId, first, 0);
        } else {
            // Both limits are not set, so return all internships.
            list = internshipDAO.getAllInternships();
        }
        return list;
    }

    /**
     * Method used to get internships list filtered according to given filters.
     *
     * @param filter filter that needs to be used: filter=città, min, max.
     * @param n      filter parameter, i.e. name of the city or number of hours.
     * @param first  first parameter used for pagination.
     * @param last   last parameter used for pagination.
     * @return list of internships.
     */
    public LinkedList<Internship> getFilteredInterships(String filter, String n, int first, int last) {
        LinkedList<Internship> list = null;

        if (first != 0 && last != 0) {
            // Both limits are set.
            list = internshipDAO.getInternshipByRange(first, last);
        } else if (first != 0 && last == 0) {
            // Only first limit is set, so return all the internships with id>=first.
            list = internshipDAO.getInternshipByRange(first, 0);
        } else {
            // Both limits are not set, so return all internships.
            list = internshipDAO.getAllInternships();
        }

        // Now we need to filter the list according to filter if specified.
        if (filter != null) {
            // Check which type of filter is used.
            switch (filter) {
                case "city": {
                    Predicate<Internship> internshipPredicate = internship -> internship.getPlace().equalsIgnoreCase(n);
                    list.removeIf(internshipPredicate);
                }
                case "min": {
                    Predicate<Internship> internshipPredicate = internship -> internship.getNumberHour() >= (Integer.valueOf(n));
                    list.removeIf(internshipPredicate);
                }
                case "max": {
                    Predicate<Internship> internshipPredicate = internship -> internship.getNumberHour() <= Integer.valueOf(n);
                    list.removeIf(internshipPredicate);
                }
                default: {
                    logger.error("Unknown error.");
                }
            }
            return list;
        } else {
            // No filter, so return list directly.
            return list;
        }

//        LinkedList<Internship> list = null;
//
//        if (filter != null) {
//            // Check which type of filter is used.
//            switch (filter) {
//                case "città": {
//                    list = internshipDAO.getInternshipfilteredByCriteria(1, n);
//                }
//                case "min": {
//                    list = internshipDAO.getInternshipfilteredByCriteria(2, n);
//                }
//                case "max": {
//                    list = internshipDAO.getInternshipfilteredByCriteria(3, n);
//                }
//                default: {
//                    logger.error("Unknown error.");
//                }
//            }
//        } else {
//            // Get all internships.
//            list = internshipDAO.getAllInternships();
//        }
//
//        // Now paginate.
//        if (first != 0 && last != 0) {
//            //  Full pagination.
//            Predicate<Internship> internshipPredicate = internship -> internship.getId() <= first && internship.getId() >= last;
//            list.removeIf(internshipPredicate);
//        } else if (first != 0 && last == 0) {
//            // Half pagination.
//            Predicate<Internship> internshipPredicate = internship -> internship.getId() <= first;
//            list.removeIf(internshipPredicate);
//        }
    }

    public int insert(Candidacy candidacy, int idIn) {
        return candidacyDAO.insert(candidacy, idIn);
    }

    public Candidacy getCandidacy(int idCandidacy) {
        return candidacyDAO.getCandidacyFromId(idCandidacy);
    }
}
