package de.joachimsohn.web;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
final class TestController {

    @GetMapping("test")
    public @NotNull String test() {
        return "Hello World!";
    }
}
