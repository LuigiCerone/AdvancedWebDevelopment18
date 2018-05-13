package rest;

import controller.CredentialController;
import model.Company;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("aziende")
public class CompanyRootResource {
    final static Logger logger = Logger.getLogger(CompanyRootResource.class);

    private Cookie authcookie;

    public CompanyRootResource(Cookie authcookie) {
        this.authcookie = authcookie;
    }

    public CompanyRootResource() {

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
                        break;
                    }
                    case 2: { // Admin.
                        break;
                    }
                    default: {
                        logger.error("userType value is not correct.");
                        return Response.serverError().build();
                    }
                }
            } else {
                return Response.ok("No active session").build();
            }
        } else {
            return Response.ok("No active session").build();
        }
        return null;
    }

}
