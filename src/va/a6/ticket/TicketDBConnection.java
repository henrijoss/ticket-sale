package va.a6.ticket;

import java.sql.*;

public class TicketDBConnection {

    public static void main(String[] args) {
        connect();
    }

    public static void connect() {

        try {
            // create our mysql database connection
//            String myDriver = "org.mariadb.jdbc.Driver";
            String myUrl = "jdbc:mariadb://localhost:3306/TicketSale";
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM tickets";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                String state = rs.getString("state");
                String owner = rs.getString("owner");

                // print the results
                System.out.print(id + " " + state + " "  + owner);
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}

