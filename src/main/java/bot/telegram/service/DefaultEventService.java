package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.entity.Frequency;
import bot.telegram.exception.ParserException;
import bot.telegram.repo.EventRepo;
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

    public boolean createEvent(String s) {
        Parser parser = new Parser();
        Event event;
        try {
            event = parser.parse(s.toLowerCase());
        } catch (ParserException e) {
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
        List<Event> events = eventRepo.findByDayOfWeekAndGroup(day,group);
        return checkWeek(week, events);
    }

    private ArrayList<Event> checkWeek(int week, List<Event> events) {
        ArrayList<Event> result = new ArrayList<>();
        for(Event event: events) {
            if (event.getFrequency() == Frequency.EVERY_WEEK) {
                result.add(event);
            } else if (week%2==1 && event.getFrequency() == Frequency.ODD_WEEK) {
                result.add(event);
            } else if (week%2==0 && event.getFrequency() == Frequency.EVEN_WEEK) {
                result.add(event);
            }
        }
        return result;
    }
}
