package vn.scrip.buoi29.api;
import vn.scrip.buoi29.model.request.UpsertMovieRequest;
import vn.scrip.buoi29.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;


    @GetMapping()
    ResponseEntity<?> getAllMovies(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(movieService.getAllMovies(page, pageSize));
    }

    @PostMapping
    ResponseEntity<?> createMovie(@Valid @RequestBody UpsertMovieRequest request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateMovie(@Valid @RequestBody UpsertMovieRequest request,
                                  @PathVariable Integer id) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}