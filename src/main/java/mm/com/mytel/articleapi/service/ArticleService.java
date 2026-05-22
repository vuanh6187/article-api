package mm.com.mytel.articleapi.service;

import mm.com.mytel.articleapi.dto.ArticleRequest;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.User;

import java.util.List;

public interface ArticleService {

    List<ArticleSummaryResponse> findAllSummaries();

    Article findById(Long id);

    Article create(ArticleRequest request, User author);

    Article update(Long id, ArticleRequest request, User currentUser);

    void delete(Long id, User currentUser);

    boolean isOwner(Long articleId, Long userId);
}
