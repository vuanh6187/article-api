package mm.com.mytel.articleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSummaryResponse {

    private Long id;
    private String title;
    private String description;
    private String tag;
    private boolean lockComment;
    private String authorUsername;
}
