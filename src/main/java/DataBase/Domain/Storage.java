package DataBase.Domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_generator")
    @SequenceGenerator(name = "storage_generator", sequenceName = "storage_idx", allocationSize = 1)
    private int cellsId;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "cellsId")
    List<StorageTransactions> storageTransactionsList;

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
