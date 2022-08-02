package proceso;
import dinamica.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

public class AprobarOrdenCompra extends GenericTransaction 
{
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";	
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		String naturaleza = "";	
		int interruptor_item = 0;
		//int interruptor_servicio = 0;
		int mca = 1;
		
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
			file =  "C:/log/logAprobarOrdenCompra_"+fechaActual+".txt";
		else
			file = "./logAprobarOrdenCompra_"+fechaActual+".txt";
		
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
			
			String sqlOrdenCompra   = getSQL(getResource("select-ordenCompra.sql"),inputParams);
			Recordset rsOrdenCompra = db.get(sqlOrdenCompra); //consulta la orden de compra cabecera con el id proveniente del front.
			
			str = "Consultando datos de la orden de compra...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsOrdenCompra.getRecordCount()>0)
			{
				rsOrdenCompra.first();
				
				if(rsOrdenCompra.getString("smn_entidad_rf") != null)
					inputParams.setValue("smn_entidad_rf", rsOrdenCompra.getInt("smn_entidad_rf"));
				else
					inputParams.setValue("smn_entidad_rf", 0);
					
				String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams);
				Recordset rsRol = db.get(sqlRol); //consulta la orden de compra cabecera con el id proveniente del front.
				
				str = "Validando rol de usuario...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsRol.getRecordCount()>0)
				{				
					//**INYECCION DE VALORES EN EL VALIDATOR PROVENIENTES DE LA TABLA: smn_orden_compra_cabecera
					
					str = "Procesando orden de compra...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
						if(rsOrdenCompra.getString("smn_documento_id") != null)
							inputParams.setValue("smn_documento_id", rsOrdenCompra.getInt("smn_documento_id"));
						else
							inputParams.setValue("smn_documento_id", 0);
						
						if(rsOrdenCompra.getString("occ_orden_compra_numero") != null)
							inputParams.setValue("occ_orden_compra_numero", rsOrdenCompra.getInt("occ_orden_compra_numero"));
						else
							inputParams.setValue("occ_orden_compra_numero", 0);
						
						if(rsOrdenCompra.getString("smn_proveedor_id") != null)
							inputParams.setValue("smn_proveedor_id", rsOrdenCompra.getInt("smn_proveedor_id"));
						else
							inputParams.setValue("smn_proveedor_id", 0);
						
						if(rsOrdenCompra.getString("smn_sucursal_rf") != null)
							inputParams.setValue("smn_sucursal_rf", rsOrdenCompra.getInt("smn_sucursal_rf"));
						else
							inputParams.setValue("smn_sucursal_rf", 0);
						
						if(rsOrdenCompra.getString("occ_monto_ml") != null)
							inputParams.setValue("occ_monto_ml", rsOrdenCompra.getDouble("occ_monto_ml"));
						else
							inputParams.setValue("occ_monto_ml", 0.0);
						
						if(inputParams.getDouble("occ_monto_ml") <= 0.0)
						{
							mensaje = "**error**, La orden de compra no tiene monto";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							rc = 1;
							return 1;
						}
						
						if(rsOrdenCompra.getString("occ_monto_ma") != null)
							inputParams.setValue("occ_monto_ma", rsOrdenCompra.getDouble("occ_monto_ma"));
						else
							inputParams.setValue("occ_monto_ma", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_desc_rete_ml") != null)
							inputParams.setValue("occ_monto_desc_rete_ml", rsOrdenCompra.getDouble("occ_monto_desc_rete_ml"));
						else
							inputParams.setValue("occ_monto_desc_rete_ml", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_impuesto_ml") != null)
							inputParams.setValue("occ_monto_impuesto_ml", rsOrdenCompra.getDouble("occ_monto_impuesto_ml"));
						else
							inputParams.setValue("occ_monto_impuesto_ml", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_impuesto_ma") != null)
							inputParams.setValue("occ_monto_impuesto_ma", rsOrdenCompra.getDouble("occ_monto_impuesto_ma"));
						else
							inputParams.setValue("occ_monto_impuesto_ma", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_neto_ml") != null)
							inputParams.setValue("occ_monto_neto_ml", rsOrdenCompra.getDouble("occ_monto_neto_ml"));
						else
							inputParams.setValue("occ_monto_neto_ml", 0.0);
						
						if(rsOrdenCompra.getString("smn_moneda_rf") != null)
							inputParams.setValue("smn_moneda_rf", rsOrdenCompra.getInt("smn_moneda_rf"));
						else
							inputParams.setValue("smn_moneda_rf", 0);
						
						if(rsOrdenCompra.getString("smn_tasa_rf") != null)
							inputParams.setValue("smn_tasa_rf", rsOrdenCompra.getInt("smn_tasa_rf"));
						else
							inputParams.setValue("smn_tasa_rf", 0);
						
						if(rsOrdenCompra.getString("smn_auxiliar_rf") != null)
							inputParams.setValue("smn_auxiliar_rf", rsOrdenCompra.getInt("smn_auxiliar_rf"));
						else
							inputParams.setValue("smn_auxiliar_rf", 0);
						
						if(rsOrdenCompra.getString("occ_fecha_orde_compra") != null)
							inputParams.setValue("occ_fecha_orde_compra", rsOrdenCompra.getDate("occ_fecha_orde_compra"));
						else
							inputParams.setValue("occ_fecha_orde_compra", 0);
						
						String sqlModulo   = getSQL(getResource("select-modulo.sql"),inputParams);
						Recordset rsModulo = db.get(sqlModulo); //consulta el modulo.
						
						if(rsModulo.getRecordCount()>0)
						{
							rsModulo.first();
							inputParams.setValue("smn_modulos_id", rsModulo.getInt("smn_modulos_id"));
						}
						else
						{
							mensaje = "**error**, la orden de compra no contiene smn_modulo_id";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							rc = 1;
							return 1; //valor null.
						}
						
						String sqlDoc   = getSQL(getResource("select-documento.sql"),inputParams);
						Recordset rsDoc = db.get(sqlDoc); //consulta el documento.
						
						if(rsDoc.getRecordCount()>0)
						{
							rsDoc.first();
							inputParams.setValue("smn_documentos_generales_id", rsDoc.getInt("smn_documento_id"));
						}
						else
						{
							mensaje = "**error**, la orden de compra no contiene smn_documentos_generales_id";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							rc = 1;
							return 1; //valor null.
						}
						
						String sqlSec  = getSQL(getResource("select-secuencia.sql"),inputParams);
						Recordset rsSec = db.get(sqlSec); //consulta la secuencia.
						
						if(rsSec.getRecordCount()>0)
						{
							rsSec.first();
							if(rsSec.getString("secuencia") != null)
								inputParams.setValue("secuencia", rsSec.getInt("secuencia"));
							else
								inputParams.setValue("secuencia", 1);
						}
						else
							inputParams.setValue("secuencia", 1);
						
						inputParams.setValue("cal_tipo_almacen", "RE");
						String sqlAlmacen  = getSQL(getResource("select-almacen.sql"),inputParams);
						Recordset rsAlmacen = db.get(sqlAlmacen); //consulta el almacen.
						
						if(rsAlmacen.getRecordCount()>0)
						{
							rsAlmacen.first();
							inputParams.setValue("smn_almacen_rf", rsAlmacen.getInt("smn_almacen_rf"));
						}
						else
						{
							mensaje = "**error**, la orden de compra no contiene smn_almacen_rf";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							rc = 1;
							return 1; //valor null.
						}
						
						String sqlTipo_doc  = getSQL(getResource("select-tipo_documento.sql"),inputParams);
						Recordset rsTipo_doc = db.get(sqlTipo_doc); //consulta el tipo de documento.
						
						if(rsTipo_doc.getRecordCount()>0)
						{
							rsTipo_doc.first();
							inputParams.setValue("smn_tipo_documento_id", rsTipo_doc.getInt("smn_tipo_documento_id"));
						}
						
					//*********************	
					
					String sqlOrdenCompraDetalle = getSQL(getResource("select-orden_compra_detalle.sql"),inputParams);
					Recordset rsOrdenCompraDetalle = db.get(sqlOrdenCompraDetalle); //consulta los detalles de la orden de compra.
							
					str = "Consultando detalles de orden de compra...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
						
					if(rsOrdenCompraDetalle.getRecordCount()>0)
					{
						str = "Procesando detalles de orden de compra...";	
						bw.write(str);
						bw.flush();
						bw.newLine();
							
						while(rsOrdenCompraDetalle.next() != false)
						{
							//** INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE: smn_orden_compra_detalle.
										
								inputParams.setValue("smn_orden_compra_detalle_id", rsOrdenCompraDetalle.getInt("smn_orden_compra_detalle_id"));
									
								if(rsOrdenCompraDetalle.getString("smn_item_rf") != null && rsOrdenCompraDetalle.getInt("smn_item_rf") != 0)
								{
									inputParams.setValue("smn_item_id", rsOrdenCompraDetalle.getInt("smn_item_rf"));
									
									String sqlitem = getSQL(getResource("select-item.sql"),inputParams);
									Recordset rsitem = db.get(sqlitem); //consulta que el item este activo y disponible.
								
									if(rsitem.getRecordCount()<1)
									{
										mensaje = "**error**, no se encontro el item o esta inactivo";
										str = mensaje;	
										bw.write(str);
										bw.flush();
										bw.newLine();
										rc = 1;
										return 1; //no se encontro el item o esta inactivo. 
									}
									
									naturaleza = "IT";
								}
								else
								{
									inputParams.setValue("smn_item_id", 0);
								}
								
								if(rsOrdenCompraDetalle.getString("smn_servicios_id") != null && rsOrdenCompraDetalle.getInt("smn_servicios_id") != 0)
								{
									inputParams.setValue("smn_servicios_id", rsOrdenCompraDetalle.getInt("smn_servicios_id"));
									naturaleza = "SE";
								}
								else
								{
									inputParams.setValue("smn_servicios_id", 0);
								}
								
								if(rsOrdenCompraDetalle.getString("smn_afijo_rf") != null && rsOrdenCompraDetalle.getInt("smn_afijo_rf") != 0)
								{
									inputParams.setValue("smn_afijo_rf", rsOrdenCompraDetalle.getInt("smn_afijo_rf"));
									naturaleza = "AF";
								}
								else
								{
									inputParams.setValue("smn_afijo_rf", 0);
								}
	
								if(rsOrdenCompraDetalle.getString("ocd_cantidad_recibida") != null)
									inputParams.setValue("ocd_cantidad_recibida", rsOrdenCompraDetalle.getDouble("ocd_cantidad_recibida"));
								else
									inputParams.setValue("ocd_cantidad_recibida", 0.0);
									
								if(rsOrdenCompraDetalle.getString("ocd_cantidad_pedida") != null)
									inputParams.setValue("ocd_cantidad_pedida", rsOrdenCompraDetalle.getDouble("ocd_cantidad_pedida"));
								else
									inputParams.setValue("ocd_cantidad_pedida", 0.0);
								
								inputParams.setValue("ocd_descripcion", rsOrdenCompraDetalle.getString("ocd_descripcion"));
										
								if(rsOrdenCompraDetalle.getString("smn_unidad_medida_rf") != null)
									inputParams.setValue("smn_unidad_medida_rf", rsOrdenCompraDetalle.getInt("smn_unidad_medida_rf"));
								else
									inputParams.setValue("smn_unidad_medida_rf", 0);
										
								if(rsOrdenCompraDetalle.getString("ocd_costo_ml") != null)
									inputParams.setValue("ocd_costo_ml", rsOrdenCompraDetalle.getDouble("ocd_costo_ml"));
								else
									inputParams.setValue("ocd_costo_ml", 0.0);
									
								if(rsOrdenCompraDetalle.getString("smn_tasa_rf") != null)
									inputParams.setValue("smn_tasa_rf", rsOrdenCompraDetalle.getInt("smn_tasa_rf"));
								else
									inputParams.setValue("smn_tasa_rf", 0);
									
								if(rsOrdenCompraDetalle.getString("smn_moneda_rf") != null)
									inputParams.setValue("smn_moneda_rf", rsOrdenCompraDetalle.getInt("smn_moneda_rf"));
								else
									inputParams.setValue("smn_moneda_rf", 0);
									
								if(rsOrdenCompraDetalle.getString("ocd_costo_ma") != null)
									inputParams.setValue("ocd_costo_ma", rsOrdenCompraDetalle.getDouble("ocd_costo_ma"));
								else
									inputParams.setValue("ocd_costo_ma", 0.0);
									
								if(rsOrdenCompraDetalle.getString("ocd_monto_bruto_ml") != null)
									inputParams.setValue("ocd_monto_bruto_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_bruto_ml"));
								else
									inputParams.setValue("ocd_monto_bruto_ml", 0.0);
										
								if(rsOrdenCompraDetalle.getString("ocd_monto_bruto_ma") != null)
									inputParams.setValue("ocd_monto_bruto_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_bruto_ma"));
								else
									inputParams.setValue("ocd_monto_bruto_ma", 0.0);
									
								if(rsOrdenCompraDetalle.getString("ocd_monto_desc_reten_ml") != null)
									inputParams.setValue("ocd_monto_desc_reten_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_desc_reten_ml"));
								else
									inputParams.setValue("ocd_monto_desc_reten_ml", 0.0);
										
								if(rsOrdenCompraDetalle.getString("ocd_monto_desc_reten_ma") != null)
									inputParams.setValue("ocd_monto_desc_reten_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_desc_reten_ma"));
								else
									inputParams.setValue("ocd_monto_desc_reten_ma", 0.0);
										
								if(rsOrdenCompraDetalle.getString("ocd_monto_impuesto_ml") != null)
									inputParams.setValue("ocd_monto_impuesto_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_impuesto_ml"));
								else
									inputParams.setValue("ocd_monto_impuesto_ml", 0.0);
										
								if(rsOrdenCompraDetalle.getString("ocd_monto_impuesto_ma") != null)
									inputParams.setValue("ocd_monto_impuesto_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_impuesto_ma"));
								else
									inputParams.setValue("ocd_monto_impuesto_ma", 0.0);
									
								if(rsOrdenCompraDetalle.getString("ocd_monto_neto_ml") != null)
									inputParams.setValue("ocd_monto_neto_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_neto_ml"));
								else
									inputParams.setValue("ocd_monto_neto_ml", 0.0);
									
