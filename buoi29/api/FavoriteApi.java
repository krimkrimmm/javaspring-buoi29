package vn.scrip.buoi29.api;
import vn.scrip.buoi29.entity.Favorite;
import vn.scrip.buoi29.model.request.AddFavoriteRequest;
import vn.scrip.buoi29.model.request.RemoveFavoriteRequest;
import vn.scrip.buoi29.service.FavoriteService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteService favoriteService;

    // 1. Lấy danh sách phim yêu thích (có phân trang)
    @GetMapping
    public ResponseEntity<?> getFavorites(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<Favorite> favoritePage = favoriteService.getFavorites(page, pageSize);
        return ResponseEntity.ok(favoritePage);
    }

    // 2. Thêm phim vào danh sách yêu thích
    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestBody AddFavoriteRequest request) {
        Favorite favorite = favoriteService.addFavorite(request.getMovieId());
        return ResponseEntity.ok(favorite);
    }

    // 3. Xoá 1 phim ra khỏi danh sách yêu thích
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestBody RemoveFavoriteRequest request) {
        favoriteService.removeFavorite(request.getMovieId());
        return ResponseEntity.ok().build();
    }

    // 4. Xoá tất cả phim yêu thích
    @DeleteMapping("/removeAll")
    public ResponseEntity<?> removeAllFavorites() {
        favoriteService.removeAllFavorites();
        return ResponseEntity.ok().build();
    }
}