package com.selection.domain.tag;

import com.selection.domain.BaseEntity;
import com.selection.domain.article.Article;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "TAGS")
public class Tag extends BaseEntity {

    @Column(nullable = false, length = 10)
    private String name;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @Builder
    public Tag(String name, Article article) {
        this.name = name;
        this.article = article;
    }

}