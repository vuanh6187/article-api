package mm.com.mytel.articleapi.mapper;

import mm.com.mytel.articleapi.dto.ArticleResponse;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public ArticleResponse toResponse(Article article) {
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

    public ArticleSummaryResponse toSummaryResponse(Article article) {
        return ArticleSummaryResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .tag(article.getTag())
                .isLockComment(article.isLockComment())
                .authorUsername(article.getAuthor().getUsername())
                .build();
    }
}
