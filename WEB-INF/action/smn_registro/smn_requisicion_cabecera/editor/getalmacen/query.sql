select 
	smn_compras.smn_documentos.dcc_modalidad as modalidad  
from smn_compras.smn_documentos
where smn_compras.smn_documentos.smn_documentos_id=${fld:id}