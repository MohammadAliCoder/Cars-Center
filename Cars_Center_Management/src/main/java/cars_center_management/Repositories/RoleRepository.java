package cars_center_management.Repositories;

import cars_center_management.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {


    @Override
    Role getOne(Integer integer);

    Role findByRole(String role);
    List<Role> findById(int id);

}
