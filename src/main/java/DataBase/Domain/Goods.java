package DataBase.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goods_generator")
    @SequenceGenerator(name = "goods_generator", sequenceName = "goods_idx", allocationSize = 1)
    private int goodsId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detailId")
    private Catalog catalog;

    private int size;
    private int deliveryTime;
    private int purchasePrice;
    private int sellingPrice;
    private String producer;
    private Timestamp sellingPriceLastModified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "providerId")
    private Provider provider;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "goodsId")
    List<StorageTransactions> storageTransactionsList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "goodsId")
    List<OrderContent> orderContentList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "goodsId")
    List<Reject> rejectList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "goodsId")
    List<DeliveryContent> deliveryContentList;

    public Goods() {
    }

    public Goods(Catalog catalog, int size, int deliveryTime, int purchasePrice, int sellingPrice, String producer, Provider provider) {
        this.catalog = catalog;
        this.size = size;
        this.deliveryTime = deliveryTime;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.producer = producer;
        this.provider = provider;
    }

    @JsonIgnore
    public String getGoodsName(){
        return catalog.getGoodsName();
    }

    @JsonIgnore
    public Integer getDetailId(){
        return catalog.getDetailId();
    }

    @JsonIgnore
    public String getProviderName(){
        return provider.getProviderName();
    }

    @JsonIgnore
    public String getProviderCategory(){
        return provider.getCategory();
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Timestamp getSellingPriceLastModified() { return sellingPriceLastModified; }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
