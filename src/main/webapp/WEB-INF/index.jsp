<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - Home</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="/css/styles.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md sticky-top pulse animated">
    <div class="container-fluid"><a class="navbar-brand text-uppercase" href="#">REVEDIA</a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav">
                <li class="nav-item"><a class="nav-link" href="music">Musica</a></li>
                <li class="nav-item"><a class="nav-link" href="movies">Film</a></li>
                <li class="nav-item"><a class="nav-link" href="books">Libri</a></li>
            </ul>
            <form class="form-inline mr-auto search-form" action="/search">
                <div class="form-group"><label class="searchLbl" for="search-field-box"><i class="fa fa-search"></i></label><input class="form-control search-field" type="search" id="search-field-box" name="query" placeholder="Cerca un contenuto" autocomplete="off"></div>
                <div class="search-autocomplete-box" style="display: none">
                </div>
            </form>
            <div class="dropdown ml-auto dropdown-user-controls" style="${hideuser}">
                <a class="text-left dropdown-user-controls-btn" data-toggle="dropdown" aria-expanded="false" href="#">
                    <i class="fa fa-user-circle"></i><c:out value="${user.nickname}"/>
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-user-controls-menu">
                    <div class="dropdown-info-area"><i class="fa fa-user-circle"></i>
                        <h5 class="dropdown-info-header"><c:out value="${user.nickname}"/></h5>
                        <h6 class="dropdown-info-subheader">Utente ${fn:toLowerCase(user.permissions)}</h6>
                    </div>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/user"><i class="fa fa-area-chart item-icon"></i>Profilo</a>
                    <c:if test="${user.permissions != 'STANDARD'}"><a class="dropdown-item" href="/manage"><i class="fa fa-edit item-icon"></i>Gestisci contenuti</a></c:if>
                    <a class="dropdown-item" href="/logout"><i class="fa fa-sign-out item-icon"></i>Esci</a></div>
            </div>
            <a href="/Login">
                <button class="btn btn-primary btn-sm ml-auto nav-login-btn" type="button" style="${signupbutton}">Accedi</button>
            </a>
        </div>
    </div>
</nav>
<!-- carousel -->
<div class="carousel slide" data-ride="carousel" data-pause="false" id="carousel-1">
    <div class="carousel-inner">
        <div class="carousel-item active" style="background-image: url(&quot;images/music/${albumCarousel.id}.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">ALBUM</h6>
                    <h4 class="card-title"><c:out value="${albumCarousel.name}"/></h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i><c:out value="${albumCarousel.user}"/></h6>
                    <a class="card-link" href="/music/album?id=${albumCarousel.id}">Scopri di più</a>
                </div>
            </div>
        </div>
        <div class="carousel-item" style="background-image: url(&quot;images/music/${songCarousel.albumID}.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">BRANO</h6>
                    <h4 class="card-title"><c:out value="${songCarousel.name}"/></h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i><c:out value="${songCarousel.user}"/></h6>
                    <a class="card-link" href="/music/song?name=<c:out value="${songCarousel.name}"/>&album=${songCarousel.albumID}">Scopri di più</a>
                </div>
            </div>
        </div>
        <div class="carousel-item" style="background-image: url(&quot;images/movies/${movieCarousel.imageId}.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">FILM</h6>
                    <h4 class="card-title"><c:out value="${movieCarousel.title}"/></h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i><c:out value="${movieCarousel.user}"/></h6>
                    <a class="card-link" href="/movies/movie?title=<c:out value="${movieCarousel.title}"/>">Scopri di più</a>
                </div>
            </div>
        </div>
        <div class="carousel-item" style="background-image: url(&quot;images/books/${bookCarousel.imageId}.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">LIBRO</h6>
                    <h4 class="card-title"><c:out value="${bookCarousel.title}"/></h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i><c:out value="${bookCarousel.user}"/></h6>
                    <a class="card-link" href="/books/book?title=<c:out value="${bookCarousel.title}"/>">Scopri di più</a>
                </div>
            </div>
        </div>
    </div>
    <div><a class="carousel-control-prev" href="#carousel-1" role="button" data-slide="prev" style="display: none;"><span class="carousel-control-prev-icon"></span><span class="sr-only">Previous</span></a><a class="carousel-control-next" href="#carousel-1"
                                                                                                                                                                                                                role="button" data-slide="next" style="display: none;"><span class="carousel-control-next-icon"></span><span class="sr-only">Next</span></a></div>
    <ol class="carousel-indicators">
        <li data-target="#carousel-1" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-1" data-slide-to="1"></li>
        <li data-target="#carousel-1" data-slide-to="2"></li>
        <li data-target="#carousel-1" data-slide-to="3"></li>
    </ol>
