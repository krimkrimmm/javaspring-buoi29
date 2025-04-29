package vn.scrip.buoi29.api;
import vn.scrip.buoi29.entity.Favorite;
import vn.scrip.buoi29.model.request.AddFavoriteRequest;
import vn.scrip.buoi29.model.request.LoginRequest;
import vn.scrip.buoi29.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authService.login(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();

        return ResponseEntity.ok().build();
    }
}