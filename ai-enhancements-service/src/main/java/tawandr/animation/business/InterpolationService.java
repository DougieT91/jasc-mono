package tawandr.animation.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class InterpolationService {
    public byte[] interpolate(List<String> images) {
        log.debug("interpolating images using cloud convert api");
        // Todo: Implement
        if(CollectionUtils.isEmpty(images)){
            return "No Images to interpolate".getBytes();
        } else {
            return String.format("Interpolated %d images", images.size()).getBytes();
        }
    }
}
