select
case
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RE' then '${lbl:b_registrada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='GE' then '${lbl:b_generada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='PC' then '${lbl:b_parcialmente_recibida}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RC' then '${lbl:b_recibida}'
	end as occ_estatus_combo,
	smn_compras.smn_orden_compra_cabecera.smn_orden_compra_cabecera_id,
	--smn_compras.smn_orden_compra_cabecera.smn_requisicion_cabecera_id,
	--smn_compras.smn_requisicion_cabecera.req_numero as smn_requisicion_cabecera_id,
	(select smn_base.smn_entidades.ent_descripcion_corta|| ' - ' || smn_base.smn_entidades.ent_nro_id_fiscal from  smn_base.smn_entidades where smn_base.smn_entidades.smn_entidades_id is not null  and smn_base.smn_entidades.smn_entidades_id=smn_compras.smn_orden_compra_cabecera.smn_entidad_rf) as smn_entidad_rf_combo,
	(select smn_base.smn_sucursales.suc_empresa|| ' - ' || smn_base.smn_sucursales.suc_nombre from  smn_base.smn_sucursales where smn_base.smn_sucursales.smn_sucursales_id is not null and smn_base.smn_sucursales.smn_sucursales_id=smn_compras.smn_orden_compra_cabecera.smn_sucursal_rf) as smn_sucursal_rf_combo,
	(select smn_compras.smn_documentos.dcc_nombre|| ' - ' || smn_compras.smn_documentos.dcc_codigo from smn_compras.smn_documentos where smn_compras.smn_documentos.smn_documentos_id is not null and smn_compras.smn_documentos.smn_documentos_id=smn_compras.smn_orden_compra_cabecera.smn_documento_id) as smn_documento_id_combo,
	smn_compras.smn_orden_compra_cabecera.occ_orden_compra_numero,
	(Select aux_descripcion From smn_compras.smn_proveedor inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id=smn_compras.smn_proveedor.smn_auxiliar_rf where smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_orden_compra_cabecera.smn_proveedor_id) as smn_proveedor_id_combo,
	smn_compras.smn_orden_compra_cabecera.occ_fecha_orde_compra,
	(select smn_base.smn_formas_pago.fop_descripcion|| ' - ' || smn_base.smn_formas_pago.fop_medio_pago from  smn_base.smn_formas_pago where smn_base.smn_formas_pago.smn_formas_pago_id is not null and smn_base.smn_formas_pago.smn_formas_pago_id=smn_compras.smn_orden_compra_cabecera.smn_forma_pago_rf) as smn_forma_pago_rf_combo,
	smn_compras.smn_orden_compra_cabecera.occ_fecha_registro,
	smn_compras.smn_orden_compra_cabecera.occ_descripcion,
	smn_compras.smn_orden_compra_cabecera.occ_monto_ml
	--smn_compras.smn_oferta.ofe_numero_documento,
	--smn_compras.smn_oferta.ofe_monto_ml
from
	smn_compras.smn_orden_compra_cabecera
	--inner join 
	--smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_orden_compra_cabecera.smn_requisicion_cabecera_id
	--LEFT OUTER JOIN 
	--smn_compras.smn_oferta ON smn_compras.smn_orden_compra_cabecera.smn_oferta_id = smn_compras.smn_oferta.smn_oferta_id
order by smn_compras.smn_orden_compra_cabecera.occ_fecha_registro desc



