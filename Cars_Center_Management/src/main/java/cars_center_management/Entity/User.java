package cars_center_management.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collector;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;


    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotNull(message = "*Please provide an email")
    private String email;


    @Column(name = "password")
    @NotNull(message = "*Please provide your password")
    private String password;


    @Column(name = "name")
    @NotNull(message = "*Please provide your name")
    private String name;


    @Column(name = "last_name")
    @NotNull(message = "*Please provide your last name")
    private String lastName;


    @Column(name = "active")
    @NotNull
    private int active;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public @Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String getEmail() {
        return email;
    }

    public @NotNull(message = "*Please provide your name") String getName() {
        return name;
    }

    public @NotNull(message = "*Please provide your last name") String getLastName() {
        return lastName;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    private String myRole(){
        return getRoles().get(0).getRole();
    }
    @Override
    public Collection< GrantedAuthority> getAuthorities() {

        return Arrays.asList(this::myRole);
    }

    public @Length(min = 5, message = "*Your password must have at least 5 characters") @NotNull(message = "*Please provide your password") String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword( @NotNull(message = "*Please provide your password") String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
