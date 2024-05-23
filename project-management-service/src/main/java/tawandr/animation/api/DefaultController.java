package tawandr.animation.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Slf4j
@RestController
@RequiredArgsConstructor
public class DefaultController {
    @GetMapping("/status")
    public String status() {
        return "AI Based Enhancements Service is running";
    }
}


