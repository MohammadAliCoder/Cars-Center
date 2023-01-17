package cars_center_management.Services;

import cars_center_management.Entity.Role;
import cars_center_management.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@CacheConfig(cacheNames={"Role"})
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @CachePut(cacheNames="Role", key="#role.id")
    @Transactional
    public Role save(Role role){
       return roleRepository.save(role);
    }

    @Transactional
    public void delete(Role role){
         roleRepository.delete(role);
    }

    @Transactional
    public List<Role> rolefindAll(){
        return roleRepository.findAll();
    }
    @CacheEvict(value = "Role", key = "#id")
    @Transactional
    public List<Role> findById(int id){
        return roleRepository.findById(id);
    }

    @CacheEvict(value = "Role", key = "#role")
    @Transactional
   public Role findByRole(String role){
        return roleRepository.findByRole(role);
    }

}
