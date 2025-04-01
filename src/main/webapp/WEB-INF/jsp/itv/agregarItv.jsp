<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Revisión ITV</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
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
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/coches/${matricula}">Detalles del Coche</a></li>
        <li class="breadcrumb-item active" aria-current="page">ITV</li>
    </ol>
</nav>
<div class="container mt-5">
    <h2 class="text-center">ITV</h2>
    <form:form modelAttribute="itv" method="POST" class="mt-3">
        <input type="hidden" name="matricula" value="${matricula}" />
        <div class="form-row">
            <div class="col-md-6">
                <div class="form-group">
                    <form:label path="precio">Precio:</form:label>
                    <form:input path="precio" type="number" step="0.01" class="form-control" required="true"/>
                </div>
                <div class="form-group">
                    <form:label path="kmRevision">Kilómetros Revisión:</form:label>
                    <form:input path="kmRevision" type="number" step="1" class="form-control" required="true"/>
                </div>
                <div class="form-group">
                    <form:label path="fechaApto">Fecha Apto:</form:label>
                    <form:input path="fechaApto" type="date" class="form-control" required="true"/>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <form:label path="fechaProximaItv">Fecha Próxima ITV:</form:label>
                    <form:input path="fechaProximaItv" type="date" class="form-control" required="true"/>
                </div>
                <div class="form-row">
                    <div class="col-md-6">
                        <div class="form-group form-check mt-4">
                            <form:checkbox path="apto" class="form-check-input" id="apto"/>
                            <form:label path="apto" for="apto" class="form-check-label">Apto</form:label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <form:label path="observaciones">Observaciones:</form:label>
                            <form:textarea path="observaciones" rows="5" class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-12 d-flex justify-content-center mt-4">
                <button type="submit" class="btn btn-light">Guardar</button>
            </div>
        </div>
    </form:form>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
