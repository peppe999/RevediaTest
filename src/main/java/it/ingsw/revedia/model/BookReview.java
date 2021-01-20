package it.ingsw.revedia.model;

public class BookReview extends Review
{	
	private String user;
	private String book;
	
	public BookReview() { super(); }

	
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }

	public String getBook() { return book; }
	public void setBook(String book) { this.book = book; }
}
