package com.group8.library_management.service.impl;


import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.entity.Follow;
import com.group8.library_management.entity.FollowId;
import com.group8.library_management.entity.User;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.repository.FollowRepository;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;


    private String getMessage(String key, String defaultMsg) {
        return messageSource.getMessage(key, null, defaultMsg, LocaleContextHolder.getLocale());
    }


    @Override
    @Transactional
    public BaseAPIRes<Void> follow(String username, String targetType, Integer targetId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(getMessage("follow.error.user_not_found", "Không tìm thấy người dùng.")));
        Integer userId = user.getId();

        FollowId followId = new FollowId();
        followId.setUserId(userId);
        followId.setTargetId(targetId);
        followId.setTargetType(targetType);

        if (followRepository.existsById(followId)) {
            String msg = getMessage("follow.error.already_following", "Bạn đã theo dõi đối tượng này.");
            return BaseAPIRes.error(HttpStatus.BAD_REQUEST, msg);
        }

        try {
            Follow follow = new Follow();
            follow.setId(followId);
            followRepository.save(follow);
            String msg = getMessage("follow.success", "Theo dõi thành công.");
            return BaseAPIRes.success(HttpStatus.OK, msg, null);
        } catch (Exception ex) {
            String msg = getMessage("follow.error.internal", "Có lỗi hệ thống.");
            return BaseAPIRes.error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        }
    }

}
