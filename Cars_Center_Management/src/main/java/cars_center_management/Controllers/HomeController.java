package cars_center_management.Controllers;

import cars_center_management.Configuration.UserRequest;
import cars_center_management.Configuration.UserToken;
import cars_center_management.Entity.Cars;
import cars_center_management.Entity.Parameters;
import cars_center_management.Entity.User;
import cars_center_management.MQ.Message;
import cars_center_management.MQ.Sender;
import cars_center_management.Services.CarsService;
import cars_center_management.Services.ParameterService;
import cars_center_management.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController("/")
public class HomeController {


    @Autowired
    UserService userService;
    @Autowired
    CarsService carsService;
    @Autowired
    ParameterService parameterService;
    @Autowired
    Sender sender;



    @Autowired
    private UserToken userToken;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "")
    public String Login(@RequestBody UserRequest loginRequest){
        final Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails=userService.loadUserByUsername(loginRequest.getUsername());
        String token=userToken.generateToken(userDetails);
        return token;
    }

    @GetMapping(value="")
    public ModelAndView MainHome(){
        return new ModelAndView("MainHome");
    }




    @GetMapping(value={"login"})
    public ModelAndView login(){
        return  new ModelAndView("LOGIN");
    }
    @GetMapping(value="signup")
    public ModelAndView signupGET(){
        ModelAndView modelAndView = new ModelAndView();
        return new ModelAndView("signup","user",new User());
    }

    @PostMapping (value = "signup")
    public ModelAndView signupPOST(@Valid User user) {
        userService.save(user,2);
        return  new ModelAndView("LOGIN");
    }

    @GetMapping(value="CarsHome")
    public ModelAndView CarsHome(String search,String selected){
        List<Cars> listcars = null;

        if(search!=null&&selected!=null){
            switch (selected){
                case "Name":
                    listcars=carsService.findByName(search);
                    break;

                case "Price":
                    listcars=carsService.findByPrice(Float.valueOf(search));
                    break;

                case "Number of Seats":
                    listcars=carsService.findByNumber_seats(Integer.valueOf(search));
                    break;

            }
        }else{

            listcars=carsService.findAll();

        }


        return new ModelAndView("CarsHome","list",listcars);
    }

    @GetMapping(value="MyCars")
    public ModelAndView MyCars(String search,String selected){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ModelAndView("MyCars","list",getCarsforUser(auth.getName(),search,selected));
    }


    @GetMapping(value="AddCar")
    public ModelAndView GetAddCar(){
        return  new ModelAndView("AddCar","Cars",new Cars());
    }
    @PostMapping(value = "AddCar")
    public ModelAndView PostAddCar(@Valid Cars cars){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        cars.setUsername(auth.getName());
        carsService.saveCar(cars);
        return  new ModelAndView("AddCar","Cars",new Cars());
    }

    @PostMapping(value = "UpdateCar",params = "Update")
    public ModelAndView Edit(int id) {
       Cars cars=carsService.findByID(id);
        return new ModelAndView("UpdateCar","cars",cars);
    }

    @PostMapping(value = "MyCars",params = "Save")
    public ModelAndView UpdateCarSave(@Valid Cars cars) {
        carsService.saveCar(cars);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ModelAndView("MyCars","list",getCarsforUser(auth.getName(),null,null));
    }

    @PostMapping(value = "MyCars",params = "Delete")
    public ModelAndView Delete(int id) {
        //_____________
        carsService.Delete(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

         return new ModelAndView("MyCars","list",getCarsforUser(auth.getName(),null,null));
    }

    @GetMapping(value = "Shopping")
    public ModelAndView GetShopping(String search,String selected){
        return new ModelAndView("Shopping","list",getCarsforSale(search,selected));
    }

    @PostMapping(value = "Sale",params = "Sale")
    public ModelAndView Shopping(int id) {
        Cars cars=carsService.findByID(id);
        float rate=parameterService.findByKey(cars.getNumberseats()).getValue();

        float sellingPrice =(float) (cars.getPrice()*(rate/100))+cars.getPrice();

        cars.setSellingprice(sellingPrice);
        return new ModelAndView("Sale","cars",cars);
    }

    @PostMapping(value = "Shopping",params = "Save")
    public ModelAndView Shopping_Save(@Valid Cars cars) {

        // add sale date
        try {
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            cars.setDatesale(date);


            carsService.saveCar(cars);
        }catch (Exception  e){
            return new ModelAndView("Error");
        }


        return new ModelAndView("Shopping","list",getCarsforSale(null,null));
    }


    @GetMapping(value = "Message")
    public ModelAndView GetMessage(){
        return new ModelAndView("Message");
    }
    @PostMapping(value = "Message")
    public ModelAndView PosMessage(String senderemail,String receiveremail,String TextArea1){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        sender.sendMsg(new Message(senderemail,receiveremail,TextArea1,date));

        return new ModelAndView("Message");
    }

    @GetMapping(value = "parameters")
    public ModelAndView GetParameters(){

        return new ModelAndView("Parameters","listparameter",parameterService.findAll());
    }
    @PostMapping(value = "parameters",params = "Delete")
    public ModelAndView DeleteParameter(int id) {
        parameterService.Delete(id);
        return new ModelAndView("Parameters","listparameter",parameterService.findAll());
    }

    @GetMapping(value = "addParameter")
    public ModelAndView Get_AddParameter(){

        return new ModelAndView("AddParameters","parameter",new Parameters());
    }
    @PostMapping(value = "addParameter")
    public ModelAndView Post_AddParameter(@Valid Parameters parameters){
        parameterService.AddParameter(parameters);
        return new ModelAndView("AddParameters","parameter",new Parameters());
    }
    @PostMapping(value = "updateParameter")
    public ModelAndView Get_updateParameter(int id){
        Parameters parameters=parameterService.findById(id);
        return new ModelAndView("UpdateParameters","parameter",parameters);
    }
    @PostMapping(value = "parameters")
    public ModelAndView post_UpdateParameter(@Valid Parameters parameters){
        parameterService.AddParameter(parameters);
        return new ModelAndView("Parameters","listparameter",parameterService.findAll());
    }





//______________________my method

public List<Cars> getCarsforSale(String search,String selected){
    List<Cars> listcars = null;

    if(search!=null&&selected!=null){
        switch (selected){
            case "Name":
                listcars=carsService.findByBuyernameIsNullAndName(search);
                break;

            case "Price":
                listcars=carsService.findByBuyernameIsNullAndPrice(Float.valueOf(search));
                break;

            case "Number of Seats":
                listcars=carsService.findByBuyernameIsNullAndNumberseats(Integer.valueOf(search));
                break;

        }
    }else{

        listcars=carsService.findByBuyernameIsNull();

    }

    return listcars;
}

public  List<Cars> getCarsforUser(String username,String search,String selected){
    List<Cars> listcars = null;

    if(search!=null&&selected!=null){
        switch (selected){
            case "Name":
                listcars=carsService.findByNameAndUsername(search,username);
                break;

            case "Price":
                listcars=carsService.findByPriceAndUsername(Float.valueOf(search),username);
                break;

            case "Number of Seats":
                listcars=carsService.findByNumberseatsAndUsername(Integer.valueOf(search),username);
                break;

        }
    }else{

        listcars=carsService.findByUsername(username);

    }

return listcars;

}

}
