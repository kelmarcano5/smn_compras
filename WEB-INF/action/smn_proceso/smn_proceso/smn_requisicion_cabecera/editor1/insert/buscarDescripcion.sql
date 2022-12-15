select
		smn_compras.smn_requisicion_cabecera.req_descripcion
from
		smn_compras.smn_requisicion_cabecera
where
		upper(smn_compras.smn_requisicion_cabecera.req_descripcion) = upper(${fld:req_descripcion})
