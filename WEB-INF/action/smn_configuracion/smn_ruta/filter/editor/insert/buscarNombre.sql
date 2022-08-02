select
		smn_compras.smn_ruta.rut_nombre
from
		smn_compras.smn_ruta
where
		upper(smn_compras.smn_ruta.rut_nombre) = upper(${fld:rut_nombre})
