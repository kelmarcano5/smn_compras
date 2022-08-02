setComboValue('smn_usuario_id','${fld:smn_usuario_id}','form1');
setComboValue('smn_documento_id','${fld:smn_documento_id}','form1');
document.form1.id.value='${fld:smn_rel_usuario_documento_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

