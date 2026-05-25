package mm.com.mytel.articleapi.service;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.ArticleResponse;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.ErrorCode;
import mm.com.mytel.articleapi.exception.ForbiddenException;
import mm.com.mytel.articleapi.exception.NotFoundException;
import mm.com.mytel.articleapi.mapper.ArticleMapper;
import mm.com.mytel.articleapi.repo.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> findAllSummaries() {
        return articleRepository.findAllWithAuthorOrderByCreatedAtDesc().stream()
                .map(articleMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleSummaryResponse> findAllSummaries(Pageable pageable) {
        return articleRepository.findAllWithAuthor(pageable)
                .map(articleMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse findResponseById(Long id) {
        return articleMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Article findById(Long id) {
        return articleRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    @Transactional
    public Article create(ArticleRequest request, User author) {
        Article article = Article.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .tag(request.getTag())
                .lockComment(request.isLockComment())
                .author(author)
                .build();
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public ArticleResponse createResponse(ArticleRequest request, User author) {
        return articleMapper.toResponse(create(request, author));
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
        article.setLockComment(request.isLockComment());

        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public ArticleResponse updateResponse(Long id, ArticleRequest request, User currentUser) {
        return articleMapper.toResponse(update(id, request, currentUser));
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

    @Override
    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> findArticleByTag(String tag) {
        return articleRepository.findAllArticleWithTag(tag).stream()
                .map(articleMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleSummaryResponse> findArticleByTag(String tag, Pageable pageable) {
        return articleRepository.findAllArticleWithTag(tag, pageable)
                .map(articleMapper::toSummaryResponse);
    }

    private void ensureOwner(Article article, User currentUser) {
        if (!article.getAuthor().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(ErrorCode.ARTICLE_NOT_OWNER);
        }
    }
}
