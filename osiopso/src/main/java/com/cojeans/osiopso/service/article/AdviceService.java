package com.cojeans.osiopso.service.article;

import com.cojeans.osiopso.dto.request.feed.ArticleRequestDto;
import com.cojeans.osiopso.dto.response.feed.AdviceListResponseDto;
import com.cojeans.osiopso.dto.response.feed.ArticleDetailResponseDto;
import com.cojeans.osiopso.dto.response.feed.ArticlePhotoResponseDto;
import com.cojeans.osiopso.entity.feed.Advice;
import com.cojeans.osiopso.entity.feed.Article;
import com.cojeans.osiopso.entity.feed.ArticlePhoto;
import com.cojeans.osiopso.repository.article.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class AdviceService {

    private final ArticleRepository articleRepository;
    private final AdviceRepository adviceRepository;
    private final ArticlePhotoRepository photoRepository;

    public List<AdviceListResponseDto> listAdvice() {
        List<Advice> Advices = adviceRepository.findList();
        List<AdviceListResponseDto> list = new ArrayList<>();

        for (Advice advice : Advices) {
            AdviceListResponseDto dto = AdviceListResponseDto.builder()
                    .build();
            list.add(dto);
        }

        for (AdviceListResponseDto response : list) {
            System.out.println(response.toString());
        }

        return list;
    }



    // 1. param 으로 훈수 찾아오기
    // 2. 훈수 게시물 Id로 articleTag 찾아오기
    // 3. articleTag iterator 돌려서 id로 keyword
    public ArticleDetailResponseDto detailAdvice(Long articleNo) {
        Advice advice = adviceRepository.findById(articleNo).orElseThrow();

        // 사진 가져오기
        List<ArticlePhoto> photoEntityList = photoRepository.findAllById(advice.getId());
        List<ArticlePhotoResponseDto> photoResponseDtoList = new ArrayList<>();

        for (ArticlePhoto ap : photoEntityList) {
            photoResponseDtoList.add(ArticlePhotoResponseDto.builder()
                    .originFilename(ap.getOriginFilename())
                    .storeFilename(ap.getStoreFilename())
                    .build());
        }

        return ArticleDetailResponseDto.builder()
                .id(advice.getId())
                .hit(advice.getHit())
                .content(advice.getContent())
                .createTime(advice.getCreateTime())
                .dtype(advice.getDtype())
                .photos(photoResponseDtoList)
                .modifyTime(advice.getModifyTime())
                .userId(advice.getUser().getId())
                .isSelected(advice.isSelected())
                .subject(advice.getSubject())
                .build();
    }


    public boolean editAdvice(Long articleNo, ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.findById(articleNo).orElseThrow();

        ArticleRequestDto editDto = ArticleRequestDto.builder()
                .dtype(articleRequestDto.getDtype())
                .content(articleRequestDto.getContent())
                .createTime(articleRequestDto.getCreateTime())
                .modifyTime(articleRequestDto.getModifyTime())
                .isSelected(articleRequestDto.isSelected())
                .subject(articleRequestDto.getSubject())
                .build();

        if (articleRepository.save(editDto.toEntity(article.getUser(), articleNo)) == null) {
            return false;
        } else {
            return true;
        }
    }

}