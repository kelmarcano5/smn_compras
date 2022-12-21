select
		smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	case
	when smn_compras.smn_requisicion_cabecera.req_estatus='SO' then '${lbl:b_solicited}'
	when smn_compras.smn_requisicion_cabecera.req_estatus='GE' then '${lbl:b_generated}'
	when smn_compras.smn_requisicion_cabecera.req_estatus='AP' then '${lbl:b_aprobated}'
	end as req_estatus,
select
		smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
select
		smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id,
	smn_compras.smn_requisicion_cabecera.req_numero,
	smn_compras.smn_requisicion_cabecera.req_estatus,
	smn_compras.smn_requisicion_cabecera.smn_tipo_documento_id,
	smn_compras.smn_requisicion_cabecera.smn_documento_id,
	smn_compras.smn_requisicion_cabecera.req_descripcion,
	smn_compras.smn_requisicion_cabecera.req_fecha_requerido,
	smn_compras.smn_requisicion_cabecera.req_fecha_registro
	
from
	smn_compras.smn_requisicion_cabecera
