setComboValue('smn_requisicion_cabecera_id','${fld:smn_requisicion_cabecera_id}','form1');
document.form1.smn_linea_id.value='${fld:smn_linea_id@#,###,###}';
setComboValue('smn_naturaleza_id','${fld:smn_naturaleza_id}','form1');
setComboValue('smn_servicio_id','${fld:smn_servicio_id}','form1');
setComboValue('smn_item_id','${fld:smn_item_id}','form1');
setComboValue('smn_afijo_id','${fld:smn_afijo_id}','form1');
setComboValue('rrs_producto_encontrado','${fld:rrs_producto_encontrado}','form1');
setComboValue('smn_contrato_id','${fld:smn_contrato_id}','form1');
document.form1.rrs_motivo_variacion.value='${fld:rrs_motivo_variacion@js}';
document.form1.rrs_porcentaje.value='${fld:rrs_porcentaje@#,###,##0.00}';
document.form1.rss_cantidad.value='${fld:rss_cantidad@#,###,##0.00}';
document.form1.rrs_precio.value='${fld:rrs_precio@#,###,##0.00}';
document.form1.rrs_monto.value='${fld:rrs_monto@#,###,##0.00}';
setComboValue('smn_moneda_id','${fld:smn_moneda_id}','form1');
document.form1.rrs_precio_moneda_alterna.value='${fld:rrs_precio_moneda_alterna@#,###,##0.00}';
document.form1.rrs_monto_moneda_alterna.value='${fld:rrs_monto_moneda_alterna@#,###,##0.00}';
setComboValue('smn_proveedor_id','${fld:smn_proveedor_id}','form1');
document.form1.rrs_especificaciones_del_requerimiento.value='${fld:rrs_especificaciones_del_requerimiento@js}';
document.form1.rrs_fecha_de_requerido.value='${fld:rrs_fecha_de_requerido@dd-MM-yyyy}';
document.form1.rrs_observaciones.value='${fld:rrs_observaciones@js}';
document.form1.id.value='${fld:smn_requisicion_detalle_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

