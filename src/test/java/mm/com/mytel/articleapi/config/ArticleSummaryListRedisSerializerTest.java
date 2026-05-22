package mm.com.mytel.articleapi.config;

import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleSummaryListRedisSerializerTest {

    private final ArticleSummaryListRedisSerializer serializer =
            new ArticleSummaryListRedisSerializer(new ObjectMapper());

    @Test
    void roundTripPreservesArticleSummaries() {
        List<ArticleSummaryResponse> original = List.of(
                ArticleSummaryResponse.builder()
                        .id(1L)
                        .title("Title")
                        .description("Description")
                        .tag("java")
                        .lockComment(true)
                        .authorUsername("author")
                        .build());

        byte[] bytes = serializer.serialize(original);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

        @SuppressWarnings("unchecked")
        List<ArticleSummaryResponse> restored = (List<ArticleSummaryResponse>) serializer.deserialize(bytes);

        assertThat(restored).hasSize(1);
        assertThat(restored.get(0).getId()).isEqualTo(1L);
        assertThat(restored.get(0).getTitle()).isEqualTo("Title");
        assertThat(restored.get(0).isLockComment()).isTrue();
    }
}
