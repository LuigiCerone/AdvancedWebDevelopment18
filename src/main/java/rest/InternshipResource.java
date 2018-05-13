package rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("offerte")
public class InternshipResource {

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
    public Response getOffertaByID(@PathParam("id") int n){

        return Response.ok(n).build();
    }


    //GET /rest/res1/offerte?{FILTER}{first={m}[&last={n}]

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getOffertaByIDWithQuery(@QueryParam("m") int m , @QueryParam("n") int n){
//
//        //TODO qualcosa
//    }
}

