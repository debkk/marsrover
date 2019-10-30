package marsrover.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.File;

@Data
public class Photo {
    private String id;
    @JsonProperty("earth_date")
    private String earthDate;
    @JsonProperty("img_src")
    private String imgSrc;
    private String sol;
    private Camera camera;
    private Rover rover;
}
