<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Producto</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
		<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/estilos.css">
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div>
	<jsp:include page="../cabeceraAplicacion.jsp" />
</div>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/coches">Inicio</a></li>
        <li class="breadcrumb-item active" aria-current="page">Recambio</li>
    </ol>
</nav>
<div class="container mt-5">
    <h2>Agregar Producto</h2>
    <form:form modelAttribute="recambio" method="post" class="mt-3">
        <div class="form-group col-md-6">
            <form:label path="categoria">Categoría:</form:label>
            <form:select path="categoria" class="form-control">
                <form:options items="${categorias}" itemValue="idCategoria" itemLabel="descripcion"/>
            </form:select>
        </div>
        <div class="form-group col-md-6">
            <form:label path="descripcion">Descripción:</form:label>
            <form:input path="descripcion" type="text" class="form-control" required="true"/>
        </div>
        <div class="form-group col-md-6">
            <form:label path="precio">Precio:</form:label>
            <form:input path="precio" type="number" step="0.01" class="form-control" required="true"/>
        </div>
        <div class="form-group col-md-6">
            <form:label path="fechaCompra">Fecha de Compra:</form:label>
            <form:input path="fechaCompra" type="date" class="form-control" required="true"/>
        </div>
        <div class="d-flex justify-content-center">
        	<button type="submit" class="btn btn-dark">Guardar</button>
       	</div>
    </form:form>
</div>
</body>
</html>
