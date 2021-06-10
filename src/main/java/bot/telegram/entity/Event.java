package bot.telegram.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@MappedSuperclass
public class Event implements Comparable<Event>{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(name = "target_group")
    private String group;

    @NonNull
    @Embedded
    private Time time;

    @NonNull
    private String title;


    private String description;

    @Override
    public int compareTo(Event event) {
        return time.compareTo(event.getTime());
    }
}
