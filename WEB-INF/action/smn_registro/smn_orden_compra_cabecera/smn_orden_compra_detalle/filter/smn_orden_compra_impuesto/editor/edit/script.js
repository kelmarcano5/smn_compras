
document.form1.smn_orden_compra_detalle_id.value='${fld:smn_orden_compra_detalle_id@#,###,###}';
setComboValue('smn_cod_impuesto_deduc_rf','${fld:smn_cod_impuesto_deduc_rf}','form1');

document.form1.oci_monto_base_ml.value=formatear_monto('${fld:oci_monto_base_ml@#,###,###.00}');

document.form1.oci_porcentaje_impuesto.value='${fld:oci_porcentaje_impuesto}';
document.form1.oci_sustraendo_ml.value='${fld:oci_sustraendo_ml}';
setComboValue('smn_tipo_impuesto_rf','${fld:smn_tipo_impuesto_rf}','form1');
document.form1.oci_monto_impuesto_ml.value=formatear_monto('${fld:oci_monto_impuesto_ml@#,###,###.00}');
setComboValue('smn_moneda','${fld:smn_moneda}','form1');
setComboValue('smn_tasa','${fld:smn_tasa}','form1');
document.form1.oci_monto_impuesto_ma.value='${fld:oci_monto_impuesto_ma}';
document.form1.id.value='${fld:smn_impuesto_oc_id}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

