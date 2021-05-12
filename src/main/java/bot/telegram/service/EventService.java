package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.repo.EventRepo;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public interface EventService {
    boolean createEvent(String s);
    List<Event> getEvents(String group);
}
