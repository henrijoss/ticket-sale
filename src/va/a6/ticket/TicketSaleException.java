package va.a6.ticket;

public class TicketSaleException extends RuntimeException {

    public static String error;

    public TicketSaleException(String errorMessage) {
        super(errorMessage);
        error = errorMessage;
    }

//    public static String showCorrectError(TicketState ticketState) {
//        String ticketMessage = "Das Ticket ist bereits ";
//        switch (ticketState) {
//            case FREE: return ticketMessage + "frei";
//            case SOLD: return ticketMessage + "verkauft";
//            case RESERVED: return ticketMessage + "reserviert";
//            default: return "error";
//        }
//    }
}

// TODO: Split Exception into TicketSale and Ticket Exception
