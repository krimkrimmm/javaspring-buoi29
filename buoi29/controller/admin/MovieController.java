package vn.scrip.buoi29.controller.admin;
import vn.scrip.buoi29.repository.ActorRepository;
import vn.scrip.buoi29.repository.CountryRepository;
import vn.scrip.buoi29.repository.DirectorRepository;
import vn.scrip.buoi29.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class MovieController {
    private final CountryRepository countryRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    @GetMapping
    public String getIndexPage() {
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/movie/create";
    }

    @GetMapping("/{id}")
    public String getDetailsPage(@PathVariable Long id) {
        return "admin/movie/detail";
    }
}