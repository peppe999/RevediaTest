<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:forEach items="${reviews}" var="review">
    <div class="row">
        <div class="col">
            <div class="card explore-card review-card">
                <div class="card-body">
                    <div class="card-info">
                        <div class="row review-card-header">
                            <div class="col d-md-flex align-self-center align-items-md-center review-card-user-area-container left-container">
                                <div class="d-flex align-items-center flex-wrap review-card-user-area">
                                    <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i><span class="nickname-span"><c:out value="${review.user}"/></span></h6>
                                    <ul class="list-inline text-nowrap">
                                        <c:forEach var="counter" begin="0" end="4">
                                            <c:choose>
                                                <c:when test="${counter < review.numberOfStars}">
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
                                    <h6 class="text-nowrap text-muted card-subtitle mb-2 rate-section"><i class="fa fa-thumbs-up card-icon locked-like-icon like-bts like-icon <c:if test="${not empty review.actualUserRate && review.actualUserRate}">selected-like-icon</c:if>"></i><span class="like-counter">${review.likeNumber}</span></h6>
                                    <h6 class="text-nowrap text-muted card-subtitle mb-2"><i class="fa fa-thumbs-down card-icon locked-like-icon dislike-btn like-icon <c:if test="${not empty review.actualUserRate && not review.actualUserRate}">selected-like-icon</c:if>"></i><span class="dislike-counter">${review.dislikeNumber}</span></h6>
                                </div>
                            </div>
                        </div>
                        <p class="review-card-txt"><c:out value="${review.description}"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>