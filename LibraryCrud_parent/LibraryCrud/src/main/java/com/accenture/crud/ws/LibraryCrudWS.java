package com.accenture.crud.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.accenture.crudjsf.model.Book;
import com.accenture.crudjsf.persistence.LibraryPersistence;

@WebService
public class LibraryCrudWS {

	public LibraryPersistence persistence = new LibraryPersistence();

	@WebMethod
	public List<Book> allBooks() {
		return persistence.fillBooks();
	}

	@WebMethod
	public Book findById(@WebParam(name = "id") int id) {
		return persistence.findBookById(id);
	}

	@WebMethod
	public String create(@WebParam(name = "book") Book book) {
		int saveResult = persistence.saveBook(book);
		String navigationResult = "";
		if (saveResult != 0) {
			navigationResult = "Book was registred succefully..!";

		} else {
			navigationResult = "Book couldnt be created ";
		}
		return navigationResult;
	}

	@WebMethod
	public String update(@WebParam(name = "book") Book book) {

		Book bookOld = persistence.findBookById(book.getId());
		String message = "";
		if (bookOld != null) {
			persistence.updateBooks(book);
			message = "Book was updated";
		}else {
			message = "Book not found";
		}
		return message;
	}
	
	@WebMethod
	public String delete(@WebParam(name = "book") Book book) {

		Book bookOld = persistence.findBookById(book.getId());
		String message = "";
		if (bookOld != null) {
			persistence.deleteBook(book.getId());
			message = "Book was deleted successfully!";
		}else {
			message = "Book was not deleted.. ";
		}
		return message;
	}

}
