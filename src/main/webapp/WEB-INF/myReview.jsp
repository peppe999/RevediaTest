<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<h2><i class="fa fa-pencil-square section-title-icon"></i>La tua recensione</h2>
<div class="row">
    <div class="col">
        <div class="card explore-card review-card">
            <div class="card-body">
                <div class="card-info">
                    <div class="row review-card-header">
                        <div class="col d-md-flex align-self-center align-items-md-center review-card-user-area-container left-container">
                            <div class="d-flex align-items-center flex-wrap review-card-user-area">
                                <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i><c:out value="${myreview.user}"/></h6>
                                <ul class="list-inline text-nowrap">
                                    <c:forEach var="counter" begin="0" end="4">
                                        <c:choose>
                                            <c:when test="${counter < myreview.numberOfStars}">
                                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="col align-self-center review-card-user-area-container">
                            <div class="justify-content-start align-items-center flex-wrap justify-content-sm-end review-card-user-area">
                                <h6 class="text-nowrap text-muted card-subtitle mb-2 rate-section"><i class="fa fa-thumbs-up card-icon locked-like-icon like-icon"></i>${myreview.likeNumber}</h6>
                                <h6 class="text-nowrap text-muted card-subtitle mb-2"><i class="fa fa-thumbs-down card-icon locked-like-icon like-icon"></i>${myreview.dislikeNumber}</h6>
                            </div>
                        </div>
                    </div>
                    <p class="review-card-txt"><c:out value="${myreview.description}"/></p>
                </div>
            </div>
        </div>
    </div>
</div>