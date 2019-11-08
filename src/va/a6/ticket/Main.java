package va.a6.ticket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/index")

public class Main extends HttpServlet {
    private static TicketSale ticketSale = new TicketSale();

    public static void main(String[] args) {

//        for (int i = 0; i < ticketSale.getTickets().length; i++) {
//            System.out.println(ticketSale.getTickets()[i].getId());
//        }

        System.out.println(ticketSale.getTickets()[3].getTicketState());
        ticketSale.buyTicket(ticketSale.getTickets()[3]);
        System.out.println(ticketSale.getTickets()[3].getTicketState());
        ticketSale.cancelTicket(ticketSale.getTickets()[3]);
        System.out.println(ticketSale.getTickets()[3].getTicketState());
        ticketSale.reserveTicket(ticketSale.getTickets()[3], "Henri");
        System.out.println(ticketSale.getTickets()[3].getTicketState());
        ticketSale.cancelTicketReservation(ticketSale.getTickets()[3]);
        System.out.println(ticketSale.getTickets()[3].getTicketState());
    }
}
