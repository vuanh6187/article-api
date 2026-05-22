package mm.com.mytel.articleapi.service;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.ForbiddenException;
import mm.com.mytel.articleapi.exception.NotFoundException;
import mm.com.mytel.articleapi.repo.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> findAllSummaries() {
        return articleRepository.findAllWithAuthorOrderByCreatedAtDesc().stream()
                .map(this::toSummary)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Article findById(Long id) {
        return articleRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new NotFoundException("Bài viết không tồn tại"));
    }

    @Override
    @Transactional
    public Article create(ArticleRequest request, User author) {
        Article article = Article.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .tag(request.getTag())
                .author(author)
                .build();
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public Article update(Long id, ArticleRequest request, User currentUser) {
        Article article = findById(id);
        ensureOwner(article, currentUser);

        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setContent(request.getContent());
        article.setTag(request.getTag());

        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public void delete(Long id, User currentUser) {
        Article article = findById(id);
        ensureOwner(article, currentUser);
        articleRepository.delete(article);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(Long articleId, Long userId) {
        return articleRepository.findById(articleId)
                .map(article -> article.getAuthor().getId().equals(userId))
                .orElse(false);
    }

    private void ensureOwner(Article article, User currentUser) {
        if (!article.getAuthor().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Bạn không có quyền thao tác bài viết này");
        }
    }

    private ArticleSummaryResponse toSummary(Article article) {
        return ArticleSummaryResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .tag(article.getTag())
                .authorUsername(article.getAuthor().getUsername())
                .build();
    }
}
