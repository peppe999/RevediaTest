<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<div class="section-box">
    <h6 class="carousel-card-genre section-box-title">ALBUM</h6>
    <c:choose>
        <c:when test="${fn:length(albumsList) == 0}">
            <h6 class="no-result-lbl">Nessun risultato</h6>
        </c:when>
        <c:otherwise>
            <a class="section-box-link dropdown-item" href="/music/album?id=${albumsList[0].id}">
                <div class="section-box-info-cont">
                    <div class="section-box-img" style="background-image: url('/images/music/${albumsList[0].id}.jpg');"></div>
                    <h6 class="carousel-card-genre section-box-txt"><c:out value="${albumsList[0].name}"/></h6>
                </div>
            </a>
        </c:otherwise>
    </c:choose>
</div>
<div class="section-box">
    <h6 class="carousel-card-genre section-box-title">BRANI</h6>
    <c:choose>
        <c:when test="${fn:length(songsList) == 0}">
            <h6 class="no-result-lbl">Nessun risultato</h6>
        </c:when>
        <c:otherwise>
            <a class="section-box-link dropdown-item" href="/music/song?name=<c:out value="${songsList[0].name}"/>&album=${songsList[0].albumID}">
                <div class="section-box-info-cont">
                    <div class="section-box-img" style="background-image: url('/images/music/${songsList[0].albumID}.jpg');"></div>
                    <h6 class="carousel-card-genre section-box-txt"><c:out value="${songsList[0].name}"/> - <c:out value="${songsList[0].albumName}"/></h6>
                </div>
            </a>
        </c:otherwise>
    </c:choose>
</div>
<div class="section-box">
    <h6 class="carousel-card-genre section-box-title">FILM</h6>
    <c:choose>
        <c:when test="${fn:length(moviesList) == 0}">
            <h6 class="no-result-lbl">Nessun risultato</h6>
        </c:when>
        <c:otherwise>
            <a class="section-box-link dropdown-item" href="/movies/movie?title=<c:out value="${moviesList[0].title}"/>">
                <div class="section-box-info-cont">
                    <div class="section-box-img" style="background-image: url('/images/movies/${moviesList[0].imageId}.jpg');"></div>
                    <h6 class="carousel-card-genre section-box-txt"><c:out value="${moviesList[0].title}"/></h6>
                </div>
            </a>
        </c:otherwise>
    </c:choose>
</div>
<div class="section-box last-box">
    <h6 class="carousel-card-genre section-box-title">LIBRI</h6>
    <c:choose>
        <c:when test="${fn:length(booksList) == 0}">
            <h6 class="no-result-lbl">Nessun risultato</h6>
        </c:when>
        <c:otherwise>
            <a class="section-box-link dropdown-item" href="/books/book?title=<c:out value="${booksList[0].title}"/>">
                <div class="section-box-info-cont">
                    <div class="section-box-img" style="background-image: url('/images/books/${booksList[0].imageId}.jpg');"></div>
                    <h6 class="carousel-card-genre section-box-txt"><c:out value="${booksList[0].title}"/></h6>
                </div>
            </a>
        </c:otherwise>
    </c:choose>
</div>