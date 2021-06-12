package bot.telegram.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "group_name")
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    private String groupName;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> ownersIds = new HashSet<>();

    public void addOwner(Long ownerId) {
        ownersIds.add(ownerId);
    }
}
