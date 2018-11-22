/*
 * Esta classe realiza as Partes 1 (Análise Exploratória) e 2 (Sistema de Recomendação) do teste. 
 * 
 * A partir daqui, o código realiza a primeira fase da análise exploratória. Ou seja, nesta etapa é feita a leitura e carregamento dos dados na memória. O código foi escrito com uma abordagem orientada a objetos. No código abaixo são importadas as classes necessárias para o desenvolvimento do restante da solução.
 */
import java.io.BufferedWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.LinearNNSearch;

public class Teste {

	public static void main(String[] args) throws Exception{
		/*
		 * Início da leitura dos arquivos csv. Note que este código recebe como parâmetro duas Strings com os paths dos arquivos csv do MovieLens. Caso demore muito tempo para realizar esta etapa, pode-se utilizar a versão pequena dos arquivos do MovieLens, descrita no arquivo README deste código. Note que a leitura para a memória é realizada para objetos de classes do tipo Movie.
		 */
		System.out.println("Carregando arquivos, aguarde...");
		System.out.print("Arquivo movies...");
		Reader reader = Files.newBufferedReader(Paths.get(args[0]));//path do arquivo movies.csv
        CSVParser csv = new CSVParser(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        List<Movie> movies = new ArrayList<Movie>();
        List<String> genres;        
        for(CSVRecord line : csv) {
	        genres = new ArrayList<String>();
	        for(String genre : line.get("genres").split("\\|"))
	        	genres.add(genre);	        
	        movies.add(new Movie(Integer.parseInt(line.get("movieId")), line.get("title"), genres));
        }
        System.out.println(" ok");
        /*
         * Continuação da leitura dos arquivos csv para a memória. No código anterior foi lido o arquivo movies.csv. Agora o arquivo ratings.csv. A leitura da versão completa do arquivo ratings.csv pode demorar alguns minutos ou horas, dependendo da sua máquina. Note que a leitura para a memória é realizada para objetos de classes do tipo User, Genre e Movie.
         */
        System.out.println("Arquivo ratings...");
        csv.close();
        reader = Files.newBufferedReader(Paths.get(args[1]));//path do arquivo ratings.csv
        csv = new CSVParser(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        List<User> users = new ArrayList<User>();
        List<Genre> genrelist = new ArrayList<Genre>();
        genrelist.add(new Genre("Action"));
        genrelist.add(new Genre("Adventure"));
        genrelist.add(new Genre("Animation"));
        genrelist.add(new Genre("Children"));
        genrelist.add(new Genre("Comedy"));
        genrelist.add(new Genre("Crime"));
        genrelist.add(new Genre("Documentary"));
        genrelist.add(new Genre("Drama"));
        genrelist.add(new Genre("Fantasy"));
        genrelist.add(new Genre("Film-Noir"));
        genrelist.add(new Genre("Horror"));
        genrelist.add(new Genre("Musical"));
        genrelist.add(new Genre("Mystery"));
        genrelist.add(new Genre("Romance"));
        genrelist.add(new Genre("Sci-Fi"));
        genrelist.add(new Genre("Thriller"));
        genrelist.add(new Genre("War"));
        genrelist.add(new Genre("Western"));
        genrelist.add(new Genre("(no genres listed)"));
        Movie m;
        
        for(CSVRecord line : csv) {
        	if(line.getRecordNumber()%100000==0)
        		System.out.println(line.getRecordNumber()+" ratings lidos...");//imprime na tela o andamento da leitura a cada 100000 linhas
        	if(!containsId(users,Integer.parseInt(line.get("userId")))){
        		users.add(new User(Integer.parseInt(line.get("userId"))));
        		users.get(users.size()-1).addMovie(Integer.parseInt(line.get("movieId")),Double.parseDouble(line.get("rating")));
        		movies.get(getIndex(movies, Integer.parseInt(line.get("movieId")))).updateRating(Double.parseDouble(line.get("rating")));
        	}
        	else{
        		users.get(users.size()-1).addMovie(Integer.parseInt(line.get("movieId")),Double.parseDouble(line.get("rating")));
        		movies.get(getIndex(movies, Integer.parseInt(line.get("movieId")))).updateRating(Double.parseDouble(line.get("rating")));
        	}
        	
        	m = movies.get(getIndex(movies, Integer.parseInt(line.get("movieId"))));
        	for(String genre : m.getGenres()){
        		switch (genre) {
				case "Action":
					genrelist.get(0).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(0).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(0).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(0).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(0).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Adventure":
					genrelist.get(1).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(1).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(1).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(1).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(1).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Animation":
					genrelist.get(2).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(2).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(2).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(2).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(2).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Children":
					genrelist.get(3).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(3).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(3).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(3).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(3).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Comedy":
					genrelist.get(4).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(4).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(4).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(4).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(4).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Crime":
					genrelist.get(5).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(5).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(5).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(5).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(5).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Documentary":
					genrelist.get(6).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(6).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(6).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(6).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(6).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Drama":
					genrelist.get(7).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(7).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(7).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(7).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(7).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Fantasy":
					genrelist.get(8).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(8).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(8).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(8).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(8).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Film-Noir":
					genrelist.get(9).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(9).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(9).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(9).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(9).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Horror":
					genrelist.get(10).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(10).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(10).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(10).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(10).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Musical":
					genrelist.get(11).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(11).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(11).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(11).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(11).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Mystery":
					genrelist.get(12).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(12).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(12).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(12).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(12).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Romance":
					genrelist.get(13).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(13).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(13).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(13).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(13).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Sci-Fi":
					genrelist.get(14).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(14).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(14).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(14).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(14).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Thriller":
					genrelist.get(15).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(15).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(15).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(15).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(15).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "War":
					genrelist.get(16).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(16).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(16).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(16).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(16).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "Western":
					genrelist.get(17).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(17).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(17).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(17).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(17).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				case "(no genres listed)":
					genrelist.get(18).updateRating(Double.parseDouble(line.get("rating")));
					if(!genrelist.get(18).getMovies().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(18).addMovies(Integer.parseInt(line.get("movieId")));
					if(Double.parseDouble(line.get("rating")) == 5.0 && 
							!genrelist.get(18).getMovies5().contains(Integer.parseInt(line.get("movieId"))))
						genrelist.get(18).addMovies5(Integer.parseInt(line.get("movieId")));
					break;
				default:
					break;
				}
        	}
        }
        System.out.println("Arquivo ratings... ok\n");
        csv.close();
        /*
         * Fim da leitura dos arquivos e início da segunda fase da análise exploratória. Nesta etapa, é feita a análise estatística básica.
         */
        System.out.println("PARTE 1\nEstatística Básica");
        System.out.println("Total de filmes: "+movies.size());
        System.out.println("Total de usuários: "+users.size());
        int total = 0;
        for(User user : users)
        	total = total + user.getMovies().size();        
        System.out.println("Total de ratings: "+total);
        
        double meanNRatings = 0;
        int totalNRatings = 0;
        double meanRating = 0;        
        for(Movie movie : movies){
        	meanNRatings = meanNRatings + movie.getNRatings();
        	totalNRatings++;
        	meanRating = meanRating + movie.getRating();        	
        }
        meanNRatings = meanNRatings / totalNRatings;
        meanRating = meanRating / movies.size();
        System.out.println("Número médio de ratings por filme: "+meanNRatings);
        System.out.println("Rating médio: "+meanRating);
        /*
         * Fim da segunda fase e início da terceira fase da análise exploratória. Nesta etapa, são obtidas características gerais dos dados.
         */
        System.out.println("\nCaracterísticas Gerais");
        Movie mostSeen = movies.get(0);
        Movie highestRating = movies.get(0);
        for(Movie movie : movies){
        	if(movie.getNRatings() > mostSeen.getNRatings())
        		mostSeen = movie;
        	if(movie.getNRatings() > meanNRatings && movie.getRating() > highestRating.getRating())
        		highestRating = movie;
        }        
        System.out.println("Filme mais visto (mais ratings): "+mostSeen.getTitle() + " com rating médio "+mostSeen.getRating()+" de " +
        		mostSeen.getNRatings()+" usuários");
        System.out.println("Filme com maior rating (considerando apenas filmes com número de ratings maior que número médio de ratings por filme): " + highestRating.getTitle() + " com rating médio "+highestRating.getRating() + " de "+highestRating.getNRatings()+" usuários");
        
        Genre mostSeenGenre = genrelist.get(0);
        Genre highestRatingGenre = genrelist.get(0);
        for(Genre genre : genrelist){
        	if(genre.getNRatings() > mostSeenGenre.getNRatings())
        		mostSeenGenre = genre;
        	if(genre.getRating() > highestRatingGenre.getRating())
        		highestRatingGenre = genre;
        }
        System.out.println("Gênero mais visto (mais ratings): "+mostSeenGenre.getName()+" com rating médio "+mostSeenGenre.getRating()+" de "+
        		mostSeenGenre.getNRatings()+" usuários");
        System.out.println("Gênero com maior rating (relação entre gênero e rating): "+highestRatingGenre.getName()+" com rating médio "+highestRatingGenre.getRating()+" de "+
        		highestRatingGenre.getNRatings()+" usuários");
        
        Genre mostMovies = genrelist.get(0);
        for(Genre genre : genrelist){
        	if(genre.getMovies().size() > mostMovies.getMovies().size())
        		mostMovies = genre;
        }
        System.out.println("Gênero com mais filmes: "+mostMovies.getName()+" com "+mostMovies.getMovies().size()+" filmes");
        
        Genre mostMovies5 = genrelist.get(0);
        for(Genre genre : genrelist){
        	if(genre.getMovies5().size() > mostMovies5.getMovies5().size())
        		mostMovies5 = genre;
        }
        System.out.println("Gênero com mais filmes de rating 5 (mais filmes \"bons\"): "+mostMovies5.getName()+" com "+mostMovies5.getMovies5().size()+" filmes");
        
        Genre mostMovies5prop = genrelist.get(0);
        for(Genre genre : genrelist){
        	if((double)genre.getMovies5().size()/genre.getMovies().size() > (double)mostMovies5prop.getMovies5().size()/mostMovies5prop.getMovies().size())
        		mostMovies5prop = genre;
        }
        System.out.println("Gênero com mais filmes de rating 5 (mais filmes \"bons\") proporcional ao que produziu: "+mostMovies5prop.getName()+" com "+mostMovies5prop.getMovies5().size()+" filmes de um total de "+mostMovies5prop.getMovies().size());
        
        Genre mostSD = genrelist.get(0);
        for(Genre genre : genrelist){
        	if(genre.getSD() > mostSD.getSD())
        		mostSD = genre;
        }
        System.out.println("Gênero com maior desvio padrão no rating (maior heterogeneidade entre os usuários): "+mostSD.getName()+" com desvio padrão de "+mostSD.getSD()+" no rating");        
        
        List<GenreCorrelation> genrecorrelation = new ArrayList<GenreCorrelation>();
        for(Movie movie : movies){
        	if(movie.getGenres().size() > 1){
	        	for(int i = 0; i < movie.getGenres().size()-1; i++){
	        		for(int j = i+1; j < movie.getGenres().size(); j++){
		        			if(!containsGenre(genrecorrelation, movie.getGenres().get(i), movie.getGenres().get(j)))
		        				genrecorrelation.add(new GenreCorrelation(movie.getGenres().get(i), movie.getGenres().get(j)));
		        			else
		        				genrecorrelation.get(getIndexCorrelation(genrecorrelation, movie.getGenres().get(i), movie.getGenres().get(j))).increaseN();
	        		}
	        	}
        	}
        }
        //ordena a lista dos gêneros mais correlacionados
        Collections.sort(genrecorrelation, new Comparator<GenreCorrelation>() {
            @Override public int compare(GenreCorrelation c1, GenreCorrelation c2) {
                return c2.getN() - c1.getN();
            }

        });
        System.out.print("Gêneros mais correlacionados (filmes que possuem ambos os gêneros): ");
        for(int i = 0; i < 5; i++)
        	System.out.print(genrecorrelation.get(i).getGenre1()+" e "+genrecorrelation.get(i).getGenre2()+" com "+
        			genrecorrelation.get(i).getN()+" filmes, ");
        
        /*
         * Aqui começa a Parte 2 do teste. O sistema de recomendação usa o id do usuário como entrada. Após a digitação do id, uma lista com 10 filmes recomendados é exibida. A recomendação é feita em relação aos vizinhos mais próximos (baseado nos gêneros) de 5 filmes com maior rating que o usuário atribuiu.
         */
        System.out.println("\n\n\nPARTE 2\nSistema de Recomendação\nDigite o id do usuário para listar os filmes recomendados (digite 'q' para finalizar)");
        Scanner scan = new Scanner(System.in);
        String key;
        List<RatingMovie> ratings;
        int target[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//vetor de atributos do filme: a primeira posição é o id e o restante são os gêneros, onde 0 é ausente e 1 presente
        BufferedWriter writer;
        CSVPrinter csvwriter;
        Instances data;
        LinearNNSearch knn;
        Instances result;
        Instance instance;
        
        while(!(key = scan.nextLine()).equals("q")){
        	writer = Files.newBufferedWriter(Paths.get("files/temp.csv"));//arquivo temporário para realizar a busca por vizinhos mais próximos
        	csvwriter = new CSVPrinter(writer, CSVFormat.DEFAULT
            		.withHeader("movieID","Action","Adventure","Animation","Children","Comedy","Crime","Documentary","Drama","Fantasy","Film-Noir",
            				"Horror","Musical","Mystery","Romance","Sci-Fi","Thriller","War","Western","(no genres listed)"));//cabeçalho do arquivo csv temporário
        	try{
		        ratings = users.get(getIndexUser(users, Integer.parseInt(key))).getMovies();
		        Collections.sort(ratings, new Comparator<RatingMovie>() {//ordena os ratings dos filmes que o usuário atribuiu
		            @Override public int compare(RatingMovie r1, RatingMovie r2) {
		                return Double.compare(r2.getRating(), r1.getRating());
		            	//return (int) (r2.getRating() - r1.getRating());
		            }
		
		        });
		        for(int i = 0; i < 5; i++){//recupera os gêneros dos 5 filmes com maior rating do usuário
		        	m = movies.get(getIndex(movies, ratings.get(i).getMovie()));
		        	for(String g : m.getGenres()){
		        		switch (g) {
						case "Action":
							target[0] = 1;
							break;
						case "Adventure":
							target[1] = 1;
							break;
						case "Animation":
							target[2] = 1;
							break;
						case "Children":
							target[3] = 1;
							break;
						case "Comedy":
							target[4] = 1;
							break;
						case "Crime":
							target[5] = 1;
							break;
						case "Documentary":
							target[6] = 1;
							break;
						case "Drama":
							target[7] = 1;
							break;
						case "Fantasy":
							target[8] = 1;
							break;
						case "Film-Noir":
							target[9] = 1;
							break;
						case "Horror":
							target[10] = 1;
							break;
						case "Musical":
							target[11] = 1;
							break;
						case "Mystery":
							target[12] = 1;
							break;
						case "Romance":
							target[13] = 1;
							break;
						case "Sci-Fi":
							target[14] = 1;
							break;
						case "Thriller":
							target[15] = 1;
							break;
						case "War":
							target[16] = 1;
							break;
						case "Western":
							target[17] = 1;
							break;
						case "(no genres listed)":
							target[18] = 1;
							break;
						default:
							break;
						}
		        	}
		        }
		        //armazena o vetor de atributos (gêneros) do usuário no arquivo csv
		        csvwriter.printRecord("target",target[0],target[1],target[2],target[3],target[4],target[5],target[6],target[7],target[8],
		        		target[9],target[10],target[11],target[12],target[13],target[14],target[15],target[16],target[17],target[18]);
		        
		        for(Movie movie : movies){
		        	if(!containsIdMovie(ratings,movie.getMovieId())){//exclui os filmes que o usuário já assistiu
			        	for(int i = 0; i < target.length; i++)
			            	target[i] = 0;
			        	for(String g : movie.getGenres()){
			        		switch (g) {
							case "Action":
								target[0] = 1;
								break;
							case "Adventure":
								target[1] = 1;
								break;
							case "Animation":
								target[2] = 1;
								break;
							case "Children":
								target[3] = 1;
								break;
							case "Comedy":
								target[4] = 1;
								break;
							case "Crime":
								target[5] = 1;
								break;
							case "Documentary":
								target[6] = 1;
								break;
							case "Drama":
								target[7] = 1;
								break;
							case "Fantasy":
								target[8] = 1;
								break;
							case "Film-Noir":
								target[9] = 1;
								break;
							case "Horror":
								target[10] = 1;
								break;
							case "Musical":
								target[11] = 1;
								break;
							case "Mystery":
								target[12] = 1;
								break;
							case "Romance":
								target[13] = 1;
								break;
							case "Sci-Fi":
								target[14] = 1;
								break;
							case "Thriller":
								target[15] = 1;
								break;
							case "War":
								target[16] = 1;
								break;
							case "Western":
								target[17] = 1;
								break;
							case "(no genres listed)":
								target[18] = 1;
								break;
							default:
								break;
							}
			        	}
			        	//armazena o vetor de atributos (gêneros) dos filmes que o usuário não assistiu no arquivo csv
			        	csvwriter.printRecord(movie.getMovieId(),target[0],target[1],target[2],target[3],target[4],target[5],target[6],
			        			target[7],target[8],target[9],target[10],target[11],target[12],target[13],target[14],target[15],target[16],
			        			target[17],target[18]);
		        	}
		        }
		    	csvwriter.flush();
		    	csvwriter.close();
		    	
		    	/*
		    	 * A partir daqui o código inicia a análise de similaridade entre o vetor de atributos do usuário com os vetores de atributos dos filmes que o usuário não assistiu.
		    	 */
		    	data = DataSource.read("files/temp.csv");        
		        knn = new LinearNNSearch(data);
		        knn.getDistanceFunction().setAttributeIndices("2-last");//exclui o id do filme para o cálculo da distância Euclidiana        
		        result = knn.kNearestNeighbours(data.get(0), data.numInstances());//o primeiro vetor é usado como de referência (usuário) e o restante para os vizinhos (filmes)
		        for(int i = 0; i < 10; i++){//lista os 10 filmes mais similares aos gêneros do usuário
		        	instance = result.get(i);
		        	System.out.println(movies.get(getIndex(movies, Integer.parseInt(instance.stringValue(0)))).getTitle());
		        }
	        	System.out.println("\nDigite o id do usuário para listar os filmes recomendados (digite 'q' para finalizar)");        	        	
        	}
        	catch(ArrayIndexOutOfBoundsException e){//tratamento de exceção quando a busca por índice no vetor retorna -1, ou seja, quando não existe um determinado id
        		System.out.println("Não existe esse id");
        		System.out.println("\nDigite o id do usuário para listar os filmes recomendados (digite 'q' para finalizar)");
        	}
        	catch (NumberFormatException e) {//tratamento de exceção quando o usuário digita um valor diferente de numérico
        		System.out.println("\nDigite o id do usuário para listar os filmes recomendados (digite 'q' para finalizar)");
			}
        }
        scan.close();
	}
	
	/*
	 * Funções criadas para o desenvolvimento da solução.
	 */
	public static int getIndex(List<Movie> movies, int id){//retorna o id de um filme na lista de filmes
		for(int i = 0; i < movies.size(); i++){
			if(movies.get(i).getMovieId() == id)
				return i;
		}
		return -1;
	}
	
	public static int getIndexUser(List<User> users, int id){//retorna o id de um usuário na lista de usuários
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUserId() == id)
				return i;
		}
		return -1;
	}
	
	public static int getIndexCorrelation(List<GenreCorrelation> correlation, String genre1, String genre2){//retorna o id de um par de gêneros
		for(int i = 0; i < correlation.size(); i++){
			if((correlation.get(i).getGenre1().equals(genre1) && correlation.get(i).getGenre2().equals(genre2)) || 
		    		(correlation.get(i).getGenre1().equals(genre2) && correlation.get(i).getGenre2().equals(genre1)))
				return i;
		}
		return -1;
	}
	
	public static boolean containsId(final List<User> list, final int id){//verifica se existe um usuário na lista de usuários
	    return list.stream().filter(o -> o.getUserId() == id).findFirst().isPresent();
	}
	
	public static boolean containsIdMovie(final List<RatingMovie> list, final int id){//verifica se existe um filme na lista de filmes-rating
	    return list.stream().filter(o -> o.getMovie() == id).findFirst().isPresent();
	}
	
	public static boolean containsGenre(final List<GenreCorrelation> list, final String genre1, final String genre2){//verifica se existe um par de gêneros na lista de pares de gêneros correlacionados
	    return list.stream().filter(o -> (o.getGenre1().equals(genre1) && o.getGenre2().equals(genre2)) || 
	    		(o.getGenre1().equals(genre2) && o.getGenre2().equals(genre1))).findFirst().isPresent();
	}
	
	/*
	 * Classes de estrutura de dados criadas para o desenvolvimento da solução.
	 */
	public static class GenreCorrelation{
		private String genre1;
		private String genre2;
		private int n;//número de filmes que possuem dois gêneros (genre1 e genre2)
		
		public GenreCorrelation(String genre1, String genre2) {
			this.genre1 = genre1;
			this.genre2 = genre2;
			this.n = 1;
		}

		public String getGenre1() {
			return genre1;
		}
		public void setGenre1(String genre1) {
			this.genre1 = genre1;
		}
		public String getGenre2() {
			return genre2;
		}
		public void setGenre2(String genre2) {
			this.genre2 = genre2;
		}
		public void increaseN(){
			this.n++;
		}
		public int getN(){
			return n;
		}
	}
	
}
