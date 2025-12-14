$(document).ready(function () {

        function validarPasswords() {
            const pass = document.getElementById("password").value;
            const confirm = document.getElementById("confirmPassword").value;
            const error = document.getElementById("passwordError");
            if (pass !== confirm) {
                error.style.display = "block";
                return false;
            }
            error.style.display = "none";
            return true;
        }

    // Toggle explícito del menú lateral de usuario
    $(document).on('click', '[data-toggle="collapse"][data-target="#sidebarMenu"]', function (event) {
        event.preventDefault();
        $('#sidebarMenu').collapse('toggle');
    });

    $(document).on('click', function (event) {
        const sidebar = $('#sidebarMenu');
        const toggle = $('[data-target="#sidebarMenu"]');

        if (
            sidebar.hasClass('show') &&
            !sidebar.is(event.target) &&
            sidebar.has(event.target).length === 0 &&
            !toggle.is(event.target) &&
            toggle.has(event.target).length === 0
        ) {
            sidebar.collapse('hide');
        }
    });

    // Validación visual Bootstrap para formularios marcados
    (function () {
        const forms = document.querySelectorAll('.js-validate');
        Array.prototype.forEach.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();

    $(function () {
            $('[data-toggle="tooltip"]').tooltip();
    });


    setTimeout(function () {
        const alert = document.querySelector('.alert');
        if (alert) {
            alert.classList.remove('show');
            alert.classList.add('fade');
            alert.style.opacity = 0;
            setTimeout(() => alert.remove(), 500);
        }
    }, 5000);

});



