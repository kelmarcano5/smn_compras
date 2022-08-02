document.form1.rul_codigo.value='${fld:rul_codigo@js}';
document.form1.rul_nombre.value='${fld:rul_nombre@js}';
document.form1.rul_cantidad_desde.value='${fld:rul_cantidad_desde@#####}';
document.form1.rul_cantidad_hasta.value='${fld:rul_cantidad_hasta@######}';
document.form1.rul_var_cantidad.value='${fld:rul_var_cantidad@#,######}';
setComboValue('smn_monedas_id','${fld:smn_monedas_id}','form1');
document.form1.rul_monto_desde.value='${fld:rul_monto_desde@#,###,##0.00}';
document.form1.rul_monto_hasta.value='${fld:rul_monto_hasta@#,###,##0.00}';
document.form1.rul_var_monto.value='${fld:rul_var_monto@#,###,##0.00}';
document.form1.id.value='${fld:smn_regla_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");


document.form1.rul_codigo.disabled=true;
 

