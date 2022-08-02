select
	smn_base.smn_transaccion_general.trg_descripcion,
	${field}
from
	smn_compras.smn_documentos
	left outer join smn_base.smn_transaccion_general on smn_base.smn_transaccion_general.smn_transaccion_general_id = smn_compras.smn_documentos.dcc_transaccion_id
where
		smn_compras.smn_documentos.smn_documentos_id = ${fld:id}
	
