package va.a6.ticket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
//            Registry registry = startRegistry();
            TicketSale ticketSale = new TicketSale(getTicketsFromDB(dataSource), getOptionsFromDB(dataSource));
            ctx.setAttribute("ticketSale", ticketSale);
//            registry.bind("TicketSale", ticketSale);
            System.out.println("RMI started on port 1099");
            System.out.println("Database loaded successfully");
        } catch (NamingException | RemoteException e) {
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

    private Registry startRegistry() {
        Registry registry = null;
        try {
            registry = LocateRegistry
                    .createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return registry;
    }
}
