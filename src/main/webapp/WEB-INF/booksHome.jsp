<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - Libri</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md sticky-top pulse animated">
    <div class="container-fluid"><a class="navbar-brand text-uppercase" href="/">REVEDIA</a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav">
                <li class="nav-item"><a class="nav-link" href="music">Musica</a></li>
                <li class="nav-item"><a class="nav-link" href="movies">Film</a></li>
                <li class="nav-item"><a class="nav-link active" href="#">Libri</a></li>
            </ul>
            <form class="form-inline mr-auto" target="_self">
                <div class="form-group"><label class="searchLbl" for="search-field">
                    <i class="fa fa-search"></i></label>
                    <input class="form-control search-field" type="search" id="search-field-1" name="search" placeholder="Cerca un contenuto" autocomplete="off">
                </div>
            </form>
            <div class="dropdown ml-auto dropdown-user-controls" style="${hideuser}">
                <a class="text-left dropdown-user-controls-btn" data-toggle="dropdown" aria-expanded="false" href="#">
                    <i class="fa fa-user-circle"></i>${user.nickname}
                </a>
                <div class="dropdown-menu dropdown-menu-right dropdown-user-controls-menu">
                    <div class="dropdown-info-area"><i class="fa fa-user-circle"></i>
                        <h5 class="dropdown-info-header">${user.nickname}</h5>
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
<div class="cat-main-container">
    <div class="container-fluid pulse animated">
        <div class="row">
            <div class="col align-self-center filter-col">
                <h2 class="section-title"><i class="fa fa-book section-title-icon"></i>Generi di libri<a id="filter-btn" href="#filterarea" data-toggle="collapse" aria-expanded="true" aria-controls="filterarea" role="button"><i class="fa fa-caret-down"></i></a></h2>
            </div>
        </div>
        <div class="row collapse show" id="filterarea">
            <c:forEach var="genre" items="${genreList}">
                <div class="col genre-col">
                    <div class="card genre-card"><a class="genre-card-link" href="books/explore?genre=${genre}"></a>
                        <div class="card-body">
                            <div class="card-info">
                                <h4 class="text-nowrap text-truncate card-title">${genre}</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="container-fluid pulse animated">
        <div class="row">
            <div class="col block-col">
                <div class="row">
                    <div class="col align-self-center">
                        <h2 class="section-title"><i class="fa fa-star section-title-icon"></i>Libri migliori</h2>
                    </div>
                </div>
                <c:forEach var="index" begin="0" end="3">
                    <c:if test="${index % 2 == 0}">
                        <div class="row">
                    </c:if>

                    <c:if test="${index < fn:length(bestBooksList)}">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url('images/books/${bestBooksList[index].imageId}.jpg');"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                                <c:choose>
                                                    <c:when test="${i < bestBooksList[index].rating}">
                                                        <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </ul>
                                        <h4 class="card-title">${bestBooksList[index].title}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${bestBooksList[index].user}</h6><a class="card-link" href="books/book?title=<c:out value="${bestBooksList[index].title}"/>">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${index % 2 != 0}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="col block-col">
                <div class="row">
                    <div class="col align-self-center">
                        <h2 class="section-title"><i class="fa fa-calendar section-title-icon"></i>Libri più recenti</h2>
                    </div>
                </div>
                <c:forEach var="index" begin="0" end="3">
                    <c:if test="${index % 2 == 0}">
                        <div class="row">
                    </c:if>

                    <c:if test="${index < fn:length(latestBooksList)}">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url('images/books/${latestBooksList[index].imageId}.jpg');"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                                <c:choose>
                                                    <c:when test="${i < latestBooksList[index].rating}">
                                                        <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestBooksList[index].title}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestBooksList[index].user}</h6><a class="card-link" href="books/book?title=<c:out value="${latestBooksList[index].title}"/>">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${index % 2 != 0}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<footer>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h6 class="footer-logo">REVEDIA</h6>
                <p class="footer-text">Revedia è un punto di incontro per tutti gli amanti di musica, film e libri<br>Esplora il vasto catalogo di contenuti, contribuendo anche tu con le tue recensioni</p><a class="card-link" href="/aboutus">Chi siamo</a></div>
        </div>
    </div>
</footer>
<script src="js/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="js/bs-init.js"></script>
</body>

</html>