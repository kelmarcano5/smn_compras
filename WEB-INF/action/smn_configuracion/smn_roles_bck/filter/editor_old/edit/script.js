setComboValue('smn_usuarios_id','${fld:smn_usuarios_id}','form1');
setComboValue('rol_tipo','${fld:rol_tipo}','form1');
setComboValue('smn_corporaciones_id','${fld:smn_corporaciones_id}','form1');
setComboValue('smn_entidades_id','${fld:smn_entidades_id}','form1');
setComboValue('smn_sucursales_id','${fld:smn_sucursales_id}','form1');
setComboValue('smn_areas_servicios_id','${fld:smn_areas_servicios_id}','form1');
setComboValue('smn_unidades_servicios_id','${fld:smn_unidades_servicios_id}','form1');
setComboValue('smn_estructura_organizacional_id','${fld:smn_estructura_organizacional_id}','form1');
document.form1.id.value='${fld:smn_roles_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

