package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.entity.Frequency;
import bot.telegram.entity.RepeatingEvent;
import bot.telegram.entity.WeekRepeatingEvent;
import bot.telegram.repo.EventRepo;
import bot.telegram.service.interfaces.EventService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
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
        int week = numberOfWeek(today);

        List<Event> events = checkWeek(week, eventRepo.findWeekRepeatingEvents(today.getDayOfWeek(),group));
        events.addAll(eventRepo.findSingleEvents(today.getDayOfMonth(), today.getMonth(), group));
        events.addAll(checkDateRepeatingEvent(eventRepo.findRepeatingEvent(group), today));
        return events;
    }

    private int numberOfWeek(LocalDate today) {
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        LocalDate firstDay = LocalDate.of(today.getYear(), 1,1);
        if (firstDay.getDayOfWeek().getValue() > 4) {
            week++;
        }
        return week;
    }

    private ArrayList<Event> checkDateRepeatingEvent(List<RepeatingEvent> events, LocalDate today) {
        ArrayList<Event> result = new ArrayList<>();
        for (RepeatingEvent event: events) {
            LocalDate date = event.getDate();
            if (Period.between(today,date).getDays() % event.getPeriod() == 0) {
                result.add(event);
            }
        }
        return result;
    }

    private ArrayList<Event> checkWeek(int week, List<WeekRepeatingEvent> events) {
        ArrayList<Event> result = new ArrayList<>();
        for(WeekRepeatingEvent event: events) {
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
