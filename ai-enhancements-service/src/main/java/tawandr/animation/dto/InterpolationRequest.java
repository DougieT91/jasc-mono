package tawandr.animation.dto;

import lombok.Data;

import java.util.List;

@Data
public class InterpolationRequest {
    private List<byte[]> images;
    private String interpolationMethod;
    private String AiModel;
}
