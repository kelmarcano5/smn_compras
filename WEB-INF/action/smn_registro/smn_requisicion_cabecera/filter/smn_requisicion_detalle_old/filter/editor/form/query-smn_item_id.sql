select 
    smn_base.smn_item.smn_item_id as id, 
    smn_base.smn_item.itm_nombre as item 
from 
    smn_base.smn_item
INNER JOIN 
    smn_inventario.smn_caracteristica_item on smn_inventario.smn_caracteristica_item.smn_item_rf= smn_base.smn_item.smn_item_id
INNER JOIN 
    smn_compras.smn_rel_linea_item on smn_compras.smn_rel_linea_item.smn_item_id = smn_inventario.smn_caracteristica_item.smn_item_rf order by smn_base.smn_item.itm_nombre asc