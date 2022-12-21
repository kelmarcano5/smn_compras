var monto_ml = document.getElementById('ocd_monto_bruto_ml').value;
var monto_ma = document.getElementById('ocd_monto_bruto_ma').value;

<impuestos_rows>
	var porcentaje = "${fld:imp_porcentaje_calculo}";
	var sustraendo = "${fld:imp_ui_sustraendo}";
	var impuesto_ml = (parseFloat(monto_ml)*parseFloat(porcentaje))-parseFloat(sustraendo);
	var impuesto_ma = (parseFloat(monto_ma)*parseFloat(porcentaje))-parseFloat(sustraendo);
	document.getElementById('ocd_monto_impuesto_ml').value = impuesto_ml;
	document.getElementById('ocd_monto_impuesto_ma').value = impuesto_ma;
</impuestos_rows>