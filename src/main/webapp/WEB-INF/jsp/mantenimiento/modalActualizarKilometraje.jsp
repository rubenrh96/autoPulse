<div class="modal fade" id="actualizarKilometrajeModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel">¿Desea actualizar los kilómetros?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="actualizarKilometrajeForm">
				    <div class="form-group">
				        <label for="kilometros">Kilómetros actuales</label>
				        <input type="number" class="form-control" id="kilometros" name="kilometros" required>
				        <input type="hidden" id="matricula" name="matricula" value="${coche.matricula}" />
				    </div>
				    <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
				        <button type="button" id="actualizarKilometrajeBtn" class="btn btn-primary">Actualizar</button>
				    </div>
				</form>
            </div>
        </div>
    </div>
</div>


<script>
$(document).ready(function() {
	$('#btnActualizarMantenimiento').click(function() {
		$("#actualizarKilometrajeModal").modal('show');
	});
	    $("#actualizarKilometrajeBtn").click(function() {
	        var matricula = $("#matricula").val();
	        var kilometros = $("#kilometros").val();
	        
	        $.ajax({
	            url: "/coches/" + matricula,
	            type: "POST",
	            data: {
	                "kilometros": kilometros
	            },
	            success: function(response) {
	                $("#actualizarKilometrajeModal").modal('hide');
	                alert("Kilometraje actualizado correctamente.");
	            },
	            error: function(xhr, status, error) {
	                alert("Error al actualizar el kilometraje.");
	            },
	            complete: function() {
	                // Recarga la página desde el servidor una vez que la petición AJAX se completa
	                window.location.reload(true);
	            }
	        });
	    });
	});
</script>