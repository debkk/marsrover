package marsrover.entity;

import lombok.Data;

@Data
public class Rover {
    private RoverName name;

    public enum RoverName {
        Curiosity,
        Opportunity,
        Spirit
    }
}
