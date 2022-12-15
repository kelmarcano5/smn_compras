var base = '${fld:base}';
var calculo = '${fld:calculo@##,###,##0.00}';

document.getElementById('drc_porcentaje').value=calculo;
document.getElementById('drc_porcentaje').disabled=true;

var montoml = document.getElementById('drc_monto_base').value;
document.getElementById('drc_monto_base').disabled=true;

total = parseFloat(montoml)*parseFloat(calculo)/100*(parseFloat(base)/100);
document.getElementById('drc_monto_descuento').value=total.toFixed(2);
document.getElementById('drc_monto_descuento').disabled=true;