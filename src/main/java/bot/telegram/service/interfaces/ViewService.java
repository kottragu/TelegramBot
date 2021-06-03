package bot.telegram.service.interfaces;

import bot.telegram.entity.Event;
import java.util.List;

public interface ViewService {

    String convert(List<Event> events);
}
