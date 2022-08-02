select
		smn_compras.smn_regla.rul_nombre
from
		smn_compras.smn_regla
where
		upper(smn_compras.smn_regla.rul_nombre) = upper(${fld:rul_nombre})
