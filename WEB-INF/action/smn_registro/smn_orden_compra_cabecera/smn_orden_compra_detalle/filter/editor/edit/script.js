setComboValue('smn_linea_id','${fld:smn_linea_id}','form1');
setComboValue('smn_servicios_id','${fld:smn_servicios_id}','form1');
setComboValue('smn_item_rf','${fld:smn_item_rf}','form1');
setComboValue('smn_afijo_rf','${fld:smn_afijo_rf}','form1');
setComboValue('smn_contrato_modulo_id','${fld:smn_contrato_modulo_id}','form1');
document.form1.ocd_descripcion.value='${fld:ocd_descripcion@js}';
document.form1.ocd_cantidad_pedida.value='${fld:ocd_cantidad_pedida@######0.00}';
setComboValue('smn_unidad_medida_rf','${fld:smn_unidad_medida_rf}','form1');
document.form1.ocd_monto_bruto_ml.value='${fld:ocd_monto_bruto_ml@#,###,##0.00}';
document.form1.ocd_monto_impuesto_ml.value='${fld:ocd_monto_impuesto_ml@#,###,##0.00}';
document.form1.ocd_monto_desc_reten_ml.value='${fld:ocd_monto_desc_reten_ml@#,###,##0.00}';
setComboValue('smn_moneda_rf','${fld:smn_moneda_rf}','form1');
setComboValue('smn_tasa_rf','${fld:smn_tasa_rf}','form1');
document.form1.ocd_monto_bruto_ma.value='${fld:ocd_monto_bruto_ma@#,###,##0.00}';
document.form1.ocd_monto_impuesto_ma.value='${fld:ocd_monto_impuesto_ma@#,###,##0.00}';
document.form1.ocd_monto_desc_reten_ma.value='${fld:ocd_monto_desc_reten_ma@#,###,##0.00}';
document.form1.id.value='${fld:smn_orden_compra_detalle_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");

if ('${fld:req_cabecera_version}' == 'A')
	document.getElementById("ocd_precio_ml").disabled = false;
else
if ('${fld:req_cabecera_version}' == 'D')
	document.getElementById("ocd_precio_ml").disabled = true;

if ('${fld:ocd_costo_ml}' == '') {
	document.getElementById("ocd_precio_ml").value=0;
}else{
	document.form1.ocd_precio_ml.value='${fld:ocd_costo_ml@#,###,##0.00}';
}

if ('${fld:ocd_costo_ma}' == '') {
	document.getElementById("ocd_precio_ma").value=0;
}else{
	document.form1.ocd_precio_ma.value='${fld:ocd_costo_ma@#,###,##0.00}';
}


document.form1.smn_linea_id.disabled=true; 
document.form1.smn_servicios_id.disabled=true; 
document.form1.smn_item_rf.disabled=true; 
document.form1.smn_afijo_rf.disabled=true; 
document.form1.smn_contrato_modulo_id.disabled=true; 
document.form1.ocd_descripcion.disabled=true; 
document.form1.ocd_cantidad_pedida.disabled=true; 
 

