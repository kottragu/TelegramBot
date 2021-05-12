package bot.telegram.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Data
@NoArgsConstructor
@Table(name = "event_repo", schema = "schedule_repository")
public class Event implements Comparable<Event>{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(name = "target_group")
    private String group;

    @NonNull
    private Frequency frequency;

    @NonNull
    @JsonProperty(value = "dayofweek")
    private DayOfWeek dayOfWeek;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private Time time;

    @NonNull
    private String title;


    private String description;

    @Override
    public int compareTo(Event event) {
        return time.compareTo(event.getTime());
    }
}
