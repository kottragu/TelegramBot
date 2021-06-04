package bot.telegram.repo;

import bot.telegram.entity.Event;
import bot.telegram.entity.RepeatingEvent;
import bot.telegram.entity.SingleEvent;
import bot.telegram.entity.WeekRepeatingEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {
    @Transactional
    @Query("select wre from WeekRepeatingEvent as wre " +
            "where wre.dayOfWeek=?1 " +
            "and wre.group=?2")
    List<WeekRepeatingEvent> findWeekRepeatingEvents(DayOfWeek dayOfWeek, String group);

    @Transactional
    @Query("select se from SingleEvent as se " +
            "where se.day=?1 " +
            "and se.month=?2 " +
            "and se.group=?3")
    List<SingleEvent> findSingleEvents(int day, Month month, String group);

    @Transactional
    @Query("select re from RepeatingEvent as re " +
            "where re.group=?1")
    List<RepeatingEvent> findRepeatingEvent(String group);


}
