setComboValue('smn_tipo_documento_id','${fld:smn_tipo_documento_id}','form1');
displayFields('${fld:smn_tipo_documento_id}');
document.form1.dcc_codigo.value='${fld:dcc_codigo@js}';
document.form1.dcc_nombre.value='${fld:dcc_nombre@js}';
setComboValue('dcc_transaccion_id','${fld:dcc_transaccion_id}','form1');
setComboValue('dcc_recurrente','${fld:dcc_recurrente}','form1');
document.form1.dcc_fecha_inicio.value='${fld:dcc_fecha_inicio@dd-MM-yyyy}';
document.form1.dcc_fecha_final.value='${fld:dcc_fecha_final@dd-MM-yyyy}';
setComboValue('dcc_modalidad','${fld:dcc_modalidad}','form1');
setComboValue('dcc_moneda_alterna','${fld:dcc_moneda_alterna}','form1');
setComboValue('dcc_esoferta','${fld:dcc_esoferta}','form1');
document.form1.dcc_cant_ofertas.value='${fld:dcc_cant_ofertas@#,###,###}';
requiereoferta();
document.form1.id.value='${fld:smn_documentos_id@#,###,###}';

setComboValue('dcc_genera_pedido','${fld:dcc_genera_pedido}','form1');
document.form1.dcc_cant_dias_cierre.value='${fld:dcc_cant_dias_cierre@js}';
document.form1.dcc_hora_cierre.value='${fld:dcc_hora_cierre@js}';

document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");






