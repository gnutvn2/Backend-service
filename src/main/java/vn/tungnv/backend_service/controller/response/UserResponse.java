package vn.tungnv.backend_service.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDay;
    private String username;
    private String email;
    private String phone;
}
