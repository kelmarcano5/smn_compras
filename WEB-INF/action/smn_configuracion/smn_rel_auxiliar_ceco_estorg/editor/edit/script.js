setComboValue('smn_auxiliar_rf','${fld:smn_auxiliar_rf}','form1');
setComboValue('smn_centro_costo_rf','${fld:smn_centro_costo_rf}','form1');
setComboValue('smn_estructura_organizacional_rf','${fld:smn_estructura_organizacional_rf}','form1');
document.form1.id.value='${fld:smn_rel_auxiliar_ceco_estorg_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 


 


 

