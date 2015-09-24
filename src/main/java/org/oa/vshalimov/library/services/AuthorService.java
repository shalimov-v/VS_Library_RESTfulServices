package org.oa.vshalimov.library.services;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.Responses;
import org.oa.vshalimov.library.dao.FacadeDAO;
import org.oa.vshalimov.library.data.Author;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/authors")
public class AuthorService {

    @Path("/view")
    @GET
    @Produces("application/xml")
    public List<Author> viewAll() {
        FacadeDAO facade = new FacadeDAO();
        List<Author> authors = facade.getAuthorDAO().loadAll();
        facade.closeSqlConnection();
        if (authors.isEmpty()) {
            throw new NotFoundException("Search error. Items not found.");
        }
        return authors;
    }

    @Path("/view/{sortBy}")
     @GET
     @Produces("application/xml")
     public List<Author> sortedView(@PathParam("sortBy") String sortType) {
        FacadeDAO facade = new FacadeDAO();
        List<Author> authors = facade.getAuthorDAO().loadAll();
        facade.closeSqlConnection();
        if (authors.isEmpty()) {
            throw new NotFoundException("Search error. Items not found.");
        } else {
            switch (sortType) {
                case "firstName":
                    Collections.sort(authors, (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
                    break;
                case "lastName":
                    Collections.sort(authors, ((o1, o2) -> o1.getLastName().compareTo(o2.getLastName())));
                    break;
                default:
                    break;
            }
        }
        return authors;
    }

    @Path("/find")
    @GET
    @Produces("application/xml")
    public List<Author> findBy(@QueryParam("by") String parameter, @QueryParam("query") String queryString) {
        FacadeDAO facade = new FacadeDAO();
        List<Author> authors = facade.getAuthorDAO().findByParameter(parameter, queryString);
        facade.closeSqlConnection();
        if (authors.isEmpty()) {
            throw new NotFoundException("Error searching by query string '" + queryString + "'. Items not found.");
        } else {
            return authors;
        }
    }

    @Path("/add")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response add(Author itemToAdd) {
        FacadeDAO facade = new FacadeDAO();
        boolean created = facade.getAuthorDAO().add(itemToAdd);
        facade.closeSqlConnection();
        if (!created) {
            return Response.status(Responses.NOT_MODIFIED).entity("Error adding item.").type("application/xml").build();
        } else {
            return Response.status(200).entity("Item successfully created.").type("application/xml").build();
        }
    }

    @Path("/delete")
    @DELETE
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response delete(Author itemToDelete) {
        FacadeDAO facade = new FacadeDAO();
        boolean deleted = facade.getAuthorDAO().delete(itemToDelete);
        facade.closeSqlConnection();
        if (!deleted) {
            return Response.status(Responses.NOT_MODIFIED).entity("Error deleting item.").type("application/xml").build();
        } else {
            return Response.status(200).entity("Item successfully deleted.").type("application/xml").build();
        }
    }

    @Path("/update")
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response update(Author itemToUpdate) {
        FacadeDAO facade = new FacadeDAO();
        boolean updated = facade.getAuthorDAO().update(itemToUpdate);
        facade.closeSqlConnection();
        if (!updated) {
            return Response.status(Responses.NOT_MODIFIED).entity("Error updating item.").type("application/xml").build();
        } else {
            return Response.status(200).entity("Item successfully updated.").type("application/xml").build();
        }
    }

}