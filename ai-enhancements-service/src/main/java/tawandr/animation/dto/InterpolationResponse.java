package tawandr.animation.dto;

import com.tawandr.utils.messaging.dtos.GenericResponse;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class InterpolationResponse extends GenericResponse {
    private byte[] data;
}
