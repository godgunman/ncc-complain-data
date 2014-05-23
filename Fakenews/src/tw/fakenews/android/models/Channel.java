package tw.fakenews.android.models;

public class Channel {

	public String categoryName;
	public Category[] category;
	int size;

	public class Category {
		public String categoryName;
		public Complain[] data;
	}
}