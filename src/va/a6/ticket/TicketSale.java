package va.a6.ticket;

import java.util.List;

public class TicketSale {

    private TicketDBController ticketDBController = new TicketDBController();
    private boolean reservationsPossible;
    private List<Ticket> tickets;

    TicketSale(List<Ticket> _tickets, boolean _reservationsPossible) {
        this.tickets = _tickets;
        this.reservationsPossible = _reservationsPossible;
    }

    public synchronized boolean isReservationsPossible() {
        return reservationsPossible;
    }

    synchronized void setReservationsPossible(boolean reservationsPossible) {
        this.reservationsPossible = reservationsPossible;
        ticketDBController.updateOptionsTable(reservationsPossible);
    }

    public synchronized List<Ticket> getTickets() {
        return tickets;
    }

    synchronized boolean buyTicket(Ticket ticket) {
        if (isTicketInCorrectState(TicketState.FREE, ticket)) {
            ticket.setTicketState(TicketState.SOLD);
            ticketDBController.updateTicketTable(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    synchronized boolean reserveTicket(Ticket ticket, String reservationName) {
        if (reservationsPossible) {
            if (isTicketInCorrectState(TicketState.FREE, ticket)) {
                ticket.setTicketState(TicketState.RESERVED);
                ticket.setTicketOwner(reservationName);
                ticketDBController.updateTicketTable(ticket);
                return true;
            }
            throw new TicketException(ticket.getTicketState());
        }
        throw new TicketSaleException("Reservierungen sind nicht mehr möglich");
    }

    synchronized boolean cancelTicketReservation(Ticket ticket) {
        if (isTicketInCorrectState(TicketState.RESERVED, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            ticket.setTicketOwner(null);
            ticketDBController.updateTicketTable(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    synchronized boolean cancelTicket(Ticket ticket) {
        if (isTicketInCorrectState(TicketState.SOLD, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            ticketDBController.updateTicketTable(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    private synchronized boolean isTicketInCorrectState(TicketState ticketState, Ticket ticket) {
        return ticketDBController.getTicketStateFromDB(ticket.getId()).equals(ticketState);
    }
}
