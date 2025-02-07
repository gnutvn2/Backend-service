package vn.tungnv.backend_service.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.tungnv.backend_service.common.Gender;
import vn.tungnv.backend_service.common.UserStatus;
import vn.tungnv.backend_service.common.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class UserCreateRequest implements Serializable {
    @NotBlank(message = "firstName must be not blank")
    private String firstName;
    @NotBlank(message = "lastName must be not blank")
    private String lastName;
    @NotNull(message = "dateOfBirth must be not null")
    private Date dateOfBirth;
    @NotNull(message = "gender must be not null")
    private Gender gender;
    @NotBlank(message = "phone must be not blank")
    private String phone;
    @Email(message = "email invalid")
    private String email;
    @NotBlank(message = "userName must be not blank")
    private String username;
    @NotBlank(message = "password must be not blank")
    private String password;
    @NotNull(message = "userStatus must be not null")
    private UserStatus userStatus;
    @NotNull(message = "userType must be not null")
    private UserType userType;
    @NotNull(message = "address must be not null")
    private List<AddressRequest> addressRequests;
}
