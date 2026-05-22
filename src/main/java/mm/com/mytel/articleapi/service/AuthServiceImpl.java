package mm.com.mytel.articleapi.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.AuthResponse;
import mm.com.mytel.articleapi.dto.LoginRequest;
import mm.com.mytel.articleapi.dto.RegisterRequest;
import mm.com.mytel.articleapi.dto.UpdateProfileRequest;
import mm.com.mytel.articleapi.dto.UserResponse;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.ConflictException;
import mm.com.mytel.articleapi.exception.NotFoundException;
import mm.com.mytel.articleapi.exception.UnauthorizedException;
import mm.com.mytel.articleapi.repo.UserRepository;
import mm.com.mytel.articleapi.security.CustomUserDetails;
import mm.com.mytel.articleapi.security.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public AuthResponse registerAndLogin(RegisterRequest request) {
        User user = register(request);
        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Email hoặc mật khẩu không đúng"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Email hoặc mật khẩu không đúng");
        }

        return buildAuthResponse(user);
    }

    @Override
    public UserResponse getCurrentUserResponse() {
        User user = getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Unauthorized");
        }
        return toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateProfileApi(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), userId)) {
            throw new ConflictException("Username đã tồn tại");
        }

        user.setUsername(request.getUsername());
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUser();
        }
        return null;
    }

    @Override
    public void loginUser(User user, HttpServletRequest request) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
    }

    @Override
    @Transactional
    public User updateProfile(Long userId, UpdateProfileRequest request, HttpServletRequest httpRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), userId)) {
            throw new ConflictException("Username đã tồn tại");
        }

        user.setUsername(request.getUsername());
        User saved = userRepository.save(user);
        loginUser(saved, httpRequest);
        return saved;
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .token(jwtService.generateToken(user.getEmail()))
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
