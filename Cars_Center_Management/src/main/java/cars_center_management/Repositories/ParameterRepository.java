package cars_center_management.Repositories;

import cars_center_management.Entity.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ParameterRepository")
public interface ParameterRepository extends JpaRepository<Parameters,Integer> {

    Parameters findBySeats(int seats);
    Parameters findByValue(float value);
    Parameters findById(int id);

}
