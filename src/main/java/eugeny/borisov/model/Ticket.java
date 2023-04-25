package eugeny.borisov.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {
    @JsonProperty("token")
    private String token;
    @JsonProperty("ticket")
    private Seat ticket;

    public Ticket(Seat seat) {
        this.token = UUID.randomUUID().toString();
        this.ticket = seat;
    }

    public String getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }
}
