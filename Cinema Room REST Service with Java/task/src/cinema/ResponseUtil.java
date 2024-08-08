package cinema;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static <T> Map<String, Object> generateResponse(T object) {
        Map<String, Object> response = new HashMap<>();

        // Check the type of object and fill the response accordingly
        if (object instanceof Seat) {
            Map<String, Object> ticketDetails = new HashMap<>();
            Seat seat = (Seat) object;
            ticketDetails.put("row", seat.getRow());
            ticketDetails.put("column", seat.getColumn());
            ticketDetails.put("price", seat.getPrice());
            response.put("token", seat.getToken());
            response.put("ticket", ticketDetails);
        } else if (object instanceof ErrorResponse) {
            ErrorResponse error = (ErrorResponse) object;
            response.put("error", error.getError());
        } else if (object instanceof CinemaResponse) {
            CinemaResponse cinemaResponse = (CinemaResponse) object;
            response.put("rows", cinemaResponse.getRows());
            response.put("columns", cinemaResponse.getColumns());
            response.put("seats", cinemaResponse.getAvailableSeats());
        } else if (object instanceof Stats) {
            Stats stats = (Stats) object;
            response.put("income", stats.income);
            response.put("available", stats.available);
            response.put("purchased", stats.purchased);
        }
        // Add more object types as needed

        return response;
    }
}