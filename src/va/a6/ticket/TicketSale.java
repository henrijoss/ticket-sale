package va.a6.ticket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TicketSale extends UnicastRemoteObject implements TicketSale_Interface {

    private TicketDBController ticketDBController = new TicketDBController();
    private boolean reservationsPossible;
    private List<Ticket> tickets;

    TicketSale(List<Ticket> _tickets, boolean _reservationsPossible) throws RemoteException {
        this.tickets = _tickets;
        this.reservationsPossible = _reservationsPossible;
    }

    public synchronized boolean isReservationsPossible() {
        return reservationsPossible;
    }

    public synchronized void setReservationsPossible(boolean reservationsPossible) {
        this.reservationsPossible = reservationsPossible;
        ticketDBController.updateOptionsTable(reservationsPossible);
    }

    public synchronized List<Ticket> getTickets() {
        return tickets;
    }

    public synchronized boolean buyTicket(Ticket ticket) {
        if (isTicketInCorrectState(TicketState.FREE, ticket)) {
            ticket.setTicketState(TicketState.SOLD);
            ticketDBController.updateTicketTable(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean reserveTicket(Ticket ticket, String reservationName) {
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

    public synchronized boolean cancelTicketReservation(Ticket ticket) {
        if (isTicketInCorrectState(TicketState.RESERVED, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            ticket.setTicketOwner(null);
            ticketDBController.updateTicketTable(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean cancelTicket(Ticket ticket) {
        if (isTicketInCorrectState(TicketState.SOLD, ticket)) {
            ticket.setTicketState(TicketState.FREE);
            ticketDBController.updateTicketTable(ticket);
            return true;
        }
        throw new TicketException(ticket.getTicketState());
    }

    public synchronized boolean isTicketInCorrectState(TicketState ticketState, Ticket ticket) {
        return ticketDBController.getTicketStateFromDB(ticket.getId()).equals(ticketState);
    }
}
