package tawandr.animation.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tawandr.animation.dto.InterpolationRequest;
import tawandr.animation.dto.InterpolationResponse;
import tawandr.animation.integration.CloudConvertService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterpolationServiceTest {


    @Mock
    private CloudConvertService cloudConvertService;
    @Mock
    private LocalGifCreator localConvertService;

    private InterpolationRequest request;
    private byte[] image1;
    private byte[] image2;
    private byte[] expectedGif;
    private List<byte[]> imagesList;
    private String interpolationMethod;
    private String aiModel;

    @InjectMocks
    private InterpolationService interpolationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        image1 = new byte[]{1, 2, 3};
        image2 = new byte[]{4, 5, 6};
        expectedGif = new byte[]{7, 8, 9};

        imagesList = List.of(image1, image2);
        interpolationMethod = "AI (Cloud Service)";
        aiModel = "Model B";

        request = new InterpolationRequest();
        request.setImages(imagesList);
        request.setInterpolationMethod(interpolationMethod);
        request.setAiModel(aiModel);


        when(cloudConvertService.processImages(any())).thenReturn(expectedGif);
        when(localConvertService.createGif(any())).thenReturn(expectedGif);
    }

    @Test
    void interpolateShouldGracefullyFailIfEmptyImagesListIsProvided() {
        request.setImages(Collections.emptyList());
        InterpolationResponse response = interpolationService.interpolate(request);

        assertThat(response.isSuccess()).isFalse();
        assertThat("No Images to interpolate").isEqualTo(response.getMessage());
    }

    @Test
    void interpolateShouldCallCloudServiceIfLocalMethodIsRequested() {
        InterpolationResponse response = interpolationService.interpolate(request);

        assertTrue(response.isSuccess());
        assertArrayEquals(expectedGif, response.getData());
        assertNull(response.getMessage());
        verify(cloudConvertService, times(1)).processImages(anyList());
    }

    @Test
    void interpolateShouldNotCallLocalServiceIfLocalMethodIsRequested() {
        InterpolationResponse response = interpolationService.interpolate(request);

        assertTrue(response.isSuccess());
        assertArrayEquals(expectedGif, response.getData());
        assertNull(response.getMessage());
        verify(localConvertService, never()).createGif(anyList());
    }

    @Test
    void interpolateShouldCallLocalServiceIfLocalMethodIsRequested() {
        request.setInterpolationMethod("Local (Device)");

        InterpolationResponse response = interpolationService.interpolate(request);

        assertTrue(response.isSuccess());
        assertArrayEquals(expectedGif, response.getData());
        assertNull(response.getMessage());
        verify(localConvertService, times(1)).createGif(anyList());
    }

    @Test
    void interpolateShouldNotCallCloudServiceIfLocalMethodIsRequested() {
        request.setInterpolationMethod("Local (Device)");

        InterpolationResponse response = interpolationService.interpolate(request);

        assertTrue(response.isSuccess());
        assertArrayEquals(expectedGif, response.getData());
        assertNull(response.getMessage());
        verify(cloudConvertService, never()).processImages(anyList());
    }


    @Test
    void testInterpolateWithException() {
        RuntimeException expectedException = new RuntimeException("Test exception");

        doThrow(expectedException).when(cloudConvertService).processImages(anyList());

        final RuntimeException rte = assertThrows(RuntimeException.class,
                () -> interpolationService.interpolate(request));

        assertThat(rte.getMessage()).isEqualTo(expectedException.getMessage());
    }
}