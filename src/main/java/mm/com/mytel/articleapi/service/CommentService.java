package mm.com.mytel.articleapi.service;

import mm.com.mytel.articleapi.dto.CommentRequest;
import mm.com.mytel.articleapi.entity.Comment;
import mm.com.mytel.articleapi.entity.User;

import java.util.List;

public interface CommentService {

    List<Comment> findByArticleId(Long articleId);

    Comment addComment(Long articleId, CommentRequest request, User author);
}
