var naturaleza = '${fld:tlc_naturaleza}';

if(naturaleza == 'IN')
{
	setComboValue('smn_naturaleza_id','IT','form1');
	$("#smn_item_id").prop("disabled",false);
	$("#buscarItem").css("display","");
	$("#smn_servicio_id").prop("disabled",true);
	$("#smn_afijo_id").prop("disabled",true);
}
else
if (naturaleza == 'SE') 
{
	setComboValue('smn_naturaleza_id','SE','form1');
	$("#smn_item_id").prop("disabled",true);
	$("#buscarItem").css("display","none");
	$("#smn_servicio_id").prop("disabled",false);
	$("#smn_afijo_id").prop("disabled",true);
}
else
if (naturaleza == 'AF') 
{
	setComboValue('smn_naturaleza_id','AF','form1');
	$("#smn_item_id").prop("disabled",true);
	$("#buscarItem").css("display","none");
	$("#smn_servicio_id").prop("disabled",true);
	$("#smn_afijo_id").prop("disabled",false);
}