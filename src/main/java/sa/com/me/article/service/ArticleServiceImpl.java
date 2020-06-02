package sa.com.me.article.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import sa.com.me.article.client.UserClient;
import sa.com.me.article.data.ArticleRepository;
import sa.com.me.article.model.Article;
import sa.com.me.article.util.JwtTokenProvider;
import sa.com.me.core.dto.AuthorityDto;
import sa.com.me.core.exception.NotAuthorizedException;
import sa.com.me.core.service.BaseService;
import sa.com.me.core.util.Action;
import sa.com.me.core.util.Resource;

@Service
public class ArticleServiceImpl extends BaseService implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserClient userClient;

    @Override
    @Cacheable(value = "article", key = "'ArticleService_findById_'.concat(#id)")
    public Optional<Article> findById(Long id) {
        return articleRepository.findByIdAndDeletedAtIsNull(id);
    }

    @Override
    @Cacheable(value = "allArticles", key = "'findAllArticles_page'.concat(#page).concat('_size_').concat(#size)")
    public Page<Article> findAll(int page, int size) {
        return articleRepository.findAllAndDeletedAtIsNull(this.getPageable(page, size));
    }

    @Override
    @Caching(put = { @CachePut(value = "article", key = "'ArticleService_findById_'.concat(#result.id)") }, evict = {
            @CacheEvict(value = "allArticles", allEntries = true) })
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "article", allEntries = true),
            @CacheEvict(value = "allArticles", allEntries = true) })
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public void validateAuthority(String token, String resource, String action) {

        String role = tokenProvider.getRolesFromToken(token.replace("Bearer ", "")).get(0);
        AuthorityDto authority = new AuthorityDto(role, resource, action);
        if (userClient.checkUserAuthority(authority) == null) {
            throw new NotAuthorizedException("You are not authorized to perform this action", "401", "role");
        }

    }

}