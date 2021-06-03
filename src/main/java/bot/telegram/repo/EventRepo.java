package bot.telegram.repo;

import bot.telegram.entity.Event;
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
    @Query("select re from RepeatingEvent as re " +
            "where re.dayOfWeek=?1 " +
            "and re.group=?2")
    List<Event> findRepeatingEvents(DayOfWeek dayOfWeek, String group);

    @Transactional
    @Query("select se from SingleEvent as se " +
            "where se.day=?1 " +
            "and se.month=?2")
    List<Event> findSingleEvents(int day, Month month);
}
