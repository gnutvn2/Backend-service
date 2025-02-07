package vn.tungnv.backend_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Get name")
public class HelloWord {
    @Operation(summary = "Test get name")
    @GetMapping("/hello")
    public String getting(@RequestParam("name") String name){
        return "Name: " + name;
    }
}
