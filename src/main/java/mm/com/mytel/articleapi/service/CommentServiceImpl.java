package mm.com.mytel.articleapi.service;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.CommentRequest;
import mm.com.mytel.articleapi.dto.CommentResponse;
import mm.com.mytel.articleapi.entity.Article;
import mm.com.mytel.articleapi.entity.Comment;
import mm.com.mytel.articleapi.entity.User;
import mm.com.mytel.articleapi.exception.BadRequestException;
import mm.com.mytel.articleapi.exception.ErrorCode;
import mm.com.mytel.articleapi.mapper.CommentMapper;
import mm.com.mytel.articleapi.repo.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByArticleId(Long articleId) {
        articleService.findById(articleId);
        return commentRepository.findByArticleIdWithAuthor(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> findResponsesByArticleId(Long articleId) {
        return findByArticleId(articleId).stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public Comment addComment(Long articleId, CommentRequest request, User author) {
        Article article = articleService.findById(articleId);

        if (article.isLockComment() && !articleService.isOwner(articleId, author.getId())) {
            throw new BadRequestException(ErrorCode.ARTICLE_COMMENTS_LOCKED);
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .article(article)
                .author(author)
                .build();

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public CommentResponse addCommentResponse(Long articleId, CommentRequest request, User author) {
        return commentMapper.toResponse(addComment(articleId, request, author));
    }
}
