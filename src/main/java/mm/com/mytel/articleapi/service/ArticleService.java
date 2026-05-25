package mm.com.mytel.articleapi.service;

import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.ArticleResponse;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    List<ArticleSummaryResponse> findAllSummaries();

    Page<ArticleSummaryResponse> findAllSummaries(Pageable pageable);

    ArticleResponse findResponseById(Long id);

    Article findById(Long id);

    Article create(ArticleRequest request, User author);

    ArticleResponse createResponse(ArticleRequest request, User author);

    Article update(Long id, ArticleRequest request, User currentUser);

    ArticleResponse updateResponse(Long id, ArticleRequest request, User currentUser);

    void delete(Long id, User currentUser);

    boolean isOwner(Long articleId, Long userId);

    List<ArticleSummaryResponse> findArticleByTag(String tag);

    Page<ArticleSummaryResponse> findArticleByTag(String tag, Pageable pageable);
}
