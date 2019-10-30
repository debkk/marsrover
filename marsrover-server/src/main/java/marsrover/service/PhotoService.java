package marsrover.service;

import lombok.RequiredArgsConstructor;
import marsrover.entity.Camera;
import marsrover.entity.Photo;
import marsrover.entity.Rover;
import marsrover.external.NasaRestClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final NasaRestClient nasaRestClient;
    private final CachingService cachingService;

    public List<Photo> getPhotosByDate(Rover.RoverName rover, Date date) throws IOException {
        List<Photo> photos;

        // get photo list from NASA
        photos = nasaRestClient.getPhotosByEarthDate(rover, date);
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
