setComboValue('smn_usuarios_id','${fld:smn_usuarios_id}','form1');
setComboValue('smn_lineas_id','${fld:smn_lineas_id}','form1');
document.form1.id.value='${fld:smn_rel_usuario_linea_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

