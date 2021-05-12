package bot.telegram.service;

import bot.telegram.entity.Event;

import java.util.List;

public interface ViewService {

    String convert(List<Event> events);
}
