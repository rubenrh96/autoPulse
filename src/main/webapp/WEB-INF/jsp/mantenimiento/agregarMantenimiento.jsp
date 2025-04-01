<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Mantenimiento</title>
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
        <li class="breadcrumb-item active" aria-current="page">Mantenimiento</li>
    </ol>
</nav>
<div class="container mt-5">
    <h2 class="text-center">Mantenimiento</h2>
    <form:form modelAttribute="mantenimiento" method="POST" class="mt-3">
        <input type="hidden" name="matricula" value="${matricula}" />
        <div class="form-row">
            <!-- Columna Izquierda -->
            <div class="col-md-6">
                <div class="form-group">
                    <form:label path="categoria">Categoría</form:label>
                    <form:select path="categoria.idCategoria" class="form-control">
                        <form:option value="" label="-- Seleccionar --" />
                        <form:options items="${categorias}" itemValue="idCategoria" itemLabel="descripcion" />
                    </form:select>
                </div>
                <div class="form-group">
                    <form:label path="precio">Precio</form:label>
                    <form:input path="precio" type="number" step="0.01" class="form-control" required="true" />
                </div>
                <div class="form-group">
                    <form:label path="descripcion">Descripción</form:label>
                    <form:textarea path="descripcion" class="form-control" required="true" />
                </div>
            </div>
            <!-- Columna Derecha -->
            <div class="col-md-6">
                <div class="form-group">
                    <form:label path="fecha">Fecha</form:label>
                    <form:input path="fecha" type="date" class="form-control" required="true" />
                </div>
                <div class="form-group">
                    <form:label path="kmMantenimiento">Kilómetros de Mantenimiento</form:label>
                    <form:input path="kmMantenimiento" type="number" class="form-control" required="true" />
                </div>
                <div class="form-group">
                    <div class="form-check mt-4">
                        <form:checkbox path="pagado" class="form-check-input" />
                        <form:label path="pagado" class="form-check-label">Pagado</form:label>
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

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
