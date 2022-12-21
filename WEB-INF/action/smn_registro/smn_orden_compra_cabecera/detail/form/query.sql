
select
	case
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RE' then '${lbl:b_registrada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='GE' then '${lbl:b_generada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='PC' then '${lbl:b_parcialmente_recibida}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RC' then '${lbl:b_recibida}'
	end as occ_estatus_combo,
	(select smn_base.smn_entidades.ent_descripcion_corta|| ' - ' || smn_base.smn_entidades.ent_nro_id_fiscal from  smn_base.smn_entidades where smn_base.smn_entidades.smn_entidades_id is not null  and smn_base.smn_entidades.smn_entidades_id=smn_compras.smn_orden_compra_cabecera.smn_entidad_rf) as smn_entidad_rf_combo,
	(select smn_base.smn_sucursales.suc_empresa|| ' - ' || smn_base.smn_sucursales.suc_nombre from  smn_base.smn_sucursales where smn_base.smn_sucursales.smn_sucursales_id is not null and smn_base.smn_sucursales.smn_sucursales_id=smn_compras.smn_orden_compra_cabecera.smn_sucursal_rf) as smn_sucursal_rf_combo,
	(select smn_compras.smn_documentos.dcc_nombre|| ' - ' || smn_compras.smn_documentos.dcc_codigo from smn_compras.smn_documentos where smn_compras.smn_documentos.smn_documentos_id is not null and smn_compras.smn_documentos.smn_documentos_id=smn_compras.smn_orden_compra_cabecera.smn_documento_id) as smn_documento_id_combo,
	(select smn_compras.smn_proveedor.prv_estatus|| ' - ' || smn_compras.smn_proveedor.prv_fecha_vigencia from smn_compras.smn_proveedor where smn_compras.smn_proveedor.smn_proveedor_id is not null and smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_orden_compra_cabecera.smn_proveedor_id) as smn_proveedor_id_combo,
	(select smn_base.smn_formas_pago.fop_descripcion|| ' - ' || smn_base.smn_formas_pago.fop_medio_pago from  smn_base.smn_formas_pago where smn_base.smn_formas_pago.smn_formas_pago_id is not null and smn_base.smn_formas_pago.smn_formas_pago_id=smn_compras.smn_orden_compra_cabecera.smn_forma_pago_rf) as smn_forma_pago_rf_combo,
	(select smn_base.smn_monedas.mon_codigo|| ' - ' || smn_base.smn_monedas.mon_nombre from smn_base.smn_monedas where smn_base.smn_monedas.smn_monedas_id=smn_compras.smn_orden_compra_cabecera.smn_moneda_rf) as smn_moneda_rf_combo,
	(select smn_base.smn_tasas_de_cambio.tca_moneda_referencia|| ' - ' || smn_base.smn_tasas_de_cambio.tca_tasa_cambio from smn_base.smn_tasas_de_cambio where smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id=smn_compras.smn_orden_compra_cabecera.smn_tasa_rf) as smn_tasa_rf_combo,
	(select smn_compras.smn_requisicion_cabecera.req_numero|| ' - ' || smn_compras.smn_requisicion_cabecera.req_descripcion from smn_compras.smn_requisicion_cabecera where smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id=smn_compras.smn_orden_compra_cabecera.smn_requisicion_cabecera_id) as smn_requisicion_cabecera_id_combo,
	(select smn_base.smn_auxiliar.aux_codigo|| ' - ' || smn_base.smn_auxiliar.aux_descripcion from smn_base.smn_auxiliar where smn_base.smn_auxiliar.smn_auxiliar_id=smn_compras.smn_orden_compra_cabecera.smn_auxiliar_rf) as smn_auxiliar_rf_combo,
	(select smn_base.smn_condicion_pago.cop_codigo|| ' - ' || smn_base.smn_condicion_pago.cop_descripcion from smn_base.smn_condicion_pago where smn_base.smn_condicion_pago.smn_condicion_pago_id=smn_compras.smn_orden_compra_cabecera.smn_condicion_pago_rf) as smn_condicion_pago_rf_combo,
	smn_compras.smn_orden_compra_cabecera.*
from
	smn_compras.smn_orden_compra_cabecera
where
	smn_orden_compra_cabecera_id = ${fld:id}
