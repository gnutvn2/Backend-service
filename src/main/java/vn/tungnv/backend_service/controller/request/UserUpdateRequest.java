package vn.tungnv.backend_service.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.tungnv.backend_service.common.Gender;
import vn.tungnv.backend_service.common.UserStatus;
import vn.tungnv.backend_service.common.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserUpdateRequest implements Serializable {
    @NotNull(message = "id must be not null")
    @Min(value = 1, message = "userId must be equals or greater than 1")
    private Long id;
    @NotBlank(message = "firstName must be not blank")
    private String firstName;
    @NotBlank(message = "lastName must be not blank")
    private String lastName;
    @NotNull(message = "dateOfBirth must be not null")
    private Date dateOfBirth;
    @NotNull(message = "gender must be not null")
    private Gender gender;
    @NotNull(message = "userStatus must be not null")
    private UserStatus userStatus;
    @NotNull(message = "userType must be not null")
    private UserType userType;
    private List<AddressRequest> addressRequests;
}
