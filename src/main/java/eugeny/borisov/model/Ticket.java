package eugeny.borisov.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Ticket {
    @JsonProperty("token")
    private String token;
    @JsonProperty("ticket")
    private Seat ticket;

    public Ticket(Seat seat) {
        this.token = UUID.randomUUID().toString();
        this.ticket = seat;
    }
}
