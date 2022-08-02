select
		smn_compras.smn_lineas.lin_codigo
from
		smn_compras.smn_lineas
where
		upper(smn_compras.smn_lineas.lin_codigo) = upper(${fld:lin_codigo})
