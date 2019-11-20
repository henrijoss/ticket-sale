package va.a6.ticket;

import javax.servlet.jsp.PageContext;

public class TicketException extends RuntimeException {

//    public static String error;
    public TicketState ticketState;

    public TicketException(TicketState _ticketState) {
//        super(showCorrectError(ticketState));
//        error = showCorrectError(ticketState);
//        ptx.setAttribute("error", showCorrectError(ticketState));
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
}