document.form1.tlc_codigo.value='${fld:tlc_codigo@js}';
document.form1.tlc_nombre.value='${fld:tlc_nombre@js}';
setComboValue('tlc_naturaleza','${fld:tlc_naturaleza}','form1');
document.form1.id.value='${fld:smn_tipo_linea_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


document.form1.tlc_codigo.disabled=true;
 

