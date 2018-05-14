package rest;

import controller.CredentialController;
import controller.StudentController;
import model.Candidacy;
import model.Student;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CandidacyResource {
    final static Logger logger = Logger.getLogger(CandidacyResource.class);

    private Cookie authcookie;

    public CandidacyResource(Cookie authcookie) {
        this.authcookie = authcookie;
    }


}
