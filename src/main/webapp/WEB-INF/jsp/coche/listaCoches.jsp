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
        <li class="breadcrumb-item active" aria-current="page">Inicio</li>
    </ol>
</nav>
<!-- Botones para seleccionar la lista a mostrar -->
<div class="tab-buttons-container">
    <button class="tab-button active" onclick="showContent('coches')">Coches</button>
    <button class="tab-button" onclick="showContent('recambios')">Recambios</button>
</div>

<!-- Panel para la lista de coches -->
<div id="coches" class="content-panel active">
    <div class="container mt-5">
        <div class="row">
            <c:forEach var="coche" items="${coches}" varStatus="status">
                <div class="card-deck col-md-4 mb-4">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h5 class="card-title">${coche.marca} ${coche.modelo}</h5>
                            <p class="card-text">
                                <strong>Matrícula:</strong> ${coche.matricula}<br>
                            </p>
                        </div>
                        <div class="card-footer">
                            <a href="/coches/${coche.matricula}" class="btn btn-secondary"><i class="fas fa-eye"></i> Ver</a>
                            <a href="/" class="btn btn-danger"><i class="fas fa-trash"></i> Eliminar</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Panel para la lista de recambios -->
<div id="recambios" class="content-panel">
    <div class="container mt-5">
        <div class="row">
            <c:forEach var="recambio" items="${recambios}" varStatus="status">
                <div class="card-deck col-md-4 mb-4">
                    <div class="card card-custom">
                        <div class="card-body">
                        	<div class="d-flex justify-content-between">
                            	<h5 class="card-title  mb-0">${recambio.descripcion}</h5>
                            	<h5 class="card-title  mb-0">x${recambio.cantidad}</h5>
                            </div>
                            <br>
                            <p><strong><fmt:formatDate value="${recambio.fechaCompra}" pattern="dd-MM-yyyy" /></strong></p>
                            <div class="d-flex justify-content-between">
							    <p class="card-text mb-0">
							        <strong>${recambio.categoria.descripcion}</strong>
							    </p>
							    <p class="card-text mb-0">
							        <strong>${recambio.precio}€</strong>
							    </p>
							</div>
                        </div>
                        <div class="card-footer">
                            <a href="/recambios/${recambio.idRecambio}" class="btn btn-secondary"><i class="fas fa-eye"></i> Ver</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script>
    // Función para mostrar el contenido correspondiente y activar el botón seleccionado
    function showContent(type) {
        // Esconder todos los paneles
        var panels = document.getElementsByClassName('content-panel');
        for (var i = 0; i < panels.length; i++) {
            panels[i].style.display = 'none';
            panels[i].classList.remove('active');
        }

        // Desactivar todos los botones
        var tabButtons = document.getElementsByClassName('tab-button');
        for (var i = 0; i < tabButtons.length; i++) {
            tabButtons[i].classList.remove('active');
        }

        // Mostrar el panel seleccionado
        document.getElementById(type).style.display = 'block';
        document.getElementById(type).classList.add('active');

        // Activar el botón seleccionado
        if(type === 'coches') {
            tabButtons[0].classList.add('active');
        } else {
            tabButtons[1].classList.add('active');
        }
    }
</script>
</body>
</html>
