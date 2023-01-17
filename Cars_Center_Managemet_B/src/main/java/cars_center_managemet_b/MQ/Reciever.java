package cars_center_managemet_b.MQ;

import cars_center_managemet_b.Entity.Cars;
import cars_center_managemet_b.Mail.Mail;
import cars_center_managemet_b.Mail.MailService;
import cars_center_managemet_b.Services.CarsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@Component
public class Reciever {

    @Autowired
    CarsService carsService;
    @Autowired
    MailService  mailService;


    @RabbitListener(queues = "${jsa.rabbitmq.queue}", containerFactory = "jsaFactory")
    public void recievedMessage(Message message) {

            float sum=0;
        List<Cars> carsList=carsService.findByDatesaleBetween(message.getDate());
        for (int i=0;i<carsList.size();i++){
            sum+=carsList.get(i).getSellingprice();
        }


        //_________________Mail_____________________
        Mail mail = new Mail();
        mail.setMailFrom(message.getSenderemail());
        mail.setMailTo(message.getReceiveremail());
        mail.setMailSubject("Spring Boot - Cars Center Management");
        mail.setMailContent("Date :"+message.getDate()
                +"\nPrice sum:   "+sum
                +"\n"+message.getContent());

       mailService.sendEmail(mail);


    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

}