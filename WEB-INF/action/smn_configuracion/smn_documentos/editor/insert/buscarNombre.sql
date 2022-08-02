select
		smn_compras.smn_documentos.dcc_nombre
from
		smn_compras.smn_documentos
where
		upper(smn_compras.smn_documentos.dcc_nombre) = upper(${fld:dcc_nombre})
