package tawandr.animation.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tawandr.animation.business.InterpolationService;
import tawandr.animation.dto.InterpolationRequest;
import tawandr.animation.dto.InterpolationResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
@RestController
@RequiredArgsConstructor
public class DefaultController {

    private final InterpolationService interpolationService;
    @GetMapping("/status")
    public String status() {
        return "AI Based Enhancements Service is running";
    }
    
    @PostMapping("interpolate")
    public ResponseEntity<InterpolationResponse> interpolate(@RequestBody InterpolationRequest request){
        log.debug("received images for interpolation: \n{}", request.toString());
        ResponseEntity<InterpolationResponse> responseEntity = ResponseEntity.ok(interpolationService.interpolate(request));
        log.debug("Interpolation complete");
        return responseEntity;
    }

}


