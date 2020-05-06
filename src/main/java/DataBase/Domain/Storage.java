package DataBase.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int cellsId;
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
