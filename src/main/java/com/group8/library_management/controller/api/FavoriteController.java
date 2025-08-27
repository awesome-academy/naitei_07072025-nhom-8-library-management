package com.group8.library_management.controller.api;

import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.service.FavoriteService;
import com.group8.library_management.service.JwtService;
import com.group8.library_management.utils.GetMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/${api.version}/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private GetMessage getMessage;

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Integer bookId, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(BaseAPIRes.error(getMessage.msg("auth.token.missing")));
        }
        String token = authHeader.substring(7);
        Integer userId = jwtService.extractUserId(token);
        boolean removed = favoriteService.removeFavorite(userId, bookId);
        String msg = removed
            ? getMessage.msg("favorite.remove.success")
            : getMessage.msg("favorite.remove.notfound");
        return ResponseEntity.ok(BaseAPIRes.success(msg, null));
    }
}
