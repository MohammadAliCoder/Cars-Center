package cars_center_management.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"cars_center_management.*"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
@Profile("default")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }

//authorize
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
        //سماحيات للمستخدمين

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/parameters").hasAnyAuthority("ADMIN")
                .antMatchers("/addParameter").hasAnyAuthority("ADMIN")
                .antMatchers("/updateParameter").hasAnyAuthority("ADMIN")
                .antMatchers("/Message").hasAnyAuthority("ADMIN","User")
                .antMatchers("/Sale/**").hasAnyAuthority("ADMIN","User")
                .antMatchers("/Shopping/**").hasAnyAuthority("ADMIN","User")
                .antMatchers("/UpdateCar/**").hasAnyAuthority("ADMIN","User")
                .antMatchers("/AddCar").hasAnyAuthority("ADMIN","User")
                .antMatchers("/MyCars").hasAnyAuthority("ADMIN","User")
                .antMatchers("/CarsHome").hasAnyAuthority("ADMIN","User")
                .and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/CarsHome")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().httpBasic();



    }

    @Override
    public void configure(WebSecurity web)  {//ليتعرف على الصفحات
        web.ignoring()
                .antMatchers("/resources/**", "/static/**");
    }

}
