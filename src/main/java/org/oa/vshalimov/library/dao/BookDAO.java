package org.oa.vshalimov.library.dao;

import org.oa.vshalimov.library.core.Log;
import org.oa.vshalimov.library.data.Author;
import org.oa.vshalimov.library.data.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements AbstractDAO<Book> {

    private final static String TABLE_NAME = "book";

    private Statement statement;

    public BookDAO(Statement statement) {
        this.statement = statement;
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, isbn VARCHAR(25) NOT NULL, authorid INT NOT NULL, PRIMARY KEY (id), CONSTRAINT FK_author FOREIGN KEY (authorid) REFERENCES author (id) ON UPDATE CASCADE ON DELETE CASCADE)");
        } catch (SQLException e) {
            Log.error("Error while creating table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
    }

    @Override
    public List<Book> loadAll() {
        List<Book> books = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            while (resultSet.next()) {
                FacadeDAO facade = new FacadeDAO();
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String isbn = resultSet.getString("isbn");
                long authorId = resultSet.getLong("authorId");
                Author author = facade.getAuthorDAO().findById(authorId);
                books.add(new Book(id, name, isbn, author));
                facade.closeSqlConnection();
            }
        } catch (SQLException e) {
            Log.error("Error while loading data from table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
        return books;
    }

    @Override
    public List<Book> findByParameter(String parameter, String queryString) {
        String sql;
        List<Book> books = new ArrayList<>();
        try {
            if (parameter.equals("author")) {
                sql = "SELECT * FROM " + TABLE_NAME + " WHERE authorid IN (SELECT id FROM author WHERE lastName LIKE ('%" + queryString + "%'))";
            } else {
                sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + parameter + " LIKE('%" + queryString + "%')";
            }
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FacadeDAO facade = new FacadeDAO();
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String isbn = resultSet.getString("isbn");
                long authorId = resultSet.getLong("authorId");
                Author author = facade.getAuthorDAO().findById(authorId);
                books.add(new Book(id, name, isbn, author));
                facade.closeSqlConnection();
            }
        } catch (SQLException e) {
            Log.error("Error while searching data in table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
        return books;
    }

    @Override
    public Book findById(long itemId) {
        Book book = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=" + itemId);
            while (resultSet.next()) {
                FacadeDAO facade = new FacadeDAO();
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String isbn = resultSet.getString("isbn");
                long authorId = resultSet.getLong("authorId");
                Author author = facade.getAuthorDAO().findById(authorId);
                book = new Book(id, name, isbn, author);
                facade.closeSqlConnection();
            }
        } catch (SQLException e) {
            Log.error("Error while searching data in table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
        return book;
    }

    @Override
    public boolean add(Book itemToAdd) {
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + "(name, isbn, authorid) VALUES ('" + itemToAdd.getName() + "', '" + itemToAdd.getIsbn() + "', " + itemToAdd.getAuthor().getId() + ")");
        } catch (SQLException e) {
            Log.error("Error while adding data to table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Book itemToDelete) {
        try {
            statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id=" + itemToDelete.getId());
        } catch (SQLException e) {
            Log.error("Error while deleting data from table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Book itemToUpdate) {
        try {
            statement.executeUpdate("UPDATE " + TABLE_NAME + " SET name='" + itemToUpdate.getName() + "', isbn='" + itemToUpdate.getIsbn() + "', authorid=" + itemToUpdate.getAuthor().getId() + " WHERE id=" + itemToUpdate.getId());
        } catch (SQLException e) {
            Log.error("Error while updating data from table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }
}
