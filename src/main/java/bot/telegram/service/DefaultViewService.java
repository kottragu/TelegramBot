package bot.telegram.service;

import bot.telegram.entity.Event;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DefaultViewService implements ViewService {


    @Override
    public String convert(List<Event> events) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd.MM.yyyy");
        StringBuilder result = new StringBuilder(dateFormat.format(new Date()));
        Collections.sort(events);
        for (Event e: events) {
            result.append("\n");
            result.append(e.getTime()).append(" ").append(e.getTitle()).append(" ");
            if (e.getDescription() != null)
                result.append(e.getDescription());
        }
        return result.toString();
    }
}
