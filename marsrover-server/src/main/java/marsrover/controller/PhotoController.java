package marsrover.controller;

import lombok.RequiredArgsConstructor;
import marsrover.entity.Photo;
import marsrover.service.DateService;
import marsrover.service.PhotoService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/photo")
public class PhotoController {

    private final PhotoService photoService;
    private final DateService dateService;

    @GetMapping
    public Photo getPhoto(@RequestParam String earthDate) {
        LocalDate date = dateService.parseLocalDate(earthDate);
        return photoService.getPhotoByDate(date);

    }
}
