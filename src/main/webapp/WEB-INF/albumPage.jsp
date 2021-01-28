<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>musica</title>
    <link rel="stylesheet" href="../albumassets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="../albumassets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../albumassets/fonts/ionicons.min.css">
    <link rel="stylesheet" href="../albumassets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="../albumassets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="../albumassets/css/Contact-Form-Clean.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="../albumassets/css/Navigation-with-Search.css">
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../albumassets/css/styles.css">
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
                <li class="nav-item"><a class="nav-link active" href="#">Musica</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Film</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Libri</a></li>
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
                        <h6 class="dropdown-info-subheader">UTENTE ${user.permissions}</h6>
                    </div>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#"><i class="fa fa-area-chart item-icon"></i>Profilo</a>
                    <a class="dropdown-item" href="#"><i class="fa fa-edit item-icon"></i>Gestisci contenuti</a>
                    <a class="dropdown-item" href="/logout"><i class="fa fa-sign-out item-icon"></i>Esci</a></div>
            </div>
            <a href="/Login">
                <button class="btn btn-primary btn-sm ml-auto nav-login-btn" type="button" style="${signupbutton}">Accedi</button>
            </a>
        </div>
    </div>
</nav>
    <div class="container" id="contprincipale" style="background-image: url(&quot;images/music/${album.id}.jpg&quot;);">
        <div class="row">
            <div class="col">
                <h1 id="tx-1" style="font-size: 100px;">${album.artist}</h1>
                <h1 id="tx-1" style="font-size: 64px;">${album.name}</h1>
                <h1 id="tx-1" style="font-size: 38px;">Etichetta: ${album.label}</h1>
                <div><span id="span-3">data pubblicazione: ${album.releaseDate} &nbsp; &nbsp; &nbsp; &nbsp;</span></div>
                <div><span id="span-2">durata:  ${durata} minuti</span></div>
                <div><span id="span-1">Numero Brani: ${album.numberOfSongs}&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <h3 class="text"><i class="icon-playlist icon"></i>Tracklist</h3>
                <div class="card">
                    <c:forEach items="${songs} " var="song" >

                        <div class="card-header" id="card"><a id="song" href="#">${song}</a></div>

                    </c:forEach>

                </div>
            </div>
        </div>
    </div>
   <!-- <div class="container-fluid" id="contlink">
        <h3 class="text" style="font-size: 15px;"><i class="fa fa-music icon"></i>&nbsp; &nbsp;Dove ascoltare questo album</h3>
        <p style="font-size: 25px;"><i class="fa fa-spotify"></i>Spotify</p>
    </div>*/-->
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h3 class="text"><i class="icon ion-ios-people icon"></i>&nbsp; &nbsp;Cosa pensi di questo contenuto?</h3>
                <h3 class="text">Condividi con gli altri la tua opinione.</h3>
            </div>
            <div class="col">
                <form action="/sendalbumreview" method="post" style="${hideuser}" >
                    <h3 class="text">Valuta&nbsp;&nbsp;<i class="fa fa-star-o icon"></i><i class="fa fa-star-o icon"></i><i class="fa fa-star-o icon"></i><i class="fa fa-star-o icon"></i><i class="fa fa-star-o icon"></i></h3>
                    <div class="form-group">
                        <textarea class="form-control" name="reviewinput"></textarea>
                        <button class="btn btn-primary" type="submit" >Invia la tua recensione</button>

                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h3 class="text"><i class="material-icons icon">chrome_reader_mode</i>&nbsp;Recensioni di altri utenti</h3>
            </div>
        </div>
        <div class="row">

            <c:forEach items="${reviews}" var="review">
                <div class="col">
                    <div class="card" id="cards">
                        <div class="card-body" id="cards">
                            <h4 class="card-title">
                                <i class="fa fa-user-circle-o icon"></i>&nbsp;${review.user}

                                <c:forEach var="counter" begin="0" end="4">

                                    <c:choose>
                                        <c:when test="${counter < review.numberOfStars}">
                                            <i class="fa fa-star icon"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-star-o icon"></i>
                                        </c:otherwise>
                                    </c:choose>



                            </c:forEach>

                                <p class="card-text">${review.description}</p>
                            <i class="fa fa-thumbs-o-up ic"></i><i class="fa fa-thumbs-o-down ic"></i>
                        </div>
                    </div>
                </div>

            </c:forEach>


        </div>
    </div>
    <footer class="footer">
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <h6 class="footer-logo">REVEDIA</h6>
                    <p class="footer-text">Revedia è un punto di incontro per tutti gli amanti di musica, film e libri<br>Esplora il vasto catalogo di contenuti, contribuendo anche tu con le tue recensioni</p><a class="card-link" href="#">Chi siamo</a></div>
            </div>
        </div>
    </footer>
    <script src="../albumassets/js/jquery.min.js"></script>
    <script src="../albumassets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>