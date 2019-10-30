package marsrover.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * FHAZ - Front Hazard Avoidance Camera
 * RHAZ - Rear Hazard Avoidance Camera
 * MAST - Mast Camera
 * CHEMCAM - Chemistry and Camera Complex
 * MAHLI - Mars Hand Lens Imager
 * MARDI - Mars Descent Imager
 * NAVCAM - Navigation Camera
 * PANCAM - Panoramic Camera
 * MINITES - Miniature Thermal Emission Spectrometer (Mini-TES)
 */
@Data
public class Camera {
    private String id;
    private ShortName name;

    @JsonProperty("full_name")
    private String fullName;

    public enum ShortName {
        FHAZ,
        RHAZ,
        MAST,
        CHEMCAM,
        MAHLI,
        MARDI,
        NAVCAM,
        PANCAM,
        MINITES
    }
}
