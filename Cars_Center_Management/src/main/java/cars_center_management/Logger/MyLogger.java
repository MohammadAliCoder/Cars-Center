package cars_center_management.Logger;

import cars_center_management.Entity.Cars;
import cars_center_management.Services.CarsService;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLogger.class);
    @Autowired
    CarsService carsService;
    public static void InfoLogger(){
        LOGGER.error("Message logged at ERROR level");
        LOGGER.warn("Message logged at WARN level");
        LOGGER.info("Message logged at INFO level");
        LOGGER.debug("Message logged at DEBUG level");

    }


    //AOP expression for which methods shall be intercepted
    @Around("execution(* cars_center_management.Services..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            //Log method execution time
            LOGGER.info("Execution (" + SecurityContextHolder.getContext().getAuthentication().getName() + ") time of " + className + "." + methodName + " :: " + stopWatch.getTime() + " ms");
        }

        return result;
    }


}
