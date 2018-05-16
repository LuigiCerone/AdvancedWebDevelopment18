package rest;

import controller.CompanyController;
import controller.CredentialController;
import controller.InternshipController;
import model.Company;
import model.Internship;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

@Path("aziende")
public class CompanyResource {
    final static Logger logger = Logger.getLogger(CompanyResource.class);

    private Cookie authcookie;

    public CompanyResource(Cookie authcookie) {
        this.authcookie = authcookie;
    }

    public CompanyResource() {

    }

    @GET
    @Path("aziende/{id: [0-9]+}/offerte")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InternshipByCompany(@PathParam("id") int idCompany) {

        LinkedList<Internship> list = new InternshipController().getListInternshiByCompanyID(idCompany);
        return Response.ok(list).build();

    }


    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCompany(@PathParam("id") int id, Company company) {

        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                switch (userType) {
                    case 0: { // Student.
                        return Response.status(403).build();
                    }
                    case 1: { // Company.
                        if (new CompanyController().selectiveUpdate(company, 1, id))
                            return Response.noContent().build();
                        else
                            return Response.serverError().build();
                    }
                    case 2: { // Admin.
                        if (new CompanyController().selectiveUpdate(company, 2, id))
                            return Response.noContent().build();
                        else
                            return Response.serverError().build();
                    }
                    default: {
                        logger.error("userType value is not correct.");
                        return Response.serverError().build();
                    }
                }
            } else {
                logger.debug("Error with user type.");
                return Response.ok("No active session").build();
            }
        } else {
            return Response.ok("No active session").build();
        }
    }

}
