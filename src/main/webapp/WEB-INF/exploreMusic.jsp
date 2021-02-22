<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - Musica ${genre}</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="/css/styles.css">
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
                    <li class="nav-item"><a class="nav-link active" href="/music">Musica</a></li>
                    <li class="nav-item"><a class="nav-link" href="/movies">Film</a></li>
                    <li class="nav-item"><a class="nav-link" href="/books">Libri</a></li>
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
     <div class="genre-page-info-section" style="background-image: url(&quot;/images/bts-getty.jpg&quot;);">
        <div class="genre-page-info-section-cover">
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <h1 class="genre-page-info-section-cat-lbl">Musica</h1>
                        <h1 class="genre-page-info-section-genre-lbl">${genre}</h1>
                        <h1 class="genre-page-info-section-num-lbl">${countAlbums} album</h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="cat-main-container">
        <div class="container-fluid pulse animated">
            <div class="row">
                <div class="col block-col">
                    <div class="row">
                        <div class="col align-self-center">
                            <h2 class="section-title"><i class="fa fa-star section-title-icon"></i>Brani migliori del genere</h2>
                        </div>
                    </div>
                    <c:forEach var="index" begin="0" end="3">
                        <c:if test="${index % 2 == 0}">
                            <div class="row">
                        </c:if>

                        <c:if test="${index < fn:length(bestSongsList)}">
                            <div class="col">
                                <div class="card home-card">
                                    <div class="card-body">
                                        <div class="card-img-cover" style="background-image: url('/images/music/${bestSongsList[index].albumID}.jpg');"></div>
                                        <div class="card-info">
                                            <ul class="list-inline">
                                                <c:forEach var="i" begin="0" end="4">
                                                    <c:choose>
                                                        <c:when test="${i < bestSongsList[index].rating}">
                                                            <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </ul>
                                            <h4 class="card-title"><c:out value="${bestSongsList[index].name}"/> - <c:out value="${bestSongsList[index].albumName}"/></h4>
                                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${bestSongsList[index].user}</h6><a class="card-link" href="song?name=<c:out value="${bestSongsList[index].name}"/>&album=${bestSongsList[index].albumID}">Scopri di più</a></div>
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
                            <h2 class="section-title"><i class="fa fa-calendar section-title-icon"></i>Brani più recenti del genere</h2>
                        </div>
                    </div>
                    <c:forEach var="index" begin="0" end="3">
                        <c:if test="${index % 2 == 0}">
                            <div class="row">
                        </c:if>

                        <c:if test="${index < fn:length(latestSongsList)}">
                            <div class="col">
                                <div class="card home-card">
                                    <div class="card-body">
                                        <div class="card-img-cover" style="background-image: url('/images/music/${latestSongsList[index].albumID}.jpg');"></div>
                                        <div class="card-info">
                                            <ul class="list-inline">
                                                <c:forEach var="i" begin="0" end="4">
                                                    <c:choose>
                                                        <c:when test="${i < latestSongsList[index].rating}">
                                                            <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </ul>
                                            <h4 class="card-title"><c:out value="${latestSongsList[index].name}"/> - <c:out value="${latestSongsList[index].albumName}"/></h4>
                                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestSongsList[index].user}</h6><a class="card-link" href="song?name=<c:out value="${latestSongsList[index].name}"/>&album=${latestSongsList[index].albumID}">Scopri di più</a></div>
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
            <div class="row">
                <div class="col block-col">
                    <div class="row">
                        <div class="col align-self-center">
                            <h2 class="section-title"><i class="fa fa-star section-title-icon"></i>Album migliori del genere</h2>
                        </div>
                    </div>
                    <c:forEach var="index" begin="0" end="3">
                        <c:if test="${index % 2 == 0}">
                            <div class="row">
                        </c:if>

                        <c:if test="${index < fn:length(bestAlbumsList)}">
                            <div class="col">
                                <div class="card home-card">
                                    <div class="card-body">
                                        <div class="card-img-cover" style="background-image: url('/images/music/${bestAlbumsList[index].id}.jpg');"></div>
                                        <div class="card-info">
                                            <ul class="list-inline">
                                                <c:forEach var="i" begin="0" end="4">
                                                    <c:choose>
                                                        <c:when test="${i < bestAlbumsList[index].rating}">
                                                            <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </ul>
                                            <h4 class="card-title"><c:out value="${bestAlbumsList[index].name}"/></h4>
                                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${bestAlbumsList[index].user}</h6><a class="card-link" href="album?id=${bestAlbumsList[index].id}">Scopri di più</a></div>
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
                            <h2 class="section-title"><i class="fa fa-calendar section-title-icon"></i>Album più recenti del genere</h2>
                        </div>
                    </div>
                    <c:forEach var="index" begin="0" end="3">
                        <c:if test="${index % 2 == 0}">
                            <div class="row">
                        </c:if>

                        <c:if test="${index < fn:length(latestAlbumsList)}">
                            <div class="col">
                                <div class="card home-card">
                                    <div class="card-body">
                                        <div class="card-img-cover" style="background-image: url('/images/music/${latestAlbumsList[index].id}.jpg');"></div>
                                        <div class="card-info">
                                            <ul class="list-inline">
                                                <c:forEach var="i" begin="0" end="4">
                                                    <c:choose>
                                                        <c:when test="${i < latestAlbumsList[index].rating}">
                                                            <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </ul>
                                            <h4 class="card-title"><c:out value="${latestAlbumsList[index].name}"/></h4>
                                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestAlbumsList[index].user}</h6><a class="card-link" href="album?id=${latestAlbumsList[index].id}">Scopri di più</a></div>
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
        <div class="container-fluid pulse animated filter-nav">
            <div class="row">
                <div class="col align-self-center filter-col">
                    <h2 class="section-title"><i class="fa fa-th-large section-title-icon"></i>Tutti i contenuti del genere<a id="filter-btn" href="#filterarea" data-toggle="collapse" aria-expanded="true" aria-controls="filterarea" role="button"><i class="fa fa-filter"></i></a></h2>
                </div>
                <div class="col d-inline-flex justify-content-end align-self-center filter-col"><span class="spinner-grow spinner-grow-sm page-spinner" role="status" id="load-spinner" style="/*display: none;*/"></span>
                    <div class="form-group d-inline-flex align-items-center page-numbers-controls"><button class="btn page-arrow" id="page-left-arrow" type="button"><i class="fa fa-arrow-left"></i></button><input class="form-control-sm" type="number" id="page-number-input" value="1"><span id="page-number-h"></span><span id="hidden-page-number">1</span>
                        <button
                            class="btn page-arrow" id="page-right-arrow" type="button"><i class="fa fa-arrow-right"></i></button>
                    </div>
                </div>
            </div>
            <div class="row collapse show" id="filterarea">
                <div class="col filter-col">
                    <div class="filter-card">
                        <div><a class="btn filter-collapse-btn" data-toggle="collapse" aria-expanded="false" aria-controls="collapse-1" href="#collapse-1" role="button">Ordinamento</a>
                            <div class="collapse filter-collapse" id="collapse-1">
                                <ul class="list-unstyled filter-list">
                                    <li><button class="btn filter-btn active-filter" id="alpha-order-btn" type="button">Alfabetico</button></li>
                                    <li><button class="btn filter-btn" id="time-order-btn" type="button">Cronologico</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col filter-col">
                    <div class="filter-card">
                        <div><a class="btn filter-collapse-btn" data-toggle="collapse" aria-expanded="false" aria-controls="collapse-2" href="#collapse-2" role="button">Ordine</a>
                            <div class="collapse filter-collapse" id="collapse-2">
                                <ul class="list-unstyled filter-list">
                                    <li><button class="btn filter-btn active-filter" id="asc-order-btn" type="button">Ascendente</button></li>
                                    <li><button class="btn filter-btn" id="desc-order-btn" type="button">Discendente</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col filter-col">
                    <div class="filter-card">
                        <div><a class="btn filter-collapse-btn" data-toggle="collapse" aria-expanded="false" aria-controls="collapse-3" href="#collapse-3" role="button">Tipologia</a>
                            <div class="collapse filter-collapse" id="collapse-3">
                                <ul class="list-unstyled filter-list">
				    <li><button class="btn filter-btn active-filter" id="all-music-filter-btn" type="button">Album e singoli</button></li>
                                    <li><button class="btn filter-btn" id="song-filter-btn" type="button">Brani</button></li>
                                    <li><button class="btn filter-btn" id="only-album-filter-btn" type="button">Solo album</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid pulse animated">
            <div class="row">
                <div class="col block-col" id="dynamic-explore-container">
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
    <script src="/js/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/js/bs-init.js"></script>
    <script src="/js/exploreMusicLoader.js"></script>
</body>

</html>