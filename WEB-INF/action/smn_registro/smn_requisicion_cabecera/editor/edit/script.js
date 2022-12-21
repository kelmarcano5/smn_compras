document.form1.smn_cabecera_version_id.value='${fld:smn_cabecera_version_id@#,###,###}';
document.form1.req_numero.value='${fld:req_numero@#,###,###}';
setComboValue('req_estatus','${fld:req_estatus}','form1');
setComboValue('smn_tipo_documento_id','${fld:smn_tipo_documento_id}','form1');
setComboValue('smn_documento_id','${fld:smn_documento_id}','form1');
document.form1.req_descripcion.value='${fld:req_descripcion@js}';
document.form1.req_fecha_requerido.value='${fld:req_fecha_requerido@dd-MM-yyyy}';
setComboValue('req_estatus_ruta','${fld:req_estatus_ruta}','form1');
setComboValue('smn_entidad_id','${fld:smn_entidad_id}','form1');
setComboValue('smn_sucursal_id','${fld:smn_sucursal_id}','form1');
setComboValue('smn_almacen_solicitante_rf','${fld:smn_almacen_solicitante_rf}','form1');
document.form1.id.value='${fld:smn_requisicion_cabecera_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");

document.form1.smn_almacen_solicitante_rf.disabled=false;



 

