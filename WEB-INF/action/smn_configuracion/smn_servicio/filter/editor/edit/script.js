document.form1.sco_codigo.value='${fld:sco_codigo@js}';
document.form1.sco_nombre.value='${fld:sco_nombre@js}';
setComboValue('sco_maneja_contrato','${fld:sco_maneja_contrato}','form1');
setComboValue('sco_proveedor_exclusivo','${fld:sco_proveedor_exclusivo}','form1');
setComboValue('smn_area_servicio_id','${fld:smn_area_servicio_id}','form1');
setComboValue('smn_unidades_servicios_id','${fld:smn_unidades_servicios_id}','form1');
document.form1.id.value='${fld:smn_servicio_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


document.form1.sco_codigo.disabled=true;
 

