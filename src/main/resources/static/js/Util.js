$(document).ready(function () {
    getMonedaDeConfiguracionActual();
    searchableSelector();
    searchableMultipleSelector();
});

function getMonedaDeConfiguracionActual(){
    var getConfiguracion = '/configuracion/get';
    $.get(getConfiguracion, function (cfg, status) {
        formatNumericField(cfg.moneda.nombre);
    });
}

function formatNumericField(moneda) {
    switch (moneda) {
        case 'GUARANI':
            const GUARANI = new AutoNumeric.multiple('.number', {
                decimalPlaces: 0,
                currencySymbol: "₲",
                decimalCharacter: ",",
                digitGroupSeparator: ".",
                styleRules: {
                    positive: "autoNumeric-positive",
                    negative: "autoNumeric-negative"
                },
                unformatOnSubmit: true,
                watchExternalChanges: true,
                wheelOn: "hover"
            });
            break;
        case 'REAL':
            const REAL = new AutoNumeric.multiple('.number', {
                decimalPlaces: 2,
                currencySymbol: "R$",
                decimalCharacter: ",",
                digitGroupSeparator: ".",
                styleRules: {
                    positive: "autoNumeric-positive",
                    negative: "autoNumeric-negative"
                },
                unformatOnSubmit: true,
                watchExternalChanges: true,
                wheelOn: "hover"
            });
            break;
        case 'EURO':
            const EURO = new AutoNumeric.multiple('.number', {
                decimalPlaces: 2,
                currencySymbol: "€",
                decimalCharacter: ",",
                digitGroupSeparator: ".",
                styleRules: {
                    positive: "autoNumeric-positive",
                    negative: "autoNumeric-negative"
                },
                unformatOnSubmit: true,
                watchExternalChanges: true,
                wheelOn: "hover"
            });
            break;
        case 'DOLAR':
            const DOLAR = new AutoNumeric.multiple('.number', {
                decimalPlaces: 2,
                currencySymbol: "$",
                decimalCharacter: ".",
                digitGroupSeparator: ",",
                styleRules: {
                    positive: "autoNumeric-positive",
                    negative: "autoNumeric-negative"
                },
                unformatOnSubmit: true,
                watchExternalChanges: true,
                wheelOn: "hover"
            });
            break;
        case 'PESO':
            const PESO = new AutoNumeric.multiple('.number', {
                decimalPlaces: 2,
                currencySymbol: "₱",
                decimalCharacter: ",",
                digitGroupSeparator: ".",
                styleRules: {
                    positive: "autoNumeric-positive",
                    negative: "autoNumeric-negative"
                },
                unformatOnSubmit: true,
                watchExternalChanges: true,
                wheelOn: "hover"
            });
            break;
        default:
            break;
    }
}

function searchableSelector() {
    $('select').select2();
    $('.searchableSelector').select2();
}


function searchableMultipleSelector() {
    $('select[multiple]').select2({
        multiple: true
    });
    $('.searchableMultipleSelector').select2({
        multiple: true
    });
}