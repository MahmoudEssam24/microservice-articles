package sa.com.me.article.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import sa.com.me.article.model.Article;

public interface ArticleService {

    Optional<Article> findById(Long id);
    Page<Article> findAll(int page, int size);
    Article addArticle(Article article);
    void deleteArticle(Long articleId);
    void validateAuthority(String token, String resource, String action);
}