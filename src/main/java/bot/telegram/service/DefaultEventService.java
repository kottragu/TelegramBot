package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.entity.Frequency;
import bot.telegram.entity.RepeatingEvent;
import bot.telegram.repo.EventRepo;
import bot.telegram.service.interfaces.EventService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Service
@Data
@NoArgsConstructor
public class DefaultEventService implements EventService {
    private EventRepo eventRepo;

    @Autowired
    public void setEventRepo(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public boolean createEvent(String s) throws Exception {
        Parser parser = new Parser();
        Event event;
        try {
            event = parser.parse(s);
        } catch (Exception e) {
            return false;
        }
        eventRepo.save(event);
        return true;
    }

    public List<Event> getEvents(String group) {
        group = group.toLowerCase();
        LocalDate today = LocalDate.now();
        DayOfWeek day = today.getDayOfWeek();
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        LocalDate firstDay = LocalDate.of(today.getYear(), 1,1);
        if (firstDay.getDayOfWeek().getValue() > 4) {
            week++;
        }
        List<Event> events = checkWeek(week, eventRepo.findRepeatingEvents(day,group));
        events.addAll(eventRepo.findSingleEvents(today.getDayOfMonth(), today.getMonth()));
        return checkWeek(week, events);
    }


    private ArrayList<Event> checkWeek(int week, List<Event> events) {
        ArrayList<Event> result = new ArrayList<>();
        for(Event event: events) {
            RepeatingEvent repeatingEvent = (RepeatingEvent) event;
            if (repeatingEvent.getFrequency() == Frequency.EVERY_WEEK) {
                result.add(event);
            } else if (week%2==1 && repeatingEvent.getFrequency() == Frequency.ODD_WEEK) {
                result.add(event);
            } else if (week%2==0 && repeatingEvent.getFrequency() == Frequency.EVEN_WEEK) {
                result.add(event);
            }
        }
        return result;
    }
}
