package marsrover.external;

import com.fasterxml.jackson.databind.DeserializationFeature;
import lombok.RequiredArgsConstructor;
import marsrover.entity.Photo;
import marsrover.entity.PhotoResponse;
import marsrover.entity.Rover;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class NasaRestClient {

    @Value("${nasa.client.dateFormat}")
    private String NASA_DATE_FORMAT;

    private Logger logger = LoggerFactory.getLogger(NasaRestClient.class);

    private String marsRoverPhotoBaseUri = "https://api.nasa.gov/mars-photos/api/v1";

    @Value("${nasa.client.apikey}")
    private String apiKey;

    private final JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private Client client = ClientBuilder.newClient(new ClientConfig(jacksonJsonProvider));

    public List<Photo> getPhotosByEarthDate(Rover.RoverName roverName, Date earthDate) {
        List<Photo> photos = new ArrayList<>();

        String uri = marsRoverPhotoBaseUri + "/rovers/"+  roverName.name().toLowerCase() + "/photos";
        Map<String, String> params = new HashMap<>();

        SimpleDateFormat formatter = new SimpleDateFormat(NASA_DATE_FORMAT);

        params.put("api_key", apiKey);
        params.put("earth_date", formatter.format(earthDate));

        PhotoResponse response = client.target(uri)
                .queryParam("api_key", apiKey)
                .queryParam("earth_date", formatter.format(earthDate))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(PhotoResponse.class);

        if (response != null) {
            photos = response.getPhotos();
            logger.info("Obtained {} photo information", photos.size());
        }
        return photos;
    }

    public InputStream downloadPhoto(String imgSrc) {
        return client.target(imgSrc).request().get(InputStream.class);
    }
}
