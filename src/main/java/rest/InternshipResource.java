package rest;

import controller.CompanyController;
import controller.CredentialController;
import model.Internship;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;


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
    public Response getOfferByID(@PathParam("id") int n) {

        return Response.ok(n).build();
    }


    //GET /rest/res1/offerte?{FILTER}{first={m}[&last={n}]

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getOffertaByIDWithQuery(@QueryParam("m") int m , @QueryParam("n") int n){
//
//        //TODO qualcosa
//    }

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
                        .path(StudentResource.class, "getOfferByID")
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
}

