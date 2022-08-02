package proceso;

import dinamica.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

public class Requisicion extends GenericTransaction
{
	public int service(Recordset inputParams) throws Throwable 
	{ 
		
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
		
		conn.setAutoCommit(false);
		
		int contDetalles = 0;
		String version;
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logRequisiciones_"+fechaActual+".txt";
		else
			file = "./logRequisiciones_"+fechaActual+".txt";
		
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
			Recordset rsRol = db.get(sqlRol);
			
			str = "*Validando rol...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsRol.getRecordCount()>0)
			{
				str = "*Rol aprobado";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlCabecera   = getSQL(getResource("select-requisicion_cabecera.sql"),inputParams);
				Recordset rsCabecera = db.get(sqlCabecera); 
				
				int id_cabecera_original = inputParams.getInt("smn_requisicion_cabecera_id"); //contiene el id de la requisicion original
				
				str = "*Consultando requisicion_cabecera id="+id_cabecera_original;	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsCabecera.getRecordCount()>0) 
				{
					rsCabecera.first();
					
					//-----Validacion e insercion de valores en el validator provenientes de la tabla smn_requisicion_cabecera
					
						if(rsCabecera.getString("smn_tipo_documento_id") != null)
							inputParams.setValue("smn_tipo_documento_id", rsCabecera.getInt("smn_tipo_documento_id"));
						else
							inputParams.setValue("smn_tipo_documento_id", 0);
						
						if(rsCabecera.getString("smn_documento_id") != null)
							inputParams.setValue("smn_documento_id", rsCabecera.getInt("smn_documento_id"));
						else
							inputParams.setValue("smn_documento_id", 0);
						
						if(rsCabecera.getString("req_descripcion") != null)
							inputParams.setValue("req_descripcion", rsCabecera.getString("req_descripcion"));
						else
							inputParams.setValue("req_descripcion", null);
						
						if(rsCabecera.getString("req_fecha_requerido") != null)
							inputParams.setValue("req_fecha_requerido", rsCabecera.getDate("req_fecha_requerido"));
						else
							inputParams.setValue("req_fecha_requerido", null);
						
						if(rsCabecera.getString("req_estatus_ruta") != null)
							inputParams.setValue("req_estatus_ruta", rsCabecera.getString("req_estatus_ruta"));
						else
							inputParams.setValue("req_estatus_ruta", null);
						
						if(rsCabecera.getString("smn_entidad_id") != null)
							inputParams.setValue("smn_entidad_id", rsCabecera.getInt("smn_entidad_id"));
						else
							inputParams.setValue("smn_entidad_id", 0);
						
						if(rsCabecera.getString("smn_sucursal_id") != null)
							inputParams.setValue("smn_sucursal_id", rsCabecera.getInt("smn_sucursal_id"));
						else
							inputParams.setValue("smn_sucursal_id", 0);
						
						str = "*Valores de la requisicion insertados en el validator";	
						bw.write(str);
						bw.flush();
						bw.newLine();
					//-----------------------------------------------------------------------------------
						
						String sqlModalidad   = getSQL(getResource("select-documento_modalidad.sql"),inputParams);
						Recordset rsModalidad = db.get(sqlModalidad); //busca la modalidad de la requisicion.
						
						str = "*Consultando modalidad";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsModalidad.getRecordCount()>0)
						{
							rsModalidad.first();
							
							String modalidad = rsModalidad.getString("dcc_modalidad"); //modalidad (AU=automatica/MA=manual).
							
							str = "*Modalidad = "+modalidad;
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							String sqlReq_detalle   = getSQL(getResource("select-requisicion_detalle.sql"),inputParams); 
							Recordset rsReq_detalle = db.get(sqlReq_detalle);
							
							str = "*Consultando requisicion_detalle";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsReq_detalle.getRecordCount()>0)
							{
								str = "*Total detalles encontrados = "+rsReq_detalle.getRecordCount();
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								while(rsReq_detalle.next())
								{
									contDetalles++;
									
									str = "*Procesando detalle "+contDetalles;
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									if(rsReq_detalle.getString("smn_requisicion_detalle_id") != null)
										inputParams.setValue("smn_requisicion_detalle_id", rsReq_detalle.getInt("smn_requisicion_detalle_id"));
									else
										inputParams.setValue("smn_requisicion_detalle_id", 0);
									
									str = "-Requisicion_detalle id="+inputParams.getValue("smn_requisicion_detalle_id");
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									String naturaleza = rsReq_detalle.getString("smn_naturaleza_id"); //naturaleza (IT=item / SE=servicio / AF = activo fijo).
									
									str = "-Naturaleza del detalle ="+naturaleza;
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									version = null;
									
									if(naturaleza.equals("IT")) //requisicion por item.
									{
										if(rsReq_detalle.getString("rrs_producto_encontrado").equals("SI") && rsReq_detalle.getString("smn_item_id") != null)
										{
											String sqlItem   = getSQL(getResource("select-caracteristica_item.sql"),inputParams); //buscar item.
											Recordset rsItem = db.get(sqlItem);
											
											str = "-Consultando caracteristica item...";
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											if(rsItem.getRecordCount()>0)
											{
												rsItem.first();
												
												if(rsItem.getString("smn_item_rf") != null)
												{
													inputParams.setValue("smn_item_rf", rsItem.getInt("smn_item_rf"));
													
													str = "-Item id="+inputParams.getValue("smn_item_rf")+" encontrado en caracteristica de item";
													bw.write(str);
													bw.flush();
													bw.newLine();
													
													String prov_exclusivo = rsItem.getString("cit_proveedor_exclusivo"); 
													
													if(prov_exclusivo != null)
														inputParams.setValue("smn_proveedor_id", rsItem.getInt("cit_proveedor_exclusivo"));
													
													String sqlCR = getSQL(getResource("select-compania_relacionada_item.sql"),inputParams);
													Recordset rsCR = db.get(sqlCR); //consulta de la compañia relacionada.
													
													str ="-Consultando compania relacionada...";
													bw.write(str);
													bw.flush();
													bw.newLine();
													
													if(modalidad.equals("AU"))
													{
														if(prov_exclusivo == null)
														{
															version = "D";
														}
														else
														{
															if(rsCR.getRecordCount()>0)
																version = "A";
															else
																version = "C";
														}
													}
													else
													if(modalidad.equals("MA"))
													{
														String IT_almacenado = rsItem.getString("cit_almacenado"); //item almacenado (SI/NO). 
															
														if(IT_almacenado.equals("SI"))
														{
															String sql = getSQL(getResource("select-smn_control_item.sql"),inputParams);
															Recordset rsExistencia = db.get(sql); //consulta la cantidad en existencia del almacen.
															
															if(rsExistencia.getRecordCount() > 0)
															{
																rsExistencia.first();
																
																double coi_saldo_final_existencia;
																double rss_cantidad;
																
																if(!rsExistencia.getString("coi_saldo_final_existencia").equals(null))
																	coi_saldo_final_existencia = rsExistencia.getDouble("coi_saldo_final_existencia");
																else
																	coi_saldo_final_existencia = 0.0;
																
																if(!rsReq_detalle.getString("rss_cantidad").equals(null))
																	rss_cantidad = rsReq_detalle.getDouble("rss_cantidad");
																else
																	rss_cantidad = 0.0;
															
																if(prov_exclusivo == null)
																	prov_exclusivo = "NO";
																
																if(!rsReq_detalle.getString("rss_cantidad").equals(null))
																	rss_cantidad = rsReq_detalle.getDouble("rss_cantidad");
																else
																	rss_cantidad = 0.0;
															
																if(coi_saldo_final_existencia >= rss_cantidad)
																	version = "B";
																else
																if((coi_saldo_final_existencia < rss_cantidad) && prov_exclusivo.equals("NO"))
																	version = "D";
																else
																if((coi_saldo_final_existencia < rss_cantidad) && prov_exclusivo.equals("SI"))
																	version = "A";
															}
															else
															{
																version = "D";
															}
														}
														else
														if(IT_almacenado.equals("NO"))
														{
															if(prov_exclusivo == null)
															{
																version = "D";
															}
															else
															{
																if(rsCR.getRecordCount()>0)
																	version = "A";
																else
																	version = "C";
															}
														}
													}	
												}
												else
												{
													version = "D";
												}
											}
											else
											{
												mensaje = "No se encontro la caracteristica del item";
												str = "-"+mensaje;
												bw.write(str);
												bw.flush();
												bw.newLine();
												rc = 12; //no se encontro la caracteristica del item.
											}
										}
										else
										{
											version = "D";
										}
									}
									else
									if(naturaleza.equals("SE")) //requisicion por servicio
									{
										if(rsReq_detalle.getString("smn_servicio_id") != null)
										{
											inputParams.setValue("smn_servicio_id", rsReq_detalle.getInt("smn_servicio_id"));
											
											String sqlServicio   = getSQL(getResource("select-servicio.sql"),inputParams);
											Recordset rsServicio = db.get(sqlServicio); //consultar la tabla servicio
											
											str = "-Consultando servicio id="+inputParams.getValue("smn_servicio_id");
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											if(rsServicio.getRecordCount()>0)
											{
												rsServicio.first();
												
												String prov_exclusivo = rsServicio.getString("sco_proveedor_exclusivo"); 
												
												if(prov_exclusivo != null)
													inputParams.setValue("smn_proveedor_id", rsServicio.getInt("sco_proveedor_exclusivo"));
												
												String sqlCR = getSQL(getResource("select-compania_relacionada_serv.sql"),inputParams);
												Recordset rsCR = db.get(sqlCR); //consulta de la compañia relacionada.
												
												str = "-Consultando compania relacionada...";
												bw.write(str);
												bw.flush();
												bw.newLine();
												
												
												if(prov_exclusivo == null)
												{
													version = "D";
												}
												else
												{
													if(rsCR.getRecordCount()>0)
														version = "A";
													else
														version = "C";	
												}
											}
										}
										else
										{
											version = "D";
										}
									}
									else
									if(naturaleza.equals("AF")) //requisicion por activo fijo.
									{
										if(rsReq_detalle.getString("smn_afijo_id") != null)
										{
											inputParams.setValue("smn_afijo_id", rsReq_detalle.getInt("smn_afijo_id"));
											
											String sqlActivo_fijo   = getSQL(getResource("select-activo_fijo.sql"),inputParams);
											Recordset rsActivo_fijo = db.get(sqlActivo_fijo);
											
											str = "-Consultando activo fijo id="+inputParams.getValue("smn_afijo_id");
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											if(rsActivo_fijo.getRecordCount()>0)
											{
												rsActivo_fijo.first();
												
												String prov_exclusivo = rsActivo_fijo.getString("act_proveedor_exclusivo"); //proveedor exclusivo
												
												String sqlCR = getSQL(getResource("select-compania_activo_fijo.sql"),inputParams);
												Recordset rsCR = db.get(sqlCR); //consulta de la compañia relacionada. 
													
												str = "-Consultando compania relacionada...";
												bw.write(str);
												bw.flush();
												bw.newLine();
												
												if(prov_exclusivo == null)
												{
													version = "D";
												}
												else
												{
													if(rsCR.getRecordCount()>0)
														version = "C";
													else
														version = "D";
												}
											}
										}
										else
										{
											version = "D";
										}
									}
									
									str = "-Version identificada: "+version;
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									if(version != null)
									{
										inputParams.setValue("req_cabecera_version", version);
										
										switch(version)
										{
											case "A":
												
												inputParams.setValue("req_version_descripcion","Orden de compra");
												
												break;
												
											case "B":
												
												inputParams.setValue("req_version_descripcion","Requisicion interna");
										
												break;
												
											case "C":
												
												inputParams.setValue("req_version_descripcion","Pedido comercial");
												
												break;
												
											case "D":

												inputParams.setValue("req_version_descripcion","Cotizacion");
												
												break;
												
											default:
												str = "-*La version generada no es correcta*";
												bw.write(str);
												bw.flush();
												bw.newLine();
												bw.close();
												return 1;
										}
											
										if(rsReq_detalle.getString("smn_linea_id") != null)
											inputParams.setValue("smn_linea_id",rsReq_detalle.getInt("smn_linea_id"));
										else
											inputParams.setValue("smn_linea_id",0);
										
										if(rsReq_detalle.getString("smn_naturaleza_id") != null)
											inputParams.setValue("smn_naturaleza_id",rsReq_detalle.getString("smn_naturaleza_id"));
										else
											inputParams.setValue("smn_naturaleza_id",null);
										
										if(rsReq_detalle.getString("rrs_producto_encontrado") != null)
											inputParams.setValue("rrs_producto_encontrado",rsReq_detalle.getString("rrs_producto_encontrado"));
										else
											inputParams.setValue("rrs_producto_encontrado",null);
										
										if(rsReq_detalle.getString("smn_contrato_id") != null)
											inputParams.setValue("smn_contrato_id",rsReq_detalle.getInt("smn_contrato_id"));
										else
											inputParams.setValue("smn_contrato_id",0);
										
										if(rsReq_detalle.getString("rrs_motivo_variacion") != null)
											inputParams.setValue("rrs_motivo_variacion",rsReq_detalle.getString("rrs_motivo_variacion"));
										else
											inputParams.setValue("rrs_motivo_variacion",null);
										
										if(rsReq_detalle.getString("rrs_porcentaje") != null)
											inputParams.setValue("rrs_porcentaje",rsReq_detalle.getDouble("rrs_porcentaje"));
										else
											inputParams.setValue("rrs_porcentaje",0.0);
										
										if(rsReq_detalle.getString("rss_cantidad") != null)
											inputParams.setValue("rss_cantidad",rsReq_detalle.getDouble("rss_cantidad"));
										else
											inputParams.setValue("rss_cantidad",0);
										
										if(rsReq_detalle.getString("rrs_precio") != null)
											inputParams.setValue("rrs_precio",rsReq_detalle.getDouble("rrs_precio"));
										else
											inputParams.setValue("rrs_precio",0.0);
										
										if(rsReq_detalle.getString("rrs_monto") != null)
											inputParams.setValue("rrs_monto",rsReq_detalle.getDouble("rrs_monto"));
										else
											inputParams.setValue("rrs_monto",0.0);
										
										if(rsReq_detalle.getString("smn_moneda_id") != null)
											inputParams.setValue("smn_moneda_id",rsReq_detalle.getInt("smn_moneda_id"));
										else
											inputParams.setValue("smn_moneda_id",0);
										
										if(rsReq_detalle.getString("rrs_precio_moneda_alterna") != null)
											inputParams.setValue("rrs_precio_moneda_alterna",rsReq_detalle.getDouble("rrs_precio_moneda_alterna"));
										else
											inputParams.setValue("rrs_precio_moneda_alterna",0.0);
										
										if(rsReq_detalle.getString("rrs_monto_moneda_alterna") != null)
											inputParams.setValue("rrs_monto_moneda_alterna",rsReq_detalle.getDouble("rrs_monto_moneda_alterna"));
										else
											inputParams.setValue("rrs_monto_moneda_alterna",0.0);
										
										if(rsReq_detalle.getString("rrs_especificaciones_del_requerimiento") != null)
											inputParams.setValue("rrs_especificaciones_del_requerimiento",rsReq_detalle.getString("rrs_especificaciones_del_requerimiento"));
										else
											inputParams.setValue("rrs_especificaciones_del_requerimiento",null);
										
										if(rsReq_detalle.getString("rrs_fecha_de_requerido") != null)
											inputParams.setValue("rrs_fecha_de_requerido",rsReq_detalle.getDate("rrs_fecha_de_requerido"));
										else
											inputParams.setValue("rrs_fecha_de_requerido",null);
									
										if(rsReq_detalle.getString("rrs_observaciones") != null)
											inputParams.setValue("rrs_observaciones",rsReq_detalle.getString("rrs_observaciones"));
										else
											inputParams.setValue("rrs_observaciones",null);
										
										String sqlConsultarVersion   = getSQL(getResource("select-version_cabecera.sql"),inputParams);
										Recordset rsConsultarVersion = db.get(sqlConsultarVersion);
										
										str = "-Procesando version...";
										bw.write(str);
										bw.flush();
										bw.newLine();
										
										if(rsConsultarVersion.getRecordCount()>0)
										{
											rsConsultarVersion.first();
											
											if(rsConsultarVersion.getString("smn_requisicion_cabecera_id") != null)
												inputParams.setValue("smn_cabecera_id", rsConsultarVersion.getInt("smn_requisicion_cabecera_id"));
											else
												inputParams.setValue("smn_cabecera_id", 0);
											
											str = "-Requisicion id="+inputParams.getValue("smn_cabecera_id")+" encontrada con version "+version;
											bw.write(str);
											bw.flush();
											bw.newLine();
													
											String sqlInsertar_detalle   = getSQL(getResource("insert-requisicion_detalle.sql"),inputParams);
											Recordset rsInsertar_detalle = db.get(sqlInsertar_detalle);
											
											str = "-Registrando detalle...";
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											if(rsInsertar_detalle.getRecordCount()>0)
											{
												rsInsertar_detalle.first();
					
												inputParams.setValue("req_detalle_id", rsInsertar_detalle.getInt("id_detalle"));
												
												str = "-Detalle registrado con el id= "+inputParams.getValue("req_detalle_id");
												bw.write(str);
												bw.flush();
												bw.newLine();
												
												registrarImpuestos(conn,inputParams,str,bw);
												registrarDeducciones(conn,inputParams,str,bw);
												registrarFecha_entrega(conn,inputParams,str,bw);
											}
											else
											{
												rc = 7; //ocurrio un error al crear registro en requisicion_detalle.
												mensaje = "Ocurrio un error al crear el detalle de la requisicion";
												str = "*"+mensaje;
												bw.write(str);
												bw.flush();
												bw.newLine();
											}
										}
										else
										{
											String sqlInsertar_cabecera   = getSQL(getResource("insert-requisicion_cabecera.sql"),inputParams);
											Recordset rsInsertar_cabecera = db.get(sqlInsertar_cabecera);
											
											str = "-Registrando nueva cabecera...";
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											if(rsInsertar_cabecera.getRecordCount()>0)
											{
												rsInsertar_cabecera.first();
												
												if(rsInsertar_cabecera.getString("id_cabecera") != null)
													inputParams.setValue("smn_cabecera_id", rsInsertar_cabecera.getInt("id_cabecera"));
												else
													inputParams.setValue("smn_cabecera_id", 0);
											
												str = "-Cabecera registrada con el id= "+inputParams.getValue("smn_cabecera_id");
												bw.write(str);
												bw.flush();
												bw.newLine();
												
												String sqlInsertar_detalle   = getSQL(getResource("insert-requisicion_detalle.sql"),inputParams);
												Recordset rsInsertar_detalle = db.get(sqlInsertar_detalle);
																
												str = "-Registrando detalle";
												bw.write(str);
												bw.flush();
												bw.newLine();
												
												if(rsInsertar_detalle.getRecordCount()>0)
												{
													rsInsertar_detalle.first();
													
													inputParams.setValue("req_detalle_id", rsInsertar_detalle.getInt("id_detalle"));
													
													str = "-Detalle registrado con el id= "+inputParams.getValue("req_detalle_id");
													bw.write(str);
													bw.flush();
													bw.newLine();
													
													registrarImpuestos(conn,inputParams,str,bw);
													registrarDeducciones(conn,inputParams,str,bw);
													registrarFecha_entrega(conn,inputParams,str,bw);
												}
												else
												{
													mensaje = "ocurrio un error al crear el detalle de la requisicion";
													rc = 7; //ocurrio un error al crear registro en requisicion_detalle.
													str = "*"+mensaje;
													bw.write(str);
													bw.flush();
													bw.newLine();
												}
											}
											else
											{
												mensaje = "ocurrio un error al crear la cebecera de la requisicion";
												rc = 6; //ocurrio un error al crear registro en requisicion_cabecera.
												str = "*"+mensaje;
												bw.write(str);
												bw.flush();
												bw.newLine();
											}
										}
									}
									else
									{
										mensaje = "Ocurrio un error al versionar la requisicion";
										inputParams.setValue("mensaje", mensaje);
										str = "*"+mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
										bw.close();
										
										return 1;
									}
									
									inputParams.setValue("smn_item_rf", 0);
									inputParams.setValue("smn_servicio_id", 0);
									inputParams.setValue("smn_afijo_id", 0);
									
								} //END WHILE
									
								if(rc==0)
								{
									inputParams.setValue("smn_cabecera_id", id_cabecera_original);
										
									String sqlEstatus_cabecera   = getSQL(getResource("update-estatus_cabecera.sql"),inputParams);
									Recordset rsEstatus_cabecera = db.get(sqlEstatus_cabecera);
									
									str = "*Actualizando estatus de la requisicion...";
									bw.write(str);
									bw.flush();
									bw.newLine();
										
									if(rsEstatus_cabecera.getRecordCount()>0)
									{
										mensaje = "se generó la requisición exitosamente";
										rc = 0;
										str = "*"+mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
									}
									else
									{
										mensaje = "ocurrio un error al actualizar el estatus de la requisición a aprobada";
										rc = 4; //no se ejecuto el update al campo de estatus de la tabla smn_compras.smn_requisicion_cabecera.
										str = "*"+mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
									}
								}
							}
							else
							{
								mensaje = "no se encontraron detalles de la requisicion";
								rc = 3; //no se encontraron registros al ejecutar la consulta de requisicion detalle.
								str = "*"+mensaje;
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							mensaje = "no se encontro la modalidad del documento";
							rc = 2; //no se encontraron registros al ejecutar la consulta de la modalidad del documento.
							str = "*"+mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
				}
				else
				{
					mensaje = "la requisicion no esta solicitada";
					rc = 1; //no se encontro la requisicion_cabecera o su estatus no es *solicitada*.
					str = "*"+mensaje;
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				mensaje = "El usuario no tiene rol de aprobador o solicitante.";
				rc = 13; //el usuario no tiene rol de aprobador o solicitante.
				str = "*"+mensaje;
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			
			inputParams.setValue("mensaje", mensaje);
			
			str = "FIN DEL PROCESO";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
	        bw.close();
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		
		finally
		{
			bw.close();
			
			if(rc == 0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}
		
		return rc;
	}
	
	public int registrarImpuestos(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sql = getSQL(getResource("select-requisicion_impuesto.sql"),inputParams);
			Recordset rsImpuesto = db.get(sql); //consultar requisicion_detalle de impuesto.
			
			str = "-Determinando si el detalle requiere impuestos...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsImpuesto.getRecordCount()>0)
			{	
				while(rsImpuesto.next() != false)
				{
					if(rsImpuesto.getString("rim_monto_base") != null)
						inputParams.setValue("rim_monto_base", rsImpuesto.getDouble("rim_monto_base"));
					else
						inputParams.setValue("rim_monto_base", 0.0);
					
					if(rsImpuesto.getString("smn_cod_impuesto_deduc_rf") != null)
						inputParams.setValue("smn_cod_impuesto_deduc_rf", rsImpuesto.getInt("smn_cod_impuesto_deduc_rf"));
					else
						inputParams.setValue("smn_cod_impuesto_deduc_rf", 0);
					
					if(rsImpuesto.getString("smn_porcentaje_impuesto") != null)
						inputParams.setValue("smn_porcentaje_impuesto", rsImpuesto.getDouble("smn_porcentaje_impuesto"));
					else
						inputParams.setValue("smn_porcentaje_impuesto", 0.0);
					   
					if(rsImpuesto.getString("smn_sustraendo") != null)
						inputParams.setValue("smn_sustraendo", rsImpuesto.getDouble("smn_sustraendo"));
					else
						inputParams.setValue("smn_sustraendo", 0.0);
					
					if(rsImpuesto.getString("rim_monto_impuesto") != null)
						inputParams.setValue("rim_monto_impuesto", rsImpuesto.getDouble("rim_monto_impuesto"));
					else
						inputParams.setValue("rim_monto_impuesto", 0.0);
					   
					if(rsImpuesto.getString("smn_moneda_rf") != null)
						inputParams.setValue("smn_moneda_rf", rsImpuesto.getInt("smn_moneda_rf"));
					else
						inputParams.setValue("smn_moneda_rf", 0);
					
					if(rsImpuesto.getString("smn_tasa_rf") != null)
						inputParams.setValue("smn_tasa_rf", rsImpuesto.getInt("smn_tasa_rf"));
					else
						inputParams.setValue("smn_tasa_rf", 0);
					   
					if(rsImpuesto.getString("rim_monto_imp_moneda_alterna") != null)
						inputParams.setValue("rim_monto_imp_moneda_alterna", rsImpuesto.getDouble("rim_monto_imp_moneda_alterna"));
					else
						inputParams.setValue("rim_monto_imp_moneda_alterna", 0.0); 
					
					sql = getSQL(getResource("insert-requisicion_impuesto.sql"),inputParams);
					Recordset rsRegistrarImpuesto = db.get(sql); //registrar requisicion_detalle de impuesto.
					
					str = "-Registrando impuestos...";
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsRegistrarImpuesto.getRecordCount()>0)
					{
						rc = 0; //registro de impuesto exitosamente.
						str = "-Se registraron los impuestos exitosamente";
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				} //END WHILE
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarDeducciones(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
	
			this.setConnection(conn);
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sql = getSQL(getResource("select-requisicion_deducciones.sql"),inputParams);
			Recordset rsDeducciones = db.get(sql); //consultar deducciones de requisicion_detalle.
			
			str = "-Determinando si el detalle requiere descuentos...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsDeducciones.getRecordCount()>0)
			{	
				while(rsDeducciones.next() != false)
				{
					if(rsDeducciones.getString("smn_codigo_descuento_rf") != null)
						inputParams.setValue("smn_codigo_descuento_rf", rsDeducciones.getInt("smn_codigo_descuento_rf"));
					else
						inputParams.setValue("smn_codigo_descuento_rf", 0);
					
					if(rsDeducciones.getString("drc_monto_base") != null)
						inputParams.setValue("drc_monto_base", rsDeducciones.getDouble("drc_monto_base"));
					else
						inputParams.setValue("drc_monto_base", 0.0);
					
					if(rsDeducciones.getString("drc_porcentaje") != null)
						inputParams.setValue("drc_porcentaje", rsDeducciones.getDouble("drc_porcentaje"));
					else
						inputParams.setValue("drc_porcentaje", 0.0);
					
					if(rsDeducciones.getString("drc_monto_descuento") != null)
						inputParams.setValue("drc_monto_descuento", rsDeducciones.getDouble("drc_monto_descuento"));
					else
						inputParams.setValue("drc_monto_descuento", 0.0);
					
					if(rsDeducciones.getString("drc_monto_descuento_ma") != null)
						inputParams.setValue("drc_monto_descuento_ma", rsDeducciones.getDouble("drc_monto_descuento_ma"));
					else
						inputParams.setValue("drc_monto_descuento_ma", 0.0);
					
					sql = getSQL(getResource("insert-requisicion_descuento.sql"),inputParams);
					Recordset rsRegistrarDescuento = db.get(sql); //registrar descuentos de requisicion_detalle.
					
					str = "-Registrando descuentos...";
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsRegistrarDescuento.getRecordCount()>0)
					{
						rc = 0; //registro de descuento exitosamente.
						str = "-Se registraron los descuentos exitosamente";
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				} //END WHILE
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarFecha_entrega(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
			this.setConnection(conn);
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sql = getSQL(getResource("select-requisicion_fecha_entrega.sql"),inputParams);
			Recordset rsFecha_entrega = db.get(sql); //consultar fecha de entrega de requisicion_detalle.
			
			str = "-Determinando si el detalle requiere fecha de entrega...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsFecha_entrega.getRecordCount()>0)
			{	
				while(rsFecha_entrega.next() != false)
				{
					if(rsFecha_entrega.getString("cfe_consecutivo") != null)
						inputParams.setValue("cfe_consecutivo", rsFecha_entrega.getInt("cfe_consecutivo"));
					else
						inputParams.setValue("cfe_consecutivo", 0);
					
					if(rsFecha_entrega.getString("cfe_cantidad") != null)
						inputParams.setValue("cfe_cantidad", rsFecha_entrega.getInt("cfe_cantidad"));
					else
						inputParams.setValue("cfe_cantidad", 0);
					
					if(rsFecha_entrega.getString("cfe_fecha_de_entrega") != null)
						inputParams.setValue("cfe_fecha_de_entrega", rsFecha_entrega.getDate("cfe_fecha_de_entrega"));
					else
						inputParams.setValue("cfe_fecha_de_entrega", null);
					
					sql = getSQL(getResource("insert-fecha_entrega.sql"),inputParams);
					Recordset rsRegistrarFecha_entrega = db.get(sql); //registrar fecha de entrega de requisicion_detalle.
					
					str = "-Registrando fecha de entrega...";
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsRegistrarFecha_entrega.getRecordCount()>0)
					{
						rc = 0; //registro de fecha de entrega exitosamente.
						str = "-Se registraron las fechas de entrega exitosamente";
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				} //END WHILE
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
}