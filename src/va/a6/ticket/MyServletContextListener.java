package va.a6.ticket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        TicketSale ticketSale = new TicketSale();
        ctx.setAttribute("ticketSale", ticketSale);
        try {
            Context initialContext = new InitialContext();
            Context context = (Context)initialContext.lookup("java:comp/env");
            DataSource dataSource =(DataSource)context.lookup("jdbc/TicketDB");
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("select * from tickets");
            statement.executeUpdate("INSERT INTO tickets (state) VALUES('RESERVED')");
            System.out.println("erfolg");
            ctx.setAttribute("tickets", dataSource);
        } catch (NamingException | SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }
}
