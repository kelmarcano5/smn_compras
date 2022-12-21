WITH cierre AS ( 
	SELECT * FROM smn_caja.smn_detalle_cierre_caja
	WHERE
		smn_caja.smn_detalle_cierre_caja.cierre_id = ${fld:cierre_id} AND
		smn_caja.smn_detalle_cierre_caja.fecha_registro = ${fld:fecha_registro}
        AND CASE
            WHEN ${fld:smn_formas_pago_id} IS NOT NULL THEN smn_caja.smn_detalle_cierre_caja.smn_formas_pago_id = ${fld:smn_formas_pago_id}
            ELSE smn_caja.smn_detalle_cierre_caja.smn_formas_pago_id = smn_caja.smn_detalle_cierre_caja.smn_formas_pago_id
        END
	GROUP BY
		smn_caja.smn_detalle_cierre_caja.cierre_id, 
		smn_caja.smn_detalle_cierre_caja.fecha_registro, 
		smn_caja.smn_detalle_cierre_caja.id, 
		smn_caja.smn_detalle_cierre_caja.numero, 
		smn_caja.smn_detalle_cierre_caja.monto_ml, 
		smn_caja.smn_detalle_cierre_caja.monto_ma, 
		smn_caja.smn_detalle_cierre_caja.cuenta_bancaria, 
		smn_caja.smn_detalle_cierre_caja.contratante, 
		smn_caja.smn_detalle_cierre_caja.fop_descripcion, 
		smn_caja.smn_detalle_cierre_caja.smn_num_doc_origen_rf, 
		smn_caja.smn_detalle_cierre_caja.smn_paciente, 
		smn_caja.smn_detalle_cierre_caja.doc_forma_pago,
        smn_caja.smn_detalle_cierre_caja.smn_formas_pago_id
)
select sum(cierre.monto_ml) as total_monto_ml, sum(cierre.monto_ma) as total_monto_ma
 FROM cierre
WHERE
	cierre.cierre_id = ${fld:cierre_id} AND
	cierre.fecha_registro = ${fld:fecha_registro}