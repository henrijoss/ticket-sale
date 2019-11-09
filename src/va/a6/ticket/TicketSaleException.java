package va.a6.ticket;

public class TicketSaleException extends RuntimeException {

    public static String error;

    public TicketSaleException(TicketState ticketState) {
        super("Das Ticket ist bereits " + showCorrectError(ticketState));
        error = "Das Ticket ist bereits " + showCorrectError(ticketState);
    }

    public static String showCorrectError(TicketState ticketState) {
        switch (ticketState) {
            case FREE: return "frei";
            case SOLD: return "verkauft";
            case RESERVED: return "reserviert";
            default: return "error";
        }
    }
}
