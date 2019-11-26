package va.a6.ticket;

import java.util.List;

public class TicketSale {

    private TicketDBPreparedStatement preparedStatement = new TicketDBPreparedStatement();
    private boolean reservationsPossible = true;
    private List<Ticket> tickets;

    TicketSale(List<Ticket> _tickets) {
        this.tickets = _tickets;
    }

    public synchronized boolean isReservationsPossible() {
        return reservationsPossible;
    }

    public synchronized void setReservationsPossible(boolean reservationsPossible) {
        this.reservationsPossible = reservationsPossible;
    }

    public synchronized List<Ticket> getTickets() {
        return tickets;
    }

//    public void setTickets(Ticket[] tickets) {
//        this.tickets = tickets;
//    }

    public synchronized boolean buyTicket(Ticket ticket) {
        if (checkTicketState(TicketState.FREE, ticket)) {
            ticket.setTicketState(TicketState.SOLD);
            preparedStatement.updateDB(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean reserveTicket(Ticket ticket, String reservationName) {
        if (reservationsPossible) {
            if (checkTicketState(TicketState.FREE, ticket)) {
                ticket.setTicketState(TicketState.RESERVED);
                ticket.setTicketOwner(reservationName);
                preparedStatement.updateDB(ticket);
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
            preparedStatement.updateDB(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean cancelTicket(Ticket ticket) {
        if (checkTicketState(TicketState.SOLD, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            preparedStatement.updateDB(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    private synchronized boolean checkTicketState(TicketState ticketState, Ticket ticket) {
        return ticket.getTicketState() == ticketState;
    }
}
