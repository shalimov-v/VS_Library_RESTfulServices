package org.oa.vshalimov.library.dao;

import org.oa.vshalimov.library.core.Log;
import org.oa.vshalimov.library.data.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class AuthorDAO implements AbstractDAO<Author> {

    private final static String TABLE_NAME = "author";

    private Statement statement;

    public AuthorDAO(Statement statement) {
        this.statement = statement;
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(id INT PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(25) NOT NULL, lastName VARCHAR(50) NOT NULL)");
        } catch (SQLException e) {
            Log.error("Error while creating table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
    }

    @Override
    public List<Author> loadAll() {
        List<Author> authors = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                authors.add(new Author(id, firstName, lastName));
            }
        } catch (SQLException e) {
            Log.error("Error while loading data from table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
        return authors;
    }

    @Override
    public List<Author> findByParameter(String parameter, String queryString) {
        List<Author> authors = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + parameter + " LIKE('%" + queryString + "%')");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                authors.add(new Author(id, firstName, lastName));
            }
        } catch (SQLException e) {
            Log.error("Error while searching data in table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
        return authors;
    }

    @Override
    public Author findById(long itemId) {
        Author author = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=" + itemId);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                author = new Author(id, firstName, lastName);
            }
        } catch (SQLException e) {
            Log.error("Error while searching data in table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
        }
        return author;
    }

    @Override
    public boolean add(Author itemToAdd) {
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + "(firstName, lastName) VALUES ('" + itemToAdd.getFirstName() + "', '" + itemToAdd.getLastName() + "')");
        } catch (SQLException e) {
            Log.error("Error while adding data to table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Author itemToDelete) {
        try {
            statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id=" + itemToDelete.getId());
        } catch (SQLException e) {
            Log.error("Error while deleting data from table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Author itemToUpdate) {
        try {
            statement.executeUpdate("UPDATE " + TABLE_NAME + " SET firstName='" + itemToUpdate.getFirstName() + "', lastName='" + itemToUpdate.getLastName() + "' WHERE id=" + itemToUpdate.getId());
        } catch (SQLException e) {
            Log.error("Error while updating data from table: " + TABLE_NAME + ". " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }
}