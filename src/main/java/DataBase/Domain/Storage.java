package DataBase.Domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cellsId;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cellsId")
    List<StorageTransactions> storageTransactions;

    private int cellsSize;

    public Storage(){
    }

    public Storage(int cellsSize){
        this.cellsSize = cellsSize;
    }

    public int getCellsId() {
        return cellsId;
    }

    public void setCellsId(int cellsId) {
        this.cellsId = cellsId;
    }

    public int getCellsSize() {
        return cellsSize;
    }

    public void setCellsSize(int cellsSize) {
        this.cellsSize = cellsSize;
    }
}
