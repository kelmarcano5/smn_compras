select
	smn_base.smn_transaccion_general.trg_descripcion,
	smn_compras.smn_tipo_documento.tdc_codigo|| ' - ' ||smn_compras.smn_tipo_documento.tdc_nombre as smn_tipo_documento_id,
	smn_compras.smn_tipo_documento.tdc_nombre as tdc_nombre_pl0,
	smn_compras.smn_documentos.*
from
	smn_compras.smn_documentos
	left outer join smn_base.smn_transaccion_general on smn_base.smn_transaccion_general.smn_transaccion_general_id = smn_compras.smn_documentos.dcc_transaccion_id
where
	smn_compras.smn_tipo_documento.smn_tipo_documento_id=smn_compras.smn_documentos.smn_tipo_documento_id
	and
	smn_documentos_id = ${fld:id}
