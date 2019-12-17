package va.a6.ticket;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDBController {

    private DataSource dataSource;

    TicketDBController() {
        ServletContext context = MyServletContextListener.ctx;
        dataSource = (DataSource) context.getAttribute("TicketSaleDB");
    }

    public void updateTicketTable(Ticket ticket) {
        String sql = "UPDATE tickets SET state = ?, owner = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try {
                preparedStatement.setString(1, ticket.getTicketState().toString());
                preparedStatement.setString(2, ticket.getTicketOwner());
                preparedStatement.setInt(3, ticket.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public TicketState getTicketStateFromDB(int id) {
        String state = "";
        String sql = "SELECT state FROM tickets WHERE id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                state = resultSet.getString("state");
            }
            return TicketState.valueOf(state);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void updateOptionsTable(boolean reservationsPossible) {
        String sql = "UPDATE options SET reservationsPossible = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservationsPossible ? 1 : 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
