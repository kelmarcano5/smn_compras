<query_rows>
var base = '${fld:base}';
var calculo = '${fld:calculo@##,###,##0.00}';
var cod_descuento_id = "${fld:codigo_descuento_id}";
var cod_descuento_item = "${fld:item}";

listboxClear("smn_codigo_descuento_rf");
var option = document.createElement("option");
option.text = cod_descuento_item; 
option.value = cod_descuento_id;
document.form1.smn_codigo_descuento_rf.add(option, 0);
document.getElementById('smn_codigo_descuento_rf').disabled = true;

document.getElementById('ocd_porcentaje').value=calculo;
document.getElementById('ocd_porcentaje').disabled=true;

var montoml = document.getElementById('ocd_monto_base').value;
document.getElementById('ocd_monto_base').disabled=true;

total = parseFloat(montoml)*parseFloat(calculo)/100*(parseFloat(base)/100);
document.getElementById('ocd_monto_descuento').value=total.toFixed(2);
document.getElementById('ocd_monto_descuento').disabled=true;
</query_rows>