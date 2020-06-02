package sa.com.me.article.model;

import lombok.Data;

@Data
public class Authority {

    private Long id;

    private String roleId;
    private String resource;
    private String action;
}