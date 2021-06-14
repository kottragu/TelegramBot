package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.service.interfaces.ViewService;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DefaultViewService implements ViewService {
    @Override
    public String convert(List<Event> events) {
        if (events.size() == 0)
            return "You haven't plans for today";

        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd.MM.yyyy");
        StringBuilder result = new StringBuilder(dateFormat.format(new Date()));
        Collections.sort(events);

        for (Event e: events) {
            add(e, result);
        }

        return result.toString();
    }

    private void add(Event e, StringBuilder result) {
            result.append("\n_");
            result.append(e.getTime()).append(" ").append(e.getTitle()).append(" ");
            if (e.getDescription() != null)
                result.append(e.getDescription());
            result.append("_");
    }
}
