select
	case
	when smn_compras.smn_documentos.dcc_recurrente='SI' then '${lbl:b_yes}'
	when smn_compras.smn_documentos.dcc_recurrente='NO' then '${lbl:b_not}'
	end as dcc_recurrente,
	case
	when smn_compras.smn_documentos.dcc_modalidad='MA' then '${lbl:b_manual}'
	when smn_compras.smn_documentos.dcc_modalidad='AU' then '${lbl:b_automatico}'
	end as dcc_modalidad,
	smn_compras.smn_documentos.*
from
	smn_compras.smn_documentos,
	smn_compras.smn_tipo_documento
where
		smn_compras.smn_documentos.smn_tipo_documento_id=smn_compras.smn_tipo_documento.smn_tipo_documento_id and
		smn_compras.smn_tipo_documento.smn_tipo_documento_id=${fld:id}
