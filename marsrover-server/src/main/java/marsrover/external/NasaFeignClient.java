package marsrover.external;

import marsrover.entity.Photo;
import marsrover.entity.Rover;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.List;

@FeignClient(value = "nasa", url = "https://api.nasa.gov/mars-photos/api/v1")
public interface NasaFeignClient {

    @GetMapping(value = "/rovers/{roverName}/photos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Photo> getPhotosByEarthDate(@PathVariable Rover.RoverName roverName,
                                     @RequestParam(name = "api_key") String apiKey,
                                     @RequestParam(name = "earth_date") String earthDate);

    @GetMapping(value = "{imgSrc}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    InputStream downloadPhoto(@PathVariable String imgSrc);
}
