select
		smn_compras.smn_documentos.dcc_codigo
from
		smn_compras.smn_documentos
where
		upper(smn_compras.smn_documentos.dcc_codigo) = upper(${fld:dcc_codigo})
