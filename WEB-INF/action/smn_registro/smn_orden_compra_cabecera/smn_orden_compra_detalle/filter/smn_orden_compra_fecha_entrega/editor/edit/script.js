document.form1.smn_orden_compra_detalle_id.value='${fld:smn_orden_compra_detalle_id@#,###,###}';
document.form1.ocf_consecutivo.value='${fld:ocf_consecutivo@#,###,###}';
document.form1.ocf_cantidad.value='${fld:ocf_cantidad@#,###,##0.00}';
document.form1.ocf_fecha_entrega.value='${fld:ocf_fecha_entrega@dd-MM-yyyy}';
setComboValue('ocf_estatus','${fld:ocf_estatus}','form1');
document.form1.id.value='${fld:smn_orden_compra_fecha_entrega_id@#######}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

