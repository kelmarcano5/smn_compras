select
		smn_compras.smn_tipo_linea.tlc_nombre
from
		smn_compras.smn_tipo_linea
where
		upper(smn_compras.smn_tipo_linea.tlc_nombre) = upper(${fld:tlc_nombre})
