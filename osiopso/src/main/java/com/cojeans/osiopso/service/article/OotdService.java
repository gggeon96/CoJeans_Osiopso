package com.cojeans.osiopso.service.article;

import com.cojeans.osiopso.dto.request.feed.ArticleRequestDto;
import com.cojeans.osiopso.dto.request.feed.TagDto;
import com.cojeans.osiopso.dto.response.feed.ArticleResponseDto;
import com.cojeans.osiopso.entity.feed.Article;
import com.cojeans.osiopso.entity.feed.ArticlePhoto;
import com.cojeans.osiopso.entity.feed.ArticleTag;
import com.cojeans.osiopso.entity.feed.Ootd;
import com.cojeans.osiopso.entity.tag.Tag;
import com.cojeans.osiopso.repository.article.ArticleRepository;
import com.cojeans.osiopso.repository.article.ArticleTagRepository;
import com.cojeans.osiopso.repository.article.OotdRepository;
import com.cojeans.osiopso.repository.article.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class OotdService {

    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ArticleRepository articleRepository;
    private final OotdRepository ootdRepository;
    private final Converter converter;

    public List<ArticleResponseDto> listOotd() {
        List<Ootd> Ootds = ootdRepository.findList();
        List<ArticleResponseDto> articleRequestDtos = new ArrayList<>();

        for (Ootd ootd : Ootds) {
            ArticleResponseDto dto = ArticleResponseDto.builder()
                    .id(ootd.getId())
                    .hit(ootd.getHit())
                    .content(ootd.getContent())
                    .createTime(ootd.getCreateTime())
                    .dtype(ootd.getDtype())
                    .modifyTime(ootd.getModifyTime())
                    .userId(ootd.getUser().getId())
                    .build();

            articleRequestDtos.add(dto);
        }

        for (ArticleResponseDto articleResponseDto : articleRequestDtos) {
            System.out.println(articleResponseDto.toString());
        }

        return articleRequestDtos;
    }


    public ArticleResponseDto detailOotd(Long articleNo) {
        Ootd ootd = ootdRepository.findById(articleNo).orElseThrow();

        List<ArticleTag> articleTag = articleTagRepository.findByArticle_Id(ootd.getId());
        List<TagDto> list = new ArrayList<>();

        for (ArticleTag at : articleTag) {
            Tag tagE = tagRepository.findById(at.getTag().getId()).orElseThrow();
            list.add(TagDto.builder()
                    .type(tagE.getType())
                    .keyword(tagE.getKeyword())
                    .build());
        }


        return ArticleResponseDto.builder()
                .id(ootd.getId())
                .hit(ootd.getHit())
                .content(ootd.getContent())
                .createTime(ootd.getCreateTime())
                .dtype(ootd.getDtype())
                .modifyTime(ootd.getModifyTime())
                .tags(list)
                .userId(ootd.getUser().getId())
                .build();
    }


    public boolean editOotd(Long articleNo, ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.findById(articleNo).orElseThrow();

        // ========================= 태그수정 로직 ================================
        // 기존 태그 : 1, 2, 3 => 1, 2, 3, 4
        // 새로운 태그 : 2, 3, 4
        // 1. 새로운 태그를 돌리면서 기존태그에 없다면 추가한다.
        // 2. 기존 태그를 돌리면서 추가할 새로운 태그에 없다면 삭제한다.
        
        List<ArticleTag> articleTags = articleTagRepository.findByArticle_Id(article.getId());
        List<String> old_tags_keyword = new ArrayList<>();
        List<String> new_tags_keyword = new ArrayList<>();
        List<Tag> old_tags = new ArrayList<>();

        // 태그를 모두 삭제하려는 경우
        if (articleRequestDto.getTags().size() == 0) {
            List<ArticleTag> articleTag = articleTagRepository.findByArticle_Id(articleNo);

            for (ArticleTag at : articleTag) {
                System.out.println(at.getTag().getKeyword() + " 삭제!");
                articleTagRepository.deleteById(at.getId());
                tagRepository.deleteById(at.getTag().getId());
            }
            return true;
        }


        for (ArticleTag at : articleTags) {
            old_tags.add(tagRepository.findById(at.getId()).orElseThrow());
            old_tags_keyword.add(tagRepository.findById(at.getId()).orElseThrow().getKeyword());
        }


        
        for (TagDto new_tag : articleRequestDto.getTags()) {
            String keyword = new_tag.getKeyword();
            new_tags_keyword.add(keyword);

            // 1. 만약 기존 태그에 새로운 태그가 없는 경우 -> 저장
            if (!old_tags_keyword.contains(keyword)) {
                Tag tagE = new_tag.toEntity();
                // 기존 태그 리스트에 새로운 태그 추가
                old_tags.add(tagE);
                Tag tagSaved = tagRepository.save(tagE);

                ArticleTag articleTagE = ArticleTag.builder()
                        .article(article)
                        .tag(tagSaved)
                        .build();

                articleTagRepository.save(articleTagE);
            }
        }

        for (Tag old_tag : old_tags) {
            // 2. 만약 추가할 태그에, 추가한 기존 태그가 없는 경우 => 삭제
            if (!new_tags_keyword.contains(old_tag.getKeyword())) {
                articleTagRepository.deleteById(old_tag.getId());
                tagRepository.deleteById(old_tag.getId());
            }
        }

        // ========================= 사진수정 로직 ================================
        // 기존 사진 : 1, 2, 3 => 1, 2, 3, 4
        // 새로운 사진 : 2, 3, 4
        // 1. 새로운 사진을 돌리면서 기존사진에 없다면 추가한다.
        // 2. 기존 사진을 돌리면서 추가할 새로운 태그에 없다면 삭제한다.
        // 나중에 사진 업로드 완성되면 할 예정

        ArticleRequestDto editDto = ArticleRequestDto.builder()
            .dtype(articleRequestDto.getDtype())
            .content(articleRequestDto.getContent())
            .createTime(articleRequestDto.getCreateTime())
            .modifyTime(articleRequestDto.getModifyTime())
            .build();

        if (articleRepository.save(editDto.toEntity(article.getUser(), articleNo)) == null) {
            return false;
        } else {
            return true;
        }
    }
}
