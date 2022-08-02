setComboValue('smn_lineas_id','${fld:smn_lineas_id}','form1');
setComboValue('smn_servicio_id','${fld:smn_servicio_id}','form1');
document.form1.id.value='${fld:smn_rel_linea_servicio_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

