package DataBase.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_entity_generator")
    @SequenceGenerator(name = "order_entity_generator", sequenceName = "order_entity_idx", allocationSize = 1)
    private int orderId;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "orderId")
    List<Reject> rejectList;


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "orderId")
    List<OrderContent> orderContentList;

    private Date orderDate;

    public OrderEntity(){
    }

    public OrderEntity(Date orderDate){
        this.orderDate = orderDate;
    }

    @JsonIgnore
    public List<Reject> getRejectList() {
        return rejectList;
    }

    @JsonIgnore
    public void setRejectList(List<Reject> rejectList) {
        this.rejectList = rejectList;
    }

/*    public List<OrderContent> getOrderContentList() {
        return orderContentList;
    }

    public void setOrderContentList(List<OrderContent> orderContentList) {
        this.orderContentList = orderContentList;
    }
*/
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
