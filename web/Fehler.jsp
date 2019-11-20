<%@ page import="va.a6.ticket.TicketException" isErrorPage="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Kartenverkauf</title>
</head>
<body>
    <h1>Fehler</h1>
    <p>Die Operation konnte nicht ausgeführt werden</p>
    <p>Ursache: <%= ((TicketException)exception).showCorrectError() %></p>
    <a href="index.jsp">Zurück zur Startseite</a>
</body>
</html>