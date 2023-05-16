package marsrover.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class DateService {

    private final String[] acceptedFormats = {
            "yyyy-MM-dd",
            "MM/dd/yy",
            "MMMM d, yyyy",
            "MMM-dd-yyyy",
            "MMM-d-yyyy"
    };

    public Date parseDate(String earthDate) {
        Date parsed = null;

        try {
            parsed = DateUtils.parseDateStrictly(earthDate, acceptedFormats);
        } catch (ParseException e) {
            log.error("Invalid date {}: {}", earthDate, e.getMessage());
        }

        return parsed;
    }
}
