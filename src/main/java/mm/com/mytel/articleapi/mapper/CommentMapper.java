package mm.com.mytel.articleapi.mapper;

import mm.com.mytel.articleapi.dto.CommentResponse;
import mm.com.mytel.articleapi.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorUsername(comment.getAuthor().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
