package com.group8.library_management.controller.api;


import com.group8.library_management.dto.request.FollowRequest;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/${api.version}/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;


    @PostMapping
    public ResponseEntity<BaseAPIRes<Void>> follow(
            @RequestBody FollowRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        BaseAPIRes<Void> res = followService.follow(username, req.getTargetType(), req.getTargetId());
        return ResponseEntity.status(res.getCode()).body(res);
    }

}
