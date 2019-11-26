package va.a6.ticket;

public class TicketException extends RuntimeException {

    public TicketState ticketState;

    public TicketException(TicketState _ticketState) {
        this.ticketState = _ticketState;
    }

    public String showCorrectError() {
        String ticketMessage = "Das Ticket ist bereits ";
        switch (this.ticketState) {
            case FREE: return ticketMessage + "frei";
            case SOLD: return ticketMessage + "verkauft";
            case RESERVED: return ticketMessage + "reserviert";
            default: return "error";
        }
    }

    @Override
    public String getMessage(){
        return showCorrectError();
    }

}