package com.group8.library_management.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentReq {
    private Integer book_id;
    private String comment;
    private Integer parent_comment_id;
}
