package com.group8.library_management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFavoriteReq {
    @NotNull
    private Integer bookId;
}
