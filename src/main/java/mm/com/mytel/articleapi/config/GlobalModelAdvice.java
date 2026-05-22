package mm.com.mytel.articleapi.config;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.service.AuthService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final AuthService authService;

    @ModelAttribute("currentUser")
    public User currentUser() {
        return authService.getCurrentUser();
    }
}
