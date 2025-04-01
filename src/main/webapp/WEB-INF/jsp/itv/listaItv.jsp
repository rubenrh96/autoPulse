<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Coches</title>
    <link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet"	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/estilos.css">
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div>
	<jsp:include page="../cabeceraAplicacion.jsp" />
</div>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/coches">Inicio</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/coches/${matricula}">Detalles del Coche</a></li>
        <li class="breadcrumb-item active" aria-current="page">Listado de ITVs</li>
    </ol>
</nav>
<!-- Panel para la lista de coches -->
<div id="itvs" class="content-panel active"><br>
    <div class="container mt-5">
        <div class="row">
            <c:forEach var="itv" items="${listaItv}" varStatus="status">
                <div class="card-deck col-lg-4 col-md-6 col-sm-6 mb-4">
                    <div class="card card-custom">
                        <div class="card-body">
                        <div class="d-flex justify-content-between">
                            	<h5 class="card-title mb-0">Apto: <fmt:formatDate value="${itv.fechaApto}" pattern="dd-MM-yyyy"/></h5>
                            	<h5 class="card-title mb-0">${itv.kmRevision}km</h5>
                            </div>
                            <br>
                            <p class="card-text">${itv.observaciones}</p>
                            <div class="d-flex justify-content-between">
							    <p class="card-text mb-0">${itv.coche.matricula}</p>
							    <p class="card-text mb-0">
							        <strong>${itv.precio}€</strong>
							    </p>
							</div>
                        </div>
                        <div class="card-footer">
                            <a href="/coches/${coche.matricula}" class="btn btn-secondary"><i class="fas fa-eye"></i> Ver</a>
                            <a href="/" class="btn btn-danger"><i class="fas fa-trash "></i> Eliminar</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
