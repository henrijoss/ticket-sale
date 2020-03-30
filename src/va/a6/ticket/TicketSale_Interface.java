package va.a6.ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TicketSale_Interface extends Remote {
    boolean isReservationsPossible() throws RemoteException;
    void setReservationsPossible(boolean reservationsPossible) throws RemoteException;
    List<Ticket> getTickets() throws RemoteException;
    boolean buyTicket(Ticket ticket) throws RemoteException;
    boolean reserveTicket(Ticket ticket, String reservationName) throws RemoteException;
    boolean cancelTicketReservation(Ticket ticket) throws RemoteException;
    boolean cancelTicket(Ticket ticket) throws RemoteException;
    boolean isTicketInCorrectState(TicketState ticketState, Ticket ticket) throws RemoteException;
}
