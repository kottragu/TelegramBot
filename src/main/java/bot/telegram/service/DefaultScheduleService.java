package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.service.interfaces.EventService;
import bot.telegram.service.interfaces.ScheduleService;
import bot.telegram.service.interfaces.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;


@Service
public class DefaultScheduleService implements ScheduleService {
    private EventService eventService;
    private ViewService viewService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setViewService(ViewService viewService) {
        this.viewService = viewService;
    }

    @Override
    public boolean createEvent(Update update) throws Exception {
        return eventService.createEvent(update.getMessage().getText());
    }

    @Override
    public String getSchedule(String group) {

        List<Event> events = eventService.getEvents(group);
        return viewService.convert(events);
    }
}
