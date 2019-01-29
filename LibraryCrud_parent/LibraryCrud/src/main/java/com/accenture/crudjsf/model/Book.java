package com.accenture.crudjsf.model;

public class Book {
	
	private int id;
	private String title;
	private String author;
	private String editorial;
	
	public Book() {
		super();
	}
	
	public Book(int id, String title, String author, String editorial) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.editorial = editorial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String email) {
		this.author = email;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String password) {
		this.editorial = password;
	}

	
}
