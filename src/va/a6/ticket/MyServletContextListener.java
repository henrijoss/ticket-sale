package va.a6.ticket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        TicketSale ticketSale = new TicketSale();
        ctx.setAttribute("ticketSale", ticketSale);
        DataSource dataSource;
        try {
            Context initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/TicketDB");
            Connection connection = dataSource.getConnection();
            System.out.println("erfolg");
            ctx.setAttribute("tickets", dataSource);
            ctx.setAttribute("initialStates", getInitialStates(dataSource));
        } catch (SQLException | NamingException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public List<Ticket> getInitialStates(DataSource dataSource){
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            List<Ticket> list = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT * FROM tickets");

            while (rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("id"));
                ticket.setTicketState(TicketState.FREE);
                ticket.setTicketOwner(rs.getString("owner"));
                list.add(ticket);
            }
            return list;
        } catch (SQLException e){
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
    }
}
