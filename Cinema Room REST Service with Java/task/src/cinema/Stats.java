package cinema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {
    int income = 0;
    int available = 0;
    int purchased = 0;

    public Stats(List<Seat> seats) {


        for (Seat seat : seats) {
            if (seat.isPurchased()) {
                income += seat.getPrice();
                purchased++;
            } else {
                available++;
            }
        }




    }

}
