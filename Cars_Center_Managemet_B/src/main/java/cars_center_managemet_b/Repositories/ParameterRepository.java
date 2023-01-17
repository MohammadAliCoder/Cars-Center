package cars_center_managemet_b.Repositories;

import cars_center_managemet_b.Entity.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("ParameterRepository")
public interface ParameterRepository extends JpaRepository<Parameters,Integer> {

    Parameters findBySeats(int seats);
    Parameters findByValue(float value);
}
