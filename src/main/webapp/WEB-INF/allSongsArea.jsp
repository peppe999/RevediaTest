<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:forEach var="rowIndex" begin="0" end="4">
    <div class="row">
        <c:forEach var="index" begin="0" end="3">
            <c:if test="${rowIndex * 4 + index < fn:length(songsList)}">
                <div class="col">
                    <div class="card explore-card">
                        <div class="card-body">
                            <div class="card-info">
                                <ul class="list-inline">
                                    <c:forEach var="i" begin="0" end="4">
                                        <c:choose>
                                            <c:when test="${i < songsList[rowIndex * 4 + index].rating}">
                                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                                <h4 class="text-nowrap text-truncate card-title"><c:out value="${songsList[rowIndex * 4 + index].name}"/> - <c:out value="${songsList[rowIndex * 4 + index].albumName}"/></h4>
                                <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${songsList[rowIndex * 4 + index].user}</h6><a class="card-link" href="song?name=<c:out value="${songsList[rowIndex * 4 + index].name}"/>&album=${songsList[rowIndex * 4 + index].albumID}">Scopri di più</a></div>
                            <div class="card-img-cover" style="background-image: url('/images/music/${songsList[rowIndex * 4 + index].albumID}.jpg');"></div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</c:forEach>