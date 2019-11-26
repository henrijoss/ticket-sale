<%@ page import="va.a6.ticket.TicketSale" %>
  Created by IntelliJ IDEA.
  User: henrijoss
  Date: 05.11.19
  Time: 09:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kartenverkauf</title>
    <link rel="stylesheet" href="style/style.css"/>
</head>
<body>

<%
    ServletContext sc = request.getServletContext();
    TicketSale ticketSale = (TicketSale) sc.getAttribute("ticketSale");
%>
<h1>Kartenverkauf</h1>
<div class="ticket-container">
    <%
        for (int i = 0; i < ticketSale.getTickets().size(); i++) {
    %>
    <div class="ticket <%= ticketSale.getTickets().get(i).getTicketState() %>">
        <p>
            <%= ticketSale.getTickets().get(i).getId() %>
        </p>
    </div>
    <%
        }
    %>
</div>
<%
    if (ticketSale.isReservationsPossible()) {
%>
<p>Reservierungen können noch angenommen werden</p>
<%
} else {
%>
<p>Reservierungen nicht mehr möglich</p>
<%
    }
%>

<a href="Verkauf_eines_freien_Tickets.html">Verkauf eines freien Tickets</a><br />
<%
    if (ticketSale.isReservationsPossible()) {
%>
<a href="Reservierung_eines_Tickets.html">Reservierung eines Tickets</a><br />
<%
} else {
%>
<p><s>Reservierung eines Tickets</s></p>
<%
    }
%>
<a href="Verkauf_eines_reservierten_Tickets.html">Verkauf eines reservierten Tickets</a><br />
<a href="Stornierung_eines_Tickets.html">Stornierung eines Tickets</a><br />
<a href="Reservierungen_aufheben.html">Reservierungen aufheben</a>

</body>
</html>
