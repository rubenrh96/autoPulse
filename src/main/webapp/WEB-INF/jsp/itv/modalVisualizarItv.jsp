<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet"	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
		<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/estilos.css">
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="modal" tabindex="-1" role="dialog" id="mensajeModalItv"> 
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="text-center modal-title">Advertencia de ITV</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="itv">${mensajeModalItv}</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <script>
    $(document).ready(function() {
        // Evento de hover en el botón de mostrar mantenimiento
        $('#btnMostrarItv').click(function() { // Mouse enter
            var matricula = $("#matricula").val();
            $.ajax({
                url: '/coches/' + matricula + '/modal', // Ruta del controlador
                type: 'GET', // Tipo de solicitud
                success: function(response) {
                    // Si la solicitud es exitosa, establecer el mensaje en el cuerpo del modal
                    $('#mensajeModalItv .modal-body p#itv').html(response.mensajeModalItv);
                    // Mostrar el modal
                    $('#mensajeModalItv').modal('show');
                },
                error: function() {
                    // En caso de error, puedes mostrar un mensaje o manejar el error como prefieras
                    alert('Error al obtener el mensaje de fecha ITV.');
                }
            });
        });
    });
</script>
</body>
</html>
