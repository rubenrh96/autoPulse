<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Mantenimientos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<!-- Formulario de Filtros -->
<div class="container mt-5">
    <div id="fomularioBuscador" class="card">
        <h5 class="card-header">Buscar Mantenimientos</h5>
        <div class="card-body">
            <form action="/mantenimientos/buscador/" method="get">
                <div class="form-row">
                    <!-- Matrícula -->
                    <div class="col-md-4 mb-3">
                        <label for="matricula">Matrícula</label>
                        <input type="text" class="form-control" id="matricula" name="matricula" placeholder="Matrícula" value="${matricula}" />
                    </div>
                    <!-- Año -->
                    <div class="col-md-4 mb-3">
                        <label for="ano">Año</label>
                        <input type="number" class="form-control" id="ano" name="ano" placeholder="Año" min="1900" max="2099" value="${ano}" />
                    </div>
                    <!-- Precio Desde -->
                    <div class="col-md-4 mb-3">
                        <label for="precioDesde">Precio Desde</label>
                        <input type="number" class="form-control" id="precioDesde" name="precioDesde" placeholder="Precio Desde" value="${precioDesde}" />
                    </div>
                    <!-- Precio Hasta -->
                    <div class="col-md-4 mb-3">
                        <label for="precioHasta">Precio Hasta</label>
                        <input type="number" class="form-control" id="precioHasta" name="precioHasta" placeholder="Precio Hasta" value="${precioHasta}" />
                    </div>
                    <!-- Categoría -->
                    <div class="col-md-4 mb-3">
                        <label for="idCategoria">Categoría</label>
                        <select class="form-control" id="idCategoria" name="idCategoria">
                            <option value="">Seleccione Categoría</option>
                            <c:if test="${not empty categorias}"> 
                                <c:forEach items="${categorias}" var="categoria">
                                    <option value="${categoria.idCategoria}">${categoria.descripcion}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>
                <!-- Botón de búsqueda -->
                <div class="d-flex justify-content-center">
                	<button type="submit" class="btn btn-dark">Buscar</button>
               	</div>
            </form>
        </div>
    </div>
</div>


<div id="mantenimientos">
    <div class="container mt-5">
        <div class="row">
            <c:forEach var="mantenimiento" items="${resultado}" varStatus="status">
                <div class="card-deck col-md-6 mb-4">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h6 class="card-title"><strong>${mantenimiento.categoria.descripcion}</strong> realizado: <fmt:formatDate value="${mantenimiento.fecha}" pattern="dd-MM-yyyy"/></h6>
                            <p class="card-text">
                                <strong>Descripción:</strong> ${mantenimiento.descripcion}<br>
                                <strong>Precio:</strong> ${mantenimiento.precio}€<br>
                            </p>
                        </div>
                        <div class="card-footer">
                            <a href="/coches/${mantenimiento.coche.matricula}" class="btn btn-secondary"><i class="fas fa-eye"></i> Ver</a>
                            <%-- <a href="/mantenimientos/eliminar/${mantenimiento.idFactura}" class="btn btn-danger"><i class="fas fa-trash"></i> Eliminar</a> --%>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
