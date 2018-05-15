package rest;

import controller.CompanyController;
import controller.CredentialController;
import controller.InternshipController;
import model.Candidacy;
import model.Internship;
import model.dao.CandidacyDAO;
import org.apache.log4j.Logger;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.Timestamp;


@Path("offerte")
public class InternshipResource {

    final static Logger logger = Logger.getLogger(InternshipResource.class);

    private Cookie authcookie;

    public InternshipResource(Cookie authcookie) {
        this.authcookie = authcookie;
    }

    /*
     * Questa root resource può essere utilizzata anche come sub-resource
     * in base al parametro passato nel costruttore.
     * Se il parametro viene omesso, si tratterà di una risoursa root che
     * rappresenta tutti gli articoli, altrimenti sarà una sub-resource che
     * rappresenta i soli articoli dell'issue identificato dal parametro stesso.
     */

    private int iid = 0;

    public InternshipResource() {
        this.iid = 0;
    }

    public InternshipResource(int iid) {
        this.iid = iid;
    }

    //GET  /rest/res1/offerte
    //Accept: application/json
    @GET
    @Path("offerte/{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffertByID(@PathParam("id") int n) {



        return Response.ok(n).build();
    }


    //GET /rest/res1/offerte?{FILTER}{first={m}[&last={n}]

    @GET
    @Path("offerte/{d: [a-zA-Z]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffertWithQuery(@PathParam("d") String d, @QueryParam("f") Timestamp f , @QueryParam("l") Timestamp l){

        if( f == null && l == null ) {
            return Response.ok(d).build();
        } else if ( f !=null && l == null) {
            Internship internship = new InternshipController().checkDate(f);
            return Response.ok(internship).build();
        } else {
            Internship internship = new InternshipController().checkTwoDate(f, l);
            return Response.ok(internship).build();
        }
    }

    //POST /rest/auth/offerte/
    //Accept: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCandidacyInfo(@Context UriInfo c, Internship internship) {

        if (authcookie != null) {
            int companyId = new CredentialController().getUserIdByCookie(authcookie.getValue());

            int status = new CompanyController().addInternshipIfAllowed(companyId, internship);
            if (status > 0) {// Inserted.
                //  Build URI.
                URI u = c.getBaseUriBuilder()
                        .path(StudentResource.class)
                        .path(StudentResource.class, "getOffertByID")
                        .build(status);
                return Response.created(u).build();
            } else if (status == 0) {
                return Response.status(403).build();
            } else {
                logger.error("Error while inserting new internship offer.");
                return Response.serverError().build();
            }
        } else {
            return Response.ok("No active session").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCandidacy(@Context UriInfo c,Candidacy candidacy) {

        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                switch (userType) {
                    case 0: { // Student.
                        {
                            int in = new CandidacyDAO().insert(candidacy);
                            URI u = c.getBaseUriBuilder()
                                    .path(InternshipResource.class)
                                    .path(InternshipResource.class, "getOffertByID")
                                    .build(id   );
                            return Response.created(u).build();
                        } else {
                            return Response.serverError().build();
                        }
                    }
                    case 1: { // Company.
                        return Response.status(403).build();
                    }
                    case 2: { // Admin.
                        return Response.status(403).build();
                    }
                    default: {
                        logger.error("userType value is not correct.");
                        return Response.serverError().build();
                    }
                }
            }
            else {
                return Response.ok("No active session").build();
            }
        } else {
            return Response.ok("No active session").build();
        }
    }


    @Path("candidati"){
        public Candidacy toCandidacy() { return new  Candidacy(3); }
    }


}

