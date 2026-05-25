package mm.com.mytel.articleapi.repo;

import mm.com.mytel.articleapi.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN FETCH a.author ORDER BY a.createdAt DESC")
    List<Article> findAllWithAuthorOrderByCreatedAtDesc();

    @Query(
            value = "SELECT a FROM Article a JOIN FETCH a.author",
            countQuery = "SELECT COUNT(a) FROM Article a")
    Page<Article> findAllWithAuthor(Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.author WHERE a.id = :id")
    Optional<Article> findByIdWithAuthor(Long id);

    @Query("SELECT a FROM Article a JOIN FETCH a.author WHERE LOWER(a.tag) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY a.createdAt DESC")
    List<Article> findAllArticleWithTag(@Param("keyword") String keyword);

    @Query(
            value = "SELECT a FROM Article a JOIN FETCH a.author WHERE LOWER(a.tag) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            countQuery = "SELECT COUNT(a) FROM Article a WHERE LOWER(a.tag) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Article> findAllArticleWithTag(@Param("keyword") String keyword, Pageable pageable);
}
