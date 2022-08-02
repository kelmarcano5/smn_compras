setComboValue('smn_ruta_id','${fld:smn_ruta_id}','form1');
setComboValue('rra_aprobacion','${fld:rra_aprobacion}','form1');
setComboValue('smn_lineas_id','${fld:smn_lineas_id}','form1');
setComboValue('smn_roles_id','${fld:smn_roles_id}','form1');
setComboValue('smn_regla_id','${fld:smn_regla_id}','form1');
document.form1.id.value='${fld:smn_ruta_aprobacion_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

