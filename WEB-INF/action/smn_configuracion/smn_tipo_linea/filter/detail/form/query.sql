select
	case
		when smn_compras.smn_tipo_linea.tlc_naturaleza='AF' then '${lbl:b_activos_fijos}'
		when smn_compras.smn_tipo_linea.tlc_naturaleza='SE' then '${lbl:b_servicios}'
		when smn_compras.smn_tipo_linea.tlc_naturaleza='IN' then '${lbl:b_inventarios}'
	end as tlc_naturaleza_combo,
	smn_compras.smn_tipo_linea.*
from 
	smn_compras.smn_tipo_linea
where
	smn_tipo_linea_id = ${fld:id}
