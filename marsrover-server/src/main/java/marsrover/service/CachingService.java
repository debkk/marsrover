package marsrover.service;

import lombok.RequiredArgsConstructor;
import marsrover.entity.Photo;
import marsrover.external.NasaRestClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CachingService {

    private final NasaRestClient nasaRestClient;

    @Value("${mars.cache.dir}")
    private String IMAGE_CACHE_DIR;
    private final static String CACHE_EXT = ".marsdat";

    public File cachePhoto(String imgSrc) throws IOException {

        String sha = DigestUtils.sha512Hex(imgSrc);
        String photoFileName = IMAGE_CACHE_DIR + sha + CACHE_EXT;

        File file;
        // check if downloaded
        if (Paths.get(photoFileName).toFile().exists()) {
            file = Paths.get(photoFileName).toFile();
        } else {
            // if not, download and cache
            try (InputStream inputStream = nasaRestClient.downloadPhoto(imgSrc)) {
                Path cachedFile = Files.createFile(Paths.get(photoFileName));
                Files.copy(inputStream, cachedFile, StandardCopyOption.REPLACE_EXISTING);

                file = cachedFile.toFile();
            }
        }
        return file;
    }

    public void clearCachedPhotos() {
        List<File> files = Arrays.stream(Paths.get(IMAGE_CACHE_DIR).toFile().listFiles((d, s) -> s.toLowerCase().endsWith(CACHE_EXT))).collect(Collectors.toList());
        files.forEach(File::delete);
    }

    public File getCachedPhoto(String imgSrc) throws IOException {
        String sha = DigestUtils.sha512Hex(imgSrc);
        String photoFileName = IMAGE_CACHE_DIR + sha;

        File file;
        if (Paths.get(photoFileName).toFile().exists()) {
            file = Paths.get(photoFileName).toFile();
        } else {

            // if not, download and cache
            try (InputStream inputStream = nasaRestClient.downloadPhoto(imgSrc)) {
                Path cachedFile = Files.createFile(Paths.get(photoFileName));
                Files.copy(inputStream, cachedFile, StandardCopyOption.REPLACE_EXISTING);

                file = cachedFile.toFile();
            }
        }

        return file;
    }
}
