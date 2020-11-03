package ui;

public class Rectangle {
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private int transactions;

	public Rectangle(int id, int x, int y, int width, int height, int transactions) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.transactions = transactions;
	}
	
	public int getId() {
		return this.id;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int getTransactions() {
		return this.transactions;
	}
	
	public String toString() {
		return String.format("Rectangle { id: %d, x: %d, y: %d, width: %d, height: %d, transactions: %d }", this.id, this.x, this.y, this.width, this.height, this.transactions);
	}
	
	
}