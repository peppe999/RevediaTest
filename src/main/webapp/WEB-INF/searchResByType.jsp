<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - ${type} per "<c:out value="${queryString}"/>"</title>
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
                    <li class="nav-item"><a class="nav-link" href="/music">Musica</a></li>
                    <li class="nav-item"><a class="nav-link" href="/movies">Film</a></li>
                    <li class="nav-item"><a class="nav-link" href="/books">Libri</a></li>
                </ul>
                <form class="form-inline mr-auto search-form" action="/search">
                    <div class="form-group"><label class="searchLbl" for="search-field-box"><i class="fa fa-search"></i></label><input class="form-control search-field" type="search" id="search-field-box" name="query" placeholder="Cerca un contenuto" autocomplete="off"></div>
                    <div class="search-autocomplete-box" style="display: none">
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
    <div class="search-results-main-container">
        <div class="container-fluid pulse animated" id="search-res-container">
            <h2 class="section-title search-page-title"><i class="fa fa-search section-title-icon"></i>${type}: risultati per "<c:out value="${queryString}"/>"</h2>
            <h6 class="no-result-lbl" id="no-search-results" style="display: none">Nessun risultato</h6>
        </div>
        <div class="container-fluid pulse animated">
            <div class="row">
                <div class="col-auto align-self-center m-auto spinner-col"><span class="spinner-grow" role="status" id="loading-spinner"></span></div>
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
    <script src="js/searchResultsLoader.js"></script>
    <script src="/js/autocompleteLoader.js"></script>
</body>

</html>