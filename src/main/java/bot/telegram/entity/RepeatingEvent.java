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
@Table(name = "repeating_event_repo", schema = "schedule_repository")
public class RepeatingEvent extends Event {

    @NonNull
    private Frequency frequency;

    @NonNull
    @JsonProperty(value = "dayofweek")
    private DayOfWeek dayOfWeek;


}
