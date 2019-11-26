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

    static ServletContext ctx;

    public void contextInitialized(ServletContextEvent sce) {
        ctx = sce.getServletContext();
        DataSource dataSource;
        try {
            Context initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/TicketDB");
            ctx.setAttribute("TicketSaleDB", dataSource);
            Connection connection = dataSource.getConnection();
            TicketSale ticketSale = new TicketSale(getTicketsFromDB(dataSource));
            ctx.setAttribute("ticketSale", ticketSale);
        } catch (SQLException | NamingException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public List<Ticket> getTicketsFromDB(DataSource dataSource){
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
