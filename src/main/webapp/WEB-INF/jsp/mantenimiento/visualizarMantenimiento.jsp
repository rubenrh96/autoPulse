<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Detalles del Mantenimiento</title>
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
        <li class="breadcrumb-item active" aria-current="page">Detalles del Mantenimiento</li>
    </ol>
</nav>

<div class="container mt-5">
    <div class="card text-dark">
        <h5 class="card-header">Detalles de Mantenimiento</h5>
        <div class="card-body">
            <h5 class="card-title">${mantenimiento.coche.matricula}</h5>
            <p class="card-text"><strong>Descripción:</strong> ${mantenimiento.descripcion}</p>
            <p class="card-text"><strong>Precio:</strong> ${mantenimiento.precio}</p>
            <p class="card-text"><strong>Fecha:</strong> <fmt:formatDate value="${mantenimiento.fecha}" pattern="dd-MM-yyyy"/></p>
            <p class="card-text"><strong>Kilometraje del mantenimiento:</strong> ${mantenimiento.kmMantenimiento} km</p>
            <p class="card-text"><strong>Pagado:</strong> ${mantenimiento.pagado ? 'Sí' : 'No'}</p>
            <p class="card-text"><strong>Categoría:</strong> ${mantenimiento.categoria.descripcion}</p>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
