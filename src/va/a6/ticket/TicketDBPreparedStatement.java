package va.a6.ticket;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDBPreparedStatement {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;

    TicketDBPreparedStatement() {
        ServletContext context = MyServletContextListener.ctx;
        dataSource = (DataSource) context.getAttribute("TicketSaleDB");
    }

    public void updateDB(Ticket ticket) {
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection
                    .prepareStatement("UPDATE tickets SET state = ?, owner = ? WHERE id = ?");
            preparedStatement.setString(1, ticket.getTicketState().toString());
            preparedStatement.setString(2, ticket.getTicketOwner());
            preparedStatement.setInt(3, ticket.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
