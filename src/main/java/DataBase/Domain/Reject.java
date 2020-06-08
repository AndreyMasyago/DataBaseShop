package DataBase.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Reject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reject_generator")
    @SequenceGenerator(name = "reject_generator", sequenceName = "reject_idx", allocationSize = 1)
    private int rejectId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goodsId")
    private Goods goods;

    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private OrderEntity orderEntity;

    public Reject() {
    }

    public Reject(Goods goods, int amount, OrderEntity orderEntity) {
        this.goods = goods;
        this.amount = amount;
        this.orderEntity = orderEntity;
    }

    @JsonIgnore
    public Integer getDetailId() { return goods.getDetailId(); }

    @JsonIgnore
    public Integer getGoodsId() {return goods.getGoodsId(); }

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
    public Date getOrderDate() { return orderEntity.getOrderDate();}

    @JsonIgnore
    public int getOrderId() {return orderEntity.getOrderId();}

    public int getRejectId() {
        return rejectId;
    }

    public void setRejectId(int rejectId) {
        this.rejectId = rejectId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
