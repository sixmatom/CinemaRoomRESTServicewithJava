package cinema;

import org.springframework.http.ResponseEntity;

import java.util.*;

public class CinemaResponse {
    private int rows;
    private int columns;
    private List<Map<String, Object>> seats;


    public CinemaResponse(int rows, int columns,List<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = availableSeats(seats);
    }

    public Object getRows() {
        return rows;
    }

    public Object getColumns() {
        return columns;
    }

    public List<Map<String, Object>> availableSeats(List<Seat> seats) {
        List<Map<String, Object>> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (!seat.isPurchased()) {
                Map<String, Object> seatInfo = new HashMap<>();
                seatInfo.put("row", seat.getRow());
                seatInfo.put("column", seat.getColumn());
                seatInfo.put("price", seat.getPrice());
                availableSeats.add(seatInfo);
            }
        }
        return availableSeats;
    }
    public Object getAvailableSeats() {
        return seats;
    }

    public ResponseEntity returnTicket(String token, List<Seat> seats) {
        if (getSeatForToken(token, seats) == null)  {
            return ResponseEntity.badRequest().body(new ErrorResponse("Wrong token!"));
        }
        Seat seat = getSeatForToken(token, seats);
        seat.cancel();
        Map<String, Object> ticketDetails = new HashMap<>();
        Map<String, Object> responseBody = new HashMap<>();
        ticketDetails.put("row", seat.getRow());
        ticketDetails.put("column", seat.getColumn());
        ticketDetails.put("price", seat.getPrice());
        responseBody.put("ticket", ticketDetails);
        return ResponseEntity.ok(responseBody);
    }
    private Seat getSeatForToken(String token, List<Seat> seats) {
        for (Seat seat : seats) {

            if (token == null) {
                return null;
            } else if (token.equals(seat.getToken())) {

                return seat;
            }
        }
        return null;


    }
    public ResponseEntity buySeat(Seat seatRequest, List<Seat> seats) {
        if (seatRequest.getRow() < 1 || seatRequest.getRow() > rows ||
                seatRequest.getColumn() < 1 || seatRequest.getColumn() > columns) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The number of a row or a column is out of bounds!"));
        }

        for (Seat seat : seats) {
            if (seat.getRow() == seatRequest.getRow() && seat.getColumn() == seatRequest.getColumn()) {
                if (seat.isPurchased()) {
                    return ResponseEntity.badRequest().body(new ErrorResponse("The ticket has been already purchased!"));
                }
                String token = UUID.randomUUID().toString();
                seat.purchase(token);
                Map<String, Object> responseBody = ResponseUtil.generateResponse(seat);
                return ResponseEntity.ok(responseBody);

            }
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("The seat does not exist!"));
    }
}