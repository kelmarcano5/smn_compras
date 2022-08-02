select
		smn_compras.smn_regla.rul_codigo
from
		smn_compras.smn_regla
where
		upper(smn_compras.smn_regla.rul_codigo) = upper(${fld:rul_codigo})
