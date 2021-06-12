package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.entity.Group;
import bot.telegram.repo.GroupRepo;
import bot.telegram.service.interfaces.EventService;
import bot.telegram.service.interfaces.ScheduleService;
import bot.telegram.service.interfaces.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@Slf4j
@Service
public class DefaultScheduleService implements ScheduleService {
    private EventService eventService;
    private ViewService viewService;
    private GroupRepo groupRepo;

    @Autowired
    public void setGroupRepo(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

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
        if (checkAccess(update.getMessage().getFrom().getId(), findGroup(update.getMessage().getText()))) {
            return eventService.createEvent(update.getMessage().getText());
        }
        return false;
    }

    @Override
    public String getSchedule(Update update) {
        String group = update.getMessage().getText().substring(10).trim();
        List<Event> events = eventService.getEvents(group);
        return viewService.convert(events);
    }

    private String findGroup(String message) {
        message = message.toLowerCase();
        return message.substring(message.indexOf("group:") + 6, message.indexOf("\n", message.indexOf("group:")+6));
    }

    private boolean checkAccess(Long userId, String group) {
        Group groupInDB = groupRepo.findGroupByGroupName(group);

        if (groupInDB == null) {
            groupInDB = new Group();
            groupInDB.setGroupName(group);
            groupInDB.addOwner(userId);
            groupRepo.save(groupInDB);
        }

        return !groupInDB.getOwnersIds().isEmpty() && groupInDB.getOwnersIds().stream().anyMatch(o -> o.equals(userId));
    }
}
