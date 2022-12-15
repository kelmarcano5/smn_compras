setComboValue('smn_oferta_id','${fld:smn_oferta_id}','form1');
document.form1.ofe_consecutivo.value='${fld:ofe_consecutivo@#,###,###}';
document.form1.ofe_cantidad.value='${fld:ofe_cantidad@#,###,##0.00}';
document.form1.ofe_fecha_entrega.value='${fld:ofe_fecha_entrega@dd-MM-yyyy}';
setComboValue('ofe_status','${fld:ofe_status}','form1');
document.form1.id.value='${fld:smn_oferta_f_entrega_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


 

