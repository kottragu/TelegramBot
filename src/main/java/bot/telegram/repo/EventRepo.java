package bot.telegram.repo;

import bot.telegram.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {

    List<Event> findByDayOfWeekAndGroup(DayOfWeek dayOfWeek, String group);
}
