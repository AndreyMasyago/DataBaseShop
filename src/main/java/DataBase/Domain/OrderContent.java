package DataBase.Domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class OrderContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderContentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goodsId")
    private Goods goods;

    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private OrderEntity orderEntity;

    public OrderContent() {
    }

    public OrderContent(Goods goods, int amount, OrderEntity orderEntity) {
        this.goods = goods;
        this.amount = amount;
        this.orderEntity = orderEntity;
    }

    public Integer getDetailId() { return goods.getDetailId(); }
    public Integer getGoodsId() {return goods.getGoodsId(); }
    public String getGoodsName() { return goods.getGoodsName(); }
    public Integer getSize() { return goods.getSize();}
    public Integer getDeliveryTime() { return goods.getDeliveryTime(); }
    public Integer getPurchasePrice() { return goods.getPurchasePrice(); }
    public Integer getSellingPrice() { return goods.getSellingPrice(); }
    public String getProducer() { return goods.getProducer(); }
    public String getProviderName() { return goods.getProviderName(); }
    public String getProviderCategory() { return goods.getProviderCategory(); }

    public Date getOrderDate() { return orderEntity.getOrderDate();}
    public int getOrderId() {return orderEntity.getOrderId();}

    public int getOrderContentId() {
        return orderContentId;
    }

    public void setOrderContentId(int rejectId) {
        this.orderContentId = rejectId;
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
