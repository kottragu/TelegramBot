package bot.telegram.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ScheduleService {

    boolean createEvent(Update update) throws Exception;
    String getSchedule(String group);
}
