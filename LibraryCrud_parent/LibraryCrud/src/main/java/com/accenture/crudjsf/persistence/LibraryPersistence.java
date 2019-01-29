package com.accenture.crudjsf.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.accenture.crudjsf.model.Book;

public class LibraryPersistence {

	private List<Book> books;

	public static Statement stmObj;
	public static Connection connObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstm;

	public static Connection getConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String db_url = "jdbc:mariadb://localhost:3306/library", db_userName = "root", db_password = "1234";
			connObj = DriverManager.getConnection(db_url, db_userName, db_password);
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return connObj;
	}

	public LibraryPersistence() {
		super();
		books = new ArrayList<Book>();
		this.fillBooks();
	}

	public void addBook(Book book) {
		this.books.add(book);

	}
	
	public List<Book> fillBooks() {
		List<Book> BookList = new ArrayList<Book>();
		String sql = "select * from book";
		try {
			stmObj = getConnection().createStatement();
			resultSetObj = stmObj.executeQuery(sql);
			while (resultSetObj.next()) {
				Book book = new Book();
				book.setId(resultSetObj.getInt("id"));
				book.setTitle(resultSetObj.getString("title"));
				book.setAuthor(resultSetObj.getString("author"));
				book.setEditorial(resultSetObj.getString("editorial"));
				
				BookList.add(book);
			}
			System.out.println("Total de libros " + BookList.size());
			connObj.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return BookList;
	}

	public Book findBookById(int id) {

		Book book = new Book();
		String sql = "select * from book where id = ?";

		try {
			pstm = getConnection().prepareStatement(sql);
			pstm.setInt(1, id);
			resultSetObj = pstm.executeQuery();

			while (resultSetObj.next()) {
				book.setId(resultSetObj.getInt("id"));
				book.setTitle(resultSetObj.getString("title"));
				book.setAuthor(resultSetObj.getString("author"));
				book.setEditorial(resultSetObj.getString("editorial"));
				
			}

			connObj.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return book;
	}

	public int saveBook(Book book) {

		int saveResult = 0;
		try {

			pstm = getConnection()
					.prepareStatement("insert into book (title,author,editorial) values (?,?,?)");
			pstm.setString(1, book.getTitle());
			pstm.setString(2, book.getAuthor());
			pstm.setString(3, book.getEditorial());
			
			saveResult = pstm.executeUpdate();

			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
			return saveResult;

	}

	public String editBooks(int bookId) {
		Book editBooks = null;
		

		
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			stmObj = getConnection().createStatement();
			resultSetObj = stmObj.executeQuery("select * from book where id = " + bookId);
			if (resultSetObj != null) {
				resultSetObj.next();
				editBooks = new Book();
				editBooks.setId(resultSetObj.getInt("id"));
				editBooks.setTitle(resultSetObj.getString("name"));
				editBooks.setAuthor(resultSetObj.getString("email"));
				editBooks.setEditorial(resultSetObj.getString("passwords"));
			}
			sessionMapObj.put("editBook", editBooks);
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/editBook.xhtml?faces-redirect=true";
	}

	public String updateBooks(Book book) {
		try {
			pstm = getConnection().prepareStatement(
					"update book set title=?, author=?, editorial=?");
			pstm.setString(1, book.getTitle());
			pstm.setString(2, book.getAuthor());
			pstm.setString(3, book.getEditorial());
			pstm.setInt(6, book.getId());
			pstm.executeQuery();
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return "/books.xhtml?faces-redirect=true";
	}

	public String deleteBook(int bookId) {
		
		try {
			pstm = getConnection().prepareStatement("delete from book where id = " + bookId);
			pstm.executeUpdate();
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/books.xhtml?faces-redirect=true";
	}

	public List<Book> getBooks() {
		return this.books;
	}
}
