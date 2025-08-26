package com.group8.library_management.service;


import com.group8.library_management.dto.response.BaseAPIRes;


public interface FollowService {
    BaseAPIRes<Void> follow(String username, String targetType, Integer targetId);
}
