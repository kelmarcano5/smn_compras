package proceso;

import dinamica.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

public class GenerarOferta extends GenericTransaction
{
	
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		//String mensaje = ""; //mensaje a retornar
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
			
		conn.setAutoCommit(false);
	
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logAprobarOferta_"+fechaActual+".txt";
		else
			file = "./logAprobarOferta_"+fechaActual+".txt";
		
		File newLogFile = new File(file);
		FileWriter fw;
		String str="";
		
		if(!newLogFile.exists())
			fw = new FileWriter(newLogFile);
		else
			fw = new FileWriter(newLogFile,true);
		
		BufferedWriter bw=new BufferedWriter(fw);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		try
		{
			str = "----------"+timestamp+"----------";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
			Db db = getDb(); //objeto de conexion.
			
			String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams);
			Recordset rsRol = db.get(sqlRol); //valida si el rol es COMPRADOR
			
			str = "<Validando rol...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsRol.getRecordCount()<1)
			{
				str = "*El usuario debe ser comprador*";
				bw.write(str);
				bw.flush();
				bw.newLine();
				rc = 1;
				
				return 1;
			}
			
			str = ">Rol de usuario aceptado...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			String sqlValidar   = getSQL(getResource("select-validarCampos.sql"),inputParams);
			Recordset rsValidar = db.get(sqlValidar); //valida que los campos precios y cond. financiera no esten vacios.
			
