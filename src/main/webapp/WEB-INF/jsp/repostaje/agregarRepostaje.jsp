<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Repostaje</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
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
        <li class="breadcrumb-item active" aria-current="page">Repostaje</li>
    </ol>
</nav>
<div class="container mt-4">
    <h2 class="text-center">Repostaje</h2>
    <form:form modelAttribute="repostaje" method="POST" class="row">
        <input type="hidden" name="matricula" value="${matricula}" />
        <div class="form-group col-md-6">
            <form:label path="precio">Precio Total:</form:label>
            <form:input path="precio" type="number" step="0.01" class="form-control" required="required" id="precio"/>
        </div>
        <div class="form-group col-md-6">
            <form:label path="litros">Litros:</form:label>
            <form:input path="litros" type="number" step="0.01" class="form-control" required="required" id="litros"/>
        </div>
        <div class="form-group col-md-6">
            <form:label path="precioLitro">Precio por Litro:</form:label>
            <form:input path="precioLitro" type="number" step="0.01" class="form-control" required="required" readonly="readonly" id="precioLitro"/>
        </div>
        <div class="form-group col-md-6">
            <form:label path="kmRepostaje">Kilómetros al Repostaje:</form:label>
            <form:input path="kmRepostaje" type="number" class="form-control" required="required"/>
        </div>
        <div class="form-group col-md-6">
            <form:label path="fecha">Fecha:</form:label>
            <form:input path="fecha" type="date" class="form-control" required="true"/>
        </div>
        <div class="col-md-12 d-flex justify-content-center mt-4">
            <button type="submit" class="btn btn-light">Guardar</button>
        </div>
    </form:form>
</div>

<script>
// Función para calcular el precio por litro
function calcularPrecioPorLitro() {
    var precio = document.getElementById('precio').value;
    var litros = document.getElementById('litros').value;
    var precioPorLitro = precio / litros;

    if (!isNaN(precioPorLitro) && precioPorLitro > 0) {
        document.getElementById('precioLitro').value = precioPorLitro.toFixed(2);
    } else {
        document.getElementById('precioLitro').value = '';
    }
}

// Event listener para los campos de precio total y litros
document.getElementById('precio').addEventListener('input', calcularPrecioPorLitro);
document.getElementById('litros').addEventListener('input', calcularPrecioPorLitro);
</script>


<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
