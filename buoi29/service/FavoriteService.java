package vn.scrip.buoi29.service;
import vn.scrip.buoi29.entity.Favorite;
import vn.scrip.buoi29.entity.Movie;
import vn.scrip.buoi29.entity.User;
import vn.scrip.buoi29.exception.BadRequestException;
import vn.scrip.buoi29.exception.NotFoundException;

import vn.scrip.buoi29.repository.FavoriteRepository;
import vn.scrip.buoi29.repository.MovieRepository;
import vn.scrip.buoi29.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    // 1. Lấy danh sách phim yêu thích của user, có phân trang
    public Page<Favorite> getFavorites(Integer page, Integer pageSize) {
        // FIX userId = 1 (chưa có login)
        Integer userId = 1;
        // Kiểm tra user tồn tại
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user có id = " + userId));

        // Tạo pageable
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending());
        // Lấy danh sách favorite
        return favoriteRepository.findByUser_Id(user.getId(), pageable);
    }

    // 2. Thêm phim vào danh sách yêu thích
    public Favorite addFavorite(Integer movieId) {
        // FIX userId = 1
        Integer userId = 1;
        // Kiểm tra user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user có id = " + userId));
        // Kiểm tra phim
        Movie movie = movieRepository.findByIdAndStatusTrue(movieId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phim có id = " + movieId));

        // Kiểm tra xem đã favorite chưa (nếu cần tránh duplicate)
        Optional<Favorite> favoriteOptional = favoriteRepository.findByUser_IdAndMovie_Id(userId, movieId);
        if (favoriteOptional.isPresent()) {
            // Nếu đã tồn tại thì có thể ném lỗi hoặc return luôn tuỳ business
            throw new BadRequestException("Phim đã có trong danh sách yêu thích");
        }

        // Tạo Favorite
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);
        favorite.setCreatedAt(LocalDateTime.now());
        return favoriteRepository.save(favorite);
    }

    // 3. Xoá phim ra khỏi danh sách yêu thích
    public void removeFavorite(Integer movieId) {
        // FIX userId = 1
        Integer userId = 1;

        // Kiểm tra user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user có id = " + userId));

        // Kiểm tra xem favorite tồn tại hay không
        Favorite favorite = favoriteRepository.findByUser_IdAndMovie_Id(userId, movieId)
                .orElseThrow(() -> new NotFoundException("Phim không có trong danh sách yêu thích"));

        // Xoá
        favoriteRepository.delete(favorite);
    }

    // 4. Xoá toàn bộ danh sách yêu thích
    public void removeAllFavorites() {
        // FIX userId = 1
        Integer userId = 1;

        // Kiểm tra user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user có id = " + userId));

        // Cách 1: Lấy toàn bộ favorites của user rồi xoá
        // var favorites = favoriteRepository.findByUser_Id(userId);
        // favoriteRepository.deleteAll(favorites);

        // Cách 2: Viết hàm deleteAllByUser_Id(userId) trong repository
        favoriteRepository.deleteAllByUser_Id(userId);
    }
}