			str = "<Validando estatus y precios de la oferta...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsValidar.getRecordCount()>0)
			{
				rsValidar.first();
						
				String precio_ml = rsValidar.getString("ofe_precio_ml");
						
				if(precio_ml == null)
				{
					str = "*El precio en moneda local no debe estar vacio*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
			}
			else
			{
				str = "*La oferta no esta generada*";
				bw.write(str);
				bw.flush();
				bw.newLine();
				rc = 1;
				
				return 1;
			}
			
			str = ">Estatus y precios validados";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			String sqlOferta   = getSQL(getResource("select-oferta.sql"),inputParams);
			Recordset rsOferta = db.get(sqlOferta);
			
			str = "<<Procesando Oferta...>>";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsOferta.getRecordCount()>0)
			{
				rsOferta.first();
							
				//INYECCION DE VALORES AL VALIDATOR 
				
				if(rsOferta.getString("smn_item_compras_id") != null)
					inputParams.setValue("smn_item_compras_id", rsOferta.getInt("smn_item_compras_id"));
				else
					inputParams.setValue("smn_item_compras_id", 0);
				
				if(rsOferta.getString("smn_servicios_compras_id") != null)
					inputParams.setValue("smn_servicios_compras_id", rsOferta.getInt("smn_servicios_compras_id"));
				else
					inputParams.setValue("smn_servicios_compras_id", 0);
				
				if(rsOferta.getString("smn_activo_fijo_compras_id") != null)
					inputParams.setValue("smn_activo_fijo_compras_id", rsOferta.getInt("smn_activo_fijo_compras_id"));
				else
					inputParams.setValue("smn_activo_fijo_compras_id", 0);
				
				if(rsOferta.getString("smn_cotizacion_id") != null)
					inputParams.setValue("smn_cotizacion_id", rsOferta.getInt("smn_cotizacion_id"));
				else
					inputParams.setValue("smn_cotizacion_id", 0);
				
				if(rsOferta.getString("smn_proveedor_id") != null)
				{
					inputParams.setValue("smn_proveedor_id", rsOferta.getInt("smn_proveedor_id"));
				}
				else
				{
					str = "*No existe un proveedor relacionado a la oferta*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				String sql = getSQL(getResource("select-cond_pago.sql"),inputParams);
				Recordset rsCond_pago = db.get(sql);
				
				str = "<Buscando condicion de pago...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsCond_pago.getRecordCount() > 0)
				{
					rsCond_pago.first();
					if(rsCond_pago.getString("aux_cond_pago_rf")!=null)
						inputParams.setValue("smn_condicion_financiera_rf", rsCond_pago.getInt("aux_cond_pago_rf"));
					else
					{
						str = "*La condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id")+" Esta vacia*";
						bw.write(str);
						bw.flush();
						bw.newLine();
						rc = 1;
						
						return 1;
					}
				}
				else
				{
					str = "*No se encontro la condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id")+"*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}		
				
				str = ">Condicion de pago encontrada";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				inputParams.setValue("ofe_aplica_anticipo", rsOferta.getString("ofe_aplica_anticipo"));
									
				if(rsOferta.getString("ofe_porcentaje_anticipo") != null)
					inputParams.setValue("ofe_porcentaje_anticipo", rsOferta.getDouble("ofe_porcentaje_anticipo"));
				else
					inputParams.setValue("ofe_porcentaje_anticipo", 0.0);
									
				if(rsOferta.getString("ofe_monto_anticipo_ml") != null)
					inputParams.setValue("ofe_monto_anticipo", rsOferta.getDouble("ofe_monto_anticipo_ml"));
				else
					inputParams.setValue("ofe_monto_anticipo", 0.0);
					
				String sqlRequisicion   = getSQL(getResource("select-requisicion.sql"),inputParams);
				Recordset rsRequisicion = db.get(sqlRequisicion);
				
				str = "<Buscando requisicion...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsRequisicion.getRecordCount()>0)
				{
					rsRequisicion.first();
					inputParams.setValue("smn_requisicion_cabecera_id", rsRequisicion.getInt("smn_requisicion_cabecera_id"));
					inputParams.setValue("occ_descripcion", "Orden de compra proveniente de la requisicion "+rsRequisicion.getInt("req_numero"));
				}
				else
				{
					str = "*No se encontro la requisicion relacionada a la oferta*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				str = ">Requisicion encontrada";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlEntidad   = getSQL(getResource("select-entidad.sql"),inputParams);
				Recordset rsEntidad = db.get(sqlEntidad);
				
				str = "<Buscando entidad...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsEntidad.getRecordCount()>0)
				{
					rsEntidad.first();
					inputParams.setValue("smn_entidad_id", rsEntidad.getInt("smn_entidad_id"));
				}
				else
				{
					str = "*No se encontro la entidad*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				str = ">Entidad encontrada";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlSucursal   = getSQL(getResource("select-sucursal.sql"),inputParams);
				Recordset rsSucursal = db.get(sqlSucursal);
				
				str = "<Buscando sucursal...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsSucursal.getRecordCount()>0)
				{
					rsSucursal.first();
					inputParams.setValue("smn_sucursal_id", rsSucursal.getInt("smn_sucursal_id"));
				}
				else
				{
					str = "*No se encontro la sucursal*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				str = ">Sucursal encontrada";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlDocumento   = getSQL(getResource("select-documento.sql"),inputParams);
				Recordset rsDocumento = db.get(sqlDocumento);
				
				str = "<Buscando documento...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsDocumento.getRecordCount()>0) 		
				{
					rsDocumento.first();
					inputParams.setValue("smn_documento_id", rsDocumento.getInt("smn_documentos_id"));
				}
				else
				{
					str = "*No se encontro el documento*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				str = ">Documento encontrado";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlAuxiliar   = getSQL(getResource("select-auxiliar.sql"),inputParams);
				Recordset rsAuxiliar = db.get(sqlAuxiliar);
				
				str = "<Buscando auxiliar...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsAuxiliar.getRecordCount()>0)
				{
					rsAuxiliar.first();
					inputParams.setValue("smn_auxiliar_rf", rsAuxiliar.getInt("smn_auxiliar_rf"));
				}
				else
				{
					str = "*No se encontro el auxiliar*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				str = ">Auxiliar encontrado";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlForma_pago   = getSQL(getResource("select-forma_pago.sql"),inputParams);
				Recordset rsForma_pago = db.get(sqlForma_pago);
				
				str = "<Buscando forma de pago...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsForma_pago.getRecordCount()>0)
				{
					rsForma_pago.first();
					inputParams.setValue("smn_forma_pago_rf", rsForma_pago.getInt("smn_forma_pago_rf"));
				}
				else
				{
					str = "*No se encontro la forma de pago*";
					bw.write(str);
					bw.flush();
					bw.newLine();
					rc = 1;
					
					return 1;
				}
				
				str = ">Forma de pago encontrada";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				//----------------------------------------------------------------------
				
				String sqlCabecera_oc    = getSQL(getResource("insert-orden_compra_cabecera.sql"),inputParams);
				Recordset rsCabecera_oc  = db.get(sqlCabecera_oc);
				
				str = ">Registrando orden de compra...<";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsCabecera_oc.getRecordCount()>0)
				{
					str = ">Orden de compra registrada exitosamente";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					rsCabecera_oc.first();
					
					inputParams.setValue("smn_orden_compra_cabecera_id", rsCabecera_oc.getInt("smn_orden_compra_cabecera_id"));
					
					String sqlLinea   = getSQL(getResource("select-linea.sql"),inputParams);
					Recordset rsLinea  = db.get(sqlLinea);
					
					str = "<Buscando linea...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsLinea.getRecordCount()>0)
					{
						rsLinea.first();
						inputParams.setValue("smn_linea_id", rsLinea.getInt("smn_lineas_id"));
					}
					else
					{
						str = "*No se encontro la linea*";
						bw.write(str);
						bw.flush();
						bw.newLine();
						rc = 1;
						
						return 1;
					}
					
					str = ">Linea encontrada";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					String sqlCantidadPedida   = getSQL(getResource("select-cantidad_pedida.sql"),inputParams);
					Recordset rsCantidadPedida  = db.get(sqlCantidadPedida);
					
					str = "<Buscando cantidad pedida...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsCantidadPedida.getRecordCount()>0)
					{
						rsCantidadPedida.first();
						inputParams.setValue("ocd_cantidad_pedida", rsCantidadPedida.getDouble("rss_cantidad"));
					}
					else
					{
						str = "*No se encontro la cantidad pedida*";
						bw.write(str);
						bw.flush();
						bw.newLine();
						rc = 1;
						
						return 1;
					}
					
					str = ">Cantidad pedida encontrada";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsOferta.getString("ofe_cantidad") != null)
						inputParams.setValue("ofe_cantidad", rsOferta.getDouble("ofe_cantidad"));
					else
						inputParams.setValue("ofe_cantidad", 0.0);
					
					if(inputParams.getString("smn_item_compras_id") != null && !inputParams.getString("smn_item_compras_id").equals("0"))
					{
						double cantidad_solicitada = 0.0;
						
						str = "-Validando la cantidad de la oferta...";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						sql = getSQL(getResource("select-cantidad.sql"),inputParams);
						Recordset rsCantidad = db.get(sql);
						
						if(rsCantidad.getRecordCount() > 0)
						{
							rsCantidad.first();
							
							if(rsCantidad.getString("cantidad_solicitada") != null)
								cantidad_solicitada = rsCantidad.getDouble("cantidad_solicitada");
						}
						
						if(inputParams.getDouble("ofe_cantidad") < cantidad_solicitada)
						{
							str = "*La cantidad de la oferta es menor a la sumatoria de los despachos en ER*";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							return 1;
						}
					}
					
					String sqlUnidad_medida   = getSQL(getResource("select-unidad_medida.sql"),inputParams);
					Recordset rsUnidad_medida  = db.get(sqlUnidad_medida);
					
					str = "<Buscando unidad de medida...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsUnidad_medida.getRecordCount()>0)
					{
						str = ">Unidad de medida encontrada";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						rsUnidad_medida.first();
						inputParams.setValue("smn_unidad_medida_rf", rsUnidad_medida.getInt("smn_unidad_medida_compra_rf"));
					}
					else
					{
						inputParams.setValue("smn_unidad_medida_rf", 0);	
					}
					
					if(rsOferta.getString("ofe_precio_ml") != null)
					{
						inputParams.setValue("ofe_precio_ml", rsOferta.getDouble("ofe_precio_ml"));
					}
					else
					{
						str = "*No se encontro el precio de la oferta*";
						bw.write(str);
						bw.flush();
						bw.newLine();
						rc = 1;
						
						return 1;
					}
					
					if(rsOferta.getString("ofe_monto_ml") != null)
					{
						inputParams.setValue("ofe_monto_ml", rsOferta.getDouble("ofe_monto_ml"));
					}
					else
					{
						str = "*No se encontro el monto de la oferta*";
						bw.write(str);
						bw.flush();
						bw.newLine();
						rc = 1;
						
						return 1;
					}
					
					if(rsOferta.getString("ofe_moneda_id") != null)
						inputParams.setValue("ofe_moneda_id", rsOferta.getInt("ofe_moneda_id"));
					else
						inputParams.setValue("ofe_moneda_id", 0);
					
					if(rsOferta.getString("ofe_tasa") != null)
						inputParams.setValue("ofe_tasa", rsOferta.getInt("ofe_tasa"));
					else
						inputParams.setValue("ofe_tasa", 0);
					
					if(rsOferta.getString("ofe_precio_ma") != null)
						inputParams.setValue("ofe_precio_ma", rsOferta.getDouble("ofe_precio_ma"));
					else
						inputParams.setValue("ofe_precio_ma", 0.0);
					
					if(rsOferta.getString("ofe_monto_ma") != null)
						inputParams.setValue("ofe_monto_ma", rsOferta.getDouble("ofe_monto_ma"));
					else
						inputParams.setValue("ofe_monto_ma", 0.0);
					
					str = ">Registrando detalle de la orden de compra<";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					String sql_ocd    = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
					Recordset rs_ocd  = db.get(sql_ocd); //registrar orden de compra detalle
					
					if(rs_ocd.getRecordCount()>0)
					{
						str = ">Detalle registrado exitosamente";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						rs_ocd.first();
						
						inputParams.setValue("smn_orden_compra_detalle_id", rs_ocd.getInt("smn_orden_compra_detalle_id"));
						
						String sqlOferta_fecha    = getSQL(getResource("select-oferta_fecha_entrega.sql"),inputParams);
						Recordset rsOferta_fecha  = db.get(sqlOferta_fecha); //consulta la fecha de entrega de la oferta
						
						str = "<Consultando fecha de entrega de la oferta>";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsOferta_fecha.getRecordCount()>0)
						{
							rsOferta_fecha.first();
							//INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE LA TABLA smn_compras.smn_oferta_f_entrega
							
							if(rsOferta_fecha.getString("ofe_consecutivo") != null)
							{
								inputParams.setValue("ofe_consecutivo", rsOferta_fecha.getInt("ofe_consecutivo"));
							}
							else
							{
								str = "*El consecutivo de la fecha de entrega esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								
								return 1;
							}
							
							if(rsOferta_fecha.getString("ofe_cantidad") != null)
							{
								inputParams.setValue("ofe_cantidad", rsOferta_fecha.getDouble("ofe_cantidad"));
							}
							else
							{
								str = "*La cantidad de la fecha de entrega esta vacia*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								
								return 1;
							}
							
							if(rsOferta_fecha.getString("ofe_fecha_entrega") != null)
							{
								inputParams.setValue("ofe_fecha_entrega", rsOferta_fecha.getDate("ofe_fecha_entrega"));
							}
							else
							{
								str = "*La fecha de entrega esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								
								return 1;
							}
							
							if(rsOferta_fecha.getString("ofe_status") != null)
							{
								inputParams.setValue("ofe_estatus", rsOferta_fecha.getString("ofe_status"));
							}
							else
							{
								str = "*El estatus de la fecha de entrega esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								
								return 1;
							}
							
							//---------------------------------------------------------------------------------------
							
							String sqlFecha_ocd    = getSQL(getResource("insert-fecha_entrega_ocd.sql"),inputParams);
							Recordset rsFecha_ocd  = db.get(sqlFecha_ocd); //registra la fecha de entrega de la orden de compra detalle
							 
							str = ">Registrando fecha de entrega de la orden de compra<";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsFecha_ocd.getRecordCount()>0)
							{
								str = ">Fecha de entrega registrada exitosamente";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								rc = 0;
							}
							else
							{
								str = "*No se registro la fecha de entrega*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								
								return 1;
							}
						}
							
						sql_ocd  = getSQL(getResource("select-ocd.sql"),inputParams);
						rs_ocd   = db.get(sql_ocd); //consulta el detalle de la orden de compra.
								
						double monto_base = 0.0;
						double monto_base_ma = 0.0;
						
						str = "<Consultando el detalle de la orden de compra>";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rs_ocd.getRecordCount()>0)
						{
							rs_ocd.first();
									
							if(rs_ocd.getString("ocd_monto_bruto_ml") != null)
							{
								inputParams.setValue("oci_monto_base_ml", rs_ocd.getDouble("ocd_monto_bruto_ml"));
								monto_base = rs_ocd.getDouble("ocd_monto_bruto_ml");
							}
							else
							{
								str = "*El monto base en moneda local esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								return 1;
							}
							
							if(rs_ocd.getString("ocd_monto_bruto_ma") != null)
							{
								inputParams.setValue("oci_monto_base_ma", rs_ocd.getDouble("ocd_monto_bruto_ma"));
								monto_base_ma = rs_ocd.getDouble("ocd_monto_bruto_ma");
							}
							else
							{
								str = "*El monto base en moneda alterna esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								return 1;
							}
							
							if(rs_ocd.getString("smn_moneda_rf") != null)
								inputParams.setValue("smn_moneda", rs_ocd.getInt("smn_moneda_rf"));
							else
								inputParams.setValue("smn_moneda", 0);
							
							if(rs_ocd.getString("smn_tasa_rf") != null)
								inputParams.setValue("smn_tasa", rs_ocd.getInt("smn_tasa_rf"));
							else
								inputParams.setValue("smn_tasa", 0);
						}
						else
						{
							str = "*No se encontro el detalle de la orden de compra*";
							bw.write(str);
							bw.flush();
							bw.newLine();
							rc = 1;	
							return 1;
						}			
								
						String sqlCodigo_impuesto   = getSQL(getResource("select-impuesto_oferta.sql"),inputParams);
						Recordset rsCodigo_impuesto = db.get(sqlCodigo_impuesto); //consulta el codigo impuesto.
						
						str = ">Buscando el codigo de impuesto...";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsCodigo_impuesto.getRecordCount()>0)
						{
							str = ">Codigo de impuesto encontrado";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							rsCodigo_impuesto.first();
							
							if(rsCodigo_impuesto.getString("smn_cod_impuesto_deduc_rf") != null)
							{
								inputParams.setValue("smn_cod_impuesto_deduc_rf", rsCodigo_impuesto.getInt("smn_cod_impuesto_deduc_rf"));
							}
							else
							{
								str = "*El codigo de impuesto esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;	
								return 1;
							}
							
							if(rsCodigo_impuesto.getString("smn_porcentaje_base") != null)
								inputParams.setValue("imp_porcentaje_base", rsCodigo_impuesto.getDouble("smn_porcentaje_base"));
							else
								inputParams.setValue("imp_porcentaje_base", 0.0);
													
							if(rsCodigo_impuesto.getString("ofi_sustraendo_ml") != null)
								inputParams.setValue("imp_ui_sustraendo", rsCodigo_impuesto.getDouble("ofi_sustraendo_ml"));
							else
								inputParams.setValue("imp_ui_sustraendo", 0.0);
							
							if(rsCodigo_impuesto.getString("smn_tipo_impuesto_rf") != null)
								inputParams.setValue("imp_tipo_codigo", rsCodigo_impuesto.getString("smn_tipo_impuesto_rf"));
							else
							{
								str = "*El tipo de codigo esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								return 1;
							}
							
							if(rsCodigo_impuesto.getString("ofi_monto_impuesto_ml") != null)
								inputParams.setValue("oci_monto_impuesto_ml", rsCodigo_impuesto.getString("ofi_monto_impuesto_ml"));
							else
							{
								str = "*El monto del impuesto esta vacio*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								return 1;
							}
							
							if(rsCodigo_impuesto.getString("smn_moneda_rf") != null)
								inputParams.setValue("smn_moneda", rsCodigo_impuesto.getString("smn_moneda_rf"));
							else
								inputParams.setValue("smn_moneda", 0);
							
							if(rsCodigo_impuesto.getString("smn_tasa_rf") != null)
								inputParams.setValue("smn_tasa", rsCodigo_impuesto.getString("smn_tasa_rf"));
							else
								inputParams.setValue("smn_tasa", 0);
							
							if(rsCodigo_impuesto.getString("ofi_monto_impuesto_ma") != null)
								inputParams.setValue("oci_monto_impuesto_ma", rsCodigo_impuesto.getString("ofi_monto_impuesto_ma"));
							else
								inputParams.setValue("oci_monto_impuesto_ma", 0.0);
								
							String sqlImpuesto   = getSQL(getResource("insert-orden_compra_impuesto.sql"),inputParams);
							Recordset rsImpuesto = db.get(sqlImpuesto);
							
							str = ">Registrando impuestos de la orden de compra<";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsImpuesto.getRecordCount()<1)
							{
								str = "*No se registraron los impuestos*";
								bw.write(str);
								bw.flush();
								bw.newLine();
								rc = 1;
								return rc;
							}
							else
							{
								str = "*Impuestos registrados correctamente*";
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							sqlCodigo_impuesto = getSQL(getResource("select-codigo_impuesto.sql"),inputParams);
							rsCodigo_impuesto = db.get(sqlCodigo_impuesto); //consulta el codigo impuesto.
							
							str = ">Buscando el codigo de impuesto...";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsCodigo_impuesto.getRecordCount()>0)
							{
								str = ">Codigo de impuesto encontrado";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								double porcentaje_base;
								double porcentaje_calculo;
								double sustraendo;
								double monto_subtotal_impuesto_ml;
								double monto_total_impuesto_ml;
								double monto_total_impuesto_ma;
								double tasa_cambio;
								
								rsCodigo_impuesto.first();
								
								if(rsCodigo_impuesto.getString("smn_codigos_impuestos_id") != null)
								{
									inputParams.setValue("smn_cod_impuesto_deduc_rf", rsCodigo_impuesto.getInt("smn_codigos_impuestos_id"));
								}
								else
								{
									str = "*El codigo de impuesto esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;	
									return 1;
								}
								
								if(rsCodigo_impuesto.getString("imp_porcentaje_base") != null)
								{
									inputParams.setValue("imp_porcentaje_base", rsCodigo_impuesto.getDouble("imp_porcentaje_base"));
									porcentaje_base = rsCodigo_impuesto.getDouble("imp_porcentaje_base");
								}
								else
								{
									inputParams.setValue("imp_porcentaje_base", 0.0);
									porcentaje_base = 0.0;
								}
								
								if(rsCodigo_impuesto.getString("imp_porcentaje_calculo") != null)
								{
									inputParams.setValue("imp_porcentaje_calculo", rsCodigo_impuesto.getDouble("imp_porcentaje_calculo"));
									porcentaje_calculo = rsCodigo_impuesto.getDouble("imp_porcentaje_calculo");
								}
								else
								{
									inputParams.setValue("imp_porcentaje_calculo", 0.0);
									porcentaje_calculo = 0.0;
								}
								
								if(rsCodigo_impuesto.getString("imp_ui_sustraendo") != null)
								{
									inputParams.setValue("imp_ui_sustraendo", rsCodigo_impuesto.getDouble("imp_ui_sustraendo"));
									sustraendo = rsCodigo_impuesto.getDouble("imp_ui_sustraendo");
								}
								else
								{
									inputParams.setValue("imp_ui_sustraendo", 0.0);
									sustraendo = 0.0;
								}
								
								if(rsCodigo_impuesto.getString("imp_tipo_codigo") != null)
								{
									inputParams.setValue("imp_tipo_codigo", rsCodigo_impuesto.getString("imp_tipo_codigo"));
								}
								else
								{
									str = "*El tipo de codigo esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								monto_subtotal_impuesto_ml = (monto_base*porcentaje_base)/100;
								monto_total_impuesto_ml = (monto_subtotal_impuesto_ml*porcentaje_calculo)/100;
								inputParams.setValue("oci_monto_impuesto_ml", monto_total_impuesto_ml-sustraendo);
								
								if(inputParams.getString("imp_tipo_codigo").equals("RE"))
									inputParams.setValue("oci_monto_impuesto_ml", inputParams.getDouble("oci_monto_impuesto_ml")*-1);
								
								if(inputParams.getString("ofe_moneda_id")!=null)
									inputParams.setValue("smn_moneda", inputParams.getInt("ofe_moneda_id"));
								else
									inputParams.setValue("smn_moneda", 0);
								
								if(inputParams.getString("ofe_tasa")!=null)
									inputParams.setValue("smn_tasa", inputParams.getInt("ofe_tasa"));
								else
									inputParams.setValue("smn_tasa", 0);
								
								sql = getSQL(getResource("select-tasa.sql"),inputParams);
								Recordset rsTasaCambio = db.get(sql);
								
								if(rsTasaCambio.getRecordCount()>0)
								{
									rsTasaCambio.first();
									
									if(rsTasaCambio.getString("tca_tasa_cambio")!=null)
									{
										tasa_cambio = rsTasaCambio.getDouble("tca_tasa_cambio");
										inputParams.setValue("tca_tasa_cambio", tasa_cambio);
										monto_total_impuesto_ma = monto_total_impuesto_ml/tasa_cambio;
									}
									else
									{
										str = "*No se encontro el monto de la tasa de cambio*";
										bw.write(str);
										bw.flush();
										bw.newLine();
										return 1;
									}
								}
								else
								{
									monto_total_impuesto_ma = 0.0;
								}
								
								inputParams.setValue("oci_monto_impuesto_ma", monto_total_impuesto_ma);
								
								String sqlImpuesto   = getSQL(getResource("insert-orden_compra_impuesto.sql"),inputParams);
								Recordset rsImpuesto = db.get(sqlImpuesto);
								
								str = ">Registrando impuestos de la orden de compra<";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsImpuesto.getRecordCount()<1)
								{
									str = "*No se registraron los impuestos*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return rc;
								}
								else
								{
									str = "*Impuestos registrados correctamente*";
									bw.write(str);
									bw.flush();
									bw.newLine();
								}
							}
							else
							{
								str = "*El item no maneja impuestos*";
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}	
						
						String sqlDescuento_oferta   = getSQL(getResource("select-descuento_oferta.sql"),inputParams);
						Recordset rsDescuento_oferta = db.get(sqlDescuento_oferta); //consulta los descuentos de la oferta
						
						str = "<Consultando descuentos de la oferta>";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsDescuento_oferta.getRecordCount()>0)
						{
							while(rsDescuento_oferta.next() != false)
							{
								if(rsDescuento_oferta.getString("smn_codigo_descuento_rf") != null)
								{
									inputParams.setValue("smn_codigo_descuento_rf", rsDescuento_oferta.getInt("smn_codigo_descuento_rf"));
								}
								else
								{
									str = "*El codigo del descuento esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								if(rsDescuento_oferta.getString("ofd_monto_base_ml") != null)
								{
									inputParams.setValue("ocd_monto_base", rsDescuento_oferta.getDouble("ofd_monto_base_ml"));
								}
								else
								{
									str = "*El monto base del descuento esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								if(rsDescuento_oferta.getString("smn_porcentaje_base") != null)
								{
									inputParams.setValue("ocd_porcentaje", rsDescuento_oferta.getDouble("smn_porcentaje_base"));
								}
								else
								{
									str = "*El porcentaje del descuento esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								if(rsDescuento_oferta.getString("ofd_monto_descuento_ml") != null)
								{
									inputParams.setValue("ocd_monto_descuento", rsDescuento_oferta.getDouble("ofd_monto_descuento_ml"));
								}
								else
								{
									str = "*El monto del descuento esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								if(rsDescuento_oferta.getString("smn_moneda_rf") != null)
									inputParams.setValue("rio_moneda_rf", rsDescuento_oferta.getInt("smn_moneda_rf"));
								else
									inputParams.setValue("rio_moneda_rf", 0);
								
								if(rsDescuento_oferta.getString("smn_tasa_rf") != null)
									inputParams.setValue("rio_tasa_rf", rsDescuento_oferta.getInt("smn_tasa_rf"));
								else
									inputParams.setValue("rio_tasa_rf", 0);
								
								if(rsDescuento_oferta.getString("ofd_monto_base_ma") != null)
									inputParams.setValue("ocd_monto_base_ma", rsDescuento_oferta.getDouble("ofd_monto_base_ma"));
								else
									inputParams.setValue("ocd_monto_base_ma", 0.0);
								
								if(rsDescuento_oferta.getString("ofd_monto_descuento_ma") != null)
									inputParams.setValue("ocd_monto_descuento_ma", rsDescuento_oferta.getDouble("ofd_monto_descuento_ma"));
								else
									inputParams.setValue("ocd_monto_descuento_ma", 0.0);
								
								String sqlDescuento_oc   = getSQL(getResource("insert-descuento_oc.sql"),inputParams);
								Recordset rsDescuento_oc = db.get(sqlDescuento_oc); //consulta los descuentos de la oferta
								
								str = ">Registrando descuentos de la orden de compra<";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsDescuento_oc.getRecordCount()<1)
								{
									str = "*No se registraron los descuentos*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
							}//end while
						}	
						else
						{
							sqlDescuento_oferta   = getSQL(getResource("select-codigo_descuento.sql"),inputParams);
							rsDescuento_oferta = db.get(sqlDescuento_oferta); //consulta los descuentos de la oferta
							
							if(rsDescuento_oferta.getRecordCount()>0)
							{
								double porcentaje_calculo;
								double monto_subtotal;
								double monto_total;
								double porcentaje_base;
								double monto_subtotal_ma;
								double monto_total_ma;
								
								rsDescuento_oferta.first();
								
								if(rsDescuento_oferta.getString("smn_descuentos_retenciones_id") != null)
								{
									inputParams.setValue("smn_codigo_descuento_rf", rsDescuento_oferta.getInt("smn_descuentos_retenciones_id"));
								}
								else
								{
									str = "*El codigo del descuento esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								if(rsDescuento_oferta.getString("dyr_porcentaje_descuento") != null)
									porcentaje_calculo = rsDescuento_oferta.getDouble("dyr_porcentaje_descuento");
								else
									porcentaje_calculo = 0.0;
								
								if(rsDescuento_oferta.getString("dyr_porcentaje_base") != null)
								{
									inputParams.setValue("ocd_porcentaje", rsDescuento_oferta.getDouble("dyr_porcentaje_base"));
									porcentaje_base = rsDescuento_oferta.getDouble("dyr_porcentaje_base");
								}
								else
								{
									str = "*El porcentaje del descuento esta vacio*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								monto_subtotal = (monto_base*porcentaje_base)/100;
								monto_total = (monto_subtotal*porcentaje_calculo)/100;
								inputParams.setValue("ocd_monto_descuento", monto_total);
								
								if(inputParams.getString("ofe_moneda_id")!=null)
									inputParams.setValue("rio_moneda_rf", inputParams.getInt("ofe_moneda_id"));
								else
									inputParams.setValue("rio_moneda_rf", 0);
								
								if(inputParams.getString("ofe_tasa")!=null)
									inputParams.setValue("rio_tasa_rf", inputParams.getInt("ofe_tasa"));
								else
									inputParams.setValue("rio_tasa_rf", 0);
								
								if(inputParams.getString("ofe_monto_ma")!=null)
									inputParams.setValue("ocd_monto_base_ma", inputParams.getInt("ofe_monto_ma"));
								else
									inputParams.setValue("ocd_monto_base_ma", 0.0);
								
								monto_subtotal_ma = (monto_base_ma*porcentaje_base)/100;
								monto_total_ma = (monto_subtotal_ma*porcentaje_calculo)/100;
								inputParams.setValue("ocd_monto_descuento_ma", monto_total_ma);
								
								String sqlDescuento_oc   = getSQL(getResource("insert-descuento_oc.sql"),inputParams);
								Recordset rsDescuento_oc = db.get(sqlDescuento_oc); //consulta los descuentos de la oferta.
								
								str = ">Registrando descuentos de la orden de compra<";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsDescuento_oc.getRecordCount()<1)
								{
									str = "*No se registraron los descuentos*";
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
							}
							else
							{
								str = "*El item no maneja descuentos ni retenciones*";
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
								
						String sqlImpuestos   = getSQL(getResource("select-impuestos.sql"),inputParams);
						Recordset rsImpuestos = db.get(sqlImpuestos); //consulta los impuestos
						
						str = "<Consultando impuestos>";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsImpuestos.getRecordCount()>0)
						{
							rsImpuestos.first();
							
							if(rsImpuestos.getString("monto_impuesto_ml") != null)
								inputParams.setValue("oci_monto_impuesto_ml", rsImpuestos.getDouble("monto_impuesto_ml"));
							else
								inputParams.setValue("oci_monto_impuesto_ml", 0.0);
							
							if(rsImpuestos.getString("monto_impuesto_ma") != null)
								inputParams.setValue("oci_monto_impuesto_ma", rsImpuestos.getDouble("monto_impuesto_ma"));
							else
								inputParams.setValue("oci_monto_impuesto_ma", 0.0);
						}
						
						String sqlDescuentos   = getSQL(getResource("select-descuentos.sql"),inputParams);
						Recordset rsDescuentos = db.get(sqlDescuentos); //consulta los descuentos
						
						str = "<Consultando descuentos>";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsDescuentos.getRecordCount()>0)
						{
							rsDescuentos.first();
							
							if(rsDescuentos.getString("monto_descuento_ml") != null)
								inputParams.setValue("ocd_monto_descuento_ml", rsDescuentos.getDouble("monto_descuento_ml"));
							else
								inputParams.setValue("ocd_monto_descuento_ml", 0.0);
							
							if(rsDescuentos.getString("monto_descuento_ma") != null)
								inputParams.setValue("ocd_monto_descuento_ma", rsDescuentos.getDouble("monto_descuento_ma"));
							else
								inputParams.setValue("ocd_monto_descuento_ma", 0.0);
						}		
								
						double monto_neto_ml = inputParams.getDouble("ofe_monto_ml")+inputParams.getDouble("oci_monto_impuesto_ml")-inputParams.getDouble("ocd_monto_descuento_ml");
						double monto_neto_ma = inputParams.getDouble("ofe_monto_ma")+inputParams.getDouble("oci_monto_impuesto_ma")-inputParams.getDouble("ocd_monto_descuento_ma");
						
						inputParams.setValue("ocd_monto_neto_ml", monto_neto_ml);
						inputParams.setValue("ocd_monto_neto_ma", monto_neto_ma);
						
						String sqlActualizar_ocd   = getSQL(getResource("update-actualizar_ocd.sql"),inputParams);
						Recordset rsActualizar_ocd = db.get(sqlActualizar_ocd); //Actualiza los campos calculados del detalle de la orden de compra
						
						str = ">Actualizando montos de los detalles de la orden de compra<";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsActualizar_ocd.getRecordCount()>0)
						{
							str = ">Montos de los detalles de la orden de compra actualizados";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							String sqlActualizar_occ   = getSQL(getResource("update-actualizar_occ.sql"),inputParams);
							Recordset rsActualizar_occ = db.get(sqlActualizar_occ); //Actualiza los campos calculados de la orden de compra
							
							str = ">Actualizando montos de la orden de compra<";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsActualizar_occ.getRecordCount()>0)
							{
								str = ">Montos de la orden de compra actualizados";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								String sqlUpdate = getSQL(getResource("update-oferta.sql"),inputParams);
								Recordset rsUpdate = db.get(sqlUpdate); //actualiza el estatus de la oferta a generada.
								
								str = ">Actualizando estatus de la oferta<";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsUpdate.getRecordCount()>0)
								{
									str = ">estatus de la oferta actualizado exitosamente";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									str = ">Rechazando ofertas<";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									sqlUpdate = getSQL(getResource("update-rechazar_ofertas.sql"),inputParams);
									db.exec(sqlUpdate); //actualiza el estatus de la oferta a rechazada.
									
									str = ">Ofertas rechazadas exitosamente";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									rc = 0;
									
									str = ">>OFERTA GENERADA EXITOSAMENTE<<";
									bw.write(str);
									bw.flush();
									bw.newLine();
								}
								else
								{
									rc = 1;
									str = "Ocurrio un error al generar la oferta*";
									bw.write(str);
									bw.flush();
									bw.newLine();
								}	
							}
							else
							{
								rc = 1;
								str = "Ocurrio un error al actualizar los montos de la orden de compra*";
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							rc = 1;
							str = "Ocurrio un error al actualizar los montos de los detalles de la orden de compra*";
							bw.write(str);
							bw.flush();
							bw.newLine();
						}		
					}
					else
					{
						rc = 1;
						str = "Ocurrio un error al registrar los detalles de la orden de compra*";
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}
				else
				{
					rc = 1;
					str = "Ocurrio un error al registrar la orden de compra*";
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				rc = 1;
				str = "*No se encontro la oferta*";
				bw.write(str);
				bw.flush();
				bw.newLine();
			}	
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		
		finally
		{		
			if(rc == 0)
			{
				conn.commit();
				str = "<<Cambios en la base de datos guardados correctamente>>";	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			else
			{
				conn.rollback();
				str = "*Los cambios en la base de datos no se guardaron*";	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			
			bw.close();
			
			if(conn!=null)
				conn.close();
		}
		
		return rc;
	}
}
