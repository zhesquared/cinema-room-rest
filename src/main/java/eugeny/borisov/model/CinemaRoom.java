package eugeny.borisov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Component
public class CinemaRoom {

    @JsonPropertyOrder({"total_rows", "total_columns", "available_seats"})
    @JsonProperty("total_rows")
    private int totalRows = 9;
    @JsonProperty("total_columns")
    private int totalColumns = 9;
    @JsonProperty("available_seats")
    private List<Seat> availableSeats = new ArrayList<>();
    @JsonIgnore
    private Map<String, Seat> bookedTicket = new HashMap<>();
    @JsonIgnore
    private int currentIncome = 0;

    {
        for (int i = 1; i <= this.totalRows; i++) {
            for (int j = 1; j <= this.totalColumns; j++) {
                this.availableSeats.add(new Seat(i, j));
            }
        }
    }

    public void deleteFromAvailableSeats(Seat seat) {
        this.availableSeats.remove(seat);
    }

    public ResponseEntity purchase(Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > this.getTotalRows() ||
                seat.getColumn() < 1 || seat.getColumn() > this.getTotalColumns()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "The number of a row or a column is out of bounds!"));
        } else if (!this.getAvailableSeats().contains(seat)) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "The ticket has been already purchased!")
            );
        }

        Ticket ticket = new Ticket(seat);
        this.deleteFromAvailableSeats(seat);
        this.bookedTicket.put(ticket.getToken(), ticket.getTicket());
        currentIncome += seat.getPrice();
        return ResponseEntity.ok(ticket);
    }

    public ResponseEntity refund(String token) {
        String[] parsedToken = token.split(":");
        String refundToken = parsedToken[1].
                replaceAll("\"", "").
                replaceAll("}", "").
                strip();
        if (bookedTicket.containsKey(refundToken)) {
            Seat refundSeat = bookedTicket.get(refundToken);
            availableSeats.add(refundSeat);
            currentIncome -= refundSeat.getPrice();
            bookedTicket.remove(refundToken);
            return ResponseEntity.ok(Map.of("returned_ticket", refundSeat));
        } else {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Wrong token!")
            );
        }
    }

    public ResponseEntity getStats(String password) {
        if (password.equals("super_secret")) {
            return ResponseEntity.ok().body(
                    new TreeMap<>(Map.of(
                            "number_of_purchased_tickets", this.bookedTicket.size(),
                            "number_of_available_seats", this.getAvailableSeats().size(),
                            "current_income", currentIncome
                    ))
            );
        } else {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "The password is wrong!")
            );
        }
    }
}
