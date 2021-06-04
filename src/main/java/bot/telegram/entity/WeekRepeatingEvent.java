package bot.telegram.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import java.time.DayOfWeek;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "week_repeating_event_repo", schema = "schedule_repository")
public class WeekRepeatingEvent extends Event {
    @NonNull
    private Frequency frequency;

    @NonNull
    @JsonProperty(value = "dayofweek")
    private DayOfWeek dayOfWeek;

}
