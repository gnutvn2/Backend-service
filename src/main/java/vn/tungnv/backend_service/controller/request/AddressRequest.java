package vn.tungnv.backend_service.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class AddressRequest implements Serializable {
    @NotBlank(message = "apartmentNumber must be not blank")
    private String apartmentNumber;
    @NotBlank(message = "floor must be not blank")
    private String floor;
    @NotBlank(message = "building must be not blank")
    private String building;
    @NotBlank(message = "streetNumber must be not blank")
    private String streetNumber;
    @NotBlank(message = "street must be not blank")
    private String street;
    @NotBlank(message = "city must be not blank")
    private String city;
    @NotBlank(message = "country must be not blank")
    private String country;
    @NotNull(message = "addressType must be not null")
    private Integer addressType;
}
