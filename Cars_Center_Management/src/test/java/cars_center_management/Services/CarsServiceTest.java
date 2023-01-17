package cars_center_management.Services;


import cars_center_management.Entity.Cars;
import cars_center_management.Repositories.CarsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CarsServiceTest {

    @Mock
    CarsRepository carsRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void saveCar(){
        Cars cars1=new Cars("Bmw",5000,0,1);
        //عندما احفظ يرجع السيارة
        when(carsRepository.save(cars1)).thenReturn(cars1);
        //اذا حفظت هل ترجع السيارة
        assertThat(carsRepository.save(cars1)).isEqualTo(cars1);
    }

    @Test
    public void Delete(){

        Cars cars1=new Cars("Bmw",5000,4,1);
       Mockito.when(carsRepository.findById(cars1.getId())).thenReturn(cars1);

        assertThat(carsRepository.findById(cars1.getId())).isEqualTo(cars1);

        carsRepository.delete(cars1);
        Mockito.verify(carsRepository, times(1)).delete(cars1);
        Mockito.when(carsRepository.findById(cars1.getId())).thenReturn(null);

       assertThat(carsRepository.findById(cars1.getId())).isNull();



    }

    @Test
    public void Update(){
        Cars cars1=new Cars("Bmw",5000,0,1);
        //عندما احفظ يرجع السيارة
        String name="Bmw x5";
        cars1.setUsername(name);
        cars1.setVersion(2);
        when(carsRepository.save(cars1)).thenReturn(cars1);

        Cars cars2=new Cars(name,5000,0,2);
        //اذا حفظت هل ترجع السيارة
        assertThat(carsRepository.save(cars1)).isEqualTo(cars1);
    }


}
