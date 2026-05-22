package mm.com.mytel.articleapi.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.UpdateProfileRequest;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.BadRequestException;
import mm.com.mytel.articleapi.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProfileComponent {

    private final AuthService authService;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        User user = authService.getCurrentUser();
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setUsername(user.getUsername());
        model.addAttribute("updateProfileRequest", request);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @Valid @ModelAttribute("updateProfileRequest") UpdateProfileRequest request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }

        try {
            User user = authService.getCurrentUser();
            authService.updateProfile(user.getId(), request, httpRequest);
            return "redirect:/home";
        } catch (BadRequestException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "profile";
        }
    }
}
