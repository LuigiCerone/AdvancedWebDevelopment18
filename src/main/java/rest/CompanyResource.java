package rest;

import controller.CompanyController;
import controller.CredentialController;
import controller.InternshipController;
import javafx.util.Pair;
import model.Company;
import model.Internship;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

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


    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyById(@PathParam("id") int companyId) {
        Company company = new CompanyController().getCompanyByID(companyId);
        return Response.ok(company).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCompany(@Context UriInfo c, Company companyToInsert) {
        logger.debug(companyToInsert.toString());
        int id = new CompanyController().insertCompany(companyToInsert);

        if (id != -1) {
            URI u = c.getBaseUriBuilder()
                    .path(CompanyResource.class)
                    .path(CompanyResource.class, "getCompanyByID")
                    .build(id);
            return Response.created(u).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCompanies(@Context UriInfo context) {
        List<Pair<String, URI>> list = new CompanyController().getAllCompanies(context);
        if (list != null) {
            Response.ok(list).build();
        } else {
            Response.serverError().build();
        }
        return Response.serverError().build();
    }
}
