package spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.Model.MovieLocation;
import spring.server.MovieLocationService;

@RestController
@RequestMapping("/movies")
public class MovieLocationController {

    private final MovieLocationService service;
    // injeção de dependência via construtor
    public MovieLocationController(MovieLocationService service){
        this.service = service;
    }
    @GetMapping // mapeia as requisições HTTP, nesse caso GET
    //usado para fazer requisições opcionais por titulo
    public List<MovieLocation> getAll(@RequestParam Optional <String> title){
       //se title estiver presente, chama o método
        return title.map(service::filterByTitle)
        // se não estiver, ira chamar todos os filmes
                    .orElseGet(service::getAllMovies);
    }

}
