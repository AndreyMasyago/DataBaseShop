package DataBase.Domain;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int deliveryId;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "deliveryId")
    List<DeliveryContent> deliveryContentList;

    private Date arrivingDateOnStorage;

    public Delivery() {
    }

    public Delivery(Date arrivingDateOnStorage) {
        this.arrivingDateOnStorage = arrivingDateOnStorage;
    }

    public Date getArrivingDateOnStorage() {
        return arrivingDateOnStorage;
    }

    public void setArrivingDateOnStorage(Date arrivingDateOnStorage) {
        this.arrivingDateOnStorage = arrivingDateOnStorage;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }
}
