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
        <title>Add/Edit Author</title>
    </head>
    <body>

        <div class="panel panel-primary">
            <div class="panel-heading"><h1>Add/Edit Author</h1></div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-1">
                        <nav class="nav-sidebar">
                            <ul class="nav">
                                <li><b>Menu</b></li>
                                <li class="nav-divider"></li>
                                <li><a href="index.html">Home</a></li>
                                <li><a href="AuthorController?action=list">List All Authors</a></li>

                            </ul>
                        </nav>
                    </div>
                    <div class="col-sm-6">
                        <form method="POST" id="save" name="save" action="AuthorController?action=save">
                            <c:choose>
                                <c:when test="${not empty author}">
                                    <p>ID: <input type="text" value="${author.authorId}" name="authorId" readonly/></p>
                                    </c:when>
                                </c:choose>
                            <p>Name: <input type="text" value="${author.authorName}" name="authorName" required/></p>
                            <p>Date Created: <input type="text" value="${author.dateCreated}" name="dateCreated" required/></p>
                            <button class="btn btn-large btn-danger" type="submit" value="Save Author" name="save" >Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
       
    </body>
</html>
