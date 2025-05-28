package spring.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import spring.Model.MovieLocation;
// permite injeta-la no controller
@Service
public class MovieLocationService {
    
    private final WebClient webClient;
    //construtor //instancia o webclient e consome a url dos dados
    public MovieLocationService(WebClient.Builder builder){
        this.webClient = builder
                            .baseUrl("https://data.sfgov.org/resource/yitu-d5am.json")
                            .build();
    }


    public List<MovieLocation> getAllMovies(){
        //retorna os dados da base url
        return webClient.get()
                .retrieve() //executa a requisição e obtém a resposta
                .bodyToFlux(MovieLocation.class) //processa as respostas do webclient e converte em um flux de objetos da cla
                .collectList() // junta os elementos do tuflux como  lista
                .block(); //aguarda o fim da operação, sincrono
    }
    // usado para filtrar busca por titulo
    public List<MovieLocation> filterByTitle(String query){
        return getAllMovies().stream()
                .filter(m -> m.getTitle() != null && m.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
