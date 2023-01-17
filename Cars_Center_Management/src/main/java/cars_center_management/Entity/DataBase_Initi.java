package cars_center_management.Entity;


import cars_center_management.Repositories.RoleRepository;
import cars_center_management.Services.ParameterService;
import cars_center_management.Services.RoleService;
import cars_center_management.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
public class DataBase_Initi implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    ParameterService parameterService;


    // Save Role&Users when runtime
    @Override
    public void run(String... args) throws Exception {

        if(roleService.rolefindAll().isEmpty()) {
            Role role_Admin = new Role();
            role_Admin.setRole("ADMIN");
            roleService.save(role_Admin);

            Role role_User = new Role();
            role_User.setRole("User");
            roleService.save(role_User);
        }

//____________Save Admin User_____
        if(userService.findAll().isEmpty()) {
            User admin=new User();
            admin.setName("Admin");
            admin.setEmail("Admin@gmail.com");
            admin.setLastName("lastAdmin");
            admin.setPassword("12345");
            userService.save(admin,1);


            User user=new User();
            user.setName("Youssef");
            user.setEmail("Youssef@gmail.com");
            user.setLastName("Youssef");
            user.setPassword("12345");
            userService.save(user,2);

            parameterService.AddParameter(new Parameters(1  ,1));
            parameterService.AddParameter(new Parameters(2  ,2));
            parameterService.AddParameter(new Parameters(3  ,3));
            parameterService.AddParameter(new Parameters(4  ,4));
            parameterService.AddParameter(new Parameters(5  ,5));
            parameterService.AddParameter(new Parameters(6  ,6));
            parameterService.AddParameter(new Parameters(8  ,8));
            parameterService.AddParameter(new Parameters(10 ,10));
            parameterService.AddParameter(new Parameters(12 ,12));
        }



    }
}
