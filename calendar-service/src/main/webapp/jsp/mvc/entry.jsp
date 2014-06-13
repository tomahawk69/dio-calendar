<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<html>
   <head>
   <title>MVC samples entry</title>
   </head>
   <body>
   <h2>${entry.getId()}</h2>
   <p>Properties</p>
   <table border="1" width="200px">
   <tr>
    <th>Name</th>
    <th>Value</th>
   </tr>
   <tr>
   <td>Subject</td>
   <td>${entry.getSubject()}</td>
   </tr>
   <tr>
   <td>Description</td>
   <td>${entry.getDescription()}&nbsp;</td>
   </tr>
   </table>

   <hr>
   <p><a href="/mvc/entries">Back to entries</a></p>
   </body>
</html>