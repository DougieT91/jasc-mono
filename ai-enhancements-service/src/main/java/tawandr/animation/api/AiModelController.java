package tawandr.animation.api;

import com.tawandr.utils.crud.api.rest.resources.CrudResource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tawandr.animation.business.InterpolationService;
import tawandr.animation.domain.AIModel;

import java.util.List;

@Getter
@Slf4j
@RestController
@RequiredArgsConstructor
public class AiModelController extends CrudResource<AIModel> {
    Class<AIModel> entityClass = AIModel.class;
}


