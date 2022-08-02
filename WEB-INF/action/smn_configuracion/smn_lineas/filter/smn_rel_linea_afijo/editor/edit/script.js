setComboValue('smn_lineas_id','${fld:smn_lineas_id}','form1');
setComboValue('smn_afijo_id','${fld:smn_afijo_id}','form1');
document.form1.id.value='${fld:smn_rel_linea_afijo_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

