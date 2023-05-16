package marsrover.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("mars.cache")
public class CacheProps {
    private String dir;
}
