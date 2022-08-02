var base = '${fld:base}';
var calculo = '${fld:calculo@##,###,##0.00}';

document.getElementById('ocd_porcentaje').value=calculo;
document.getElementById('ocd_porcentaje').disabled=true;

var montoml = document.getElementById('ocd_monto_base').value;
document.getElementById('ocd_monto_base').disabled=true;

total = parseFloat(montoml)*parseFloat(calculo)/100*(parseFloat(base)/100);
document.getElementById('ocd_monto_descuento').value=total.toFixed(2);
document.getElementById('ocd_monto_descuento').disabled=true;