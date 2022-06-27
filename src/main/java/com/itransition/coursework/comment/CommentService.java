package com.itransition.coursework.comment;

import com.itransition.coursework.item.Item;
import com.itransition.coursework.item.ItemRepository;
import com.itransition.coursework.user.User;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;

import static com.itransition.coursework.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/27/2022 2:07 PM
 */

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    public ThymeleafResponse editComment(Long id, String commentBody) {
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(COMMENT_NOT_FOUND));
            comment.setBody(commentBody);
            commentRepository.save(comment);
            return new ThymeleafResponse(true, COMMENT_SAVED);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getCause().toString());
        }

    }

    public ThymeleafResponse addNewComment(String commentBody, Long id, User currentUser) {
        try {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(ITEM_NOT_FOUND));

            Comment comment = Comment
                    .builder()
                    .body(commentBody)
                    .commentedAt(LocalDateTime.now())
                    .commentedBy(currentUser)
                    .item(item)
                    .build();
            commentRepository.save(comment);
            return new ThymeleafResponse(true, COMMENT_SAVED);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getCause().toString());
        }
    }

    public ThymeleafResponse deleteComment(Long id) {

        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(COMMENT_NOT_FOUND));
            commentRepository.delete(comment);
            return new ThymeleafResponse(true, COMMENT_DELETED);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getCause().toString());
        }

    }
}
