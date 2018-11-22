/*
* Classe criada para o desenvolvimento da solução.
* Abstração para o Usuário.
*/
import java.util.ArrayList;
import java.util.List;

public class User {
	private int userId;
	private List<RatingMovie> movies;//lista com todos os filmes que o usuário realizou o rating
	
	public User(int userId) {
		this.userId = userId;
		this.movies = new ArrayList<RatingMovie>();
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<RatingMovie> getMovies() {
		return movies;
	}
	public void addMovie(int movie, double rating){
		this.movies.add(new RatingMovie(movie, rating));
	}
}
