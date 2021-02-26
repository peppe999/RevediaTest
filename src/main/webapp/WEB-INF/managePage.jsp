<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revidia - Gestione contenuti</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="/css/styles.css">
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
                        <div class="dropdown-divider"></div><a class="dropdown-item" href="/user"><i class="fa fa-area-chart item-icon"></i>Profilo</a><c:if test="${user.permissions != 'STANDARD'}"><a class="dropdown-item" href="#"><i class="fa fa-edit item-icon"></i>Gestisci contenuti</a></c:if> <a class="dropdown-item" href="/logout"><i class="fa fa-sign-out item-icon"></i>Esci</a></div>
                </div></div>
        </div>
    </nav>
    <div class="user-area-main-container">
        <div class="container-fluid">
            <div class="row">
                <div class="col align-self-center">
                    <h2 class="section-title"><i class="fa fa-pencil section-title-icon"></i>Gestione contenuti</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-10 align-self-center">
                    <div class="row">
                        <div class="col">
                            <div class="card user-alt-stat-card">
                                <div class="card-body">
                                    <div class="row card-body-row">
                                        <div class="col align-self-center">
                                            <h4 class="stat-card-value"><i class="fa fa-music card-icon"></i><span id="loaded-songs-num">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Brani pubblicati</h6>
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
                                            <h4 class="stat-card-value"><i class="fa fa-dot-circle-o card-icon"></i><span id="loaded-albums-num">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Album pubblicati</h6>
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
                                            <h4 class="stat-card-value"><i class="fa fa-film card-icon"></i><span id="loaded-movies-num">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Film pubblicati</h6>
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
                                            <h4 class="stat-card-value"><i class="fa fa-book card-icon"></i><span id="loaded-books-num">---</span></h4>
                                            <h6 class="text-muted mb-2 stat-card-title">Libri pubblicati</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid pulse animated filter-nav">
            <div class="row">
                <div class="col align-self-center filter-col">
                    <h2 class="section-title"><i class="fa fa-th-large section-title-icon"></i>Tutti i tuoi contenuti<a id="filter-btn" href="#filterarea" data-toggle="collapse" aria-expanded="true" aria-controls="filterarea" role="button"><i class="fa fa-filter"></i></a></h2>
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
                                    <li><button class="btn filter-btn active-filter" id="album-type-filter-btn" type="button">Album</button></li>
                                    <li><button class="btn filter-btn" id="song-type-filter-btn" type="button">Brani</button></li>
                                    <li><button class="btn filter-btn" id="movie-type-filter-btn" type="button">Film</button></li>
                                    <li><button class="btn filter-btn" id="book-type-filter-btn" type="button">Libri</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col filter-col">
                    <div class="form-group filter-search-group"><input type="text" class="form-input-field filter-search-input" id="search-my-content-box" required=""><label class="form-input-label filter-search-label"><i class="fa fa-search filter-search-label-icon"></i>Cerca</label></div>
                </div>
            </div>
        </div>
        <div class="container-fluid pulse animated">
            <div class="row">
                <div class="col block-col">
                    <div class="row">
                        <div class="col">
                            <div><button class="btn add-new-content-btn" type="button" data-toggle="modal" data-target="#addModal">Aggiungi nuovo contenuto</button></div>
                        </div>
                    </div>
                    <div id="dynamic-explore-container">
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" role="dialog" tabindex="-1" id="addModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Aggiungi contenuto</h4><button type="button" class="close" data-dismiss="modal" aria-label="Close" id="manage-modal-close-btn"><span aria-hidden="true">×</span></button></div>
                    <div class="modal-body">
                        <div id="category-tab" class="modal-form-tab" style="display: initial;">
                            <form>
                                <div class="form-group d-flex align-items-center add-category-group"><input type="radio" id="music-radio" class="radio-toggle-x" name="category-radio"><label class="d-flex align-items-center radio-label-x" for="music-radio"><i class="fa fa-music fa-fw radio-icon-x"></i>Musica</label></div>
                                <div class="form-group d-flex align-items-center add-category-group"><input type="radio" id="film-radio" class="radio-toggle-x" name="category-radio"><label class="d-flex align-items-center radio-label-x" for="film-radio"><i class="fa fa-film fa-fw radio-icon-x"></i>Film</label></div>
                                <div class="form-group d-flex align-items-center add-category-group"><input type="radio" id="book-radio" class="radio-toggle-x" name="category-radio"><label class="d-flex align-items-center radio-label-x" for="book-radio"><i class="fa fa-book fa-fw radio-icon-x"></i>Libro</label></div>
                            </form>
                        </div>
                        <div id="music-choice-tab" class="modal-form-tab">
                            <form>
                                <div class="form-group d-flex align-items-center add-category-group"><input type="radio" id="song-radio" class="radio-toggle-x" name="music-choice-radio"><label class="d-flex align-items-center radio-label-x" for="song-radio"><i class="fa fa-music fa-fw radio-icon-x"></i>Brano</label></div>
                                <div class="form-group d-flex align-items-center add-category-group"><input type="radio" id="album-radio" class="radio-toggle-x" name="music-choice-radio"><label class="d-flex align-items-center radio-label-x" for="album-radio"><i class="fa fa-dot-circle-o fa-fw radio-icon-x"></i>Album</label></div>
                            </form>
                        </div>
                        <div id="api-search-modality-tab" class="modal-form-tab">
                            <div class="form-group filter-search-group"><input type="text" class="form-input-field filter-search-input" id="newcont-api-search-box" required="" style="margin-bottom: 15px;"><label class="form-input-label filter-search-label"><i class="fa fa-search filter-search-label-icon"></i>Cerca</label></div>
                            <form>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer"><span class="form-input-msg form-input-error-msg" id="manage-modal-error-lbl" style="padding-right: 10px;display:none">Il contenuto non è stato aggiunto</span><button class="btn btn-sm cancel-btn" type="button" id="manage-back-btn">Annulla</button><button class="btn btn-primary btn-sm" type="button" id="manage-next-btn">Avanti</button></div>
                </div>
            </div>
        </div>
    </div>
    <footer>
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <h6 class="footer-logo">REVEDIA</h6>
                    <p class="footer-text">Revedia è un punto di incontro per tutti gli amanti di musica, film e libri<br>Esplora il vasto catalogo di contenuti, contribuendo anche tu con le tue recensioni</p><a class="card-link" href="#">Chi siamo</a></div>
            </div>
        </div>
    </footer>
    <script src="/js/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/js/bs-init.js"></script>
    <script src="/js/loadManageUserData.js"></script>
    <script src="/js/exploreManageContents.js"></script>
    <script src="/js/manageContent.js"></script>
    <script src="/js/autocompleteLoader.js"></script>
</body>

</html>