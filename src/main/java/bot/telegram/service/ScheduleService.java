package bot.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ScheduleService {

    boolean createEvent(Update update);
    String getSchedule(String group);
}
