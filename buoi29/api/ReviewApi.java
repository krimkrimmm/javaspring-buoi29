package vn.scrip.buoi29.api;
import vn.scrip.buoi29.entity.Review;
import vn.scrip.buoi29.model.request.CreateReviewRequest;
import vn.scrip.buoi29.model.request.UpdateReviewRequest;
import vn.scrip.buoi29.service.ReviewService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApi {

    private final ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<?> getReviews(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam Integer movieId) {
        Page<Review> reviewPage = reviewService.getReviewsByMovie(movieId, page, pageSize);
        return ResponseEntity.ok(reviewPage);
    }

    @PostMapping()
    public ResponseEntity<?> createReview(@Valid @RequestBody CreateReviewRequest request) {
        Review review = reviewService.createReview(request);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id,
                                          @Valid @RequestBody UpdateReviewRequest request) {
        Review review = reviewService.updateReview(id, request);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}