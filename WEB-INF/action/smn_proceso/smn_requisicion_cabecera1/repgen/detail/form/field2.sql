select
	smn_base.fields.alias,
	smn_base.fields.align,
	smn_base.fields.colname,
	smn_base.fields.width_px,
	smn_base.fields.market as col,
	smn_base.fields.orden,
	case
	when format is not null then '${fld:' || market || format
	else '${fld:' || market || '}'
	end as market,
	smn_base.fields.format,
	upper (smn_base.fields.type) as type
from
	smn_base.fields
where
	smn_base.fields.field_id in ${fields}
	and
<<<<<<<< HEAD:WEB-INF/action/smn_proceso/smn_requisicion_cabecera1/repgen/detail/form/field2.sql
	smn_base.fields.action_root = '/action/smn_registro/smn_requisicion_cabecera/repgen'
========
	smn_base.fields.action_root = '/action/smn_registro/smn_orden_compra_cabecera/repgen'
>>>>>>>> a57efee07d661d845df2ffc06524651f62dfc69e:WEB-INF/action/smn_registro/smn_orden_compra_cabecera/filter/repgen/detail/form/field2.sql
order by
		smn_base.fields.orden asc
