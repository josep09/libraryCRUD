package com.accenture.crudjsf.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.accenture.crudjsf.model.Book;
import com.accenture.crudjsf.persistence.LibraryPersistence;

@ManagedBean(name = "bookBean")
@RequestScoped

public class BookBean {

	private List<Book> books;
	private LibraryPersistence persistence;
	private Book book = new Book();

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookBean() {
		persistence = new LibraryPersistence();
	}

	// Se ejecuta al final de que se inicializan las variables
	@PostConstruct
	public void init() {
		this.findAllBooks();
	}

	public void findAllBooks() {
		books = persistence.fillBooks();
	}

	// SETTERS Y GETTERS	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	// save
	public String saveBook() {
		String navigationResult = "";
		int saveResult = persistence.saveBook(book);
		if (saveResult != 0) {
			navigationResult = "books.xhtml?faces-redirect=true";

		} else {
			navigationResult = "createBook.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}

	// edit
	public String editBook(int bookId) {
		return persistence.editBooks(bookId);
	}

	// update
	public String updateBook(Book book) {
		return persistence.updateBooks(book);
	}

	// delete
	public String deleteBook(int bookId) {
		return persistence.deleteBook(bookId);
	}

}
