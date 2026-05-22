package mm.com.mytel.articleapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.ArticleResponse;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.dto.CommentRequest;
import mm.com.mytel.articleapi.dto.CommentResponse;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.Comment;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.UnauthorizedException;
import mm.com.mytel.articleapi.service.ArticleService;
import mm.com.mytel.articleapi.service.AuthService;
import mm.com.mytel.articleapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final AuthService authService;

    @GetMapping
    public List<ArticleSummaryResponse> listArticles() {
        return articleService.findAllSummaries();
    }

    @GetMapping("/{id}")
    public ArticleResponse getArticle(@PathVariable Long id) {
        return toArticleResponse(articleService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse createArticle(@Valid @RequestBody ArticleRequest request) {
        User user = requireCurrentUser();
        return toArticleResponse(articleService.create(request, user));
    }

    @PutMapping("/{id}")
    public ArticleResponse updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        User user = requireCurrentUser();
        return toArticleResponse(articleService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id) {
        User user = requireCurrentUser();
        articleService.delete(id, user);
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponse> listComments(@PathVariable Long id) {
        return commentService.findByArticleId(id).stream()
                .map(this::toCommentResponse)
                .toList();
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request) {
        User user = requireCurrentUser();
        return toCommentResponse(commentService.addComment(id, request, user));
    }

    private User requireCurrentUser() {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Unauthorized");
        }
        return user;
    }

    private ArticleResponse toArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .content(article.getContent())
                .tag(article.getTag())
                .isLockComment(article.isLockComment())
                .authorUsername(article.getAuthor().getUsername())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    private CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorUsername(comment.getAuthor().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
