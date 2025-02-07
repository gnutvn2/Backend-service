package vn.tungnv.backend_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.tungnv.backend_service.common.UserStatus;
import vn.tungnv.backend_service.controller.request.UserCreateRequest;
import vn.tungnv.backend_service.controller.request.UserUpdateRequest;
import vn.tungnv.backend_service.controller.response.ApiResponse;
import vn.tungnv.backend_service.controller.response.UserPageResponse;
import vn.tungnv.backend_service.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/user")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all user", description = "Description")
    @GetMapping("/list")
    public ApiResponse getAllUsers(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) String sort,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Get all users");

        UserPageResponse userResponseList = userService.getAll(keyword, sort, page, size);
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all users successfully")
                .data(userResponseList)
                .build();

    }

    @PostMapping
    public ApiResponse createUser(@RequestBody @Valid UserCreateRequest request){
        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(this.userService.createUser(request))
                .build();
    }

    @PutMapping
    public ApiResponse putUser(@RequestBody @Valid UserUpdateRequest request){
        this.userService.updateUser(request);
        return  ApiResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("User update successfully")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse updateUser(@PathVariable("id") Long id, @RequestParam UserStatus status){
        this.userService.changeStatusUser(id, status);
        return ApiResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Change status user successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable("id") Long id){
        this.userService.deleteUser(id);
        return ApiResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Delete user successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse getUserById(@PathVariable("id") Long id){
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get user by id: " + id)
                .data(this.userService.findUserById(id))
                .build();
    }

}
