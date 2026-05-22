package mm.com.mytel.articleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
}
