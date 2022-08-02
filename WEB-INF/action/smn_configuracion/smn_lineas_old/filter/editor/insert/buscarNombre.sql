select
		smn_compras.smn_lineas.lin_nombre
from
		smn_compras.smn_lineas
where
		upper(smn_compras.smn_lineas.lin_nombre) = upper(${fld:lin_nombre})
