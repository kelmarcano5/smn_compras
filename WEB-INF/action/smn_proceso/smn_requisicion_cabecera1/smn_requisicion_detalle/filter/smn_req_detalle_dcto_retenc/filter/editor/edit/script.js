setComboValue('smn_requisicion_detalle_id','${fld:smn_requisicion_detalle_id}','form1');
setComboValue('smn_codigo_descuento_rf','${fld:smn_codigo_descuento_rf}','form1');
document.form1.drc_monto_base.value='${fld:drc_monto_base@#,###,##0.00}';
setComboValue('drc_porcentaje','${fld:drc_porcentaje}','form1');
document.form1.drc_monto_descuento.value='${fld:drc_monto_descuento@#,###,##0.00}';
document.form1.id.value='${fld:smn_req_detalle_dcto_retenc_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

