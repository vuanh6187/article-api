package mm.com.mytel.articleapi.component;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.AuthResponse;
import mm.com.mytel.articleapi.dto.LoginRequest;
import mm.com.mytel.articleapi.dto.RegisterRequest;
import mm.com.mytel.articleapi.dto.UpdateProfileRequest;
import mm.com.mytel.articleapi.dto.UserResponse;
import mm.com.mytel.articleapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiComponent {

    private final AuthService authService;

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.registerAndLogin(request);
    }

    @PostMapping("/auth/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/users/me")
    public UserResponse getCurrentUser() {
        return authService.getCurrentUserResponse();
    }

    @PutMapping("/users/me")
    public UserResponse updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserResponse currentUser = authService.getCurrentUserResponse();
        return authService.updateProfileApi(currentUser.getId(), request);
    }
}
