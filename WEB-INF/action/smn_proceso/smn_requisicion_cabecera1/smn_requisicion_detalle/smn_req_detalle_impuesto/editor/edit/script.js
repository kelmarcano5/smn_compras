setComboValue('smn_requisicion_detalle_id','${fld:smn_requisicion_detalle_id}','form1');
document.form1.rim_monto_base.value='${fld:rim_monto_base@######0.00}';
setComboValue('smn_cod_impuesto_deduc_rf','${fld:smn_cod_impuesto_deduc_rf}','form1');
setComboValue('smn_porcentaje_impuesto','${fld:smn_porcentaje_impuesto}','form1');
setComboValue('smn_sustraendo','${fld:smn_sustraendo}','form1');
document.form1.rim_monto_impuesto.value='${fld:rim_monto_impuesto@#,###,##0.00}';
setComboValue('smn_moneda_rf','${fld:smn_moneda_rf}','form1');
setComboValue('smn_tasa_rf','${fld:smn_tasa_rf}','form1');
document.form1.rim_monto_imp_moneda_alterna.value='${fld:rim_monto_imp_moneda_alterna@#,###,##0.00}';
document.form1.id.value='${fld:smn_req_detalle_impuesto_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

