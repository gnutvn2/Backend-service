package vn.tungnv.backend_service.service;

import vn.tungnv.backend_service.common.UserStatus;
import vn.tungnv.backend_service.controller.request.UserCreateRequest;
import vn.tungnv.backend_service.controller.request.UserUpdateRequest;
import vn.tungnv.backend_service.controller.response.UserPageResponse;
import vn.tungnv.backend_service.controller.response.UserResponse;

public interface UserService {
    UserPageResponse getAll(String keyword, String sort, int page, int size );
    UserResponse findUserById(long id);
    Long createUser(UserCreateRequest userCreateRequest);
    void updateUser(UserUpdateRequest updateRequest);
    void changeStatusUser(long id, UserStatus status);
    void deleteUser(long id);
}
