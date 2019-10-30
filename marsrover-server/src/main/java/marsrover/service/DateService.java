package marsrover.service;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateService {

    private Logger logger = LoggerFactory.getLogger(DateService.class);

    private String[] acceptedFormats = {
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
            logger.error("Invalid date {}: {}", earthDate, e.getMessage());
        }

        return parsed;
    }
}
