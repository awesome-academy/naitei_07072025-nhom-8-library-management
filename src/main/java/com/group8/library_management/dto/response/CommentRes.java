package com.group8.library_management.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRes {
    private Integer id;
    private String content;
    private String userName;
    private Integer parentCommentId;
    private LocalDateTime createdAt;
}
