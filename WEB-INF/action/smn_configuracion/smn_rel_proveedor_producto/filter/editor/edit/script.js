setComboValue('smn_proveedor_rf','${fld:smn_proveedor_rf}','form1');
document.form1.rpp_id_producto.value='${fld:rpp_id_producto@#,###,###}';
setComboValue('rpp_tipo_producto','${fld:rpp_tipo_producto}','form1');
document.form1.rpp_producto_alterno.value='${fld:rpp_producto_alterno@js}';
document.form1.id.value='${fld:smn_rel_proveedor_producto_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

