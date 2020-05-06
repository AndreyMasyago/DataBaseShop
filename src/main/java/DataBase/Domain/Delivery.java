package DataBase.Domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int deliveryId;

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
