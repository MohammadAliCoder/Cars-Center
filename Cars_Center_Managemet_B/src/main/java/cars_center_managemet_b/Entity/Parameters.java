package cars_center_managemet_b.Entity;

import javax.persistence.*;

@Entity
@Table(name = "parameters")
public class Parameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int seats;//عدد المقاعد


    private float value;//نسبة الربح

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
