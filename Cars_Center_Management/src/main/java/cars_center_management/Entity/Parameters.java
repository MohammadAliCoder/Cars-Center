package cars_center_management.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parameters")
public class Parameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int seats;


    private float value;

    public Parameters() {
    }

    public Parameters(int seats, float value) {
        this.seats = seats;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "id=" + id +
                ", seats=" + seats +
                ", value=" + value +
                '}';
    }
}
