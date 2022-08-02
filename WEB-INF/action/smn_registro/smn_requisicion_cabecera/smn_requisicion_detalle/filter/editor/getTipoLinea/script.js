var naturaleza = '${fld:tlc_naturaleza}';

if(naturaleza == 'IN')
{
	$("#smn_item_id").prop("disabled",false);
	$("#buscarItem").css("display","");
	$("#smn_servicio_id").prop("disabled",true);
	$("#smn_afijo_id").prop("disabled",true);
}
else
if (naturaleza == 'SE') 
{
	$("#smn_item_id").prop("disabled",true);
	$("#buscarItem").css("display","none");
	$("#smn_servicio_id").prop("disabled",false);
	$("#smn_afijo_id").prop("disabled",true);
}
else
if (naturaleza == 'AF') 
{
	$("#smn_item_id").prop("disabled",true);
	$("#buscarItem").css("display","none");
	$("#smn_servicio_id").prop("disabled",true);
	$("#smn_afijo_id").prop("disabled",false);
}
/*listboxClear("smn_servicio_id");

var optionChoose = document.createElement("option");
optionChoose.text = '[${lbl:b_any}]';
optionChoose.value = "0";
document.form1.smn_servicio_id.add(optionChoose, 0);

<smn_servicio_id_rows>
	var option = document.createElement("option");
	option.text = "${fld:item@js}"; 
	option.value = "${fld:id}";
	document.form1.smn_servicio_id.add(option, 0);
</smn_servicio_id_rows>
*/