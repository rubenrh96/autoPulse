<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <li class="breadcrumb-item active" aria-current="page">Neumatico</li>
    </ol>
</nav>
<div class="container mt-4">
    <h2 class="text-center">Neumático</h2>
    <form:form modelAttribute="neumatico" method="POST" class="mt-3">
        <input type="hidden" name="matricula" value="${matricula}" />
        <div class="form-row">
        <!-- Columna Izquierda -->
	        <div class="col-md-6">
	            <div class="form-group">
	                <form:label path="marca">Marca:</form:label>
	                <form:input path="marca" class="form-control" required="required" />
	            </div>
	            <div class="form-group">
	                <form:label path="modelo">Modelo:</form:label>
	                <form:input path="modelo" class="form-control" required="required" />
	            </div>
	            <div class="form-group">
	                <form:label path="anchoLlanta">Ancho de Llanta:</form:label>
	                <form:input path="anchoLlanta" type="number" class="form-control" required="required"  />
	            </div>
	            <div class="form-group">
	                <form:label path="perfilLlanta">Perfil de Llanta:</form:label>
	                <form:input path="perfilLlanta" type="number" class="form-control" required="required" />
	            </div>
	            <div class="form-group">
	                <form:label path="diametroLlanta">Diámetro de Llanta:</form:label>
	                <form:input path="diametroLlanta" class="form-control" required="required"  />
	            </div>
	            <div class="form-group">
	                <form:label path="indiceCarga">Índice de Carga:</form:label>
	                <form:input path="indiceCarga" type="number" class="form-control" required="required"  />
	            </div>
	            <div class="form-group">
	                <form:label path="indiceVelocidad">Índice de Velocidad:</form:label>
	                <form:input path="indiceVelocidad" class="form-control" required="required" />
	            </div>
	        </div>
	        
	        <!-- Columna Derecha -->
	        <div class="col-md-6">
	            <div class="form-group">
	                <form:label path="precio">Precio:</form:label>
	                <form:input path="precio" type="number" step="0.01" class="form-control" required="required"  />
	            </div>
	            <div class="form-group">
	                <form:label path="kmMontaje">Kilómetros al Montaje:</form:label>
	                <form:input path="kmMontaje" type="number" class="form-control" required="required" />
	            </div>
	            <div class="form-group">
	                <form:label path="fechaMontaje">Fecha de Montaje:</form:label>
	                <form:input path="fechaMontaje" type="date" class="form-control" required="required"  />
	            </div>
	            <div class="form-group">
	                <form:label path="descripcion">Descripción:</form:label>
	                <form:textarea path="descripcion" class="form-control" required="required" />
	            </div>
	            <div class="form-group">
	                <form:label path="numero">Número neumáticos:</form:label>
	                <form:input path="numero" type="number" class="form-control" required="required"  />
	            </div>
	            <div class="form-group">
	                <div class="form-check mt-4">
	                    <form:checkbox path="ms" class="form-check-input" id="ms"/>
	                    <form:label path="ms" class="form-check-label" for="ms" required="required" >M+S</form:label>
	                </div>
	            </div>
	        </div>
	    </div>
        <div class="form-row">
            <div class="col-md-12 d-flex justify-content-center mt-4">
                <button type="submit" class="btn btn-light">Agregar</button>
            </div>
        </div>
    </form:form>
</div>


<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>

</body>
</html>
