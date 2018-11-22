/*
* Classe criada para o desenvolvimento da solução. 
* Abstração para o gênero dos filmes.
*/
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Genre {	
	private String name;
	private double rating;//rating médio que todos os filmes do gênero recebeu
	private int nratings;//número de ratings que todos os filmes do gênero recebeu
	private List<Integer> movies;//lista com todos os ids dos filmes do gênero
	private List<Integer> movies5;//lista com todos os ids dos filmes do gênero que obtiveram rating 5
	private SummaryStatistics statistics;//objeto para realizar estatística básica (e.g. desvio padrão)
	
	public Genre(String name) {		
		this.name = name;
		this.rating = 0;
		this.nratings = 0;
		this.movies = new ArrayList<Integer>();
		this.movies5 = new ArrayList<Integer>();
		this.statistics = new SummaryStatistics();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRating(){//retorna o rating médio de todos os filmes do gênero
		if(nratings == 0)
			return 0;
		return rating / nratings;
	}
	public void updateRating(double rating){//atualiza o rating médio
		this.nratings++;
		this.rating = this.rating + rating;
		this.statistics.addValue(rating);
	}
	public void addMovies(int movie){
		this.movies.add(movie);
	}
	public void addMovies5(int movie){
		this.movies5.add(movie);
	}
	public int getNRatings(){
		return nratings;
	}
	public List<Integer> getMovies(){
		return movies;
	}
	public List<Integer> getMovies5(){
		return movies5;
	}
	public double getSD(){//retorna o desvio padrão do rating médio
		return statistics.getStandardDeviation();
	}
}
