var rol = '<rol_rows>${fld:rol_tipo}</rol_rows>'

if(rol != null && rol != '')
{	
	aprobar('${fld:id}');
}
else
{
	alertBox("No tiene permisos para ejecutar este proceso",'${lbl:b_continue_button}',null,'search();');
}
