package marsrover.service;

import marsrover.config.NasaClientProps;
import marsrover.entity.Photo;
import marsrover.entity.Rover;
import marsrover.external.NasaFeignClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PhotoService {

    private final NasaFeignClient nasaFeignClient;
    private final CachingService cachingService;

    private final NasaClientProps nasaClientProps;

    private final SimpleDateFormat formatter;

    public PhotoService(NasaFeignClient nasaFeignClient, CachingService cachingService, NasaClientProps nasaClientProps) {
        this.nasaFeignClient = nasaFeignClient;
        this.cachingService = cachingService;
        this.nasaClientProps = nasaClientProps;
        this.formatter = new SimpleDateFormat(nasaClientProps.getDateFormat());
    }

    public List<Photo> getPhotosByDate(Rover.RoverName rover, Date date) throws IOException {
        List<Photo> photos;

        // get photo list from NASA
        photos = nasaFeignClient.getPhotosByEarthDate(rover, nasaClientProps.getApiKey(), formatter.format(date));
        // check if photo already downloaded
        for (Photo photo : photos) {
            cachingService.cachePhoto(photo.getImgSrc());
        }

        // return photo list
        return photos;
    }

    public File getPhotoByUrl(String imgSrc) throws IOException {
        return cachingService.getCachedPhoto(imgSrc);
    }

    public void clearCachedPhotos() {
        cachingService.clearCachedPhotos();
    }
}
