package mm.com.mytel.articleapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.ArticleResponse;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.dto.CommentRequest;
import mm.com.mytel.articleapi.dto.CommentResponse;
import mm.com.mytel.articleapi.service.ArticleService;
import mm.com.mytel.articleapi.service.AuthService;
import mm.com.mytel.articleapi.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<ArticleSummaryResponse> listArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if (tag != null && !tag.isBlank()) {
            return articleService.findArticleByTag(tag.trim(), pageable);
        }
        return articleService.findAllSummaries(pageable);
    }

    @GetMapping("/{id}")
    public ArticleResponse getArticle(@PathVariable Long id) {
        return articleService.findResponseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse createArticle(@Valid @RequestBody ArticleRequest request) {
        return articleService.createResponse(request, authService.requireCurrentUser());
    }

    @PutMapping("/{id}")
    public ArticleResponse updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        return articleService.updateResponse(id, request, authService.requireCurrentUser());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id) {
        articleService.delete(id, authService.requireCurrentUser());
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponse> listComments(@PathVariable Long id) {
        return commentService.findResponsesByArticleId(id);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request) {
        return commentService.addCommentResponse(id, request, authService.requireCurrentUser());
    }
}
