package bot.telegram.repo;

import bot.telegram.entity.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepo extends CrudRepository<Group, Long> {
    Group findGroupByGroupName(String group);
}