</div>

<div class="home-main-container">
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-music section-title-icon"></i>Brani in evidenza</h2>
        <div class="row"> <!-- for -->
            <c:forEach items="${songs}" var="song">
                <div class="col">
                    <div class="card home-card">
                        <div class="card-body">
                            <div class="card-img-cover" style="background-image: url(&quot;images/music/${song.albumID}.jpg&quot;);">
                            </div>
                            <div class="card-info">
                                <ul class="list-inline">
                                    <c:forEach var="counter" begin="0" end="4">
                                        <c:choose>
                                            <c:when test="${counter < song.rating}">
                                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                                <h4 class="card-title"><c:out value="${song.name}"/></h4>
                                <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i><c:out value="${song.user}"/></h6>
                                <a class="card-link" href="/music/song?name=<c:out value="${song.name}"/>&album=${song.albumID}">Scopri di più</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!--/////////////////////////////////////////////////////////////////////////////////-->
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-dot-circle-o section-title-icon"></i>Album in evidenza</h2>
        <div class="row">
            <c:forEach items="${albums}" var="album">
                <div class="col">
                    <div class="card home-card">
                        <div class="card-body">
                            <div class="card-img-cover" style="background-image: url(&quot;images/music/${album.id}.jpg&quot;);"></div>
                            <div class="card-info">
                                <ul class="list-inline">
                                    <c:forEach var="counter" begin="0" end="4">
                                        <c:choose>
                                            <c:when test="${counter < album.rating}">
                                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                                <h4 class="card-title"><c:out value="${album.name}"/></h4>
                                <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i><c:out value="${album.user}"/></h6>
                                <a class="card-link" href="/music/album?id=${album.id}">Scopri di più</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!--/////////////////////////////////////////////////////////////////////////////////-->
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-film section-title-icon"></i>Film in evidenza</h2>
        <div class="row">
            <c:forEach items="${movies}" var="movie">
                <div class="col">
                    <div class="card home-card">
                        <div class="card-body">
                            <div class="card-img-cover" style="background-image: url(&quot;images/movies/${movie.imageId}.jpg&quot;);"></div>
                            <div class="card-info">
                                <ul class="list-inline">
                                    <c:forEach var="counter" begin="0" end="4">
                                        <c:choose>
                                            <c:when test="${counter < movie.rating}">
                                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                                <h4 class="card-title"><c:out value="${movie.title}"/></h4>
                                <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i><c:out value="${movie.user}"/></h6>
                                <a class="card-link" href="/movies/movie?title=<c:out value="${movie.title}"/>">Scopri di più</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!--/////////////////////////////////////////////////////////////////////////////////-->
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-book section-title-icon"></i>Libri in evidenza</h2>
        <div class="row">
            <c:forEach items="${books}" var="book">
                <div class="col">
                    <div class="card home-card">
                        <div class="card-body">
                            <div class="card-img-cover" style="background-image: url(&quot;images/books/${book.imageId}.jpg&quot;);"></div>
                            <div class="card-info">
                                <ul class="list-inline">
                                    <c:forEach var="counter" begin="0" end="4">
                                        <c:choose>
                                            <c:when test="${counter < book.rating}">
                                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                                <h4 class="card-title"><c:out value="${book.title}"/></h4>
                                <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i><c:out value="${book.user}"/></h6>
                                <a class="card-link" href="/books/book?title=<c:out value="${book.title}"/>">Scopri di più</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<footer>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h6 class="footer-logo">REVEDIA</h6>
                <p class="footer-text">Revedia è un punto di incontro per tutti gli amanti di musica, film e libri<br>
                    Esplora il vasto catalogo di contenuti, contribuendo anche tu con le tue recensioni
                </p>
                <a class="card-link" href="https://www.youtube.com/watch?v=DUXapRAVux4">Chi siamo</a></div>
        </div>
    </div>
</footer>
<script src="/js/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/js/bs-init.js"></script>
<script src="/js/autocompleteLoader.js"></script>
</body>

</html>