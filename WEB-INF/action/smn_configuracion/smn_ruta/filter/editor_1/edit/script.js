document.form1.rut_codigo.value='${fld:rut_codigo@js}';
document.form1.rut_nombre.value='${fld:rut_nombre@js}';
document.form1.rut_cantidad_aprobaciones.value='${fld:rut_cantidad_aprobaciones@#,###,##0.00}';
document.form1.id.value='${fld:smn_ruta_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


document.form1.rut_codigo.disabled=true;
 

