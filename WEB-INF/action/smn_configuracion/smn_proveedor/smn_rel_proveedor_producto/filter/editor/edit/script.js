document.form1.id.value='${fld:smn_rel_proveedor_producto_id@#,###,###}';
setComboValue('smn_proveedor_rf','${fld:smn_proveedor_id}','form1');
setComboValue('rpp_tipo_producto','${fld:rpp_tipo_producto}','form1');
setComboValue('smn_servicio_id','${fld:smn_servicios_compras_id}','form1');
setComboValue('rpp_id_producto','${fld:smn_item_rf}','form1');
setComboValue('rpp_producto_alterno','${fld:rpp_producto_alterno}','form1');
document.form1.rpp_codigo_proveedor.value='${fld:rpp_codigo_proveedor}';
document.form1.rpp_descripcion_proveedor.value='${fld:rpp_descripcion_proveedor}';
document.form1.rpp_existencia_proveedor.value=formatear_monto('${fld:rpp_existencia_proveedor@#,###,###}');
document.form1.rpp_precio_ml.value=formatear_monto('${fld:rpp_precio_ml@#,###,###}');
setComboValue('smn_moneda_id','${fld:smn_moneda_id}','form1');
document.form1.rpp_precio_ma.value=formatear_monto('${fld:rpp_precio_ma@#,###,###}');
document.form1.rpp_fecha_valides.value='${fld:rpp_fecha_valides@dd-MM-yyyy}';

document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");

