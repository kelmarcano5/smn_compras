select
	smn_compras.smn_orden_compra_cabecera.smn_orden_compra_cabecera_id,
	smn_compras.smn_orden_compra_cabecera.occ_descripcion as occ_descripcion_pl0,
select
select
select
select
select
select
select
select
	case
	when smn_compras.smn_orden_compra_detalle.ocd_estatus='GE' then '${lbl:b_generada}'
	when smn_compras.smn_orden_compra_detalle.ocd_estatus='RC' then '${lbl:b_recibida}'
	when smn_compras.smn_orden_compra_detalle.ocd_estatus='AP' then '${lbl:b_aprobada}'
	when smn_compras.smn_orden_compra_detalle.ocd_estatus='CA' then '${lbl:b_cancelada}'
	end as ocd_estatus_combo,
	smn_compras.smn_orden_compra_detalle.*
from
	smn_compras.smn_orden_compra_cabecera,
	smn_compras.smn_orden_compra_detalle
where
	smn_compras.smn_orden_compra_cabecera.smn_orden_compra_cabecera_id=smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id
	and
	smn_orden_compra_detalle_id = ${fld:id}
