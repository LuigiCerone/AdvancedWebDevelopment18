package rest;

import controller.CompanyController;
import controller.CredentialController;
import controller.InternshipController;
import model.Internship;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.LinkedList;


@Path("offerte")
public class InternshipResource {

    final static Logger logger = Logger.getLogger(InternshipResource.class);

    private Cookie authcookie;

    public InternshipResource(Cookie authcookie) {
        this.authcookie = authcookie;
    }

    public InternshipResource() {
    }

    /*
     * Questa root resource può essere utilizzata anche come sub-resource
     * in base al parametro passato nel costruttore.
     * Se il parametro viene omesso, si tratterà di una risoursa root che
     * rappresenta tutti gli articoli, altrimenti sarà una sub-resource che
     * rappresenta i soli articoli dell'issue identificato dal parametro stesso.
     */

    //GET  awd18/rest/offerte/{ID}
    //Accept: application/json
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferByID(@PathParam("id") int idInternship) {
        Internship internship = new InternshipController().getInternshipByID(idInternship);
        return Response.ok(internship).build();
    }


    //GET awd18/rest/offerte?{FILTER}{first={m}[&last={n}]
    //    ES :
    //    ?filter=ore&n=4
    //    ?filter=city&n=avezzano
    //    ?filter=min&n=4
    //    ?filter=max&n=6
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferWithQuery(@QueryParam("filter") String filter, @QueryParam("n") String n,
                                      @QueryParam("first") int first, @QueryParam("last") int last) {
        // Return internships.
        LinkedList<Internship> list = new InternshipController().getFilteredInterships(filter, n, first, last);
        return Response.ok(list).build();
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
                        .path(InternshipResource.class)
                        .path(InternshipResource.class, "getOfferByID")
                        .build(status);
                return Response.created(u).build();
            } else {
                return Response.status(403).build();
            }
        } else {
            return Response.status(403).entity("No active session").build();
        }
    }
}

