package mm.com.mytel.articleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ArticleResponse {

    private Long id;
    private String title;
    private String description;
    private String content;
    private String tag;
    private boolean isLockComment;
    private String authorUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
