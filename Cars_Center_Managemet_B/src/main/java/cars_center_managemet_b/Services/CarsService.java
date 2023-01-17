package cars_center_managemet_b.Services;

import cars_center_managemet_b.Entity.Cars;
import cars_center_managemet_b.Repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service("CarsService")
public class CarsService {

    @Autowired
    CarsRepository carsRepository;
    @Autowired
    ParameterService parameterService;
    @Transactional
    public void saveCar(Cars cars){

        if(cars.getNumberseats()<=0){//اذا كان عدد المقاعد ==0 اخد الافتراضي 4

           cars.setNumberseats(parameterService.findByKey(4).getSeats());
            carsRepository.save(cars);
        }else {
            carsRepository.save(cars);
        }

    }
    @Transactional
    public Cars findByID(int id){
        return carsRepository.findById(id);
    }
    @Transactional
    public List<Cars> findAll(){
        return carsRepository.findAll();
    }
    @Transactional
    public List<Cars> findByName(String name){
        return carsRepository.findByName(name);
    }
    @Transactional
    public List<Cars> findByNameAndUsername(String name,String user){
        return carsRepository.findByNameAndUsername(name,user);
    }
    @Transactional
    public List<Cars> findByPrice(float price){
        return carsRepository.findByPrice(price);
    }
    @Transactional
    public List<Cars> findByPriceAndUsername(float price,String username){
        return carsRepository.findByPriceAndUsername( price, username);
    }
    @Transactional
    public List<Cars> findByNumber_seats(int number_seats){
        return carsRepository.findByNumberseats(number_seats);
    }
    @Transactional
    public List<Cars> findByNumberseatsAndUsername(int numberseats,String username){
        return carsRepository.findByNumberseatsAndUsername( numberseats, username);
    }
    @Transactional
    public List<Cars> findByUsername(String username){
        return carsRepository.findByUsername(username);
    }
    @Transactional
    public void Delete(int id){
        carsRepository.deleteById(id);
   }
    @Transactional
   public List<Cars> findByBuyernameIsNullAndName(String name){
        return carsRepository.findByBuyernameIsNullAndName(name);
   }
    @Transactional
    public List<Cars> findByBuyernameIsNullAndPrice(float price){
        return carsRepository.findByBuyernameIsNullAndPrice(price);
    }
    @Transactional
    public List<Cars> findByBuyernameIsNullAndNumberseats(int numberseats){
        return carsRepository.findByBuyernameIsNullAndNumberseats(numberseats);
    }
    @Transactional
    public List<Cars> findByBuyernameIsNull(){
        return carsRepository.findByBuyernameIsNull();
    }
    //لعرض السيارات المباعة خلال الشهر
    @Transactional
    public List<Cars> findByDatesaleBetween(Date currentDate){
        Date start=Date.valueOf(currentDate.toLocalDate().minusMonths(1));
        Date end=Date.valueOf(currentDate.toLocalDate().plusMonths(1));
        return carsRepository.findByDatesaleBetween( start, end);
    }

}
