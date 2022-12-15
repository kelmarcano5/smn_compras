setComboValue('smn_entidad_rf','${fld:smn_entidad_rf}','form1');
setComboValue('smn_sucursal_rf','${fld:smn_sucursal_rf}','form1');
setComboValue('smn_documento_id','${fld:smn_documento_id}','form1');
document.form1.occ_orden_compra_numero.value='${fld:occ_orden_compra_numero@#,###,###}';
document.form1.occ_descripcion.value='${fld:occ_descripcion@js}';
setComboValue('smn_proveedor_id','${fld:smn_proveedor_id}','form1');
setComboValue('smn_auxiliar_rf','${fld:smn_auxiliar_rf}','form1');
document.form1.occ_fecha_elaboracion.value='${fld:occ_fecha_elaboracion@dd-MM-yyyy}';
document.form1.occ_fecha_orde_compra.value='${fld:occ_fecha_orde_compra@dd-MM-yyyy}';
setComboValue('smn_forma_pago_rf','${fld:smn_forma_pago_rf}','form1');
setComboValue('smn_condicion_pago_rf','${fld:smn_condicion_pago_rf}','form1');
document.form1.occ_observacion.value='${fld:occ_observacion@js}';
document.form1.occ_monto_ml.value='${fld:occ_monto_ml@#,###,##0.00}';
document.form1.occ_monto_impuesto_ml.value='${fld:occ_monto_impuesto_ml@#,###,##0.00}';
document.form1.occ_monto_desc_rete_ml.value='${fld:occ_monto_desc_rete_ml@#,###,##0.00}';
setComboValue('smn_moneda_rf','${fld:smn_moneda_rf}','form1');
setComboValue('smn_tasa_rf','${fld:smn_tasa_rf}','form1');
document.form1.occ_monto_ma.value='${fld:occ_monto_ma@#,###,##0.00}';
document.form1.occ_monto_impuesto_ma.value='${fld:occ_monto_impuesto_ma@#,###,##0.00}';
document.form1.id.value='${fld:smn_orden_compra_cabecera_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

