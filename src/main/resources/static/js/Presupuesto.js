// PARA CALCULOS PERTINENTES AL PRESUPUESTO
< !--
    $(function () {
        $("table").DataTable({
            "paging": false,
            "lengthChange": false,
            "searching": false,
            "ordering": false,
            "info": false,
            "autoWidth": false,
            "responsive": true,
        });

    });


const autoNumeric = new AutoNumeric.multiple('.number', {
    decimalPlaces: 0,
    decimalCharacter: ",",
    digitGroupSeparator: ".",
    unformatOnSubmit: true,
    watchExternalChanges: true
});

const autoNumericDecimal = new AutoNumeric.multiple('.decimal', {
    decimalPlaces: 2,
    decimalCharacter: ",",
    digitGroupSeparator: ".",
    unformatOnSubmit: true,
    watchExternalChanges: true
});
-->

    //Accion para rr y cargar modal de edicion de detalle
    $('.abrirModal').on(
        'click',
        function (event) {
            event.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function (detalle, status) {
                $('#subtotalEdit').val(detalle.subtotal);
                $('#idDetalleEdit').val(detalle.id);
                $('#idPresupuestoEdit').val(detalle.presupuesto.id);
                $('#productoNombreEdit').val(detalle.producto.nombre);
                $('#productoUnidadCobroEdit').val(
                    detalle.producto.unidadCobro);
                $('#cantidadEdit').val(detalle.cantidad);
                $('#precioProductoEdit').val(detalle.precioProducto);
                $('#precioPorDisenoEdit').val(detalle.precioPorDiseno);
                $('#precioPorTiempoEdit').val(detalle.precioPorTiempo);
                $('#tiempoNecesarioEdit').val(detalle.tiempoNecesario);
                $('#altoEdit').val(detalle.alto);
                $('#anchoEdit').val(detalle.ancho);
                $('#areaEdit').val(detalle.area);
                $('#anotacionEdit').val(detalle.anotacion);
                if (detalle.producto.unidadCobro == 'UN') {
                    $('#altoEdit').prop('readonly', true);
                    $('#anchoEdit').prop('readonly', true);
                }
                if (detalle.producto.unidadCobro == 'ML') {
                    $('#altoEdit').prop('readonly', false);
                    $('#anchoEdit').prop('readonly', true);
                }
                if (detalle.producto.unidadCobro == 'M2') {
                    $('#altoEdit').prop('readonly', false);
                    $('#anchoEdit').prop('readonly', false);
                }
            });
            $('#editDetalleModal').modal('show');
        });


function calcularArea() {
    var unidadCobro = document
        .getElementById('productoUnidadCobroEdit').value;
    var alto = parseNumber(document.getElementById('altoEdit').value);
    var ancho = parseNumber(document.getElementById('anchoEdit').value);
    if (unidadCobro == 'UN') {
        $('#areaEdit').val(1);
    }
    if (unidadCobro == 'M2') {
        $('#areaEdit').val(alto * ancho / 10000);
    }
    if (unidadCobro == 'ML') {
        $('#areaEdit').val(alto / 100);
    }
};

function calcularTotal() {
    var sumatoria = parseNumber((document.getElementById('sumatoria').value));
    var descuento = parseNumber((document.getElementById('descuento').value));
    $('#total').val((sumatoria - descuento));
}

function calcularSubtotal() {
    calcularArea();
    var unidadCobro = parseNumber(document
        .getElementById('productoUnidadCobroEdit').value);
    var cantidad = parseNumber(document.getElementById('cantidadEdit').value);
    var precioProducto = parseNumber(document
        .getElementById('precioProductoEdit').value);
    var precioPorDiseno = parseNumber(document
        .getElementById('precioPorDisenoEdit').value);
    var precioPorTiempo = parseNumber(document
        .getElementById('precioPorTiempoEdit').value);
    var tiempoNecesario = parseNumber(document
        .getElementById('tiempoNecesarioEdit').value);
    var area = parseNumber(document.getElementById('areaEdit').value);
    var calculo = ((precioProducto * cantidad * area)
        + (precioPorTiempo * tiempoNecesario) + (precioPorDiseno));
    $('#subtotalEdit').val(calculo);
};

function parseNumber(number) {
    return parseFloat(number.split('.').join('').replace(',', '.'))
}


		// 		var estadoPresupuesto = document.getElementById('estadoPresupuesto').value;
		// 		var btnGuardar = document.getElementById('btnGuardar');
		// 		var btnPagar = document.getElementById('btnPagar');
		// 		var btnDescargar = document.getElementById('btnDescargar');
		// 		var btnAprobar = document.getElementById('btnAprobar');
		// 		var btnRechazar = document.getElementById('btnRechazar');

		// 		if (estadoPresupuesto == 'true') {
		// 			deshabilitarBoton(btnGuardar);
		// 			deshabilitarBoton(btnAprobar);
		// 			deshabilitarBoton(btnRechazar);
		// 		} else if (estadoPresupuesto == 'false') {
		// 			deshabilitarBoton(btnGuardar);
		// 			deshabilitarBoton(btnPagar);
		// 			deshabilitarBoton(btnAprobar);
		// 			deshabilitarBoton(btnRechazar);
		// 		} else {
		// 			deshabilitarBoton(btnPagar);
		// 		}

		// 		function deshabilitarBoton(button) {
		// 			if (button.nodeName === "BUTTON") {
		// 				button.disabled = true;
		// 				button.hidden = true;
		// 			}

		// 			if (button.nodeName === "A") {
		// 				button.classList.add("disabled");
		// 				button.hidden = true;
		// 			}

		// 		}