								if(rsOrdenCompraDetalle.getString("ocd_monto_neto_ma") != null)
									inputParams.setValue("ocd_monto_neto_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_neto_ma"));
								else
									inputParams.setValue("ocd_monto_neto_ma", 0.0);
								
							//*********************
								
							if(naturaleza.equals("IT"))
							{
								if(interruptor_item == 0)
								{
									mca = registrarMovimientoCabecera(conn,inputParams); //registra movimiento cabecera.
									str = "Registrando movimiento cabecera...";	
									bw.write(str);
									bw.flush();
									bw.newLine();
								}
								
								inputParams.setValue("cal_tipo_almacen","RE");
								String sql = getSQL(getResource("select-almacen.sql"),inputParams);
								rsAlmacen = db.get(sql); //consulta el almacen de tipo AL.
								
								if(rsAlmacen.getRecordCount()>0)
								{
									rsAlmacen.first();
									inputParams.setValue("smn_almacen_rf", rsAlmacen.getInt("smn_almacen_rf"));
								}
								else
								{
									mensaje = "**error**, la orden de compra no contiene smn_almacen_rf";
									str = mensaje;	
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1; //valor null.
								}
								
								sql = getSQL(getResource("select-smn_control_item.sql"),inputParams);
								Recordset rsControlItem = db.get(sql); //consulta la existencia del item.
								double cantidad_existencia;
								
								if(rsControlItem.getRecordCount()>0)
								{
									rsControlItem.first();
									cantidad_existencia = rsControlItem.getDouble("coi_saldo_final_existencia");
								}
								else
								{
									cantidad_existencia = 0.0;
								}
								
								if(inputParams.getDouble("ocd_cantidad_pedida") <= cantidad_existencia)
									inputParams.setValue("mde_estatus_existencia","EX");
								else
								if(inputParams.getDouble("ocd_cantidad_pedida") > cantidad_existencia)
									inputParams.setValue("mde_estatus_existencia","ER");
								
								if(mca == 0)
								{
									str = "*Registro exitoso*";	
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									str = "Actualizando secuencia del documento...";	
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									String sqlUpdateSec   = getSQL(getResource("update-documento_secuencia.sql"),inputParams); 
									db.get(sqlUpdateSec); //actualiza la secuencia el documento.
									
									str = "*Actualizacion exitosa*";	
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									int mde = registrarMovimientoDetalle(conn,inputParams); //registra movimento detalle.
									
									str = "Registrando movimiento detalle...";	
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									if(mde == 0)
									{
										str = "*Registrado exitosamente*";	
										bw.write(str);
										bw.flush();
										bw.newLine();
										
										//** proceso de registrar movimiento detalle impuesto **
										
										String sqlOrdenCompraImpuesto   = getSQL(getResource("consultar-oci.sql"), inputParams);
										Recordset rsOrdenCompraImpuesto = db.get(sqlOrdenCompraImpuesto);
										
										str = "Consultando impuestos de la orden de compra...";	
										bw.write(str);
										bw.flush();
										bw.newLine();
										
										if(rsOrdenCompraImpuesto.getRecordCount()>0)
										{
											str = "Impuestos encontrados = "+rsOrdenCompraImpuesto.getRecordCount();	
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											rsOrdenCompraImpuesto.first();
											
											//** INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE: smn_orden_compra_impuesto.
												
												if(rsOrdenCompraImpuesto.getString("smn_cod_impuesto_deduc_rf") != null)
													inputParams.setValue("smn_cod_impuesto_deduc_rf", rsOrdenCompraImpuesto.getInt("smn_cod_impuesto_deduc_rf"));
												else
													inputParams.setValue("smn_cod_impuesto_deduc_rf", 0);
													
												if(rsOrdenCompraImpuesto.getString("oci_monto_base_ml") != null)
													inputParams.setValue("oci_monto_base_ml", rsOrdenCompraImpuesto.getDouble("oci_monto_base_ml"));
												else
													inputParams.setValue("oci_monto_base_ml", 0.0);
													
												if(rsOrdenCompraImpuesto.getString("oci_porcentaje_impuesto") != null)
													inputParams.setValue("smn_porcentaje_impuesto_rf", rsOrdenCompraImpuesto.getInt("oci_porcentaje_impuesto"));
												else
													inputParams.setValue("smn_porcentaje_impuesto_rf", 0);
													
												if(rsOrdenCompraImpuesto.getString("oci_sustraendo_ml") != null)
													inputParams.setValue("oci_sustraendo_ml", rsOrdenCompraImpuesto.getDouble("oci_sustraendo_ml"));
												else
													inputParams.setValue("oci_sustraendo_ml", 0.0);
													
												if(rsOrdenCompraImpuesto.getString("smn_tipo_impuesto_rf") != null)
													inputParams.setValue("oci_tipo_impuesto_rf", rsOrdenCompraImpuesto.getString("smn_tipo_impuesto_rf"));
												else
													inputParams.setValue("oci_tipo_impuesto_rf", null);
													
												if(rsOrdenCompraImpuesto.getString("oci_monto_impuesto_ml") != null)
													inputParams.setValue("oci_monto_impuesto_ml", rsOrdenCompraImpuesto.getDouble("oci_monto_impuesto_ml"));
												else
													inputParams.setValue("oci_monto_impuesto_ml", 0.0);
												
												if(rsOrdenCompraImpuesto.getString("smn_moneda") != null)
													inputParams.setValue("smn_moneda", rsOrdenCompraImpuesto.getInt("smn_moneda"));
												else
													inputParams.setValue("smn_moneda", 0);
												
												if(rsOrdenCompraImpuesto.getString("smn_tasa") != null)
													inputParams.setValue("smn_tasa", rsOrdenCompraImpuesto.getInt("smn_tasa"));
												else
													inputParams.setValue("smn_tasa", 0);
													
												if(rsOrdenCompraImpuesto.getString("oci_monto_impuesto_ma") != null)
													inputParams.setValue("oci_monto_impuesto_ma", rsOrdenCompraImpuesto.getDouble("oci_monto_impuesto_ma"));
												else
													inputParams.setValue("oci_monto_impuesto_ma", 0.0);
													
											//****************
													
											rc = registrarMovimientoDetalleImpuesto(conn, inputParams); //registra movimiento detalle impuesto
											
											str = "Registrando impuestos...";	
											bw.write(str);
											bw.flush();
											bw.newLine();
										}
										
										//** proceso de registrar movimiento detalle descuentos y deducciones **
										
										String sqlOrdenCompraDesc_retenc   = getSQL(getResource("select-orden_compra_desc_retenc.sql"), inputParams);
										Recordset rsOrdenCompraDesc_retenc= db.get(sqlOrdenCompraDesc_retenc);
										
										str = "Consultando descuentos y retenciones de la orden de compra...";	
										bw.write(str);
										bw.flush();
										bw.newLine();
										
										if(rsOrdenCompraDesc_retenc.getRecordCount()>0)
										{
											str = "Descuentos y retenciones encontrados = "+rsOrdenCompraDesc_retenc.getRecordCount();	
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											rsOrdenCompraDesc_retenc.first();
											
											//** INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE: smn_orden_compra_descuentos_deducciones.
												
												if(rsOrdenCompraDesc_retenc.getString("smn_codigo_descuento_rf") != null)
													inputParams.setValue("smn_codigo_descuento_rf", rsOrdenCompraDesc_retenc.getInt("smn_codigo_descuento_rf"));
												else
													inputParams.setValue("smn_codigo_descuento_rf", 0);
													
												if(rsOrdenCompraDesc_retenc.getString("ocd_monto_base") != null)
													inputParams.setValue("mdd_monto_base_ml", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_base"));
												else
													inputParams.setValue("mdd_monto_base_ml", 0.0);
												
												if(rsOrdenCompraDesc_retenc.getString("ocd_porcentaje") != null)
													inputParams.setValue("smn_porcentaje_rf", rsOrdenCompraDesc_retenc.getDouble("ocd_porcentaje"));
												else
													inputParams.setValue("smn_porcentaje_rf", 0.0);
												
												if(rsOrdenCompraDesc_retenc.getString("ocd_monto_descuento") != null)
													inputParams.setValue("mdd_monto_descuento_ml", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_descuento"));
												else
													inputParams.setValue("mdd_monto_descuento_ml", 0.0);
												
												if(rsOrdenCompraDesc_retenc.getString("smn_moneda_rf") != null)
													inputParams.setValue("smn_moneda_rf", rsOrdenCompraDesc_retenc.getInt("smn_moneda_rf"));
												else
													inputParams.setValue("smn_moneda_rf", 0);
												
												if(rsOrdenCompraDesc_retenc.getString("smn_tasa_rf") != null)
													inputParams.setValue("smn_tasa_rf", rsOrdenCompraDesc_retenc.getInt("smn_tasa_rf"));
												else
													inputParams.setValue("smn_tasa_rf", 0);
												
												if(rsOrdenCompraDesc_retenc.getString("ocd_monto_base_ma") != null)
													inputParams.setValue("mdd_monto_base_ma", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_base_ma"));
												else
													inputParams.setValue("mdd_monto_base_ma", 0.0);
												
												if(rsOrdenCompraDesc_retenc.getString("ocd_monto_descuento_ma") != null)
													inputParams.setValue("mdd_monto_descuento_ma", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_descuento_ma"));
												else
													inputParams.setValue("mdd_monto_descuento_ma", 0.0);
													
											//****************
													
											rc = registrarMovimientoDetalleDesc_retenc(conn, inputParams); //registra movimiento detalle descuentos retenciones.
											
											str = "Registrando descuentos y retenciones de la orden de compra...";	
											bw.write(str);
											bw.flush();
											bw.newLine();
										}
									}
									else
									{
										mensaje = "no se registro el detalle del movimiento";
										str = mensaje;	
										bw.write(str);
										bw.flush();
										bw.newLine();
										rc = mde; //no se registro el detalle del movimiento.
										return 1;	
									}
								}
								else
								{
									mensaje = "*No se registro el movimiento en inventario*";
									rc = mca; //no se registro el movimiento cabecera.
									str = mensaje;	
									bw.write(str);
									bw.flush();
									bw.newLine();
									rc = 1;
									return 1;
								}
								
								interruptor_item = 1;
							}
							else
							if(naturaleza.equals("SE"))
							{
								rc = registrarRecepcionServicio(conn,inputParams,str,bw);
								
								if(rc == 1)
									return rc;
							}
							else
							if(naturaleza.equals("AF"))
							{
								rc = this.registrarRecepcionAfijo(conn, inputParams, str, bw);
								
								if(rc == 1)
									return rc;
							}
						} //END WHILE
						
						rc = registrarPago(conn,inputParams,str,bw);
						
						if(rc==0)
						{
							rc = this.registrarAnticipo(conn, inputParams, str, bw);
							if(rc!=0)
								return rc;
						}
						else
						{
							rc = 1;
							return 1;
						}
						
						if(rc == 0)
						{
							str = "Actualizando estatus de la orden de compra...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							String sqlEstatus_occ   = getSQL(getResource("update-estatus_occ.sql"), inputParams);
						    db.get(sqlEstatus_occ); //actualiza el estatus de la orden de compra.
						}
						else
						{
							mensaje = "No se registro el pago de la orden de compra";
							rc = 1; //no se registro el pago de la orden de compra.
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1;
						}
					}
					else
					{
						mensaje = "No se encontraron detalles de la orden de compra";
						rc = 1; //no se encontraron detalles de la orden de compra.
						str = mensaje;	
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}
				else
				{
					mensaje = "El usuario debe ser comprador";
					rc = 1; //no se encontro un usuario CO.
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				mensaje = "la orden de compra no esta registrada";
				rc = 1; //la orden de compra no esta registrada.
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
				
			inputParams.setValue("mensaje", mensaje);
			
			str = "*FIN DEL PROCESO*";	
			bw.write(str);
			bw.flush();
			bw.newLine();
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
				str = "*Se guardaron los cambios en la base de datos*";	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			else
			{
				conn.rollback();
				str = "*No se guardaron los cambios en la base de datos*";	
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
	
	public int registrarMovimientoCabecera(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoCabecera = getSQL(getResource("insert-movimiento_cabecera.sql"),inputParams);
			Recordset rsMovimientoCabecera = db.get(sqlMovimientoCabecera); //registra movimiento cabecera.
			
			if(rsMovimientoCabecera.getRecordCount()>0)
			{
				rsMovimientoCabecera.first();
				inputParams.setValue("smn_movimiento_cabecera_id", rsMovimientoCabecera.getInt("smn_movimiento_cabecera_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarMovimientoDetalle(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoDetalle = getSQL(getResource("insert-movimiento_detalle.sql"),inputParams);
			Recordset rsMovimientoDetalle = db.get(sqlMovimientoDetalle); //registra movimiento detalle.
			
			if(rsMovimientoDetalle.getRecordCount()>0)
			{
				rsMovimientoDetalle.first();
				inputParams.setValue("smn_movimiento_detalle_id", rsMovimientoDetalle.getInt("smn_movimiento_detalle_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarMovimientoDetalleImpuesto(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoDetalleImpuesto   = getSQL(getResource("insert-movimiento_detalle_impuesto.sql"),inputParams);
			Recordset rsMovimientoDetalleImpuesto = db.get(sqlMovimientoDetalleImpuesto); //registra movimiento detalle.
			
			if(rsMovimientoDetalleImpuesto.getRecordCount()>0)
			{
				rsMovimientoDetalleImpuesto.first();
				inputParams.setValue("smn_movimiento_detalle_impuesto_id", rsMovimientoDetalleImpuesto.getInt("smn_mov_det_impuesto_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}

		return rc;
	}
	
	public int registrarMovimientoDetalleDesc_retenc(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoDetalleDesc_retenc   = getSQL(getResource("insert-movimiento_detalle_desc_retenc.sql"),inputParams);
			Recordset rsMovimientoDetalleDesc_retenc = db.get(sqlMovimientoDetalleDesc_retenc); //registra movimiento detalle.
			
			if(rsMovimientoDetalleDesc_retenc.getRecordCount()>0)
			{
				rsMovimientoDetalleDesc_retenc.first();
				inputParams.setValue("smn_movimiento_detalle_desc_ret_id", rsMovimientoDetalleDesc_retenc.getInt("smn_descuento_retencion_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarPago(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje="";	
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlOrdenCompra   = getSQL(getResource("select-ordenCompra.sql"),inputParams);
			Recordset rsOrdenCompra = db.get(sqlOrdenCompra); //consulta la orden de compra cabecera.
			
			if(rsOrdenCompra.getRecordCount()>0)
			{
				//**INYECCION DE VALORES EN EL VALIDATOR PROVENIENTES DE LA TABLA: smn_orden_compra_cabecera
				
				rsOrdenCompra.first();
				
				if(rsOrdenCompra.getString("smn_documento_id") != null)
					inputParams.setValue("smn_documento_id", rsOrdenCompra.getInt("smn_documento_id"));
				else
					inputParams.setValue("smn_documento_id", 0);
				
				if(rsOrdenCompra.getString("occ_orden_compra_numero") != null)
					inputParams.setValue("occ_orden_compra_numero", rsOrdenCompra.getInt("occ_orden_compra_numero"));
				else
					inputParams.setValue("occ_orden_compra_numero", 0);
				
				if(rsOrdenCompra.getString("smn_proveedor_id") != null)
					inputParams.setValue("smn_proveedor_id", rsOrdenCompra.getInt("smn_proveedor_id"));
				else
					inputParams.setValue("smn_proveedor_id", 0);
				
				if(rsOrdenCompra.getString("smn_sucursal_rf") != null)
					inputParams.setValue("smn_sucursal_rf", rsOrdenCompra.getInt("smn_sucursal_rf"));
				else
					inputParams.setValue("smn_sucursal_rf", 0);
				
				if(rsOrdenCompra.getString("smn_entidad_rf") != null)
					inputParams.setValue("smn_entidad_rf", rsOrdenCompra.getInt("smn_entidad_rf"));
				else
				{
					mensaje = "**error**, la orden de compra no contiene entidad";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
					return 1; //valor null.
				}
					
				if(rsOrdenCompra.getString("occ_monto_ml") != null)
					inputParams.setValue("occ_monto_ml", rsOrdenCompra.getDouble("occ_monto_ml"));
				else
					inputParams.setValue("occ_monto_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_ma") != null)
					inputParams.setValue("occ_monto_ma", rsOrdenCompra.getDouble("occ_monto_ma"));
				else
					inputParams.setValue("occ_monto_ma", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_desc_rete_ml") != null)
					inputParams.setValue("occ_monto_desc_rete_ml", rsOrdenCompra.getDouble("occ_monto_desc_rete_ml"));
				else
					inputParams.setValue("occ_monto_desc_rete_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_impuesto_ml") != null)
					inputParams.setValue("occ_monto_impuesto_ml", rsOrdenCompra.getDouble("occ_monto_impuesto_ml"));
				else
					inputParams.setValue("occ_monto_impuesto_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_impuesto_ma") != null)
					inputParams.setValue("occ_monto_impuesto_ma", rsOrdenCompra.getDouble("occ_monto_impuesto_ma"));
				else
					inputParams.setValue("occ_monto_impuesto_ma", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_neto_ml") != null)
					inputParams.setValue("occ_monto_neto_ml", rsOrdenCompra.getDouble("occ_monto_neto_ml"));
				else
					inputParams.setValue("occ_monto_neto_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_neto_ma") != null)
					inputParams.setValue("occ_monto_neto_ma", rsOrdenCompra.getDouble("occ_monto_neto_ma"));
				else
					inputParams.setValue("occ_monto_neto_ma", 0.0);
				
				if(rsOrdenCompra.getString("smn_moneda_rf") != null)
					inputParams.setValue("smn_moneda_rf", rsOrdenCompra.getInt("smn_moneda_rf"));
				else
					inputParams.setValue("smn_moneda_rf", 0);
				
				if(rsOrdenCompra.getString("smn_tasa_rf") != null)
					inputParams.setValue("smn_tasa_rf", rsOrdenCompra.getInt("smn_tasa_rf"));
				else
					inputParams.setValue("smn_tasa_rf", 0);
				
				if(rsOrdenCompra.getString("occ_fecha_orde_compra") != null)
					inputParams.setValue("occ_fecha_orde_compra", rsOrdenCompra.getDate("occ_fecha_orde_compra"));
				else
					inputParams.setValue("occ_fecha_orde_compra", 0);
				
				String sqlModulo   = getSQL(getResource("select-modulo.sql"),inputParams);
				Recordset rsModulo = db.get(sqlModulo); //consulta el modulo.
				
				if(rsModulo.getRecordCount()>0)
				{
					rsModulo.first();
					inputParams.setValue("smn_modulos_id", rsModulo.getInt("smn_modulos_id"));
				}
				else
				{
					mensaje = "**error**, la orden de compra no contiene smn_modulo_id";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
					return 1; //valor null.
				}
				
				String sqlDoc   = getSQL(getResource("select-documento.sql"),inputParams);
				Recordset rsDoc = db.get(sqlDoc); //consulta el documento.
				
				if(rsDoc.getRecordCount()>0)
				{
					rsDoc.first();
					inputParams.setValue("smn_documentos_generales_id", rsDoc.getInt("smn_documento_id"));
				}
				else
				{
					mensaje = "**error**, la orden de compra no contiene smn_documentos_generales_id";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
					return 1; //valor null.
				}
				
				String sqlSec  = getSQL(getResource("select-secuencia.sql"),inputParams);
				Recordset rsSec = db.get(sqlSec); //consulta la secuencia.
				
				if(rsSec.getRecordCount()>0)
				{
					rsSec.first();
					if(rsSec.getString("secuencia") != null)
						inputParams.setValue("secuencia", rsSec.getInt("secuencia"));
					else
						inputParams.setValue("secuencia", 1);
				}
				else
					inputParams.setValue("secuencia", 1);
				
				
				inputParams.setValue("cal_tipo_almacen", "RE");
				String sqlAlmacen  = getSQL(getResource("select-almacen.sql"),inputParams);
				Recordset rsAlmacen = db.get(sqlAlmacen); //consulta el almacen.
				
				if(rsAlmacen.getRecordCount()>0)
				{
					rsAlmacen.first();
					inputParams.setValue("smn_almacen_rf", rsAlmacen.getInt("smn_almacen_rf"));
				}
				else
				{
					mensaje = "**error**, la orden de compra no contiene smn_almacen_rf";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
					return 1; //valor null.
				}
				
			//*********************	
				
				String sqlPago   = getSQL(getResource("insert-pago.sql"),inputParams);
				Recordset rsPago = db.get(sqlPago); //registra pago.
				
				str = "Registrando pago...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsPago.getRecordCount()>0)
				{			
					rc = 0; // se registro el pago exitosamente.
					mensaje = "se registro el pago exitosamente";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
				else
				{
					rc = 1; //no se registro el pago.
					mensaje = "**error**, no se registro el pago";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				rc = 1; //no se encontro la orden de compra.
				mensaje = "**error**, no se encontro la orden de compra";
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarRecepcionServicio(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje="";	
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		double monto_bruto_ml = 0.0;
		double monto_impuesto_ml = 0.0;
		double monto_desc_reten_ml = 0.0;
		double monto_bruto_ma = 0.0;
		double monto_impuesto_ma = 0.0;
		double monto_desc_reten_ma = 0.0;
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			str = "*Procesando registro de recepcion de servicios*";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			String sqlCentroCosto  = getSQL(getResource("select-smn_centro_costo_rf.sql"),inputParams);
			Recordset rsCentroCosto = db.get(sqlCentroCosto); //consulta el centro costo.
			
			if(rsCentroCosto.getRecordCount() > 0)
			{
				rsCentroCosto.first();
				
				if(rsCentroCosto.getString("smn_centro_costo_rf") != null)
					inputParams.setValue("smn_centro_costo_rf", rsCentroCosto.getInt("smn_centro_costo_rf"));
				else
					inputParams.setValue("smn_centro_costo_rf", 0);
			}
			else
			{
				inputParams.setValue("smn_centro_costo_rf", 0);
			}
			
			String sqlTipoDoc  = getSQL(getResource("select-smn_tipo_documento_id.sql"),inputParams);
			Recordset rsTipoDoc = db.get(sqlTipoDoc); //consulta el tipo de documento.
			
			if(rsTipoDoc.getRecordCount() > 0)
			{
				rsTipoDoc.first();
				
				if(rsTipoDoc.getString("smn_tipo_documento_id") != null)
					inputParams.setValue("smn_tipo_documento_id", rsTipoDoc.getInt("smn_tipo_documento_id"));
				else
					inputParams.setValue("smn_tipo_documento_id", 0);
			}
			else
			{
				inputParams.setValue("smn_tipo_documento_id", 0);
			}
			
			String sqlDocPago  = getSQL(getResource("select-smn_documento_id_pago.sql"),inputParams);
			Recordset rsDocPago = db.get(sqlDocPago); //consulta el documento de pagos.
			
			if(rsDocPago.getRecordCount() > 0)
			{
				rsDocPago.first();
				inputParams.setValue("smn_documento_id_pago", rsDocPago.getInt("smn_documento_id"));
			}
			else
			{
				mensaje = "**error**, No se encontro el documento de pago relacionado al documento general";
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
				return 1;
			}
			
			String sqlSecuencia = getSQL(getResource("select-doc_secuencia.sql"),inputParams);
			Recordset rsSecuencia= db.get(sqlSecuencia); //consulta la secuencia del documento de pago.
			
			if(rsSecuencia.getRecordCount() > 0)
			{
				rsSecuencia.first();
				
				if(rsSecuencia.getString("secuencia") != null)
					inputParams.setValue("secuencia", rsSecuencia.getInt("secuencia"));
				else
					inputParams.setValue("secuencia", 1);
			}
			else
			{
				inputParams.setValue("secuencia", 1);
			}
			
			inputParams.setValue("smn_clase_rf", 0);
			
			String sqlUsuario = getSQL(getResource("select-smn_usuarios_id.sql"),inputParams);
			Recordset rsUsuario = db.get(sqlUsuario); //consulta el usuario relacionado al auxiliar de la OC.
			
			if(rsUsuario.getRecordCount() > 0)
			{
				rsUsuario.first();
				inputParams.setValue("smn_usuarios_id", rsUsuario.getInt("smn_usuarios_id"));
			}
			else
			{
				mensaje = "**error**, No se encontro el usuario relacionado al auxiliar de la OC";
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
				return 1;
			}
			
			String sqlCamposCalculados = getSQL(getResource("select-campos_calculados_ocd.sql"),inputParams);
			Recordset rsCamposCalculados = db.get(sqlCamposCalculados); //consulta los campos calculados de la ocd.
			
			if(rsCamposCalculados.getRecordCount() > 0)
			{
				rsCamposCalculados.first();
				
				if(rsCamposCalculados.getString("monto_bruto_ml") != null)
					monto_bruto_ml = rsCamposCalculados.getDouble("monto_bruto_ml");
				else
					monto_bruto_ml = 0.0;
				
				if(rsCamposCalculados.getString("monto_impuesto_ml") != null)
					monto_impuesto_ml = rsCamposCalculados.getDouble("monto_impuesto_ml");
				else
					monto_impuesto_ml = 0.0;
				
				if(rsCamposCalculados.getString("monto_desc_reten_ml") != null)
					monto_desc_reten_ml = rsCamposCalculados.getDouble("monto_desc_reten_ml");
				else
					monto_desc_reten_ml = 0.0;
				
				if(rsCamposCalculados.getString("monto_bruto_ma") != null)
					monto_bruto_ma = rsCamposCalculados.getDouble("monto_bruto_ma");
				else
					monto_bruto_ma = 0.0;
				
				if(rsCamposCalculados.getString("monto_impuesto_ma") != null)
					monto_impuesto_ma = rsCamposCalculados.getDouble("monto_impuesto_ma");
				else
					monto_impuesto_ma = 0.0;
				
				if(rsCamposCalculados.getString("monto_desc_reten_ma") != null)
					monto_desc_reten_ma = rsCamposCalculados.getDouble("monto_desc_reten_ma");
				else
					monto_desc_reten_ma = 0.0;
			}
			
			inputParams.setValue("nrs_monto_ml", monto_bruto_ml+monto_impuesto_ml-monto_desc_reten_ml);
			inputParams.setValue("nrs_monto_ma", monto_bruto_ma+monto_impuesto_ma-monto_desc_reten_ma);
			
			String sql = getSQL(getResource("insert-smn_nota_recepcion_servicio.sql"),inputParams);
			Recordset rsRecepcionServicio = db.get(sql); //Registra la recepcion de servicio.
			
			if(rsRecepcionServicio.getRecordCount()>0)
			{
				str = "*Se registro la nota de recepcion de servicio exitosamente*";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				sql = getSQL(getResource("update-secuencia_documento_pago.sql"),inputParams);
				Recordset rsDoc = db.get(sql); //Actualiza la secuencia del documento.
				
				if(rsDoc.getRecordCount()>0)
				{
					rc = 0; //no se registro el pago.
					mensaje = "*Se actualizo la secuencia del documento de pago exitosamente*";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
				else
				{
					rc = 1; //no se registro el pago.
					mensaje = "**error**, No se actualizo la secuencia del documento de pagos";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				rc = 1; //no se registro el pago.
				mensaje = "**error**, No se registro la nota de recepcion de servicio";
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarAnticipo(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje="";	
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlOrdenCompra   = getSQL(getResource("select-ordenCompra.sql"),inputParams);
			Recordset rsOrdenCompra = db.get(sqlOrdenCompra); //consulta la orden de compra cabecera.
			
			if(rsOrdenCompra.getRecordCount()>0)
			{
				//**INYECCION DE VALORES EN EL VALIDATOR PROVENIENTES DE LA TABLA: smn_orden_compra_cabecera
				
				rsOrdenCompra.first();
				
				if(rsOrdenCompra.getString("smn_oferta_id") != null)
					inputParams.setValue("smn_oferta_id", rsOrdenCompra.getInt("smn_oferta_id"));
				else
					inputParams.setValue("smn_oferta_id", 0);
				
				String sqlOferta   = getSQL(getResource("select-smn_oferta.sql"),inputParams);
				Recordset rsOferta = db.get(sqlOferta); //consulta la oferta.
				
				if(rsOferta.getRecordCount()>0)
				{
					rsOferta.first();
					
					if(rsOferta.getString("ofe_aplica_anticipo").equals("SI"))
					{
						str = "--Aplica anticipo--";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsOrdenCompra.getString("smn_entidad_rf") != null)
							inputParams.setValue("smn_entidad_rf", rsOrdenCompra.getInt("smn_entidad_rf"));
						else
						{
							mensaje = "**error**, la orden de compra no contiene entidad";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}
						
						if(rsOrdenCompra.getString("smn_sucursal_rf") != null)
							inputParams.setValue("smn_sucursal_rf", rsOrdenCompra.getInt("smn_sucursal_rf"));
						else
							inputParams.setValue("smn_sucursal_rf", 0);
						
						if(rsOrdenCompra.getString("smn_proveedor_id") != null)
							inputParams.setValue("smn_proveedor_id", rsOrdenCompra.getInt("smn_proveedor_id"));
						else
						{
							mensaje = "**error**, la orden de compra no contiene proveedor";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}

						if(rsOrdenCompra.getString("smn_documento_id") != null)
							inputParams.setValue("smn_documento_id", rsOrdenCompra.getInt("smn_documento_id"));
						else
						{
							mensaje = "**error**, la orden de compra no contiene documento";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}
						
						String sqlDocumento = getSQL(getResource("select-smn_documento_anticipo.sql"),inputParams);
						Recordset rsDocumento = db.get(sqlDocumento);
						
						if(rsDocumento.getRecordCount()>0)
						{
							rsDocumento.first();
							inputParams.setValue("smn_documento_id_pago", rsDocumento.getInt("smn_documento_id"));
						}
						else
						{
							mensaje = "**error**, No se encontro el documento en pagos";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}
						
						String sqlSecuencia = getSQL(getResource("select-doc_secuencia.sql"),inputParams);
						Recordset rsSecuencia = db.get(sqlSecuencia);
						
						if(rsSecuencia.getRecordCount()>0)
						{
							rsSecuencia.first();
							
							if(rsSecuencia.getString("secuencia")!=null)
								inputParams.setValue("secuencia",rsSecuencia.getInt("secuencia"));
							else
								inputParams.setValue("secuencia",1);
						}
						else
						{
							mensaje = "**error**, No se encontro la secuencia del documento en pagos";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}
						
						if(rsOferta.getString("ofe_porcentaje_anticipo")!=null)
							inputParams.setValue("ofe_porcentaje_anticipo", rsOferta.getDouble("ofe_porcentaje_anticipo"));
						else
						{
							mensaje = "**error**, El porcentaje del anticipo esta vacio";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}
						
						if(rsOferta.getString("ofe_monto_anticipo_ml")!=null)
							inputParams.setValue("ofe_monto_anticipo_ml", rsOferta.getDouble("ofe_monto_anticipo_ml"));
						else
						{
							mensaje = "**error**, El monto del anticipo esta vacio";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							return 1; //valor null.
						}
						
						if(rsOferta.getString("ofe_moneda_id")!=null)
							inputParams.setValue("ofe_moneda_id", rsOferta.getInt("ofe_moneda_id"));
						else
							inputParams.setValue("ofe_moneda_id", 0);
						
						if(rsOferta.getString("ofe_tasa")!=null)
							inputParams.setValue("ofe_tasa", rsOferta.getInt("ofe_tasa"));
						else
							inputParams.setValue("ofe_tasa", 0);
						
						if(rsOferta.getString("ofe_monto_anticipo_ma")!=null)
							inputParams.setValue("ofe_monto_anticipo_ma", rsOferta.getDouble("ofe_monto_anticipo_ma"));
						else
							inputParams.setValue("ofe_monto_anticipo_ma", 0.0);
												
						str = "Registrando anticipo...";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						String sql = getSQL(getResource("insert-smn_anticipo.sql"),inputParams);
						db.exec(sql);
						
						str = "Anticipo registrado correctamente";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						rc = 0;
					}
					else
					{
						str = "--No aplica anticipo--";	
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}
			}
			else
			{
				rc = 1; //no se encontro la orden de compra.
				mensaje = "**error**, no se encontro la orden de compra";
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarRecepcionAfijo(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
		
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			str = "*Procesando registro de recepcion de activo fijo*";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			String sqlCentroCosto  = getSQL(getResource("select-smn_centro_costo_rf.sql"),inputParams);
			Recordset rsCentroCosto = db.get(sqlCentroCosto); //consulta el centro costo.
			
			if(rsCentroCosto.getRecordCount() > 0)
			{
				rsCentroCosto.first();
				
				if(rsCentroCosto.getString("smn_centro_costo_rf") != null)
					inputParams.setValue("smn_centro_costo_rf", rsCentroCosto.getInt("smn_centro_costo_rf"));
				else
					inputParams.setValue("smn_centro_costo_rf", 0);
			}
			else
			{
				inputParams.setValue("smn_centro_costo_rf", 0);
			}
			
			str = "Registrando recepcion de activo fijo...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			String sql = getSQL(getResource("insert-smn_activo_fijo_recepcion.sql"),inputParams);
			db.exec(sql);
			
			str = "recepcion de activo fijo registrado correctamente";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			rc = 0;
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
}
