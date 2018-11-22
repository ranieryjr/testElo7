/*
* Classe criada para o desenvolvimento da solução.
* Abstração para filme.
*/
import java.util.List;

public class Movie {
	private int movieId;
	private String title;
	private List<String> genres;//lista com os nomes dos gêneros do filme
	private double rating;//rating médio do filme
	private int nratings;//número de ratings que recebeu o filme
	
	public Movie(int movieId, String title, List<String> genres) {
		this.movieId = movieId;
		this.title = title;
		this.genres = genres;
		this.rating = 0;
		this.nratings = 0;
	}
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	public double getRating(){//retorna o rating médio do filme
		if(nratings == 0)
			return 0;
		return rating / nratings;
	}
	public void updateRating(double rating){//atualiza o rating médio do filme
		this.nratings++;		
		this.rating = this.rating + rating;		
	}
	public int getNRatings(){
		return nratings;
	}
}
