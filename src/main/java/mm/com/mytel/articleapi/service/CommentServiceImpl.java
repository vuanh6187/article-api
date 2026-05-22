package mm.com.mytel.articleapi.service;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.CommentRequest;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.Comment;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.repo.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByArticleId(Long articleId) {
        articleService.findById(articleId);
        return commentRepository.findByArticleIdWithAuthor(articleId);
    }

    @Override
    @Transactional
    public Comment addComment(Long articleId, CommentRequest request, User author) {
        Article article = articleService.findById(articleId);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .article(article)
                .author(author)
                .build();

        return commentRepository.save(comment);
    }
}
