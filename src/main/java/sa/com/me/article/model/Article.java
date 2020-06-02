package sa.com.me.article.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import sa.com.me.core.model.BaseModel;

@Entity
@Table(name = "articles")
@Data
public class Article extends BaseModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 324449846255358256L;

    private String title;
    @Column(length = 20000)
    private String content;
    private Date date;
    private String imageUrl;
    private String sourceUrl;
    private String summary;
    private String thumbnailTemplateUrl;
    private String thumbnailUrl;

}