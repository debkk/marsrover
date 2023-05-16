package marsrover.service;

import lombok.RequiredArgsConstructor;
import marsrover.config.CacheProps;
import marsrover.external.NasaFeignClient;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CachingService {

    private final NasaFeignClient nasaRestClient;

    private final CacheProps cacheProps;
    private static final String CACHE_EXT = ".marsdat";

    public File cachePhoto(String imgSrc) throws IOException {

        String sha = DigestUtils.sha512Hex(imgSrc);
        String photoFileName = cacheProps.getDir() + sha + CACHE_EXT;

        File file = null;
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

        Optional<File[]> cacheFiles = Optional.ofNullable(Paths.get(cacheProps.getDir()).toFile().listFiles((d, s) -> s.toLowerCase().endsWith(CACHE_EXT)));
        if (cacheFiles.isPresent()) {
            List<File> files = Arrays.stream(cacheFiles.get()).toList();
            files.forEach(File::delete);
        }
    }

    public File getCachedPhoto(String imgSrc) throws IOException {
        String sha = DigestUtils.sha512Hex(imgSrc);
        String photoFileName = cacheProps.getDir() + sha;

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
