package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CinemaController {
    private final int rows = 9;
    private final int columns = 9;
    private final List<Seat> seats = new ArrayList<>();


    public CinemaController() {
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= columns; col++) {
                seats.add(new Seat(row, col));
            }
        }
    }

    @GetMapping("/seats")
    public ResponseEntity<Map<String, Object>> getSeats() {
        CinemaResponse response = new CinemaResponse(rows, columns, seats);
        return ResponseEntity.ok(ResponseUtil.generateResponse(response));
    }



    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody Seat seatRequest) {
        CinemaResponse response = new CinemaResponse(rows, columns, seats);
        return response.buySeat(seatRequest, seats);


    }

    @PostMapping("/return")
    public ResponseEntity<?> refundTicket(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        CinemaResponse response = new CinemaResponse(rows, columns, seats);
        return response.returnTicket(token, seats) ;
    }



    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        if (!"super_secret".equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("The password is wrong!"));
        }
        Stats stats = new Stats(seats);
        Map<String, Object> responseBody = ResponseUtil.generateResponse(stats);
        return ResponseEntity.ok(responseBody);
    }
}

