package marsrover.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("nasa.client")
public class NasaClientProps {

    String dateFormat;
    String apiKey;
}
