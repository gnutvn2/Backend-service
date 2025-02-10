package vn.tungnv.backend_service.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SignInRequest implements Serializable {
    private String username;
    private String password;
    private String platform; //web, mobile, miniApp
    private String deviceToken;
    private String versionApp;
}
