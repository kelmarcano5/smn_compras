select
	smn_compras.smn_orden_compra_impuesto.smn_impuesto_oc_id,
	smn_compras.smn_orden_compra_impuesto.smn_cod_impuesto_deduc_rf,
	smn_compras.smn_orden_compra_impuesto.oci_monto_base_ml,
	smn_compras.smn_orden_compra_impuesto.oci_porcentaje_impuesto,
	smn_compras.smn_orden_compra_impuesto.oci_sustraendo_ml,
	smn_compras.smn_orden_compra_impuesto.smn_tipo_impuesto_rf,
	smn_compras.smn_orden_compra_impuesto.oci_monto_impuesto_ml,
	smn_compras.smn_orden_compra_impuesto.oci_fecha_registro,
	(select smn_base.smn_monedas.mon_codigo|| ' - ' || smn_base.smn_monedas.mon_nombre from smn_base.smn_monedas where smn_base.smn_monedas.smn_monedas_id=smn_compras.smn_orden_compra_impuesto.smn_moneda) as smn_moneda_combo,
	(select smn_base.smn_tasas_de_cambio.tca_moneda_referencia|| ' - ' || smn_base.smn_tasas_de_cambio.tca_tasa_cambio from smn_base.smn_tasas_de_cambio where smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id=smn_compras.smn_orden_compra_impuesto.smn_tasa) as smn_tasa_combo,
	(select smn_base.smn_codigos_impuestos.imp_descripcion from smn_base.smn_codigos_impuestos where smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id=smn_compras.smn_orden_compra_impuesto.smn_cod_impuesto_deduc_rf ) as smn_cod_impuesto_deduc_rf_combo,
	(select smn_base.smn_codigos_impuestos.imp_codigo from smn_base.smn_codigos_impuestos where smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id=smn_compras.smn_orden_compra_impuesto.smn_cod_impuesto_deduc_rf) as smn_tipo_impuesto_rf_combo
from
	smn_compras.smn_orden_compra_impuesto
	where
	smn_compras.smn_orden_compra_impuesto.smn_orden_compra_detalle_id = ${fld:id2}

