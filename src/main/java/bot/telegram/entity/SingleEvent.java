package bot.telegram.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Month;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "single_event_repo", schema = "schedule_repository")
public class SingleEvent extends Event{
    private int day;
    private Month month;
}
