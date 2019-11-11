package va.a6.ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TicketOperator")
public class TicketOperator extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            if (operate(request)) {
                request.getRequestDispatcher("/Operation_erfolgreich_ausgefuehrt.html").forward(request, response);
            } else {
                request.getRequestDispatcher("/Fehler.jsp").forward(request, response);
//                response.sendRedirect("Fehler.html");
            }
        } catch (NumberFormatException | ServletException e) {
            request.getRequestDispatcher("/Fehler.jsp").forward(request, response);
        }
    }

    public boolean operate(HttpServletRequest request) {
        TicketSale ticketSale = (TicketSale) request.getServletContext().getAttribute("ticketSale");
        String operation = request.getParameter("operation");
        switch (operation) {
            case "buyTicket":
                int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber"));
                return ticketSale.buyTicket(ticketSale.getTickets()[ticketNumber-1]);
            case "reservateTicket":
                int ticketReservationNumber = Integer.parseInt(request.getParameter("ticketNumber"));
                String ticketOwner = request.getParameter("ticketOwner");
                return ticketSale.reserveTicket(ticketSale.getTickets()[ticketReservationNumber-1], ticketOwner);
            case "cancelTicket":
                int ticketNumberToCancel = Integer.parseInt(request.getParameter("ticketNumber"));
                return ticketSale.cancelTicket(ticketSale.getTickets()[ticketNumberToCancel-1]);
            case "cancelReservation":
                int ticketReservationNumberToCancel = Integer.parseInt(request.getParameter("ticketNumber"));
                return ticketSale.cancelTicketReservation(ticketSale.getTickets()[ticketReservationNumberToCancel-1]);
            case "stopReservations":
                ticketSale.setReservationsPossible(false);
                return true;
            default:
                return false;
        }
    }
}