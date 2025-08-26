package com.group8.library_management.service;

import com.group8.library_management.dto.request.CreateCommentReq;
import com.group8.library_management.dto.response.CommentRes;

import java.util.List;

public interface CommentService {
    CommentRes createComment(CreateCommentReq request);

}
