setComboValue('smn_tipo_linea_id','${fld:smn_tipo_linea_id}','form1');
setComboValue('smn_almacen_consumo_rf','${fld:smn_almacen_consumo_rf}','form1');
document.form1.lin_codigo.value='${fld:lin_codigo@js}';
document.form1.lin_nombre.value='${fld:lin_nombre@js}';
document.form1.id.value='${fld:smn_lineas_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


document.form1.lin_codigo.disabled=true;
 

