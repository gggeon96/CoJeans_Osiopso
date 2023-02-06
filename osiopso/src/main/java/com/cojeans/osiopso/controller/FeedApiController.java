package com.cojeans.osiopso.controller;

import com.cojeans.osiopso.dto.ApiResponseDto;
import com.cojeans.osiopso.dto.request.comment.CommentRequestDto;
import com.cojeans.osiopso.dto.request.feed.AdviceRequestDto;
import com.cojeans.osiopso.dto.request.feed.OotdRequestDto;
import com.cojeans.osiopso.dto.response.feed.*;
import com.cojeans.osiopso.security.UserDetail;
import com.cojeans.osiopso.service.article.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
@Tag(name = "게시글 관련 API")
public class FeedApiController {
//    private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";

    private final AdviceService adviceService;
    private final OotdService ootdService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final LikeService likeService;



    // ====================== CREATE ========================
    @PostMapping("/advice")
    public ResponseEntity<?> createAdvice(@RequestPart("advice") AdviceRequestDto adviceRequestDto,
                                          @RequestPart("picture") List<MultipartFile> picture,
                                          @AuthenticationPrincipal UserDetail user
                                          ) {

        if (adviceService.createAdvice(adviceRequestDto, picture, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "createArticle Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "createArticle Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/ootd")
    public ResponseEntity<?> createOotd(@RequestBody OotdRequestDto ootdRequestDto,
                                        @AuthenticationPrincipal UserDetail user) {

        if (ootdService.createOotd(ootdRequestDto, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "createArticle Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "createArticle Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/{articleno}/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable("articleno") Long articleNo, @AuthenticationPrincipal UserDetail user){
        if (commentService.createComment(commentRequestDto, articleNo, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "createComment Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "createComment Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/{articleno}/likearticle")
    public ResponseEntity<?> createArticleLike(@PathVariable("articleno") Long articleNo, @AuthenticationPrincipal UserDetail user){
        if (likeService.createArticleLike(articleNo, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "createLike Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "createLike Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/{commentno}/likecomment")
    public ResponseEntity<?> createCommentLike(@PathVariable("commentno") Long commentNo, @AuthenticationPrincipal UserDetail user){
        if (likeService.createCommentLike(commentNo, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "createCommentLike Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "createCommentLike Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }



    // ====================== READ ========================
    @GetMapping("/advice")
    public ResponseEntity<List<AdviceListResponseDto>> listAdivce() {
        return new ResponseEntity(new ApiResponseDto(true, "readAdviceList Success", adviceService.listAdvice()), HttpStatus.OK);
    }


    @GetMapping("/advice/{articleno}")
    public ResponseEntity<AdviceDetailResponseDto> detailAdvice(@PathVariable("articleno") Long articleNo) {
        AdviceDetailResponseDto detail = adviceService.detailAdvice(articleNo);
        return new ResponseEntity(new ApiResponseDto(true, "readAdviceDetail Success", detail), HttpStatus.OK);
    }


    @GetMapping("/ootd")
    public ResponseEntity<List<OotdListResponseDto>> listOotd() {
        return new ResponseEntity(new ApiResponseDto(true, "readOotdList Success", ootdService.listOotd()), HttpStatus.OK);
    }


    @GetMapping("/ootd/{articleno}")
    public ResponseEntity<OotdDetailResponseDto> detailOotd(@PathVariable("articleno") Long articleNo) {
        OotdDetailResponseDto detail = ootdService.detailOotd(articleNo);
        return new ResponseEntity(new ApiResponseDto(true, "readOotdDetail Success", detail), HttpStatus.OK);
    }


    // ====================== UPDATE ========================
    @PutMapping("/ootd/{articleno}")
    public ResponseEntity<?> editOotd(@PathVariable("articleno") Long articleNo, @RequestBody OotdRequestDto ootdRequestDto, @AuthenticationPrincipal UserDetail user) {
        if (ootdService.editOotd(articleNo, ootdRequestDto, user.getId())){
            return new ResponseEntity(new ApiResponseDto(true, "editOotd Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "editOotd Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/advice/{articleno}")
    public ResponseEntity<?> editAdvice(@PathVariable("articleno") Long articleNo, @RequestBody AdviceRequestDto adviceRequestDto, @AuthenticationPrincipal UserDetail user) {
        if (adviceService.editAdvice(articleNo, adviceRequestDto, user.getId())){
            return new ResponseEntity(new ApiResponseDto(true, "editAdvice Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "editAdvice Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{articleno}/comment/{commentno}")
    public ResponseEntity<?> editComment(@PathVariable("articleno") Long articleNo, @PathVariable("commentno") Long commentNo,
                                              @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetail user){
        if (commentService.editComment(articleNo, commentNo, commentRequestDto, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "editComment Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "editComment Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }



    // ====================== DELETE ========================
    @DeleteMapping("/article/{articleno}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleno") Long articleNo, @AuthenticationPrincipal UserDetail user) {
        if (articleService.deleteArticle(articleNo, user.getId())) {
            return new ResponseEntity(new ApiResponseDto(true, "deleteArticle Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "deleteArticle Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{articleno}/comment/{commentno}")
    public ResponseEntity<?> deleteComment(@PathVariable("articleno") Long articleNo, @PathVariable("commentno") Long commentNo,
                                                @AuthenticationPrincipal UserDetail user){
        if (commentService.deleteComment(articleNo, commentNo, user.getId())){
            return new ResponseEntity(new ApiResponseDto(true, "deleteComment Success", null), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponseDto(false, "deleteComment Fail", null), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}