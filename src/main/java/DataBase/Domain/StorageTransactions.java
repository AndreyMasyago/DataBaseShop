package DataBase.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class StorageTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_transactions_generator")
    @SequenceGenerator(name = "storage_transactions_generator", sequenceName = "storage_transactions_idx", allocationSize = 1)
    private int storageTransactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goodsId")
    private Goods goods;

    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cellsId")
    private Storage storage;

    private Date transactionDate;

    public StorageTransactions() {
    }

    public StorageTransactions(
            Goods goods, int amount,
            Storage storage, Date transactionDate) {
        this.goods = goods;
        this.amount = amount;
        this.storage = storage;
        this.transactionDate = transactionDate;
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
    public Integer getCellsId() {return storage.getCellsId();}

    @JsonIgnore
    public Integer getCellsSize() {return storage.getCellsSize();}

    public int getStorageTransactionId() {
        return storageTransactionId;
    }

    public void setStorageTransactionId(int storageTransactionId) {
        this.storageTransactionId = storageTransactionId;
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

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
