package marsrover.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marsrover.config.PhotoProps;
import marsrover.entity.Photo;
import marsrover.entity.Rover;
import marsrover.service.DateService;
import marsrover.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/photo")
@Slf4j
public class PhotoController {

    private final PhotoService photoService;
    private final DateService dateService;

    private final PhotoProps photoProps;

    private void seedPhotos() throws IOException {
        if (!StringUtils.isEmpty(photoProps.getSeedFile())) {
            // read in dates
            try (InputStream inputStream = getClass().getResourceAsStream(photoProps.getSeedFile());
                 Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
                stream.forEach(date -> {
                    Date earthDate = dateService.parseDate(date);
                    if (earthDate != null) {
                        try {
                            photoService.getPhotosByDate(Rover.RoverName.Curiosity, earthDate);
                            photoService.getPhotosByDate(Rover.RoverName.Opportunity, earthDate);
                            photoService.getPhotosByDate(Rover.RoverName.Spirit, earthDate);
                        } catch (IOException e) {
                            log.error("Unable to cache photos", e);
                        }
                    }
                });
            }

            // pre-load photos for those dates
        }
    }

    @GetMapping
    public ResponseEntity<List<Photo>> getPhotos(@RequestParam String earthDate,
                                 @RequestParam(required = false) Rover.RoverName rover) {
        List<Photo> photos;
        Date date = dateService.parseDate(earthDate);
        if (date == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid earthDate");
        }

        if (rover == null) {
            rover = Rover.RoverName.Curiosity;
        }

        try {
            photos = photoService.getPhotosByDate(rover, date);
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Unable to cache photos: " + e.getMessage());
        }

        return ResponseEntity.ok(photos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getCachedPhoto(@PathVariable String id, @RequestParam String imgSrc) {
        try {
            File file = photoService.getPhotoByUrl(imgSrc);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(Files.readAllBytes(file.toPath()));

        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Unable to get photo: " + e.getMessage());
        }
    }

    @PostMapping("/seed")
    public ResponseEntity reloadSeedList() {
        try {
            seedPhotos();
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Unable to seed photos: " + e.getMessage());
        }
    }

    @DeleteMapping
    public void clearCachedFiles() {
        photoService.clearCachedPhotos();
    }
}
