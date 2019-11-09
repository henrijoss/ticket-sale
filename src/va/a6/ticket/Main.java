package va.a6.ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Main")
public class Main extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TicketSale ticketSale = (TicketSale) request.getServletContext().getAttribute("ticketSale");
        try {
            int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber"));

            if (ticketSale.buyTicket(ticketSale.getTickets()[ticketNumber-1])){
                request.getRequestDispatcher("/Operation_erfolgreich_ausgefuehrt.html").forward(request, response);
//                response.sendRedirect("Operation_erfolgreich_ausgefuehrt.html");
            } else {
                response.sendRedirect("Fehler.html");
            }

        } catch (NumberFormatException | ServletException e) {
            response.sendRedirect("Fehler.html");
        }

    }

//    public static void main(String[] args) {
//
////        for (int i = 0; i < ticketSale.getTickets().length; i++) {
////            System.out.println(ticketSale.getTickets()[i].getId());
////        }
//
//        System.out.println(ticketSale.getTickets()[3].getTicketState());
//        ticketSale.buyTicket(ticketSale.getTickets()[3]);
//        System.out.println(ticketSale.getTickets()[3].getTicketState());
//        ticketSale.cancelTicket(ticketSale.getTickets()[3]);
//        System.out.println(ticketSale.getTickets()[3].getTicketState());
//        ticketSale.reserveTicket(ticketSale.getTickets()[3], "Henri");
//        System.out.println(ticketSale.getTickets()[3].getTicketState());
//        ticketSale.cancelTicketReservation(ticketSale.getTickets()[3]);
//        System.out.println(ticketSale.getTickets()[3].getTicketState());
//    }
}
