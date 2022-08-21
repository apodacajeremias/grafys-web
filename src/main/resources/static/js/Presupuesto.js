// import { AutoNumeric } from "../autonumeric/autoNumeric.min";
// import { jQuery } from "../adminlte/plugins/jquery/jquery";


$('#URLConfiguracion').ready(function () {
    var href = $('#URLConfiguracion').attr('href');
    $.get(href, function (cfg, status) {
        console.log('cfg: ' + cfg);
        console.log('cfg.moneda.nombre: ' + cfg.moneda.nombre);
    });

});
const GUARANI = new AutoNumeric.multiple('.number', {
    allowDecimalPadding: false,
    currencySymbol: "₲",
    decimalCharacter: ",",
    digitGroupSeparator: ".",
    maximumValue: "1000000000",
    minimumValue: "0",
    styleRules: {
        positive: "autoNumeric-positive",
        negative: "autoNumeric-negative"
    },
    unformatOnSubmit: true,
    watchExternalChanges: true,
    wheelOn: "hover"
});

// const REAL = new AutoNumeric.multiple('.decimal', {
//     allowDecimalPadding: true,
//     currencySymbol: "₲",
//     decimalCharacter: ",",
//     digitGroupSeparator: ".",
//     maximumValue: "1000000000",
//     minimumValue: "0",
//     styleRules: {
//         positive: "autoNumeric-positive",
//         negative: "autoNumeric-negative"
//     },
//     unformatOnSubmit: true,
//     watchExternalChanges: true,
//     wheelOn: "hover"
// });


