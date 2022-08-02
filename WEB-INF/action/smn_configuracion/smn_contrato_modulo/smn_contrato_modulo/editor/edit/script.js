setComboValue('smn_contrato_base_rf','${fld:smn_contrato_base_rf}','form1');
setComboValue('smn_documentos_id','${fld:smn_documentos_id}','form1');
document.form1.ctm_dia_factura.value='${fld:ctm_dia_factura@#,###,###}';
document.form1.ctm_cantidad.value='${fld:ctm_cantidad@#,###,###}';
document.form1.ctm_precio.value='${fld:ctm_precio@#,###,##0.00}';
document.form1.ctm_monto.value='${fld:ctm_monto@#,###,##0.00}';
document.form1.ctm_porcentaje_incremento.value='${fld:ctm_porcentaje_incremento@#,###,##0.00}';
setComboValue('ctm_direccion_rf','${fld:ctm_direccion_rf}','form1');
document.form1.ctm_fecha_renovacion.value='${fld:ctm_fecha_renovacion@dd-MM-yyyy}';
document.form1.id.value='${fld:smn_contrato_modulo_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

