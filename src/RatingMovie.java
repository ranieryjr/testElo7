
public class RatingMovie {	
	private int movie;
	private double rating;
	
	public RatingMovie(int movie, double rating) {			
		this.movie = movie;
		this.rating = rating;
	}
	
	public int getMovie() {
		return movie;
	}
	public void setMovie(int movie) {
		this.movie = movie;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
}
