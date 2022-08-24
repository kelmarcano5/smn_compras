
var smn_clase_auxiliar_rf = '${fld:smn_clase_auxiliar_rf}';
$('#smn_clase_auxiliar_rf').val(smn_clase_auxiliar_rf).trigger('change');

var smn_auxiliar_rf = '${fld:smn_auxiliar_rf}';
$('#smn_auxiliar_rf').val(smn_auxiliar_rf).trigger('change');

var smn_condicion_financiera_rf = '${fld:smn_condicion_financiera_rf}';
$('#smn_condicion_financiera_rf').val(smn_condicion_financiera_rf).trigger('change');

var smn_forma_pago_rf = '${fld:smn_forma_pago_rf}';
$('#smn_forma_pago_rf').val(smn_forma_pago_rf).trigger('change');

var smn_codigos_impuestos_rf = '${fld:smn_codigos_impuestos_rf}';
$('#smn_codigos_impuestos_rf').val(smn_codigos_impuestos_rf).trigger('change');

var smn_clasificacion_abc_rf = '${fld:smn_clasificacion_abc_rf}';
$('#smn_clasificacion_abc_rf').val(smn_clasificacion_abc_rf).trigger('change');

setComboValue('smn_clase_auxiliar_rf','${fld:smn_clase_auxiliar_rf}','form1');
setComboValue('smn_auxiliar_rf','${fld:smn_auxiliar_rf}','form1');
setComboValue('smn_condicion_financiera_rf','${fld:smn_condicion_financiera_rf}','form1');
setComboValue('smn_forma_pago_rf','${fld:smn_forma_pago_rf}','form1');
setComboValue('smn_clasificacion_abc_rf','${fld:smn_clasificacion_abc_rf}','form1');
setComboValue('prv_estatus','${fld:prv_estatus}','form1');
document.form1.prv_fecha_vigencia.value='${fld:prv_fecha_vigencia@dd-MM-yyyy}';
document.form1.prv_fecha_vigencia_anticipo.value='${fld:prv_fecha_vigencia_anticipo@dd-MM-yyyy}';
setComboValue('prv_anticipo','${fld:prv_anticipo}','form1');
setComboValue('prv_nacional_ext','${fld:prv_nacional_ext}','form1');
document.form1.id.value='${fld:smn_proveedor_id@#,###,###}';
 
document.getElementById("formTitle").innerHTML = "${lbl:b_edit_record}";
document.getElementById("grabar").disabled=false;
setFocusOnForm("form1");

setComboValue('prv_empresa_relacionada','${fld:prv_empresa_relacionada}','form1');
setComboValue('smn_entidades_id','${fld:smn_entidades_id}','form1');
setComboValue('smn_documentos_id','${fld:smn_documentos_id}','form1');	
setComboValue('smn_codigos_impuestos_rf','${fld:smn_codigos_impuestos_rf}','form1');
			
			
var em = document.getElementById('prv_empresa_relacionada').value;
//alert("update: "+em);
	if(em == 'S'){
		document.getElementById('ent').style.display='';
		document.getElementById('doc').style.display='';
	 	
	}else{
		document.getElementById('ent').style.display='none';
		document.getElementById('doc').style.display='none';
	}
	
var ant = document.getElementById('prv_anticipo').value;
if (ant == 'SI') {
			document.getElementById('fecha_ant').style.display='';
		}else{
			document.getElementById('fecha_ant').style.display='none';
		}
				

