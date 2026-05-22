package mm.com.mytel.articleapi.component;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.CommentRequest;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.Comment;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.ApiException;
import mm.com.mytel.articleapi.exception.BadRequestException;
import mm.com.mytel.articleapi.service.ArticleService;
import mm.com.mytel.articleapi.service.AuthService;
import mm.com.mytel.articleapi.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleComponent {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final AuthService authService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("articleRequest", new ArticleRequest());
        model.addAttribute("isEdit", false);
        return "articles/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("articleRequest") ArticleRequest request,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "articles/form";
        }

        User user = authService.getCurrentUser();
        articleService.create(request, user);
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        List<Comment> comments = commentService.findByArticleId(id);
        User currentUser = authService.getCurrentUser();

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        model.addAttribute("isOwner", articleService.isOwner(id, currentUser.getId()));
        model.addAttribute("isLockComment", article.isLockComment());
        model.addAttribute("commentRequest", new CommentRequest());
        return "articles/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        User currentUser = authService.getCurrentUser();

        if (!articleService.isOwner(id, currentUser.getId())) {
            return "redirect:/articles/" + id;
        }

        ArticleRequest request = new ArticleRequest();
        request.setTitle(article.getTitle());
        request.setDescription(article.getDescription());
        request.setContent(article.getContent());
        request.setTag(article.getTag());
        request.setLockComment(article.isLockComment());

        model.addAttribute("articleRequest", request);
        model.addAttribute("articleId", id);
        model.addAttribute("isEdit", true);
        return "articles/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("articleRequest") ArticleRequest request,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleId", id);
            model.addAttribute("isEdit", true);
            return "articles/form";
        }

        try {
            User user = authService.getCurrentUser();
            articleService.update(id, request, user);
            return "redirect:/articles/" + id;
        } catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("articleId", id);
            model.addAttribute("isEdit", true);
            return "articles/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        try {
            User user = authService.getCurrentUser();
            articleService.delete(id, user);
            return "redirect:/home";
        } catch (ApiException ex) {
            return "redirect:/articles/" + id;
        }
    }

    @PostMapping("/{id}/comments")
    public String addComment(
            @PathVariable Long id,
            @Valid @ModelAttribute("commentRequest") CommentRequest request,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return detail(id, model);
        }

        try {
            User user = authService.getCurrentUser();
            commentService.addComment(id, request, user);
            return "redirect:/articles/" + id;
        } catch (BadRequestException ex) {
            model.addAttribute("commentError", ex.getMessage());
            return detail(id, model);
        }
    }
}
