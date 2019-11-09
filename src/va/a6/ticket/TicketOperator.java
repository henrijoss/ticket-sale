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
        TicketSale ticketSale = (TicketSale) request.getServletContext().getAttribute("ticketSale");
        System.out.println(request.getHeader("referer"));

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
}