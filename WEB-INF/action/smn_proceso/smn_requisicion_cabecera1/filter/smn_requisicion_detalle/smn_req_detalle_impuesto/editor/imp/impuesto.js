var base = '${fld:base}';
var calculo = '${fld:calculo@##,###,##0.00}';
var sustraendo = '${fld:sustraendo@##,###,##0.00}';

document.getElementById('smn_porcentaje_impuesto').value=calculo;
document.getElementById('smn_porcentaje_impuesto').disabled=true;
document.getElementById('smn_sustraendo').value=sustraendo;
document.getElementById('smn_sustraendo').disabled=true;

		//	alert("1");
//alert("base: "+base+" impuesto calculo: "+calculo);
var montoml = document.getElementById('rim_monto_base').value;
document.getElementById('rim_monto_base').disabled=true;

total = parseFloat(montoml)*parseFloat(calculo)/100*(parseFloat(base)/100)-parseFloat(sustraendo);
document.getElementById('rim_monto_impuesto').value=total.toFixed(2);
document.getElementById('rim_monto_impuesto').disabled=true;