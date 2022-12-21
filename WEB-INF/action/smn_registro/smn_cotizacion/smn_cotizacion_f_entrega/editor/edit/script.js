setComboValue('smn_cotizacion_id','${fld:smn_cotizacion_id}','form1');
document.form1.cfe_consecutivo.value='${fld:cfe_consecutivo@#,###,###}';
document.form1.cfe_cantidad.value='${fld:cfe_cantidad@#,###,##0.00}';
document.form1.cfe_fecha_entrega.value='${fld:cfe_fecha_entrega@dd-MM-yyyy}';
setComboValue('cfe_status','${fld:cfe_status}','form1');
document.form1.id.value='${fld:smn_cotizacion_f_entrega_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

