document.form1.smn_cotizacion_id.value='${fld:smn_cotizacion_id@#,###,###}';
setComboValue('smn_proveedor_id','${fld:smn_proveedor_id}','form1');
document.form1.id.value='${fld:smn_rel_cotizacion_proveedor_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

