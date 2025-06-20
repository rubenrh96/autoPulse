$(function () {
    $('#btnMostrarItv').on('click', function () {
        const matricula = $('#matricula').val() || $(this).data('matricula');

        $.get(`/coches/${matricula}/modal`)
            .done(resp => {
                $('#itvMsg').html(resp.mensajeModalItv || 'Sin datos.');
                $('#mensajeModalItv').modal('show');
            })
            .fail(() => alert('Error al obtener el mensaje de ITV'));
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const botonMostrar = document.getElementById('btnMostrarMantenimiento');
    if (!botonMostrar) return;

    botonMostrar.addEventListener('click', function () {
        const matricula = document.getElementById('matricula')?.value || '';

        fetch(`/coches/${matricula}/modal`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la respuesta");
                }
                return response.json();
            })
            .then(data => {
                document.querySelector('#mensajeModal #motor').innerHTML = data.mensajeModalMotor;
                document.querySelector('#mensajeModal #cambio').innerHTML = data.mensajeModalCambio;
                new bootstrap.Modal(document.getElementById('mensajeModal')).show();
            })
            .catch(error => {
                console.error("Error al obtener el mensaje de mantenimiento:", error);
                alert("Error al obtener el mensaje de mantenimiento.");
            });
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const btnAbrirModal = document.getElementById('btnActualizarMantenimiento');
    const btnActualizar = document.getElementById('actualizarKilometrajeBtn');
    const modalElement = document.getElementById('actualizarKilometrajeModal');

    if (!btnAbrirModal || !btnActualizar || !modalElement) return;

    btnAbrirModal.addEventListener('click', function () {
        new bootstrap.Modal(modalElement).show();
    });

    btnActualizar.addEventListener('click', function () {
        const matricula = document.getElementById('matricula')?.value;
        const kilometros = document.getElementById('kilometros')?.value;

        if (!matricula || !kilometros) {
            alert("Debes completar todos los campos.");
            return;
        }

        fetch(`/coches/${matricula}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({ kilometros: kilometros })
        })
        .then(response => {
            if (!response.ok) throw new Error("Error en la respuesta del servidor.");
            return response.text();
        })
        .then(() => {
            bootstrap.Modal.getInstance(modalElement).hide();
            alert("Kilometraje actualizado correctamente.");
            window.location.reload(true);
        })
        .catch(() => {
            alert("Error al actualizar el kilometraje.");
        });
    });
});


