package org.oa.vshalimov.library.dao;

import org.oa.vshalimov.library.core.Log;
import org.oa.vshalimov.library.data.Author;
import org.oa.vshalimov.library.data.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FacadeDAO {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "toor";
    private static final String URL = "jdbc:mysql://localhost/test";

    private AuthorDAO authorDAO;
    private BookDAO bookDAO;

    private Connection connection;
    private Statement statement;

    public FacadeDAO() {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            Log.error("Cannot connect to database server." + e.getMessage() + "\n");
        }
        if (connection == null) {
            System.exit(1);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Log.error("Error while creating statement. " + e.getMessage() + "\n");
        }
        authorDAO = new AuthorDAO(statement);
        bookDAO = new BookDAO(statement);
    }

    public AbstractDAO<Author> getAuthorDAO() {
        return authorDAO;
    }

    public AbstractDAO<Book> getBookDAO() {
        return bookDAO;
    }

    public void closeSqlConnection() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                Log.error("Error while closing statement. " + e.getMessage() + "\n");
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Log.error("Error while closing database connection. " + e.getMessage() + "\n");
            }
        }
    }

}
