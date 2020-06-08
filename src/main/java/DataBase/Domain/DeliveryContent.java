package DataBase.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class DeliveryContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_content_generator")
    @SequenceGenerator(name = "delivery_content_generator", sequenceName = "delivery_content_idx", allocationSize = 1)
    private int deliveryContentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goodsId")
    private Goods goods;

    public DeliveryContent(){
    }

    @JsonIgnore
    public Integer getDetailId() { return goods.getDetailId(); }
    @JsonIgnore
    public String getGoodsName() { return goods.getGoodsName(); }
    @JsonIgnore
    public Integer getSize() { return goods.getSize();}
    @JsonIgnore
    public Integer getDeliveryTime() { return goods.getDeliveryTime(); }
    @JsonIgnore
    public Integer getPurchasePrice() { return goods.getPurchasePrice(); }
    @JsonIgnore
    public Integer getSellingPrice() { return goods.getSellingPrice(); }
    @JsonIgnore
    public String getProducer() { return goods.getProducer(); }
    @JsonIgnore
    public String getProviderName() { return goods.getProviderName(); }
    @JsonIgnore
    public String getProviderCategory() { return goods.getProviderCategory(); }

    @JsonIgnore
    public Date getArrivingDateOnStorage(){
        return delivery.getArrivingDateOnStorage();
    }

    public DeliveryContent(Delivery delivery, int amount, Goods goods) {
        this.delivery = delivery;
        this.amount = amount;
        this.goods = goods;
    }

    public int getDeliveryContentId() {
        return deliveryContentId;
    }

    public void setDeliveryContentId(int deliveryContentId) {
        this.deliveryContentId = deliveryContentId;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
