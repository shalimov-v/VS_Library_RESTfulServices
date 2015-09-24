package org.oa.vshalimov.library.services;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.Responses;
import org.oa.vshalimov.library.dao.FacadeDAO;
import org.oa.vshalimov.library.data.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/books")
public class BookService {

    @Path("/view")
    @GET
    @Produces("application/xml")
    public List<Book> viewAll() {
        FacadeDAO facade = new FacadeDAO();
        List<Book> books = facade.getBookDAO().loadAll();
        facade.closeSqlConnection();
        if (books.isEmpty()) {
            throw new NotFoundException("Search error. Items not found.");
        }
        return books;
    }

    @Path("/view/{sortBy}")
    @GET
    @Produces("application/xml")
    public List<Book> sortedView(@PathParam("sortBy") String sortType) {
        FacadeDAO facade = new FacadeDAO();
        List<Book> books = facade.getBookDAO().loadAll();
        facade.closeSqlConnection();
        if (books.isEmpty()) {
            throw new NotFoundException("Search error. Items not found.");
        } else {
            switch (sortType) {
                case "name":
                    Collections.sort(books, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    break;
                case "author":
                    Collections.sort(books, (o1, o2) -> o1.getAuthor().getLastName().compareTo(o2.getAuthor().getLastName()));
                default:
                    break;
            }
        }
        return books;
    }

    @Path("/find")
    @GET
    @Produces("application/xml")
    public List<Book> findBy(@QueryParam("by") String parameter, @QueryParam("query") String queryString) {
        FacadeDAO facade = new FacadeDAO();
        List<Book> books = facade.getBookDAO().findByParameter(parameter, queryString);
        facade.closeSqlConnection();
        if (books.isEmpty()) {
            throw new NotFoundException("Error searching by query string '" + queryString + "'. Items not found.");
        } else {
            return books;
        }
    }

    @Path("/add")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response add(Book itemToAdd) {
        FacadeDAO facade = new FacadeDAO();
        boolean created = facade.getBookDAO().add(itemToAdd);
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
    public Response delete(Book itemToDelete) {
        FacadeDAO facade = new FacadeDAO();
        boolean deleted = facade.getBookDAO().delete(itemToDelete);
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
    public Response update(Book itemToUpdate) {
        FacadeDAO facade = new FacadeDAO();
        boolean updated = facade.getBookDAO().update(itemToUpdate);
        facade.closeSqlConnection();
        if (!updated) {
            return Response.status(Responses.NOT_MODIFIED).entity("Error updating item.").type("application/xml").build();
        } else {
            return Response.status(200).entity("Item successfully updated.").type("application/xml").build();
        }
    }

}