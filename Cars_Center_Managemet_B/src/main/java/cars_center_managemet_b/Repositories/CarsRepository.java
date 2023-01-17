package cars_center_managemet_b.Repositories;

import cars_center_managemet_b.Entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository("CarsRepository")
public interface CarsRepository extends JpaRepository<Cars,Integer> {

    Cars findById(int id);

    List<Cars> findByName(String name);
    List<Cars> findByNameAndUsername(String name, String username);

    List<Cars> findByPrice(float price);
    List<Cars> findByPriceAndUsername(float price, String username);

    List<Cars> findByNumberseats(int numberseats);
    List<Cars> findByNumberseatsAndUsername(int numberseats, String username);

    List<Cars> findByUsername(String username);

    List<Cars> findByBuyernameIsNull();
    List<Cars> findByBuyernameIsNullAndName(String name);
    List<Cars> findByBuyernameIsNullAndPrice(float price);
    List<Cars> findByBuyernameIsNullAndNumberseats(int numberseats);

    List<Cars> findByDatesaleBetween(Date start,Date end);

}
