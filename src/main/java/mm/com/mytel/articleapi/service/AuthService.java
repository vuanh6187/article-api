package mm.com.mytel.articleapi.service;

import jakarta.servlet.http.HttpServletRequest;
import mm.com.mytel.articleapi.dto.AuthResponse;
import mm.com.mytel.articleapi.dto.LoginRequest;
import mm.com.mytel.articleapi.dto.RegisterRequest;
import mm.com.mytel.articleapi.dto.UpdateProfileRequest;
import mm.com.mytel.articleapi.dto.UserResponse;
import mm.com.mytel.articleapi.entity.User;

public interface AuthService {

    User register(RegisterRequest request);

    AuthResponse registerAndLogin(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getCurrentUserResponse();

    UserResponse updateProfileApi(Long userId, UpdateProfileRequest request);

    User getCurrentUser();

    User requireCurrentUser();

    void loginUser(User user, HttpServletRequest request);

    User updateProfile(Long userId, UpdateProfileRequest request, HttpServletRequest httpRequest);
}
