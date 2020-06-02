package sa.com.me.article.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sa.com.me.article.client.UserClient;
import sa.com.me.article.mapper.ArticleMapper;
import sa.com.me.article.model.Article;
import sa.com.me.article.service.ArticleService;
import sa.com.me.article.util.JwtTokenProvider;
import sa.com.me.core.dto.ArticleDto;
import sa.com.me.core.exception.NotFoundException;
import sa.com.me.core.model.PagedResponse;
import sa.com.me.core.util.Action;
import sa.com.me.core.util.Constants;
import sa.com.me.core.util.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@Api(value = "Article Service")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping(value = "/api/v1/private/articles/{articleId}")
    @ApiOperation(value = "Retrieve Article info by id", response = ArticleDto.class)
    public ArticleDto getArticleById(@PathVariable Long articleId) {

        Optional<Article> article = articleService.findById(articleId);
        if (article.isPresent()) {
            return articleMapper.articleToDto(article.get());
        } else {
            throw new NotFoundException("Article not found", "404", articleId + "");
        }
    }

    @GetMapping(value = "/api/v1/private/articles")
    @ApiOperation(value = "Retrieve All Articles", response = ArticleDto.class, responseContainer = "List")
    public PagedResponse<ArticleDto> getMethodName(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = Constants.MAX_PAGE_SIZE + "") int size) {
        Page<Article> articles = articleService.findAll(page - 1, size);

        return new PagedResponse<>(articleMapper.articlesToDto(articles.getContent()), articles.getNumber(),
                articles.getSize(), articles.getTotalElements(), articles.getTotalPages(), articles.isLast());
    }

    @PostMapping(value = "/api/v1/private/articles")
    @ApiOperation(value = "Add Article", response = ArticleDto.class)
    public ArticleDto addArticle(@RequestBody @Valid ArticleDto articleDto,
            @RequestHeader("Authorization") String authorization) {

        articleService.validateAuthority(authorization, Resource.ARTICLE.getValue(), Action.POST.getValue());
        Article article = articleMapper.dtoToArticle(articleDto);
        return articleMapper.articleToDto(articleService.addArticle(article));
    }

    @PutMapping(value = "/api/v1/private/articles")
    @ApiOperation(value = "Update Article", response = ArticleDto.class)
    public ArticleDto updateArticle(@RequestBody ArticleDto articleDto,
            @RequestHeader("Authorization") String authorization) {

        articleService.validateAuthority(authorization, Resource.ARTICLE.getValue(), Action.POST.getValue());
        Article article = articleMapper.dtoToArticle(articleDto);
        return articleMapper.articleToDto(articleService.addArticle(article));
    }

    @DeleteMapping(value = "/api/v1/private/articles/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Article")
    public void deleteArticle(@PathVariable Long articleId, @RequestHeader("Authorization") String authorization) {

        articleService.validateAuthority(authorization, Resource.ARTICLE.getValue(), Action.POST.getValue());
        articleService.deleteArticle(articleId);
    }

}