package DataBase.Domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int providerId;
    private String providerName;
    private String category;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "providerId")
    List<Goods> goodsList;

    public Provider() {
    }

    public Provider(String providerName, String category) {
        this.providerName = providerName;
        this.category = category;
    }
    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

