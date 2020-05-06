package DataBase.Domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class StorageTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Integer getCellsId() {return storage.getCellsId();}
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
