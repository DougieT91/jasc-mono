package tawandr.animation.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tawandr.animation.dto.InterpolationRequest;
import tawandr.animation.dto.InterpolationResponse;
import tawandr.animation.integration.CloudConvertService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterpolationService {
    private final CloudConvertService cloudConvertService;
    private final LocalGifCreator localConvertService;
    public InterpolationResponse interpolate(InterpolationRequest request) {
        log.debug("interpolating images using cloud convert api");

        final List<byte[]> images = request.getImages();


        if(CollectionUtils.isEmpty(images)){
            final InterpolationResponse response = new InterpolationResponse(null);
            response.setMessage("No Images to interpolate");
            response.setSuccess(false);
            return response;
        }

        byte[] gif;
        if(StringUtils.hasText(request.getInterpolationMethod()) && request.getInterpolationMethod().contains("Local")){
            try {
                gif = localConvertService.createGif(images);
            } catch (Exception e) {
                final String errorMessage = String.format("An Error occurred while attempting to process images. Error: %s"
                        , e.getMessage());
                log.error(errorMessage);

                throw new RuntimeException(errorMessage);
            }
        } else {
            gif = cloudConvertService.processImages(images);
        }

        log.info("Interpolated {} images", images.size());
        return new InterpolationResponse(gif);
    }
}
