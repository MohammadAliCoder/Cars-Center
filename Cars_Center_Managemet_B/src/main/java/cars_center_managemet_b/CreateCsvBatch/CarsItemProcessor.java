package cars_center_managemet_b.CreateCsvBatch;

import cars_center_managemet_b.Entity.Cars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class CarsItemProcessor implements ItemProcessor<Cars, Cars> {
    private static final Logger log = LoggerFactory.getLogger(CarsItemProcessor.class);

    @Override
    public Cars process(Cars cars) throws Exception {
        return cars;
    }

}