setComboValue('smn_cotizacion_id','${fld:smn_cotizacion_id}','form1');
setComboValue('smn_documento_id','${fld:smn_documento_id}','form1');
document.form1.ofe_numero_documento.value='${fld:ofe_numero_documento@#######}';
setComboValue('smn_proveedor_id','${fld:smn_proveedor_id}','form1');
setComboValue('smn_item_compras_id','${fld:smn_item_compras_id}','form1');
setComboValue('smn_servicios_compras_id','${fld:smn_servicios_compras_id}','form1');
setComboValue('smn_activo_fijo_compras_id','${fld:smn_activo_fijo_compras_id}','form1');
setComboValue('smn_condicion_financiera_rf','${fld:smn_condicion_financiera_rf}','form1');
setComboValue('ofe_producto_alterno','${fld:ofe_producto_alterno}','form1');
setComboValue('ofe_item_alter_prov','${fld:ofe_item_alter_prov}','form1');
setComboValue('ofe_activo_fijo_alter_prov','${fld:ofe_activo_fijo_alter_prov}','form1');
var prv_anticipo = '${fld:prv_anticipo}';
setComboValue('ofe_aplica_anticipo',prv_anticipo,'form1');
document.form1.ofe_aplica_anticipo.disabled = true;
document.form1.ofe_porcentaje_anticipo.value='${fld:ofe_porcentaje_anticipo}';
document.form1.ofe_monto_anticipo.value='${fld:ofe_monto_anticipo_ml}';
document.form1.ofe_monto_anticipo_ma.value='${fld:ofe_monto_anticipo_ma}';
document.form1.ofe_cantidad.value='${fld:ofe_cantidad@######}';
document.form1.ofe_precio_ml.value='${fld:ofe_precio_ml}';
document.form1.ofe_monto_ml.value='${fld:ofe_monto_ml}';
setComboValue('ofe_moneda_id','${fld:ofe_moneda_id}','form1');
document.form1.ofe_precio_ma.value='${fld:ofe_precio_ma}';
document.form1.ofe_monto_ma.value='${fld:ofe_monto_ma}';
document.form1.ofe_observaciones.value='${fld:ofe_observaciones@js}';
document.form1.ofe_fecha_de_requerido.value='${fld:ofe_fecha_de_requerido@dd-MM-yyyy}';
setComboValue('ofe_estatus','${fld:ofe_estatus}','form1');
document.form1.id.value='${fld:smn_oferta_id@#,###,###}';

mostrar_alterno();
cambiartipomoneda();
tasas();
mostrarAnticipo(prv_anticipo);

setComboValue('ofe_tasa','${fld:ofe_tasa}','form1');

document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

