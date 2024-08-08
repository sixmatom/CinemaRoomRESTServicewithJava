package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private final int row;
    private final int column;
    private int price;
    @JsonIgnore
    private String token;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8; // Price based on row number
        this.token = null;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isPurchased() {
        if (this.token == null) {
            return false;
        } else {
            return true;
        }
    }

    public void purchase(String token) {
        this.token = token;
    }

    public void cancel() {
        this.token = null;
    }

    public int getPrice() {
        return this.price;
    }
public String getToken() {
        return this.token;
}

}
