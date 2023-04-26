package eugeny.borisov.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Seat {

    private int row;
    private int column;
    private int price;


    public Seat(@JsonProperty("row") int row, @JsonProperty("column") int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
    }
}