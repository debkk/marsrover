package marsrover.entity;


/*
* FHAZ	Front Hazard Avoidance Camera	✔	✔	✔
RHAZ	Rear Hazard Avoidance Camera	✔	✔	✔
MAST	Mast Camera	✔
CHEMCAM	Chemistry and Camera Complex	✔
MAHLI	Mars Hand Lens Imager	✔
MARDI	Mars Descent Imager	✔
NAVCAM	Navigation Camera	✔	✔	✔
PANCAM	Panoramic Camera		✔	✔
MINITES	Miniature Thermal Emission Spectrometer (Mini-TES)		✔	✔
*/
public enum Camera {
    ;

    String CAM_FHAZ = "FHAZ";
    String CAM_RHAZ = "RHAZ";
    String CAM_MAST = "MAST";
    String CAM_CHEMCAM = "CHEMCAM";
    String CAM_MAHLI = "MAHLI";
    String CAM_MARDI = "MARDI";
    String CAM_NAVCAM = "NAVCAM";
    String CAM_PANCAM = "PANCAM";
    String CAM_MINITES = "MINITES";
}
