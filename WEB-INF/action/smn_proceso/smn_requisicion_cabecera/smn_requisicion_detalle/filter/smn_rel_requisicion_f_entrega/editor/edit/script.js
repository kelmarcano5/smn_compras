setComboValue('smn_requisicion_detalle_id','${fld:smn_requisicion_detalle_id}','form1');
document.form1.cfe_consecutivo.value='${fld:cfe_consecutivo@#,###,###}';
document.form1.cfe_cantidad.value='${fld:cfe_cantidad@#,###,###}';
document.form1.cfe_fecha_de_entrega.value='${fld:cfe_fecha_de_entrega@dd-MM-yyyy}';
document.form1.id.value='${fld:smn_rel_requisicion_f_entrega_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

