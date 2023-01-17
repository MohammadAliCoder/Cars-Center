package cars_center_managemet_b.CreateCsvBatch;

import cars_center_managemet_b.Entity.Cars;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarsRowMapper  implements RowMapper<Cars> {

    @Override
    public Cars mapRow(ResultSet resultSet, int i) throws SQLException {
        Cars cars = new Cars();
        cars.setId(resultSet.getInt("id"));
        cars.setName(resultSet.getString("name"));
        cars.setPrice(resultSet.getFloat("price"));
        cars.setNumberseats(resultSet.getInt("numberseats"));
        cars.setDatesale(resultSet.getDate("datesale"));
        cars.setSellingprice(resultSet.getFloat("sellingprice"));
        cars.setBuyername(resultSet.getString("buyername"));
        cars.setUsername(resultSet.getString("username"));
        cars.setVersion(resultSet.getInt("version"));
        return cars;
    }
}
