setComboValue('smn_requisicion_detalle_id','${fld:smn_requisicion_detalle_id}','form1');
document.form1.cot_secuencia.value='${fld:cot_secuencia@#######}';
setComboValue('smn_documento_id','${fld:smn_documento_id}','form1');
document.form1.cot_numero_documento.value='${fld:cot_numero_documento@#######}';
/*setComboValue('smn_proveedor_id','${fld:smn_proveedor_id}','form1');*/
document.form1.cot_fecha_envio.value='${fld:cot_fecha_envio@dd-MM-yyyy}';
document.form1.cot_fecha_requerido.value='${fld:cot_fecha_requerido@dd-MM-yyyy}';
setComboValue('smn_item_rf','${fld:smn_item_id}','form1');
setComboValue('cot_estatus','${fld:cot_estatus}','form1');
document.form1.id.value='${fld:smn_cotizacion_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");

document.getElementById("smn_requisicion_detalle_id").disabled=true;
document.getElementById("cot_secuencia").disabled=true;
document.getElementById("smn_documento_id").disabled=true;
document.getElementById("cot_numero_documento").disabled=true;
document.getElementById("cot_fecha_envio").disabled=true;
document.getElementById("cot_fecha_requerido").disabled=true;
 

