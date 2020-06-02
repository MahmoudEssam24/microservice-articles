package sa.com.me.article.data;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import sa.com.me.article.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{

    Optional<Article> findByIdAndDeletedAtIsNull(Long id);

    @Query("select a from Article a where a.deletedAt IS NULL")
    Page<Article> findAllAndDeletedAtIsNull(Pageable pageable);
    
    @Query("update Article a set a.deletedAt = now() where a.id = :id")
    @Transactional
    @Modifying
    void deleteById(Long id);
}