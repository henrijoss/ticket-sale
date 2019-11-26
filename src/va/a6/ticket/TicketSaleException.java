package va.a6.ticket;

public class TicketSaleException extends RuntimeException {


    public TicketSaleException(String errorMessage) {
        super(errorMessage);
    }
}
