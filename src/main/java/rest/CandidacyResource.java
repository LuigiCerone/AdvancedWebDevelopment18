package rest;

import controller.CredentialController;
import controller.InternshipController;
import model.Candidacy;
import model.Internship;
import model.dao.CandidacyDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URI;

public class CandidacyResource {
    final static Logger logger = Logger.getLogger(CandidacyResource.class);

    private Cookie authcookie;
    private int internshipId;

    public CandidacyResource(Cookie authcookie, int id) {
        this.authcookie = authcookie;
        this.internshipId = id;
    }


    //POST rest/auth/offerte/{id: [0-9]+}/candidati
    //Accept: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCandidacy(@PathParam("id") int n, @Context UriInfo c, Candidacy candidacy) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                switch (userType) {
                    case 0: { // Student.
                        int id = CandidacyDAO.insert(candidacy);
                        URI u = c.getBaseUriBuilder()
                                .path(InternshipResource.class)
                                .path(InternshipResource, "getOffertByID")
                                .build(id);
                        return Response.created(u).build();
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
            } else {
                return Response.ok("No active session").build();
            }
        } else {
            return Response.ok("No active session").build();
        }
    }

    //PUT rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}
    //Accept: application/json
    @PUT
    @Path("{idc: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCandidacy(@PathParam("idc") int candidacyId, Candidacy candidacy) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                if (userType == 1) {
                    int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                    if (idLoggedUser != -1) {
                        int status = new InternshipController().addCandidacyToInternship(idLoggedUser, candidacyId, candidacy);
                        if (status == 1) {
                            // OK, then noContent.
                            return Response.noContent().build();
                        } else if (status == 0) {
                            // Then forbidden.
                            return Response.status(403).build();
                        } else
                            return Response.serverError().build();
                    } else return Response.serverError().build();
                } else
                    return Response.status(403).build();
            } else {
                return Response.ok("No active session").build();
            }
        } else return Response.ok("No active session").build();
    }

    //DELETE rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}
    //Accept: application/json
    @DELETE
    @Path("{idc: [0-9]+}")
    public Response deleteCandidacy(@PathParam("idc") int candidacyId) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                if (userType == 1) {
                    int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                    if (idLoggedUser != -1) {
                        int status = new InternshipController().deleteCandidacyToInternship(idLoggedUser, candidacyId);
                        if (status == 1) {
                            // DELETED, then noContent.
                            return Response.noContent().build();
                        } else if (status == 0) {
                            // Then forbidden.
                            return Response.status(400).build();
                        } else
                            return Response.serverError().build();
                    } else return Response.serverError().build();
                } else
                    return Response.status(403).build();
            } else {
                return Response.ok("No active session").build();
            }
        } else return Response.ok("No active session").build();
    }


    //GET rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}/progetto-formativo
    //Accept: application/json
    @GET
    @Path("{idc: [0-9]+}/progetto-formativo")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getCandidacy(@PathParam("idc") int candidacyId) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                if (idLoggedUser != -1) {
                    int status = new InternshipController().checkIfAllowed(idLoggedUser, candidacyId, userType);
                    if (status == 1) {
                        // Retrun pdf.
                        File file = new InternshipController().getPdfForCandidacy(candidacyId);

                        // Use file.
//                        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
//                                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
//                                .build();

                        // Use stream.
                        StreamingOutput result = null;
                        try {
                            byte[] buf = new byte[8192];

                            InputStream is = new FileInputStream(file);

                            result = new StreamingOutput() {
                                @Override
                                public void write(OutputStream out) throws IOException {
                                    int c;
                                    while ((c = is.read(buf, 0, buf.length)) > 0) {
                                        out.write(buf, 0, c);
                                        out.flush();
                                    }
                                }
                            };
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        return Response.ok(result, MediaType.APPLICATION_OCTET_STREAM)
                                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                                .build();
                    } else if (status == 0) {
                        // Then forbidden.
                        return Response.status(400).build();
                    } else
                        return Response.serverError().build();
                } else return Response.serverError().build();
            } else {
                return Response.ok("No active session").build();
            }
        } else return Response.ok("No active session").

                build();
    }
}
