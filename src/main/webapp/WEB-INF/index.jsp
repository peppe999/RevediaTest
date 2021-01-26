<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - Home</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="../fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md sticky-top pulse animated">
    <div class="container-fluid"><a class="navbar-brand text-uppercase" href="#">REVEDIA</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav">
                <li class="nav-item"><a class="nav-link active" href="#">Musica</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Film</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Libri</a></li>
            </ul>
            <form class="form-inline mr-auto" target="_self">
                <div class="form-group"><label class="searchLbl" for="search-field"><i class="fa fa-search"></i></label><input class="form-control search-field" type="search" id="search-field-1" name="search" placeholder="Cerca un contenuto" autocomplete="off"></div>
            </form>
            <div class="dropdown ml-auto dropdown-user-controls" style="/*display: none;*/"><a class="text-left dropdown-user-controls-btn" data-toggle="dropdown" aria-expanded="false" href="#"><i class="fa fa-user-circle"></i>peppe</a>
                <div class="dropdown-menu dropdown-menu-right dropdown-user-controls-menu">
                    <div class="dropdown-info-area"><i class="fa fa-user-circle"></i>
                        <h5 class="dropdown-info-header">peppe</h5>
                        <h6 class="dropdown-info-subheader">Utente editor</h6>
                    </div>
                    <div class="dropdown-divider"></div><a class="dropdown-item" href="#"><i class="fa fa-area-chart item-icon"></i>Profilo</a><a class="dropdown-item" href="#"><i class="fa fa-edit item-icon"></i>Gestisci contenuti</a><a class="dropdown-item" href="#"><i class="fa fa-sign-out item-icon"></i>Esci</a></div>
            </div><button class="btn btn-primary btn-sm ml-auto nav-login-btn" type="button" style="display: none;">Accedi</button></div>
    </div>
</nav>
<!-- carousel -->
<div class="carousel slide" data-ride="carousel" data-pause="false" id="carousel-1">
    <div class="carousel-inner">
        <div class="carousel-item active" style="background-image: url(&quot;images/music/${albumCarousel.id}.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">ALBUM</h6>
                    <h4 class="card-title">${albumCarousel.name}</h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i>${albumCarousel.user}</h6>
                    <a class="card-link" href="#">Scopri di più</a>
                </div>
            </div>
        </div>
        <div class="carousel-item" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">BRANO</h6>
                    <h4 class="card-title">${songName}</h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i>${songUser}</h6>
                    <a class="card-link" href="#">Scopri di più</a>
                </div>
            </div>
        </div>
        <div class="carousel-item" style="background-image: url(&quot;images/interstellar-122823.jpg&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">FILM</h6>
                    <h4 class="card-title">${movieTitle}</h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i>${movieUser}</h6>
                    <a class="card-link" href="#">Scopri di più</a>
                </div>
            </div>
        </div>
        <div class="carousel-item" style="background-image: url(&quot;images/Isaac-Asimov-4-e1586190753716-701x514.png&quot;);">
            <div class="card">
                <div class="card-body">
                    <h6 class="carousel-card-genre">LIBRO</h6>
                    <h4 class="card-title">${bookTitle}</h4>
                    <h6 class="text-muted card-subtitle mb-2">
                        <i class="fa fa-user card-icon"></i>${bookUser}</h6>
                    <a class="card-link" href="#">Scopri di più</a>
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
        <h2><i class="fa fa-music section-title-icon"></i>Brani più recensiti</h2>
        <div class="row"> <!-- for -->
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Don't talk to strangers</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>dio</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Don't talk to strangers</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>dio</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Don't talk to strangers</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>dio</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Don't talk to strangers</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>dio</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Don't talk to strangers</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>dio</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-dot-circle-o section-title-icon"></i>Album più recensiti</h2>
        <div class="row">
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/403624.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Contenuto di prova</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>user1</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/403624.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Contenuto di prova</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>user1</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/403624.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Contenuto di prova</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>user1</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-film section-title-icon"></i>Film più recensiti</h2>
        <div class="row">
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/interstellar-122823.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Interstellar</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>nolanFan</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/interstellar-122823.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Interstellar</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>nolanFan</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/interstellar-122823.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Interstellar</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>nolanFan</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/interstellar-122823.jpg&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Interstellar</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>nolanFan</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid pulse animated content-section-area">
        <h2><i class="fa fa-book section-title-icon"></i>Libri più recensiti</h2>
        <div class="row">
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/Isaac-Asimov-4-e1586190753716-701x514.png&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Io, robot</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>asimovFan</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card home-card">
                    <div class="card-body">
                        <div class="card-img-cover" style="background-image: url(&quot;images/Isaac-Asimov-4-e1586190753716-701x514.png&quot;);"></div>
                        <div class="card-info">
                            <ul class="list-inline">
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                <li class="list-inline-item star"><i class="fa fa-star"></i></li>
                            </ul>
                            <h4 class="card-title">Io, robot</h4>
                            <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>asimovFan</h6><a class="card-link" href="#">Scopri di più</a></div>
                    </div>
                </div>
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
<script src="../js/jquery.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
<script src="../js/bs-init.js"></script>
</body>

</html>