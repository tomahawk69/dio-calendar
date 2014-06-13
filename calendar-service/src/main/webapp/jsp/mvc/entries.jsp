<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<html>
   <head>
   <title>MVC samples entries</title>
   </head>
   <body>
   <h2>${message}</h2>
   <p>Entries</p>
   <c:forEach var="entry" items="${entries}">
   <s:url value="/mvc/entry?id={entryId}" var="url">
    <s:param name="entryId" value="${entry.getId()}" />
   </s:url>
   <a href="${url}">${entry.getSubject()}</a><br/>
   </c:forEach>
   <hr />
   <p><a href="/mvc/">Back to MVC index</a></p>
   </body>
</html>