package eugeny.borisov.controller;

import eugeny.borisov.model.CinemaRoom;
import eugeny.borisov.model.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class CinemaController {
    CinemaRoom cinema = new CinemaRoom();

    @GetMapping("seats")
    public CinemaRoom getCinema() {
        return cinema;
    }

    @PostMapping("purchase")
    public ResponseEntity purchaseSeat(@RequestBody Seat seat) {
        return cinema.purchase(seat);
    }

    @PostMapping("return")
    public ResponseEntity returnTicket(@RequestBody String token) {
        return cinema.refund(token);
    }

    @PostMapping("stats")
    public ResponseEntity getStatisticts(@RequestParam(required = false) String password) {
        if (password == null) {
            return new ResponseEntity<>( Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        } else {
            return cinema.getStats(password);
        }

    }
}
