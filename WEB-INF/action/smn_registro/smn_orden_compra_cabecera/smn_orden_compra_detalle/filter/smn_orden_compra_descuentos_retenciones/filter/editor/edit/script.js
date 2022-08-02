setComboValue('smn_codigo_descuento_rf','${fld:smn_codigo_descuento_rf}','form1');
document.form1.ocd_monto_base.value=formatear_monto('${fld:ocd_monto_base@#,###,##0.00}');
document.form1.ocd_porcentaje.value='${fld:ocd_porcentaje}';
document.form1.ocd_monto_descuento.value='${fld:ocd_monto_descuento}';
setComboValue('smn_moneda_rf','${fld:smn_moneda_rf}','form1');
setComboValue('smn_tasa_rf','${fld:smn_tasa_rf}','form1');
document.form1.ocd_monto_base_ma.value=formatear_monto('${fld:ocd_monto_base_ma@#,###,##0.00}');
document.form1.ocd_monto_descuento_ma.value='${fld:ocd_monto_descuento_ma}';
document.form1.id.value='${fld:smn_descuento_retencion_oc_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");
