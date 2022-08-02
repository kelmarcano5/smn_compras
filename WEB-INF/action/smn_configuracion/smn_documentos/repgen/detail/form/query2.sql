select
		smn_compras.smn_documentos.smn_tipo_documento_id,
	smn_compras.smn_documentos.dcc_codigo,
	smn_compras.smn_documentos.dcc_nombre,
	smn_compras.smn_documentos.dcc_transaccion_id,
	smn_compras.smn_documentos.dcc_recurrente,
	smn_compras.smn_documentos.dcc_fecha_inicio,
	smn_compras.smn_documentos.dcc_fecha_final,
	smn_compras.smn_documentos.dcc_modalidad,
	smn_compras.smn_documentos.dcc_escotizacion,
	smn_compras.smn_documentos.dcc_cant_cotizaciones,
	smn_compras.smn_documentos.dcc_esoferta,
	smn_compras.smn_documentos.dcc_cant_ofertas,
	smn_compras.smn_documentos.dcc_fecha_registro
from
	smn_compras.smn_documentos 
where
	smn_compras.smn_documentos.smn_documentos_id = ${fld:id}
