select
		smn_compras.smn_tipo_linea.tlc_codigo
from
		smn_compras.smn_tipo_linea
where
		upper(smn_compras.smn_tipo_linea.tlc_codigo) = upper(${fld:tlc_codigo})
