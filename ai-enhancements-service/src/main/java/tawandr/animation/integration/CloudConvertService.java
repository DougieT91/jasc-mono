package tawandr.animation.integration;

import com.cloudconvert.client.CloudConvertClient;
import com.cloudconvert.client.setttings.StringSettingsProvider;
import com.cloudconvert.dto.request.ConvertFilesTaskRequest;
import com.cloudconvert.dto.request.UploadImportRequest;
import com.cloudconvert.dto.request.UrlExportRequest;
import com.cloudconvert.dto.request.UrlImportRequest;
import com.cloudconvert.dto.response.JobResponse;
import com.cloudconvert.dto.response.TaskResponse;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import tawandr.animation.business.GifSequenceWriter;

import java.io.*;
import java.util.List;

@Slf4j
@Component
public class CloudConvertService {

    private final CloudConvertClient client;

    public CloudConvertService() throws IOException {
        client = new CloudConvertClient(
                new StringSettingsProvider(
                        "api-key",
                        "webhook-signing-secret",
                        false)
        );
    }


    public byte[] processImages(List<byte[]> images) {
        try {
            return GifSequenceWriter.createGif(images);
        } catch (Exception e) {
            final String errorMessage = String.format("An Error occurred while attempting to process images. Error: %s"
                    , e.getMessage());
            log.error(errorMessage);
            
            throw new RuntimeException(errorMessage);
        }
    }

    @SneakyThrows
    public void uploadImage(byte[] image) {
        // byte array as inputStream
        InputStream inputStream = new ByteArrayInputStream(image);


        // Upload file using import/upload task
        final TaskResponse uploadImportTaskResponse = client
                .importUsing()
                .upload(new UploadImportRequest(), inputStream, "file.jpg").getBody();

        // Wait for import/upload task to be finished
        final TaskResponse waitUploadImportTaskResponse = client
                .tasks()
                .wait(uploadImportTaskResponse.getId())
                .getBody();

        // Get task id of import/upload task
        final String importTaskId = waitUploadImportTaskResponse.getId();
    }

    public void convertImage() {
//        String inputFile = "path/to/your/image.jpg";
//        String outputFile = "path/to/your/output.jpg";
//        String outputFormat = "png";
//
//        TaskResponse taskResponse = client.tasks().convert(inputFile, outputFile, outputFormat);
//        String taskID = taskResponse.getResult().getTaskId();
    }

    @SneakyThrows
    public byte[] downloadImage(String exportUrlTaskId) {
        // Wait for an export/url task to be finished
        final TaskResponse waitUrlExportTaskResponse = client.tasks().wait(exportUrlTaskId).getBody();

        // Get url and filename of export/url task
        final String exportUrl = waitUrlExportTaskResponse.getResult().getFiles().get(0).get("url");
        final String filename = waitUrlExportTaskResponse.getResult().getFiles().get(0).get("filename");

        // Get file as input stream using url of export/url task
        final InputStream inputStream = client.files().download(exportUrl).getBody();

        if (inputStream == null) {
            throw new RuntimeException("File not found");
        }
        return inputStream.readAllBytes();
    }


    public void interpolateImages(String[] args) {
    }

    public void createGifFromMultiplePictures(String[] args) {

//        // Define the input files
//        String[] inputFiles = {"path/to/your/image1.jpg", "path/to/your/image2.jpg", "path/to/your/image3.jpg"};
//
//        // Define the output file
//        String outputFile = "path/to/your/output.gif";
//
//        // Create a task to convert the images to a GIF
//        TaskResponse taskResponse = client.tasks().convert(inputFiles, outputFile, "gif");
//
//        // Get the task ID
//        String taskID = taskResponse.getResult().getTaskId();
//
//        // Wait for the task to complete
//        TaskResponse taskResponse2 = client.tasks().wait(taskID);
//
//        // Download the output file
//        TaskResponse taskResponse3 = client.tasks().download(taskID);
//        String downloadUrl = taskResponse3.getResult().getFiles().get(0).get("url");
//        String filename = taskResponse3.getResult().getFiles().get(0).get("filename");

        // Download the file using the download URL
        // ...
    }

    @SneakyThrows
    public void createGenericJob() {
        // Create a client
        final CloudConvertClient cloudConvertClient = new CloudConvertClient();

        // Create a job
        final JobResponse createJobResponse = cloudConvertClient
                .jobs()
                .create(
                ImmutableMap.of(
                        "import-my-file", new UrlImportRequest().setUrl("import-url"),
                        "convert-my-file", new ConvertFilesTaskRequest()
                                .setInput("import-my-file")
                                .set("width", 100)
                                .set("height", 100),
                        "export-my-file", new UrlExportRequest().setInput("convert-my-file")
                )
        ).getBody();

        // Get a job id
        final String jobId = createJobResponse.getId();

        // Wait for a job completion
        final JobResponse waitJobResponse = cloudConvertClient.jobs().wait(jobId).getBody();

        // Get an export/url task id
        final String exportUrlTaskId = waitJobResponse.getTasks().stream().filter(taskResponse -> taskResponse.getName().equals("export-my-file")).findFirst().get().getId();
    }


}
