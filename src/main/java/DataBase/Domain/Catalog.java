package DataBase.Domain;

import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.*;
import java.util.List;

@Entity
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int detailId;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "detailId")
    List<Goods> goodsList;

    private String goodsName;

    public Catalog(){
    }

    public Catalog(String goodsName){
        this.goodsName = goodsName;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
