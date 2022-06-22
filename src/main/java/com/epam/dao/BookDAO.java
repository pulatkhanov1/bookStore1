package com.epam.dao;

import com.epam.model.Book;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {
    private static String URL = "jdbc:postgresql://localhost:5432/BookStore";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "1850825";
    private static Connection connection;


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> index() {
        List<Book> books = new ArrayList<>();


        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublished_year(resultSet.getInt("published_year"));
                book.setGenre(resultSet.getString("genre"));
                book.setDescription(resultSet.getString("description"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public Book show(int id){
    Book book=null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * FROM books WHERE id=?");
            preparedStatement.setInt(1,id);

            ResultSet resultSet= preparedStatement.executeQuery();
            resultSet.next();
            book= new Book();
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setPublished_year(resultSet.getInt("published_year"));
            book.setGenre(resultSet.getString("genre"));
            book.setDescription(resultSet.getString("description"));
            book.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;

    }
     public void create(Book book){
         try {
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books(name, author, published_year, genre, description) VALUES (?,?,?,?,?)");
             preparedStatement.setString(1, book.getName());
             preparedStatement.setString(2, book.getAuthor());
             preparedStatement.setInt(3, book.getPublished_year());
             preparedStatement.setString(4, book.getGenre());
             preparedStatement.setString(5, book.getDescription());

             preparedStatement.executeUpdate();


         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     public void update(int id, Book updateBook){
         try {
             PreparedStatement preparedStatement=
             connection.prepareStatement("UPDATE books SET name=?, author=?, published_year=?, genre=?, description=? WHERE id=?");
             preparedStatement.setString(1, updateBook.getName());
             preparedStatement.setString(2, updateBook.getAuthor());
             preparedStatement.setInt(3, updateBook.getPublished_year());
             preparedStatement.setString(4, updateBook.getGenre());
             preparedStatement.setString(5, updateBook.getDescription());
             preparedStatement.setInt(6, id);
             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     public void delete(int id){
     PreparedStatement preparedStatement=null;

         try {
             preparedStatement= connection.prepareStatement("DELETE FROM books WHERE id=?");
             preparedStatement.setInt(1, id);

             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }

     }

}
