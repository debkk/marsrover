package marsrover.entity;

import lombok.Data;

@Data
public class Rover {
    private String id;
    private RoverName name;

    public enum RoverName {
        Curiosity,
        Opportunity,
        Spirit
    }
}
