document.form1.smn_orden_compra_detalle_id_name.value='${fld:ocd_descripcion_pl0@js}';
document.form1.smn_orden_compra_detalle_id.value='${fld:smn_orden_compra_detalle_id@#,###,###}';
document.form1.ofe_consecutivo.value='${fld:ofe_consecutivo@#,###,###}';
document.form1.ofe_cantidad.value='${fld:ofe_cantidad@#,###,##0.00}';
document.form1.ofe_fecha_entrega.value='${fld:ofe_fecha_entrega@dd-MM-yyyy}';
setComboValue('estatus','${fld:estatus}','form1');
document.form1.id.value='${fld:smn_orden_compra_fecha_entrega_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

