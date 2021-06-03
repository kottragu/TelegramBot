package bot.telegram.service.interfaces;

import bot.telegram.entity.Event;
import java.util.List;

public interface EventService {
    boolean createEvent(String s) throws Exception;
    List<Event> getEvents(String group);
}
