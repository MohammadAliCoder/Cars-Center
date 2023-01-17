package cars_center_management.Services;

import cars_center_management.Entity.*;
import cars_center_management.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service("UserService")
@CacheConfig(cacheNames={"user"})
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @CacheEvict(value = "User", key = "#email")
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @CachePut(cacheNames="user", key="#user.email")
    @Transactional
    public void save(User user,int type) {
        //type=1->Admin
        //type=2->user
        if(type==1){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(1);
            Role userRole = roleService.findByRole("Admin");
            user.setRoles(Arrays.asList(userRole));
            userRepository.save(user);
        }else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(2);
            Role userRole = roleService.findByRole("User");
            user.setRoles(Arrays.asList(userRole));
            userRepository.save(user);
        }



    }

    @Transactional
    public List<User> findAll(){
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String s)  {
        User user=null;
       try {
            user=findUserByEmail(s);
           user.setRoles(roleService.findById(user.getActive()));
       }catch (Exception e){
            throw new RuntimeException(e);
       }


        return user;
    }
}