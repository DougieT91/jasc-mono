package tawandr.animation.business;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LocalGifCreator {

    @SneakyThrows
    public byte[] createGif(List<byte[]> images) {
        log.info("Creating Gif animation using Local Renderer");
        return GifSequenceWriter.createGif(images);
    }
}
