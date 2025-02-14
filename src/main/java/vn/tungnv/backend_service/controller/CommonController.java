package vn.tungnv.backend_service.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.tungnv.backend_service.controller.response.ApiResponse;
import vn.tungnv.backend_service.service.MailService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/${api.version}/common")
@Slf4j(topic = "SENDER_MAIL_CONTROLLER")
@RequiredArgsConstructor
public class CommonController {

    private final MailService mailService;

    @PostMapping("/send-email")
    public ApiResponse sendEmail(
            @RequestParam String recipients,
            @RequestParam String subject,
            @RequestParam String content,
            @RequestParam(required = false) MultipartFile[] files) throws MessagingException, UnsupportedEncodingException {
            return ApiResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Send email successfully")
                    .data(mailService.sendEmail(recipients, subject, content, files))
                    .build();

    }
}
