<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Detalles del Coche</title>
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
        <li class="breadcrumb-item active" aria-current="page">Detalles del Coche</li>
    </ol>
</nav>
<div class="container mt-4">
	<div class="row">
		<div class="col-md-4">
			<div class="car-details">
				<div class="car-detail-header">
					<i class="fas fa-car mr-1"></i>Detalles del Coche
				</div>
				<div class="car-detail">
					<strong>Matrícula:</strong> ${coche.matricula}
				</div>
				<div class="car-detail">
					<strong>Marca:</strong> ${coche.marca}
				</div>
				<div class="car-detail">
					<strong>Modelo:</strong> ${coche.modelo}
				</div>
				<div class="car-detail">
					<strong>Año:</strong> ${coche.ano}
				</div>
				<div class="car-detail">
					<strong>CV:</strong> ${coche.cv}
				</div>
				<div class="car-detail">
					<strong>Kilómetros:</strong> ${coche.kilometros} Km
					<a id="btnActualizarMantenimiento" href="javascript:void(0)" class="btn btn-dark float-right mr-1"><i class="fas fa-edit"></i></a>
				</div>
				<div class="car-detail">
					<strong>Color:</strong>
					<div class="color-square" style="background-color: grey;"></div>
				</div>
			</div>
		</div>

		<div class="accordion-col col-md-8">
			<div id="accordion">
				<div class="card">
					<div class="card-header" id="headingOne">
							<button class="btn collapsed" data-toggle="collapse"
								data-target="#collapseOne" aria-expanded="false"
								aria-controls="collapseOne"><i class="fas fa-clipboard-check"></i> ITV</button>
						<a href="/itvs/${coche.matricula}" class="btn btn-dark float-right mr-1"><i	class="fas fa-plus"></i></a>
						<a href="/itvs/lista/${coche.matricula}" class="btn btn-success float-right mr-1"><i class="fas fa-tasks"></i></a>
						<a href="javascript:void(0)" id="btnMostrarItv" class="btn btn-warning float-right mr-1"><i class="fas fa-exclamation"></i></a>
					</div>

					<div id="collapseOne" class="collapse"
						aria-labelledby="headingOne" data-parent="#accordion">
						<div class="card-body">
							<table class="table text-center">
								<thead class="thead-dark">
									<tr>
										<th>Km revisión</th>
										<th>Fecha apto</th>
										<th>Precio Total</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${itv.kmRevision}Km</td>
										<td><fmt:formatDate value="${itv.fechaApto}" pattern="dd-MM-yyyy" /></td>
										<td>${itv.precio}€</td>
										<td><a href="/visualizarItv/${itv.idFactura}" class="btn btn-primary"><i
												class="fas fa-eye"></i></a> <a
											href="/eliminarItv/${itv.idFactura}" class="btn btn-danger"><i
												class="fas fa-trash"></i></a></td>
									</tr>
								</tbody>
							</table>
							<p class="text-dark">Dinero total pagado en ITV: ${precioTotal}€</p>
						</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" id="headingTwo">
							<button class="btn collapsed" data-toggle="collapse"
								data-target="#collapseTwo" aria-expanded="false"
								aria-controls="collapseTwo"><i class="fas fa-oil-can"></i> Mantenimiento</button>
						<a href="/mantenimientos/${coche.matricula}" class="btn btn-dark float-right mr-1"><i class="fas fa-plus"></i></a>
						<a href="/mantenimientos/lista/${coche.matricula}" class="btn btn-success float-right mr-1"><i class="fas fa-tasks"></i></a>
						<a href="javascript:void(0)" id="btnMostrarMantenimiento" class="btn btn-warning float-right mr-1"><i class="fas fa-exclamation"></i></a>
						<a href="/mantenimientos/buscador/${coche.matricula}" class="btn btn-info float-right mr-1"><i class="fas fa-search"></i></a>
					</div>
					<div id="collapseTwo" class="collapse"
						aria-labelledby="headingTwo" data-parent="#accordion">
						<div class="card-body">
							<table class="table text-center">
								<thead class="thead-dark">
									<tr>
										<th>Precio Total</th>
										<th>Km mantenimiento</th>
										<th>Fecha</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${mantenimiento.precio}€</td>
										<td>${mantenimiento.kmMantenimiento}Km</td>
										<td><fmt:formatDate value="${mantenimiento.fecha}" pattern="dd-MM-yyyy" /></td>
										<td><a href="/visualizarMantenimiento/${mantenimiento.idFactura}" class="btn btn-primary"><i
												class="fas fa-eye"></i></a>
											<a href="/eliminarMantenimiento/${mantenimiento.idFactura}"
											class="btn btn-danger"><i class="fas fa-trash"></i></a>
										</td>
									</tr>
								</tbody>
							</table>
							<p class="text-dark">Dinero total pagado en Mantenimiento: ${precioTotalMantenimientos}€</p>
						</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" id="headingThree">
							<button class="btn collapsed" data-toggle="collapse"
								data-target="#collapseThree" aria-expanded="false"
								aria-controls="collapseThree"><i class="fas fa-life-ring"></i> Neumáticos</button>
								<a href="/neumaticos/${coche.matricula}" class="btn btn-dark float-right mr-1"><i class="fas fa-plus"></i></a>
								<a href="/neumaticos/lista/${coche.matricula}" class="btn btn-success float-right mr-1"><i class="fas fa-tasks"></i></a>
					</div>
					<div id="collapseThree" class="collapse"
						aria-labelledby="headingThree" data-parent="#accordion">
						<div class="card-body">
							<table class="table text-center">
								<thead class="thead-dark">
									<tr>
										<th>Marca</th>
										<th>Modelo</th>
										<th>Información</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${neumaticos}" var="neumatico">
										<tr>
											<td>${neumatico.marca}</td>
											<td>${neumatico.modelo}</td>
											<td>${neumatico.anchoLlanta}/${neumatico.perfilLlanta}
												R${neumatico.diametroLlanta}
												${neumatico.indiceCarga}${neumatico.indiceVelocidad}</td>
											<td><a href="/" class="btn btn-primary"><i
													class="fas fa-eye"></i></a> <a
												href="/eliminarNeumatico/${neumatico.idNeumatico}" class="btn btn-danger"><i class="fas fa-trash"></i></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p class="text-dark">Dinero total pagado en Neumáticos:${precioTotalNeumaticos}€</p>
						</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" id="headingFour">
							<button class="btn collapsed" data-toggle="collapse"
								data-target="#collapseFour" aria-expanded="false"
								aria-controls="collapseFour"><i class="fas fa-gas-pump"></i> Repostajes</button>
						<a href="/repostajes/${coche.matricula}" class="btn btn-dark float-right mr-1"><i class="fas fa-plus"></i></a>
						<a href="/repostajes/lista/${coche.matricula}" class="btn btn-success float-right mr-1"><i class="fas fa-tasks"></i></a>
					</div>
					<div id="collapseFour" class="collapse"
						aria-labelledby="headingFour" data-parent="#accordion">
						<div class="card-body">
							<table class="table text-center">
								<thead class="thead-dark">
									<tr>
										<th>Precio Pagado</th>
										<th>Litros</th>
										<th>Precio/Litro</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${repostaje.precio}€</td>
										<td>${repostaje.litros}L</td>
										<td>${repostaje.precioLitro}€/L</td>
										<td><a href="/visualizarRepostaje/${repostaje.idRepostaje}" class="btn btn-primary"><i
												class="fas fa-eye"></i></a> <a
											href="/eliminarRepostaje/${repostaje.idRepostaje}"
											class="btn btn-danger"><i class="fas fa-trash"></i></a></td>
									</tr>
								</tbody>
							</table>
							<p class="text-dark">Dinero total pagado en Repostajes: ${precioTotalRepostajes}€</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<jsp:include page="../mantenimiento/modalActualizarKilometraje.jsp" />
<jsp:include page="../mantenimiento/modalVisualizarMantenimiento.jsp" />
<jsp:include page="../itv/modalVisualizarItv.jsp" />
</body>
</html>
