package cars_center_managemet_b.Services;

import cars_center_managemet_b.Entity.Parameters;
import cars_center_managemet_b.Repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("ParameterService")
@CacheConfig(cacheNames={"Parameters_Cache"})//employee is the name of the cache.
public class ParameterService {
    @Autowired
    ParameterRepository parameterRepository;

    @CachePut(cacheNames="Parameters_Cache", key="#parameters.id") //updating cache
    public void AddParameter(Parameters parameters){
        parameterRepository.save(parameters);
    }


    @CacheEvict(value = "Parameters", key = "#seats")
    public Parameters findByKey(int seats){
        return parameterRepository.findBySeats(seats);
    }


}
