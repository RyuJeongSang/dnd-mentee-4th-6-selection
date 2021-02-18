package com.selection.service.article;


import com.selection.domain.article.Article;
import com.selection.dto.PageRequest;
import com.selection.dto.article.ArticleLatestResponse;
import com.selection.dto.article.ArticleRequest;
import com.selection.dto.article.ArticleResponse;
import com.selection.repository.ArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article findById(Long articleId) {
        return articleRepository.findById(articleId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("해당 게시글(%s)는 존재하지 않습니다.", articleId)));
    }

    @Transactional
    public Long create(String author, ArticleRequest requestDto) {
        Article article = articleRepository.save(requestDto.toEntity(author));
        return article.getId();
    }

    @Transactional
    public Long modify(Long articleId, ArticleRequest requestDto) {
        Article article = findById(articleId);

        article.modifyTitle(requestDto.getTitle());
        article.modifyContent(requestDto.getContent());
        article.modifyChoices(requestDto.getChoices());

        return article.getId();
    }

    @Transactional
    public ArticleResponse lookUp(Long id) {
        return new ArticleResponse(findById(id));
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.delete(findById(id));
    }

    @Transactional
    public List<ArticleLatestResponse> lookUpLatest(Long numOfLatestArticles) {
        PageRequest latest = new PageRequest(1, numOfLatestArticles.intValue(), Direction.DESC);
        Page<Article> latestArticles = articleRepository.findAll(latest.of());

        return latestArticles.stream()
            .map(ArticleLatestResponse::new)
            .collect(Collectors.toList());
    }
}
