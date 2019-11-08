<%--
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
    <link rel="stylesheet" href="style/style.css" />
  </head>
  <body>
  <%!
    private int counter = 0;
    public synchronized int next() {
      counter++;
      return counter;
    }
  %>
  <h1>Kartenverkauf</h1>
  <table>
    <%
      for (int i = 1; i < 11; i++) {
    %>
        <tr>
          <%
            for (int j = 1; j < 11; j++) {
              %>
                <td><%= next() %></td>
              <%
            }
          %>
        </tr>
    <%
      }
    %>
  </table>
  </body>
</html>
