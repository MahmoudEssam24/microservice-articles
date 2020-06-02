package sa.com.me.article.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import sa.com.me.article.model.Article;
import sa.com.me.core.dto.ArticleDto;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article dtoToArticle(ArticleDto articleDto);
    ArticleDto articleToDto(Article article);

    @IterableMapping(elementTargetType = ArticleDto.class)
    List<ArticleDto> articlesToDto(List<Article> articles);
}