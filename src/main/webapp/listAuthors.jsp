<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link href="css/sidebar.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body>

        <div class="panel panel-primary">
            <div class="panel-heading"><h1>Author List</h1></div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-1">
                        <nav class="nav-sidebar">
                            <ul class="nav">
                                <li><b>Menu</b></li>
                                <li class="nav-divider"></li>
                                <li><a href="index.html">Home</a></li>
                                <li class="active"><a href="AuthorController?action=list">List All Authors</a></li>

                            </ul>
                        </nav>
                    </div>
                    <div class="col-sm-6">
                        <form method="POST" id="edit" name="edit" action="AuthorController?action=addEditDelete">
                            <table class="table table-striped table-condensed">
                                <tr>
                                    <th align="left" class="tableHead">ID</th>
                                    <th align="left" class="tableHead">Author Name</th>
                                    <th align="right" class="tableHead">Date Added</th>
                                </tr>
                                <c:forEach var="a" items="${authors}" varStatus="rowCount">

                                    <td align="left">
                                        <input type="checkbox" name="authorId" value="${a.authorId}" />

                                    </td>
                                    <td align="left">${a.authorName}</td>
                                    <td>
                                        <fmt:formatDate pattern="M/d/yyyy" value="${a.dateCreated}"></fmt:formatDate>
                                   </td>
                                        </tr>
                                </c:forEach>
                            </table>
                            <br>
                            <input type="submit" value="Add/Edit Author" name="addEdit" />&nbsp;
                            <input type="submit" value="Delete" name="delete" />
                        </form>
                        <c:if test="${errMsg != null}">
                            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                                ${errMsg}</p>
                            </c:if>
                    </div>
                </div>
            </div>
        </div>
        
    </body>
</html>

