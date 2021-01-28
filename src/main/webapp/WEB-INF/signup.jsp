<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - Registrati</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="../fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="../css/styles.css">
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
                <li class="nav-item"><a class="nav-link" href="books">Libri</a></li>
            </ul>
            <form class="form-inline mr-auto" target="_self">
                <div class="form-group"><label class="searchLbl" for="search-field">
                    <i class="fa fa-search"></i></label>
                    <input class="form-control search-field" type="search" id="search-field-1" name="search" placeholder="Cerca un contenuto" autocomplete="off">
                </div>
            </form>
        </div>
    </div>
</nav>
<div class="search-results-main-container">
    <div class="container-fluid pulse animated">
        <div class="row">
            <div class="col-8 col-sm-5 col-xl-4 login-col">
                <h2 class="section-title login-page-title"><i class="fa fa-user-plus section-title-icon"></i>Crea nuovo account</h2>
                <p class="section-title" style="color: red">${usernotavaible}</p>
                <form action="/register" method="post">
                    <div class="form-group">
                        <input class="form-control form-input-field" name="username" type="text" required="">
                        <label class="form-input-label">Username</label>
                    </div>
                    <div class="form-group">
                        <input class="form-control form-input-field" name="firstname" type="text" required="">
                        <label class="form-input-label">Nome</label>
                    </div>
                    <div class="form-group">
                        <input class="form-control form-input-field" name="lastname" type="text" required="">
                        <label class="form-input-label">Cognome</label>
                    </div>
                    <div class="form-group">
                        <input class="form-control form-input-field" name="mail" type="text" required="">
                        <label class="form-input-label">Email</label>
                    </div>
                    <div class="form-group">
                        <input class="form-control form-input-field" id="password1" name="password" type="password" required="">
                        <label class="form-input-label">Password</label>
                    </div>
                    <div class="form-group">
                        <input class="form-control form-input-field" id="password2" type="password" required="">
                        <label class="form-input-label">Ripeti Password</label>
                    </div>
                    <button class="btn btn-primary btn-sm d-block login-btn" id="signinButton" type="submit">Registrati</button>
                </form>
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
<script src="../js/bs-init.js"></script>
<script src="../js/passwordCheck.js"></script>
</body>
</html>