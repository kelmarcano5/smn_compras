setComboValue('mtv_tipo','${fld:mtv_tipo}','form1');
document.form1.mtv_codigo.value='${fld:mtv_codigo@js}';
document.form1.mtv_nombre.value='${fld:mtv_nombre@js}';
document.form1.id.value='${fld:smn_motivos_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


document.form1.mtv_codigo.disabled=true;
 

