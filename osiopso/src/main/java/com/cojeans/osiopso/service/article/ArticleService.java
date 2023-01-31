package com.cojeans.osiopso.service.article;

import com.cojeans.osiopso.dto.feed.ArticleDto;
import com.cojeans.osiopso.dto.feed.ArticlePhotoDto;
import com.cojeans.osiopso.dto.feed.ArticleTagDto;
import com.cojeans.osiopso.entity.feed.Advice;
import com.cojeans.osiopso.entity.feed.Article;
import com.cojeans.osiopso.entity.feed.ArticlePhoto;
import com.cojeans.osiopso.entity.feed.ArticleTag;
import com.cojeans.osiopso.entity.user.User;
import com.cojeans.osiopso.repository.article.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;

    public boolean createArticle(ArticleDto articleDto) {
        // 추후에 로그인 기능이 완성된다면 어떤식으로 유저 정보(JWT) 를 받아올지?
        User token = new User();

        articleRepository.save(articleDto.toEntity(token, 0L));
        tagRepository.save(articleDto.toTagEntity());
        articleTagRepository.save(articleDto.toArticleTag());




        return true;
//        if (articleRepository.save(articleDto.toEntity(token, 0L)) == null) {
//            return false;
//        } else {
//            return true;
//        }
    }

    public boolean deleteArticle(Long articleNo) {
        articleRepository.deleteById(articleNo);

        Optional<Article> byId = articleRepository.findById(articleNo);
        System.out.println(byId);
        // 확실히 지워진 경우 (삭제한 articleNo로 해당 게시물을 찾을 수 없어야 한다.)
        if (articleRepository.findById(articleNo).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
