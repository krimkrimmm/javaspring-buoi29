package vn.scrip.buoi29.controller.web;
import vn.scrip.buoi29.entity.Episode;
import vn.scrip.buoi29.entity.Movie;
import vn.scrip.buoi29.entity.User;
import vn.scrip.buoi29.model.dto.UserDTO;
import vn.scrip.buoi29.model.enums.MovieType;

import vn.scrip.buoi29.service.EpisodeService;
import vn.scrip.buoi29.service.MovieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final MovieService movieService;
    private final EpisodeService episodeService;
    private final HttpSession session;

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Movie> hotMovies = movieService.findHotMovie(true, 4);
        List<Movie> phimLeList = movieService.findByType(MovieType.PHIM_LE, true, 1, 6).getContent();
        List<Movie> phimBoList = movieService.findByType(MovieType.PHIM_BO, true, 1, 6).getContent();
        List<Movie> phimChieuRapList = movieService.findByType(MovieType.PHIM_CHIEU_RAP, true, 1, 6).getContent();
        model.addAttribute("hotMovies", hotMovies);
        model.addAttribute("phimLeList", phimLeList);
        model.addAttribute("phimBoList", phimBoList);
        model.addAttribute("phimChieuRapList", phimChieuRapList);
        return "web/index";
    }

    @GetMapping("/phim-bo")
    public String getPhimBoPage(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "18") Integer pageSize,
                                Model model) {
        Page<Movie> moviePage = movieService.findByType(MovieType.PHIM_BO, true, page, pageSize);
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("currentPage", page);
        return "web/phim-bo";
    }

    @GetMapping("/phim-le")
    public String getPhimLePage(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "18") Integer pageSize,
                                Model model) {
        Page<Movie> moviePage = movieService.findByType(MovieType.PHIM_LE, true, page, pageSize);
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("currentPage", page);
        return "web/phim-le";
    }

    @GetMapping("/phim-chieu-rap")
    public String getPhimChieuRapPage(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "18") Integer pageSize,
                                      Model model) {
        Page<Movie> moviePage = movieService.findByType(MovieType.PHIM_CHIEU_RAP, true, page, pageSize);
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("currentPage", page);
        return "web/phim-chieu-rap";
    }

    @GetMapping("/phim/{id}/{slug}")
    public String getMovieDetailsPage(@PathVariable Integer id, @PathVariable String slug, Model model) {
        // Thông tin chi tiết phim
        Movie movie = movieService.findMovieDetails(id, slug);
        model.addAttribute("movie", movie);

        // Lấy danh sách tập phim (movieId, status = true, sort by displayOrder asc)
        List<Episode> episodes = episodeService.findEpisodesByMovieId(id);
        model.addAttribute("episodes", episodes);

        // Kiem tra xem phim co trong danh sach yeu thich cua user khong
        Boolean isFavorite = true; //favorite.checkFavorite(id);
        model.addAttribute("isFavorite", isFavorite);
        return "web/chi-tiet-phim";
    }

    // /xem-phim/{id}/{slug}?tap=1, 2, 3 -> Phim bo
    // /xem-phim/{id}/{slug}?tap=full -> Phim le, phim chieu rap
    @GetMapping("/xem-phim/{id}/{slug}")
    public String getWatchMovieDetailsPage(@PathVariable Integer id, @PathVariable String slug, Model model, @RequestParam String tap) {
        // Thông tin chi tiết phim
        Movie movie = movieService.findMovieDetails(id, slug);
        model.addAttribute("movie", movie);

        // Lấy danh sách tập phim (movieId, status = true, sort by displayOrder asc)
        List<Episode> episodes = episodeService.findEpisodesByMovieId(id);
        model.addAttribute("episodes", episodes);

        // Lấy thông tin tập phim (tap = displayOrder)
        // tap = "1" => displayOrder = 1
        // tap = "2" => displayOrder = 2
        // tap = "full" => displayOrder = 1
        // Tìm kiếm (movieId, status = true, displayOrder = tap)
        // Lấy tập phim theo `tap`
        Episode episode = episodeService.findEpisodeByDisplayOrder(id, tap);
        model.addAttribute("episode", episode);
        return "web/xem-phim";
    }

    @GetMapping("/phim-yeu-thich")
    public String getFavoritePage(Model model) {
        return "web/phim-yeu-thich";
    }

    @GetMapping("/dang-nhap")
    public String showLoginPage() {
        UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "web/login";
        }
        return "redirect:/";
    }
}