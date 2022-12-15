setComboValue('smn_oferta_id','${fld:smn_oferta_id}','form1');
document.form1.rio_monto_base_ml.value='${fld:rio_monto_base_ml@#,###,##0.00}';
setComboValue('rio_cod_impuesto_deduc_id','${fld:rio_cod_impuesto_deduc_id}','form1');
document.form1.rio_porcentaje_deduc.value='${fld:rio_porcentaje_deduc@#,###,##0.00}';
document.form1.rio_sustraendo.value='${fld:rio_sustraendo@#,###,##0.00}';
document.form1.rio_monto_impuesto_ml.value='${fld:rio_monto_impuesto_ml@#,###,##0.00}';
setComboValue('rio_moneda_rf','${fld:rio_moneda_rf}','form1');
setComboValue('rio_tasa_rf','${fld:rio_tasa_rf}','form1');
document.form1.rio_monto_impuesto_ma.value='${fld:rio_monto_impuesto_ma@#,###,##0.00}';
setComboValue('rio_cod_descuento','${fld:rio_cod_descuento}','form1');
document.form1.rio_monto_base_ml.value='${fld:rio_monto_base_ml@#,###,##0.00}';
document.form1.rio_monto_base_ma.value='${fld:rio_monto_base_ma@#,###,##0.00}';
document.form1.rio_porcentaje_descuento.value='${fld:rio_porcentaje_descuento@#,###,##0.00}';
document.form1.rio_monto_descuento_ml.value='${fld:rio_monto_descuento_ml@#,###,##0.00}';
document.form1.rio_monto_descuento_ma.value='${fld:rio_monto_descuento_ma@#,###,##0.00}';
document.form1.id.value='${fld:smn_impuest_deducc_oferta_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

