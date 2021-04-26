package launchcode.liftoff_project.Model.data;

import launchcode.liftoff_project.Model.MeetupCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupCategoryRepository extends CrudRepository<MeetupCategory, Integer> {
}
