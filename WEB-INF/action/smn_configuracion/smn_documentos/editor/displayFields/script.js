var naturaleza = '${fld:tdc_naturaleza}';

if(naturaleza == 'RE')
{
	document.getElementById('codigo').style.display = '';
	document.getElementById('nombre').style.display = '';
	document.getElementById('documentoGeneral').style.display = '';
	document.getElementById('recurrente').style.display = '';
	document.getElementById('modalidad').style.display = '';
	document.getElementById('requiere_ma').style.display = '';
	document.getElementById('requiere_ofe').style.display = '';
	document.getElementById('cantidad_ofe').style.display = '';
	document.getElementById('generaPedido').style.display = '';
	document.getElementById('fecha_desde').style.display = '';
	document.getElementById('fecha_hasta').style.display = '';
}
/*else
{
	document.getElementById('dcc_codigo').value = '';
	document.getElementById('dcc_nombre').value = '';
	setSelectedIndexValue('[Cualquiera]', dcc_transaccion_id);
	setSelectedIndexValue('No', dcc_recurrente);
	setSelectedIndexValue('Manual', dcc_modalidad);
	setSelectedIndexValue('No', dcc_moneda_alterna);
	setSelectedIndexValue('No', dcc_esoferta);
	document.getElementById('dcc_cant_ofertas').value = '';
	setSelectedIndexValue('No', dcc_genera_pedido);
	document.getElementById('fecha_desde').value = '';
	document.getElementById('fecha_hasta').value = '';
}*/
else
if(naturaleza == 'CO')
{
	document.getElementById('codigo').style.display = '';
	document.getElementById('nombre').style.display = '';
	document.getElementById('documentoGeneral').style.display = '';
	document.getElementById('recurrente').style.display = 'none';
	document.getElementById('modalidad').style.display = 'none';
	document.getElementById('requiere_ma').style.display = 'none';
	document.getElementById('requiere_ofe').style.display = 'none';
	document.getElementById('cantidad_ofe').style.display = 'none';
	document.getElementById('generaPedido').style.display = 'none';
	document.getElementById('fecha_desde').style.display = '';
	document.getElementById('fecha_hasta').style.display = '';
}
/*else
{
	//document.getElementById('dcc_codigo').value = '';
	//document.getElementById('dcc_nombre').value = '';
	setSelectedIndexValue('[Cualquiera]', dcc_transaccion_id);
	setSelectedIndexValue('No', dcc_recurrente);
	setSelectedIndexValue('Manual', dcc_modalidad);
	setSelectedIndexValue('No', dcc_moneda_alterna);
	setSelectedIndexValue('No', dcc_esoferta);
	document.getElementById('dcc_cant_ofertas').value = '';
	setSelectedIndexValue('No', dcc_genera_pedido);
	document.getElementById('fecha_desde').value = '';
	document.getElementById('fecha_hasta').value = '';
}*/
else
if(naturaleza == 'OF' || naturaleza == 'OC')
{
	document.getElementById('codigo').style.display = '';
	document.getElementById('nombre').style.display = '';
	document.getElementById('documentoGeneral').style.display = '';
	document.getElementById('recurrente').style.display = 'none';
	document.getElementById('modalidad').style.display = 'none';
	document.getElementById('requiere_ma').style.display = '';
	document.getElementById('dcc_moneda_alterna').disabled = true; // activa/desactiva el campo de moneda alterna
	document.getElementById('requiere_ofe').style.display = 'none';
	document.getElementById('cantidad_ofe').style.display = 'none';
	document.getElementById('generaPedido').style.display = 'none';
	document.getElementById('fecha_desde').style.display = '';
	document.getElementById('fecha_hasta').style.display = '';
}
/*else
{
	//document.getElementById('dcc_codigo').value = '';
	//document.getElementById('dcc_nombre').value = '';
	setSelectedIndexValue('[Cualquiera]', dcc_transaccion_id);
	setSelectedIndexValue('No', dcc_recurrente);
	setSelectedIndexValue('Manual', dcc_modalidad);
	setSelectedIndexValue('No', dcc_moneda_alterna);
	setSelectedIndexValue('No', dcc_esoferta);
	document.getElementById('dcc_cant_ofertas').value = '';
	setSelectedIndexValue('No', dcc_genera_pedido);
	document.getElementById('fecha_desde').value = '';
	document.getElementById('fecha_hasta').value = '';
}*/