select
		smn_compras.smn_ruta.rut_codigo
from
		smn_compras.smn_ruta
where
		upper(smn_compras.smn_ruta.rut_codigo) = upper(${fld:rut_codigo})
