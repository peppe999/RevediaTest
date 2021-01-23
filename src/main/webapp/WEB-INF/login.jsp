<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Revedia - Accedi</title>
        <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
        <link rel="stylesheet" href="../fonts/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
        <link rel="stylesheet" href="../css/styles.css">
        <meta charset="UTF-8">
        <title>Login</title>
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
                <!--<div class="dropdown ml-auto dropdown-user-controls" style="/*display: none;*/">
                    <a class="text-left dropdown-user-controls-btn" data-toggle="dropdown" aria-expanded="false" href="#"><i class="fa fa-user-circle"></i>peppe</a>
                    <div class="dropdown-menu dropdown-menu-right dropdown-user-controls-menu">
                        <div class="dropdown-info-area"><i class="fa fa-user-circle"></i>
                            <h5 class="dropdown-info-header">peppe</h5>
                            <h6 class="dropdown-info-subheader">Utente editor</h6>
                        </div>
                        <div class="dropdown-divider">

                        </div>
                        <a class="dropdown-item" href="#"><i class="fa fa-area-chart item-icon"></i>Profilo</a><a class="dropdown-item" href="#"><i class="fa fa-edit item-icon"></i>Gestisci contenuti</a><a class="dropdown-item" href="#"><i class="fa fa-sign-out item-icon"></i>Esci</a></div>
                </div>-->
                <button class="btn btn-primary btn-sm ml-auto nav-login-btn" type="button" style="display: none;">Accedi</button></div>
        </div>
    </nav>
    <div class="d-flex align-items-center search-results-main-container">
        <div class="container-fluid pulse animated">
            <div class="row">
                <div class="col-8 col-sm-4 col-xl-3 login-col">
                    <h2 class="section-title login-page-title"><i class="fa fa-sign-in section-title-icon"></i>Login</h2>
                    <form action="/loginUser" method="post">
                        <div class="form-group">
                            <input class="form-control form-input-field" type="text" name="username" required="required">
                            <label class="form-input-label">Username</label>
                        </div>
                        <div class="form-group">
                            <input class="form-control form-input-field" type="password" name="password" required="required">
                            <label class="form-input-label">Password</label>
                        </div>
                        <!--<a class="card-link" href="#">Ho dimenticato la password</a>-->
                        <button class="btn btn-primary btn-sm d-block login-btn" type="submit">Accedi</button>
                    </form>
                </div>
                <div class="col-8 col-sm-5 col-xl-5 offset-sm-3 offset-xl-4 login-col">
                    <h2 class="section-title login-page-title"><i class="fa fa-user-plus section-title-icon"></i>Non sei registrato?</h2>
                    <h6 class="section-title login-page-subtitle">Registrati subito in pochi passi</h6>
                    <a class="btn btn-primary btn-sm" role="button" href="/signup">Crea nuovo account</a>
                </div>
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
                    <a class="card-link" href="#">Chi siamo</a>
                </div>
            </div>
        </div>
    </footer>
    <script src="../js/jquery.min.js"></script>
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/bs-init.js"></script>
    </body>
</html>