package vn.tungnv.backend_service.controller.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class UserPageResponse extends PageResponseAbstract{
    private List<UserResponse> data;
}
