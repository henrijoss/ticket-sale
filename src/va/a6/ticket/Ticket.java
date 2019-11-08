package va.a6.ticket;

public class Ticket {
    private TicketState ticketState = TicketState.FREE;
    private String ticketOwner;
    private int id;

    Ticket(int _id) {
        setId(_id);
    }

    public TicketState getTicketState() {
        return ticketState;
    }

    public void setTicketState(TicketState ticketState) {
        this.ticketState = ticketState;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public void setTicketOwner(String ticketOwner) {
        this.ticketOwner = ticketOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
