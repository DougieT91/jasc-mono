package tawandr.animation.api;

import com.tawandr.utils.crud.api.rest.resources.CrudResource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tawandr.animation.domain.Animation;

import java.security.Principal;
import java.util.List;

@Getter
@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimationController extends CrudResource<Animation> {
    Class<Animation> entityClass = Animation.class;

    @PostMapping("frames/{animationId}")
    public ResponseEntity<List<String>> createThin(@RequestBody List<String> frames, @PathVariable Long animationId, Principal principal) {
        return null;
    }

    @GetMapping("/status")
    public String status() {
        return "Animation Service is running";
    }
}


