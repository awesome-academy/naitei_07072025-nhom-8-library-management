package com.group8.library_management.service.impl;

import com.group8.library_management.dto.request.CreateCommentReq;
import com.group8.library_management.dto.response.CommentRes;
import com.group8.library_management.entity.Book;
import com.group8.library_management.entity.BookComment;
import com.group8.library_management.entity.User;
import com.group8.library_management.exception.CommentException;
import com.group8.library_management.repository.BookCommentRepository;
import com.group8.library_management.repository.BookRepository;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.BorrowRecordService;
import com.group8.library_management.service.CommentService;
import com.group8.library_management.utils.GetMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookCommentRepository bookCommentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowRecordService borrowRecordService;
    private final GetMessage getMessage;

    @Override
    public CommentRes createComment(CreateCommentReq request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CommentException(getMessage.msg("comment.error.user_not_found")));


        Book book = bookRepository.findById(Integer.valueOf(request.getBook_id()))
                .orElseThrow(() -> new CommentException(getMessage.msg("comment.error.book_not_found")));

        // Check if user has borrowed this book before
        if (!borrowRecordService.hasUserBorrowedBook(username, request.getBook_id())) {
            throw new CommentException(getMessage.msg("comment.error.not_borrowed"));
        }


        BookComment comment = new BookComment();
        comment.setUser(user);
        comment.setBook(book);
        comment.setContent(request.getComment());

        // Set parent comment if provided
        if (request.getParent_comment_id() != null) {
            BookComment parentComment = bookCommentRepository.findById(request.getParent_comment_id())
                    .orElseThrow(() -> new CommentException(getMessage.msg("comment.error.parent_not_found")));
            comment.setParentComment(parentComment);
        }

        // Save comment
        BookComment savedComment = bookCommentRepository.save(comment);

        // Return response
        return CommentRes.builder()
                .id(savedComment.getId())
                .content(savedComment.getContent())
                .userName(savedComment.getUser().getFullName())
                .parentCommentId(
                        savedComment.getParentComment() != null ? savedComment.getParentComment().getId() : null)
                .createdAt(savedComment.getCreatedAt())
                .build();
    }

}
