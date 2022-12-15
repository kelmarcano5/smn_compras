select
select
select
select
select
select
select
select
select
select
select
	case
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RE' then '${lbl:b_registrada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='GE' then '${lbl:b_generada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='PC' then '${lbl:b_parcialmente_recibida}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RC' then '${lbl:b_recibida}'
	end as occ_estatus_combo,
	smn_compras.smn_orden_compra_cabecera.*
from
	smn_compras.smn_orden_compra_cabecera
where
	smn_orden_compra_cabecera_id = ${fld:id}
