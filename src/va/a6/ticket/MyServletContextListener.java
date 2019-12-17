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
            TicketSale ticketSale = new TicketSale(getTicketsFromDB(dataSource), getOptionsFromDB(dataSource));
            ctx.setAttribute("ticketSale", ticketSale);
            System.out.println("Database loaded successfully");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> getTicketsFromDB(DataSource dataSource) {
        String sql = "SELECT * FROM tickets";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Ticket> list = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("id"));
                ticket.setTicketState(TicketState.valueOf(rs.getString("state")));
                ticket.setTicketOwner(rs.getString("owner"));
                list.add(ticket);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
    }

    public boolean getOptionsFromDB(DataSource dataSource) {
        String sql = "SELECT reservationsPossible FROM options";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            boolean reservationsPossible = true;
            while (rs.next()) {
                reservationsPossible = rs.getBoolean("reservationsPossible");
            }
            return reservationsPossible;
        } catch (SQLException e) {
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
    }
}
