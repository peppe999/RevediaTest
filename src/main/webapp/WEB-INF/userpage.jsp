<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - ${user.nickname}</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="../fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
    <nav class="navbar navbar-light navbar-expand-md sticky-top pulse animated">
        <div class="container-fluid"><a class="navbar-brand text-uppercase" href="/">REVEDIA</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
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
                <div class="dropdown ml-auto dropdown-user-controls" style="/*display: none;*/"><a class="text-left dropdown-user-controls-btn" data-toggle="dropdown" aria-expanded="false" href="#"><i class="fa fa-user-circle"></i>${user.nickname}</a>
                    <div class="dropdown-menu dropdown-menu-right dropdown-user-controls-menu">
                        <div class="dropdown-info-area"><i class="fa fa-user-circle"></i>
                            <h5 class="dropdown-info-header">${user.nickname}</h5>
                            <h6 class="dropdown-info-subheader">Utente ${fn:toLowerCase(user.permissions)}</h6>
                        </div>
                        <div class="dropdown-divider"></div><a class="dropdown-item" href="#"><i class="fa fa-area-chart item-icon"></i>Profilo</a><c:if test="${user.permissions != 'STANDARD'}"><a class="dropdown-item" href="/manage"><i class="fa fa-edit item-icon"></i>Gestisci contenuti</a></c:if> <a class="dropdown-item" href="/logout"><i class="fa fa-sign-out item-icon"></i>Esci</a></div>
                </div></div>
        </div>
    </nav>
    <div class="user-area-main-container">
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <div class="row">
                        <div class="col">
                            <div>
                                <h1 class="user-area-username-h">${user.nickname}</h1>
                                <h3 class="user-area-name-h">${user.firstName} ${user.lastName}</h3>
                                <h4 class="user-area-level-h">Utente ${fn:toLowerCase(user.permissions)}</h4><a class="card-link user-area-edit-link" href="#" data-toggle="modal" data-target="#editModal">Modifica i tuoi dati</a></div>
                        </div>
                    </div>
                </div>
                <div class="col align-self-center">
                    <div class="card user-main-stat-card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col align-self-center">
                                    <h4 class="stat-card-main-value"><i class="fa fa-star card-icon"></i><span id="quality">---</span></h4>
                                    <h6 class="text-muted mb-2 stat-card-main-title">Qualità media</h6>
                                </div>
                                <div class="col align-self-center">
                                    <h4 class="stat-card-main-value"><i class="fa fa-trophy card-icon"></i><span id="num-reviews-perc">---</span></h4>
                                    <h6 class="text-muted mb-2 stat-card-main-title">Contenuti recensiti</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col align-self-center">
                    <h2 class="section-title"><i class="fa fa-area-chart section-title-icon"></i>Statistiche attività</h2>
                </div>
                <div class="col-auto align-self-center" style="display: none">
                    <ul class="list-inline text-left chartjs-time-mode-controls">
                        <li class="list-inline-item"><button class="btn chartjs-time-mode-active-control" type="button">Mese</button></li>
                        <li class="list-inline-item"><button class="btn" type="button">Anno</button></li>
                        <li class="list-inline-item"><button class="btn" type="button">Totale</button></li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col user-chart-container"><canvas id="myChart" class="user-chart"></canvas>
</div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="card user-main-stat-card">
                        <div class="card-body">
                            <div class="row card-body-row">
                                <div class="col align-self-center">
                                    <h4 class="stat-card-main-value"><i class="fa card-icon" id="fav-cat-icn"></i><span id="fav-cat">---</span></h4>
                                    <h6 class="text-muted mb-2 stat-card-main-title">Categoria preferita</h6>
                                </div>
                                <div class="col align-self-center">
                                    <div class="row">
                                        <div class="col">
                                            <h4 class="stat-card-value" id="fav-music-genre">---</h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Genere musica preferito</h6>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <h4 class="stat-card-value" id="fav-movie-genre">---</h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Genere film preferito</h6>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <h4 class="stat-card-value" id="fav-book-genre">---</h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Genere libri preferito</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col align-self-center">
                    <div class="row">
                        <div class="col">
                            <div class="card user-alt-stat-card">
                                <div class="card-body">
                                    <div class="row card-body-row">
                                        <div class="col align-self-center">
                                            <h4 class="stat-card-value"><i class="fa fa-pencil-square card-icon"></i><span id="num-reviews">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Recensioni pubblicate</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card user-alt-stat-card">
                                <div class="card-body">
                                    <div class="row card-body-row">
                                        <div class="col align-self-center">
                                            <h4 class="stat-card-value"><i class="fa fa-check-circle card-icon"></i><span id="num-rated-reviews">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Recensioni valutate</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card user-alt-stat-card">
                                <div class="card-body">
                                    <div class="row card-body-row">
                                        <div class="col align-self-center">
                                            <h4 class="stat-card-value"><i class="fa fa-heart card-icon"></i><span id="best-review">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Recensione migliore</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card user-alt-stat-card">
                                <div class="card-body">
                                    <div class="row card-body-row">
                                        <div class="col align-self-center">
                                            <h4 class="stat-card-value"><i class="fa fa-thumbs-up card-icon"></i><span id="like-perc">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Apprezzamento contenuti valutati</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" role="dialog" tabindex="-1" id="editModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Modifica i tuoi dati</h4><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button></div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group"><input class="form-control form-input-field" type="text" required="" value="${user.firstName}" id="firstNameInp"><label class="form-input-label">Nome</label></div>
                            <div class="form-group"><input class="form-control form-input-field" type="text" required="" value="${user.lastName}" id="lastNameInp"><label class="form-input-label">Cognome</label></div>
                            <div class="form-group"><input class="form-control form-input-field" type="email" required="" value="${user.mail}" id="mailInp"><label class="form-input-label">Email</label></div>
                            <div class="form-group" id="pswGroup"><input class="form-control form-input-field" type="password" required="" value="" id="pswInp"><label class="form-input-label">Inserisci la tua password</label></div>
                        </form>
                    </div>
                    <div class="modal-footer"><button class="btn btn-sm cancel-btn" type="button" data-dismiss="modal">Annulla</button><button class="btn btn-primary btn-sm" type="button" id="confirmBtn" style="/*box-shadow: inset 0px 0px 20px 20px rgba(255,255,255,0.1);*/">Conferma</button></div>
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
    <script src="../js/jquery.min.js"></script>
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/chart.min.js"></script>
    <script src="../js/bs-init.js"></script>
    <script src="../js/loadChart.js"></script>
    <script src="../js/loadUserData.js"></script>
    <script src="../js/updateUserData.js"></script>
    <script src="/js/autocompleteLoader.js"></script>
</body>

</html>