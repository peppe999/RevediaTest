<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Revedia - Musica <%= request.getParameter("genre") %></title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:400,500,700">
    <link rel="stylesheet" href="fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>

<body>
    <nav class="navbar navbar-light navbar-expand-md sticky-top pulse animated">
        <div class="container-fluid"><a class="navbar-brand text-uppercase" href="#">REVEDIA</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse"
                id="navcol-1">
                <ul class="nav navbar-nav">
                    <li class="nav-item"><a class="nav-link active" href="music">Musica</a></li>
                    <li class="nav-item"><a class="nav-link" href="movies">Film</a></li>
                    <li class="nav-item"><a class="nav-link" href="books">Libri</a></li>
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
    <div style="/*padding: 40px;*/background: url(&quot;images/bts-getty.jpg&quot;) center / cover no-repeat;box-shadow: rgba(66, 66, 66, 0.5) 0px 2px 10px -4px;">
        <div style="padding: 40px;height: 100%;backdrop-filter: blur(5px);background: linear-gradient(90deg, rgba(214,89,55,0.65) 0%, rgba(222,147,0,0.65) 100%);">
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <h1 style="/*display: inline-block;*/color: #f0f0f0;font-size: 18px;margin-bottom: 6px;font-weight: 600;">Musica</h1>
                        <h1 style="/*display: inline-block;*/color: #f0f0f0;font-size: 36px;margin-bottom: 16px;font-weight: 600;"><%= request.getParameter("genre") %></h1>
                        <h1 style="/*display: inline-block;*/color: #f0f0f0;font-size: 14px;margin-bottom: 0;font-weight: 500;">${countAlbums} Albums</h1>
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
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list.get(0).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list.get(0).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                             <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list.get(1).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list.get(1).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                             <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list.get(2).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list.get(2).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                             <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list.get(3).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list.get(3).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col block-col">
                    <div class="row">
                        <div class="col align-self-center">
                            <h2 class="section-title"><i class="fa fa-calendar section-title-icon"></i>Brani più recenti del genere</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < latestList.get(0).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestList.get(0).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestList.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < latestList.get(1).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestList.get(1).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestList.get(1).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < latestList.get(2).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestList.get(2).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestList.get(2).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col block-col">
                    <div class="row">
                        <div class="col align-self-center">
                            <h2 class="section-title"><i class="fa fa-star section-title-icon"></i>Album migliori del genere</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < listAlbums.get(0).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${listAlbums.get(0).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${listAlbums.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < listAlbums.get(1).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${listAlbums.get(1).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${listAlbums.get(1).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < listAlbums.get(2).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${listAlbums.get(2).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${listAlbums.get(2).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < listAlbums.get(3).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${listAlbums.get(3).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${listAlbums.get(3).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col block-col">
                    <div class="row">
                        <div class="col align-self-center">
                            <h2 class="section-title"><i class="fa fa-calendar section-title-icon"></i>Album più recenti del genere</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < latestListAlbums.get(0).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestListAlbums.get(0).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestListAlbums.get(0).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                            <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < latestListAlbums.get(1).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestListAlbums.get(1).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestListAlbums.get(1).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="card home-card">
                                <div class="card-body">
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < latestListAlbums.get(2).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${latestListAlbums.get(2).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${latestListAlbums.get(2).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
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
                    <h2 class="section-title"><i class="fa fa-th-large section-title-icon"></i>Tutti i contenuti del genere<a id="filter-btn" href="#filterarea" data-toggle="collapse" aria-expanded="true" aria-controls="filterarea" role="button"><i class="fa fa-filter"></i></a></h2>
                </div>
                <div class="col d-inline-flex justify-content-end align-self-center filter-col"></div>
            </div>
        </div>
        <div class="container-fluid pulse animated">
             <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// --> 
            <div class="row">
                <div class="col block-col">
                <div class="row">
                 <c:forEach var="count" begin="0" end="3">
                        <div class="col">
                            <div class="card explore-card">
                                <div class="card-body">
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list1.get(count).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list1.get(count).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list1.get(count).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                </div>
                            </div>
                            </div>
                     </c:forEach>     
                   </div>     
              <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->         
                    <div class="row">
                <div class="col block-col">
                <div class="row">
                 <c:forEach var="count" begin="0" end="3">
                        <div class="col">
                            <div class="card explore-card">
                                <div class="card-body">
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list2.get(count).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list2.get(count).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list2.get(count).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                </div>
                            </div>
                            </div>
                     </c:forEach>     
                   </div>
 <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->                    
                    <div class="row">
                <div class="col block-col">
                <div class="row">
                 <c:forEach var="count" begin="0" end="3">
                        <div class="col">
                            <div class="card explore-card">
                                <div class="card-body">
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list3.get(count).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list3.get(count).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list3.get(count).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                </div>
                            </div>
                            </div>
                     </c:forEach>     
                   </div>
 <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->                    
                    <div class="row">
                <div class="col block-col">
                <div class="row">
                 <c:forEach var="count" begin="0" end="3">
                        <div class="col">
                            <div class="card explore-card">
                                <div class="card-body">
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list4.get(count).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list4.get(count).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list4.get(count).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                </div>
                            </div>
                            </div>
                     </c:forEach>     
                   </div>
 <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->                    
                   <div class="row">
                <div class="col block-col">
                <div class="row">
                 <c:forEach var="count" begin="0" end="3">
                        <div class="col">
                            <div class="card explore-card">
                                <div class="card-body">
                                    <div class="card-info">
                                        <ul class="list-inline">
                                           <c:forEach var="i" begin="0" end="4">
                                        		<c:choose>
                                            		<c:when test="${i < list5.get(count).getRating()}">
                                               			 <li class="list-inline-item star selected-star"><i class="fa fa-star"></i></li>
                                            		</c:when>
                                            		<c:otherwise>
                                                		<li class="list-inline-item star"><i class="fa fa-star"></i></li>
                                            		</c:otherwise>
                                        		</c:choose>
                                    		</c:forEach>
                                        </ul>
                                        <h4 class="card-title">${list5.get(count).getName()}</h4>
                                        <h6 class="text-muted card-subtitle mb-2"><i class="fa fa-user card-icon"></i>${list5.get(count).getUser()}</h6><a class="card-link" href="#">Scopri di più</a></div>
                                    <div class="card-img-cover" style="background-image: url(&quot;images/maxresdefault.jpg&quot;);"></div>
                                </div>
                            </div>
                            </div>
                     </c:forEach>     
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
    <script src="js/jquery.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="js/bs-init.js"></script>
    <script src="js/pageInputResizer.js"></script>
</body>

</html>