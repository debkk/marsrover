package marsrover.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("mars.photo")
public class PhotoProps {
    private String seedFile;
}
