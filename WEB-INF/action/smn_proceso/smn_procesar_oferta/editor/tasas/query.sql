select 
    smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id as id, 
    smn_base.smn_tasas_de_cambio.tca_tasa_cambio AS item,
    smn_base.smn_tasas_de_cambio.tca_fecha_vigencia as fecha_item 
from 
    smn_base.smn_tasas_de_cambio
WHERE
    smn_monedas_id = ${fld:ofe_moneda_id} 
    AND
    tca_fecha_vigencia >= (SELECT ofe_fecha_registro FROM smn_compras.smn_oferta WHERE smn_oferta_id = ${fld:smn_oferta_id})