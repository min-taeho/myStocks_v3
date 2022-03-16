package com.nuritech.stock.mystock.notice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nuritech.stock.mystock.common.domain.BaseTimeEntity;
import com.nuritech.stock.mystock.common.domain.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * Notice Entity
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notice extends BaseTimeEntity {

    private static final long serialVersionUID = 9141498463876264960L;

    @Comment("식별자")
    @ApiModelProperty("식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("제목")
    @ApiModelProperty("제목")
    @Column(length = 100)
    private String title;

    @Comment("내용")
    @ApiModelProperty("내용")
    @Column(length = 4000)
    private String contents;

    @Comment("분류")
    @ApiModelProperty("분류")
    @Column(length = 10)
    private String contentsType;

    @Builder
    public Notice(Integer id,
                  String title,
                  String contents,
                  String contentsType) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.contentsType = contentsType;
    }

    public void update(String title,
                       String contents,
                       String contentsType) {
        this.title = title;
        this.contents = contents;
        this.contentsType = contentsType;
    }
}
