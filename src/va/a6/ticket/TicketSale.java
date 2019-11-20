package va.a6.ticket;

public class TicketSale {
    private boolean reservationsPossible = true;

    private Ticket[] tickets = new Ticket[100];

    TicketSale() {
        for (int i= 0; i < tickets.length; i++) {
            tickets[i] = new Ticket(i+1);
        }
    }

    public synchronized boolean isReservationsPossible() {
        return reservationsPossible;
    }

    public synchronized void setReservationsPossible(boolean reservationsPossible) {
        this.reservationsPossible = reservationsPossible;
    }

    public synchronized Ticket[] getTickets() {
        return tickets;
    }

//    public void setTickets(Ticket[] tickets) {
//        this.tickets = tickets;
//    }

    public synchronized boolean buyTicket(Ticket ticket) {
        if (checkTicketState(TicketState.FREE, ticket)) {
            ticket.setTicketState(TicketState.SOLD);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean reserveTicket(Ticket ticket, String reservationName) {
        if (reservationsPossible) {
            if (checkTicketState(TicketState.FREE, ticket)) {
                ticket.setTicketState(TicketState.RESERVED);
                ticket.setTicketOwner(reservationName);
                return true;
            }
            throw new TicketException(ticket.getTicketState());
        }
        throw new TicketSaleException("Reservierungen sind nicht mehr möglich");
    }

    public synchronized boolean cancelTicketReservation(Ticket ticket) {
        if (checkTicketState(TicketState.RESERVED, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            ticket.setTicketOwner(null);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean cancelTicket(Ticket ticket) {
        if (checkTicketState(TicketState.SOLD, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    private synchronized boolean checkTicketState(TicketState ticketState, Ticket ticket) {
        return ticket.getTicketState() == ticketState;
    }
}
