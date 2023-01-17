package cars_center_management.Services;

import cars_center_management.Entity.Parameters;
import cars_center_management.Repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ParameterService")
@CacheConfig(cacheNames={"parameters"})
public class ParameterService {
    @Autowired
    ParameterRepository parameterRepository;

    @CacheEvict(value = "parameters", allEntries=true)
    public void AddParameter(Parameters parameters){
        parameterRepository.save(parameters);
    }

    @CacheEvict(value = "parameters",allEntries = true)
    public Parameters findByKey(int seats){
        return parameterRepository.findBySeats(seats);
    }
    @Cacheable(value="parameters")
    public List<Parameters> findAll(){
        return parameterRepository.findAll();
    }
    @CacheEvict(value = "parameters",allEntries = true)
    public void Delete(int id) {
      parameterRepository.deleteById(id);
    }
    @CacheEvict(value = "parameters",allEntries = true)
    public Parameters findById(int id){
        return parameterRepository.findById(id);
    }
}
