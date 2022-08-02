setComboValue('smn_lineas_id','${fld:smn_lineas_id}','form1');
setComboValue('smn_item_id','${fld:smn_item_id}','form1');
document.form1.id.value='${fld:smn_rel_linea_item_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

