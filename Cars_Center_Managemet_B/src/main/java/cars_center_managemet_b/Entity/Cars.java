package cars_center_managemet_b.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "cars")
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private float price;

    @NotNull
    private int numberseats;

    private Date datesale;

    private float sellingprice;


    private String buyername;

    private String username;

    @Version
    private int version;

    public Cars() {
    }

    public Cars(@NotNull String name, @NotNull float price, @NotNull int numberseats) {
        this.name = name;
        this.price = price;
        this.numberseats = numberseats;
    }

    public Cars(@NotNull String name, @NotNull float price, @NotNull int numberseats, int version) {
        this.name = name;
        this.price = price;
        this.numberseats = numberseats;
        this.version = version;
    }

    public Cars(Date datesale, float sellingprice, String buyername, String username, int version) {
        this.datesale = datesale;
        this.sellingprice = sellingprice;
        this.buyername = buyername;
        this.username = username;
        this.version = version;
    }

    public Cars(@NotNull String name, @NotNull float price, @NotNull int numberseats, Date datesale, float sellingprice, String buyername, String username, int version) {
        this.name = name;
        this.price = price;
        this.numberseats = numberseats;
        this.datesale = datesale;
        this.sellingprice = sellingprice;
        this.buyername = buyername;
        this.username = username;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumberseats() {
        return numberseats;
    }

    public void setNumberseats(int numberseats) {
        this.numberseats = numberseats;
    }

    public Date getDatesale() {
        return datesale;
    }

    public void setDatesale(Date datesale) {
        this.datesale = datesale;
    }

    public float getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(float sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", numberseats=" + numberseats +
                ", datesale=" + datesale +
                ", sellingprice=" + sellingprice +
                ", buyername='" + buyername + '\'' +
                ", username='" + username + '\'' +
                ", version=" + version +
                '}';
    }
}

