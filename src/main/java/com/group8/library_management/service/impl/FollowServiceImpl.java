package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.entity.Follow;
import com.group8.library_management.entity.FollowId;
import com.group8.library_management.entity.User;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.repository.FollowRepository;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.FollowService;
import com.group8.library_management.utils.GetMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final GetMessage getMessage;

    @Override
    @Transactional
    public BaseAPIRes<Void> follow(String targetType, Integer targetId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(getMessage.msg(
                        "follow.error.user_not_found", "Không tìm thấy người dùng.")));
        Integer userId = user.getId();

        FollowId followId = new FollowId();
        followId.setUserId(userId);
        followId.setTargetId(targetId);
        followId.setTargetType(targetType);

        if (followRepository.existsById(followId)) {
            String msg = getMessage.msg("follow.error.already_following", "Bạn đã theo dõi đối tượng này.");
            return BaseAPIRes.error(HttpStatus.BAD_REQUEST, msg);
        }

        try {
            Follow follow = new Follow();
            follow.setId(followId);
            followRepository.save(follow);
            String msg = getMessage.msg("follow.success", "Theo dõi thành công.");
            return BaseAPIRes.success(HttpStatus.OK, msg, null);
        } catch (Exception ex) {
            String msg = getMessage.msg("follow.error.internal", "Có lỗi hệ thống.");
            return BaseAPIRes.error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        }
    }
}
