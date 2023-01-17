package cars_center_management.Services;

import cars_center_management.Entity.Cars;
import cars_center_management.Repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
