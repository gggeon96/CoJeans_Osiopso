package com.cojeans.osiopso.service.article;

import com.cojeans.osiopso.dto.request.comment.CommentRequestDto;
import com.cojeans.osiopso.entity.feed.Article;
import com.cojeans.osiopso.entity.feed.Comment;
import com.cojeans.osiopso.entity.user.User;
import com.cojeans.osiopso.repository.article.ArticleRepository;
import com.cojeans.osiopso.repository.article.CommentRepository;
import com.cojeans.osiopso.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public boolean createComment(CommentRequestDto dto, Long articleno, Long id) {
        User user = userRepository.findById(id).orElseThrow();
        Article article = articleRepository.findById(articleno).orElseThrow();

        Comment build = Comment.builder()
                .user(user)
                .content(dto.getContent())
                .article(article)
                .build();

        commentRepository.save(build);
        return true;
    }

    public boolean editComment(Long articleno, Long commentno, CommentRequestDto dto, Long userId) {
        Article article = articleRepository.findById(articleno).orElseThrow();

        // 게시글 작성자만 수정권한이 있다.
        if (userId != article.getUser().getId()) {
            return false;
        }

        Comment comment = commentRepository.findByIdAndArticle_Id(commentno, articleno);

        if (commentRepository.save(comment.builder()
                .id(comment.getId())
                .content(dto.getContent())
                .article(article)
                .user(comment.getUser())
                .build()) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteComment(Long commentno, Long articleno, Long userId) {
        Article article = articleRepository.findById(userId).orElseThrow();

        // 게시글 작성자만 삭제권한이 있다.
        if (userId != article.getUser().getId()) {
            return false;
        }

        commentRepository.deleteByIdAndArticle_Id(commentno, articleno);

        // 제대로 지워졌다면?
        if (commentRepository.findByIdAndArticle_Id(articleno, commentno) == null) {
            return true;
        } else {
            return false;
        }
    }
}
