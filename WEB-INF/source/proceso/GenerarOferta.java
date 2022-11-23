package proceso;

import dinamica.*;
import javax.sql.DataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerarOferta extends GenericTransaction
{
	
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		//String mensaje = ""; //mensaje a retornar
		int ofe_proveedor_actual_id = 0;//variable temporal con id de la oferta en proceso, se va actualizando a medida que se va procesando una oferta.
		int ofe_entidad_actual_id = 0;
		int ofe_sucursal_actual_id = 0;
		String sqlOfertasAp="";  
		
		
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sistemaOperativo = System.getProperty("os.name");
		String file;
		
		
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/log_ProcesarOrdenCompra"+fechaActual+".txt";
		else
			file = "./log_ProcesarOrdenCompra"+fechaActual+".txt";
		
		File newLogFile = new File(file);
		FileWriter fw;
		String str="";
		
		if(!newLogFile.exists())
			fw = new FileWriter(newLogFile);
		else
			fw = new FileWriter(newLogFile,true);
		
		BufferedWriter bw=new BufferedWriter(fw);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		
		//reuse superclass code
		super.service(inputParams);
		
		
		//get security datasource
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		if (jndiName==null)
		{
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");	
		}	
			

		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
			
		conn.setAutoCommit(false);
	
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			str = "Inicio proceso de ofertas"+ " \n <br><br>";
			
			str = str + "- Validando el rol del usuario, que sea comprador"+ " \n <br>";
			
			String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams);
			Recordset rsRol = db.get(sqlRol); //valida si el rol es COMPRADOR
			
			if(rsRol.getRecordCount()<1)
			{
				rc = 1;
				str = str+ "*El usuario debe ser comprador* \n <br>";
				inputParams.setValue("mensaje", "*El usuario debe ser comprador*");
				System.out.println(inputParams.getValue("mensaje"));
				bw.write(str);
				bw.flush();
				bw.newLine();
				getRequest().setAttribute("mensaje", str);
				
				return rc;
			}
			
			if (inputParams.getValue("smn_proveedor_id")==null || inputParams.getInt("smn_proveedor_id")==0)
			{
				if (inputParams.getValue("smn_sucursal_id")==null || inputParams.getInt("smn_sucursal_id")==0)
				{
					if (inputParams.getValue("smn_entidad_id")==null || inputParams.getInt("smn_entidad_id")==0)
					{
						sqlOfertasAp   = getSQL(getResource("select-ofertas.sql"),inputParams);
						 
					}else
					{
						sqlOfertasAp   = getSQL(getResource("select-ofertas-entidad.sql"),inputParams);
						
					}
				}else
				{
					sqlOfertasAp   = getSQL(getResource("select-ofertas-entidad-sucursal.sql"),inputParams);
					
				}				
			}else{
				if (inputParams.getValue("smn_sucursal_id")==null || inputParams.getInt("smn_sucursal_id")==0)
				{
					if (inputParams.getValue("smn_entidad_id")==null || inputParams.getInt("smn_entidad_id")==0)
					{
						sqlOfertasAp   = getSQL(getResource("select-ofertas-proveedor.sql"),inputParams);
						 
					}else
					{
						sqlOfertasAp   = getSQL(getResource("select-ofertas-entidad-proveedor.sql"),inputParams);
						
					}
				}else
				{
					sqlOfertasAp   = getSQL(getResource("select-ofertas-entidad-sucursal-proveedor.sql"),inputParams);
					
				}
			}
			
			str = str + "- Verificando que existen Ofertas Aprobadas para el criterio de búsqueda seleccionado. "+ " \n <br>";
			
			Recordset rsOfertasAp = db.get(sqlOfertasAp);
			
			if (rsOfertasAp.getRecordCount()>0)
			{
				str = str + "Existen registros de ofertas aprobadas"+ " \n <br>";

				
				ofe_proveedor_actual_id = 0;
				ofe_entidad_actual_id = 0;
				ofe_sucursal_actual_id = 0;
				
				while(rsOfertasAp.next() != false)
				{
					//la entidad y sucursal vienen de la requisicion
					/*String sqlEntidad   = getSQL(getResource("select-entidad.sql"),inputParams);
					Recordset rsEntidad = db.get(sqlEntidad);*/
					
					if (rsOfertasAp.getValue("smn_entidad_id")==null || rsOfertasAp.getInt("smn_entidad_id")==0)
					{						
						rc = 1;
						//inputParams.setValue("mensaje","*no se encontro la entidad*");
						//System.out.println(inputParams.getValue("mensaje"));
						str = str + "*no se encontro la entidad asociada a la oferta a la que pertenece la oferta "+rsOfertasAp.getInt("smn_oferta_id") +"*\n <br>";
						bw.write(str);
						bw.flush();
						bw.newLine();
						getRequest().setAttribute("mensaje", str);
						
						return rc;
					}	
					
					
					inputParams.setValue("smn_oferta_id", rsOfertasAp.getInt("smn_oferta_id"));
					inputParams.setValue("smn_entidad_id", rsOfertasAp.getInt("smn_entidad_id"));
					inputParams.setValue("smn_sucursal_id", rsOfertasAp.getInt("smn_sucursal_id"));
					inputParams.setValue("smn_proveedor_id", rsOfertasAp.getInt("smn_proveedor_id"));
					inputParams.setValue("smn_auxiliar_rf", rsOfertasAp.getInt("smn_auxiliar_rf"));
					
					String sqlValidar   = getSQL(getResource("select-validarCampos.sql"),inputParams);
					Recordset rsValidar = db.get(sqlValidar); //valida que los campos precios y cond. financiera no esten vacios.
					
					if(rsValidar.getRecordCount()>0)
					{
						rsValidar.first();
						str = str + "- Validando que los campos precios y cond. financiera no esten vacios. "+ " \n <br>";
						String precio_ml = rsValidar.getString("ofe_precio_ml");
								
						if(precio_ml == null)
						{
							rc = 1;
							//inputParams.setValue("mensaje", "*el precio en moneda local no debe estar vacio*");
							//System.out.println(inputParams.getValue("mensaje"));
							str = str + "*el precio en moneda local no debe estar vacio*"+ " \n <br>";
							bw.write(str);
							bw.flush();
							bw.newLine();
							getRequest().setAttribute("mensaje", str);
							
							return rc;
						}
					}
					else
					{
						rc = 1;
						//inputParams.setValue("mensaje", "*La oferta no esta aprobada*");
						//System.out.println(inputParams.getValue("mensaje"));
						str = str + "*La oferta "+ inputParams.getInt("smn_oferta_id")+" no esta aprobada*"+ " \n <br>";
						bw.write(str);
						bw.flush();
						bw.newLine();
						getRequest().setAttribute("mensaje", str);
						
						return rc;
					}
					
					String sqlOferta   = getSQL(getResource("select-oferta.sql"),inputParams);
					Recordset rsOferta = db.get(sqlOferta);
					
					str = str + "- Buscando los datos de la oferta "+ inputParams.getInt("smn_oferta_id")+" \n <br>";
					
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
							
							if(rsOferta.getString("smn_proveedor_id") != null)
							{
								inputParams.setValue("smn_proveedor_id", rsOferta.getInt("smn_proveedor_id"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no existe un proveedor relacionado con la oferta*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no existe un proveedor relacionado con la oferta "+ inputParams.getInt("smn_oferta_id")+" *\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							
							String sql = getSQL(getResource("select-cond_pago.sql"),inputParams);
							Recordset rsCond_pago = db.get(sql);
							str = str + "- Buscando la condicion de pago. \n <br>";
							
							if(rsCond_pago.getRecordCount() > 0)
							{
								rsCond_pago.first();
								if(rsCond_pago.getString("aux_cond_pago_rf")!=null)
									inputParams.setValue("smn_condicion_financiera_rf", rsCond_pago.getInt("aux_cond_pago_rf"));
								else
								{
									rc = 1;
									//inputParams.setValue("mensaje","La condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id")+" Esta vacia");
									//System.out.println(inputParams.getValue("mensaje"));
									str = str + "*La condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id")+" Esta vacia*\n <br>";
									bw.write(str);
									bw.flush();
									bw.newLine();
									getRequest().setAttribute("mensaje", str);
									return rc;
								}
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","No se encontro la condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id"));
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*No se encontro la condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id")+"*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							
							}			
							
							inputParams.setValue("ofe_aplica_anticipo", rsOferta.getString("ofe_aplica_anticipo"));
												
							if(rsOferta.getString("ofe_porcentaje_anticipo") != null)
								inputParams.setValue("ofe_porcentaje_anticipo", rsOferta.getDouble("ofe_porcentaje_anticipo"));
							else
								inputParams.setValue("ofe_porcentaje_anticipo", 0.0);
							
							//LC-se comenta esta parte del código que ya estaba aquí porque este campo no existe en la BD
							/*if(rsOferta.getString("ofe_monto_anticipo") != null)
								inputParams.setValue("ofe_monto_anticipo", rsOferta.getDouble("ofe_monto_anticipo"));
							else
								inputParams.setValue("ofe_monto_anticipo", 0.0);*/
								
							//la requisicion viene de la cotizacion, que a su vez viene de la oferta
							//por lo que se inserta en el detalle de la orden compra asociada a esa oferta
							/*String sqlRequisicion   = getSQL(getResource("select-requisicion.sql"),inputParams);
							Recordset rsRequisicion = db.get(sqlRequisicion);
							
							if(rsRequisicion.getRecordCount()>0)
							{
								rsRequisicion.first();
								inputParams.setValue("smn_requisicion_cabecera_id", rsRequisicion.getInt("smn_requisicion_cabecera_id"));
							*/
							if (rsOfertasAp.getValue("smn_requisicion_cabecera_id")!=null) 
							{	
								inputParams.setValue("smn_requisicion_cabecera_id", rsOfertasAp.getInt("smn_requisicion_cabecera_id"));
								inputParams.setValue("ocd_descripcion", "Orden de compra proveniente de la requisicion "+rsOfertasAp.getInt("req_numero"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro la requisicion relacionada a la oferta*");
								//System.out.println(inputParams.getValue("mensaje"));
								
								str = str + "*no se encontro la requisicion relacionada a la oferta*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								
								return rc;
							}
							
							
							///////////////////////////////
							String sqlDocumento   = getSQL(getResource("select-documento.sql"),inputParams);
							Recordset rsDocumento = db.get(sqlDocumento);
							str = str + "- Buscando el documento. \n <br>";
							if(rsDocumento.getRecordCount()>0) 		
							{
								rsDocumento.first();
								str = str + "  Documento "+ rsDocumento.getInt("smn_documentos_id")+" \n <br>";
								inputParams.setValue("smn_documento_id", rsDocumento.getInt("smn_documentos_id"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro el documento*");
								//System.out.println(inputParams.getValue("mensaje"));
								
								str = str + "*no se encontro el documento*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								
								return rc;
							}
							/*
							////al auxiliar se llega por la cotización que a su vez depende de cada oferta
							String sqlAuxiliar   = getSQL(getResource("select-auxiliar.sql"),inputParams);
							Recordset rsAuxiliar = db.get(sqlAuxiliar);
							
							if(rsAuxiliar.getRecordCount()>0)
							{
								rsAuxiliar.first();
								inputParams.setValue("smn_auxiliar_rf", rsAuxiliar.getInt("smn_auxiliar_rf"));
							}*/
							
							str = str + "- Verificando el Auxiliar. \n <br>";
							
							if (rsOfertasAp.getValue("smn_auxiliar_rf")!=null) 
							{
								str = str + "  Auxiliar "+ rsOfertasAp.getInt("smn_auxiliar_rf")+" \n <br>";
								inputParams.setValue("smn_auxiliar_rf", rsOfertasAp.getInt("smn_auxiliar_rf"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro el auxiliar*");
								//System.out.println(inputParams.getValue("mensaje"));
								
								str = str + "*no se encontro el auxiliar*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							///////////////////////
							
							
							String sqlForma_pago   = getSQL(getResource("select-forma_pago.sql"),inputParams);
							Recordset rsForma_pago = db.get(sqlForma_pago);
							str = str + "- Verificando la forma de pago. \n <br>";
							
							if(rsForma_pago.getRecordCount()>0)
							{
								rsForma_pago.first();
								str = str + "  Forma de pago "+ rsForma_pago.getInt("smn_forma_pago_rf")+" \n <br>";
								inputParams.setValue("smn_forma_pago_rf", rsForma_pago.getInt("smn_forma_pago_rf"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro la forma de pago*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no se encontro la forma de pago*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							
							//----------------------------------------------------------------------
							//LC - Verifica si la oferta que se está procesando corresponde al mismo 
							//proveedor de la oferta anterior, sino entonces genera una nueva Orden de compra cabecera
							if ((ofe_entidad_actual_id != inputParams.getInt("smn_entidad_id")) || (ofe_sucursal_actual_id != inputParams.getInt("smn_sucursal_id")) || (ofe_proveedor_actual_id != inputParams.getInt("smn_proveedor_id")))
							{
								
								ofe_entidad_actual_id = inputParams.getInt("smn_entidad_id");
								ofe_sucursal_actual_id = inputParams.getInt("smn_sucursal_id");
								ofe_proveedor_actual_id = inputParams.getInt("smn_proveedor_id");
								String sqlCabecera_oc    = getSQL(getResource("insert-orden_compra_cabecera.sql"),inputParams);
								Recordset rsCabecera_oc  = db.get(sqlCabecera_oc);
							
								if(rsCabecera_oc.getRecordCount()>0)
								{
									rsCabecera_oc.first();
								
									inputParams.setValue("smn_orden_compra_cabecera_id", rsCabecera_oc.getInt("smn_orden_compra_cabecera_id"));
								}
							}
								
							String sqlLinea   = getSQL(getResource("select-linea.sql"),inputParams);
							Recordset rsLinea  = db.get(sqlLinea);
							
							str = str + "- Verificando la linea. \n <br>";
							
							if(rsLinea.getRecordCount()>0)
							{
								rsLinea.first();
								str = str + "  Linea "+ rsLinea.getInt("smn_lineas_id")+" \n <br>";
								inputParams.setValue("smn_linea_id", rsLinea.getInt("smn_lineas_id"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro la linea*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no se encontro la linea*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							
							
							String sqlCantidadPedida   = getSQL(getResource("select-cantidad_pedida.sql"),inputParams);
							Recordset rsCantidadPedida  = db.get(sqlCantidadPedida);
							
							str = str + "- Buscando la cantidad pedida. \n <br>";
							if(rsCantidadPedida.getRecordCount()>0)
							{
								rsCantidadPedida.first();
								str = str + "  Cantidad "+ rsCantidadPedida.getDouble("rss_cantidad")+" \n <br>";
								inputParams.setValue("ocd_cantidad_pedida", rsCantidadPedida.getDouble("rss_cantidad"));
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro la cantidad pedida*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no se encontro la cantidad pedida*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							
							if(rsOferta.getString("ofe_cantidad") != null)
								inputParams.setValue("ofe_cantidad", rsOferta.getDouble("ofe_cantidad"));
							else
								inputParams.setValue("ofe_cantidad", 0.0);
							
							String sqlUnidad_medida   = getSQL(getResource("select-unidad_medida.sql"),inputParams);
							Recordset rsUnidad_medida  = db.get(sqlUnidad_medida);
							
							str = str + "- Verificando la unidad de medida de compra. \n <br>";
							
							if(rsUnidad_medida.getRecordCount()>0)
							{
								rsUnidad_medida.first();
								if (rsUnidad_medida.getString("smn_unidad_medida_compra_rf")!=null)
								{
									inputParams.setValue("smn_unidad_medida_rf", rsUnidad_medida.getInt("smn_unidad_medida_compra_rf"));
									str = str + "  Unidad de medida de compra "+ rsUnidad_medida.getInt("smn_unidad_medida_compra_rf")+" \n <br>";
								}else
								{
									inputParams.setValue("smn_unidad_medida_rf", 0);
									str = str + "  Unidad de medida de compra: 0 \n <br>";
								}
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro la unidad de medida*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no se encontro la unidad de medida*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							
							str = str + "- Verificando el precio de la oferta. \n <br>";
							
							if(rsOferta.getString("ofe_precio_ml") != null)
							{
								inputParams.setValue("ofe_precio_ml", rsOferta.getDouble("ofe_precio_ml"));
								str = str + "  Precio de la oferta: "+ rsOferta.getDouble("ofe_precio_ml")+" \n <br>";
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro el precio de la oferta*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no se encontro el precio de la oferta*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
							}
							
							str = str + "- Verificando el monto de la oferta. \n <br>";
							if(rsOferta.getString("ofe_monto_ml") != null)
							{
								inputParams.setValue("ofe_monto_ml", rsOferta.getDouble("ofe_monto_ml"));
								str = str + "  Monto de la oferta: "+ rsOferta.getDouble("ofe_monto_ml")+" \n <br>";
							}
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se encontro el monto de la oferta*");
								//System.out.println(inputParams.getValue("mensaje"));
								str = str + "*no se encontro el monto de la oferta*\n <br>";
								bw.write(str);
								bw.flush();
								bw.newLine();
								getRequest().setAttribute("mensaje", str);
								return rc;
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
							
							String sql_ocd    = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
							Recordset rs_ocd  = db.get(sql_ocd); //registrar orden de compra detalle
							
							if(rs_ocd.getRecordCount()>0)
							{
								
								rs_ocd.first();
								str = str + "  Se inserta un nuevo detalle de orden de compra, con id: "+ rs_ocd.getInt("smn_orden_compra_detalle_id")+" \n <br>";
								inputParams.setValue("smn_orden_compra_detalle_id", rs_ocd.getInt("smn_orden_compra_detalle_id"));
								
								String sqlOferta_fecha    = getSQL(getResource("select-oferta_fecha_entrega.sql"),inputParams);
								Recordset rsOferta_fecha  = db.get(sqlOferta_fecha); //consulta la fecha de entrega de la oferta
								str = str + "- Consultamos la fecha de entrega de la oferta. \n <br>";
								
								if(rsOferta_fecha.getRecordCount()>0)
								{
									rsOferta_fecha.first();
									//INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE LA TABLA smn_compras.smn_oferta_f_entrega
									
									if(rsOferta_fecha.getString("ofe_consecutivo") != null)
									{
										inputParams.setValue("ofe_consecutivo", rsOferta_fecha.getInt("ofe_consecutivo"));
										str = str + "  Consecutivo de la fecha_entrega de la oferta: "+ rsOferta_fecha.getInt("ofe_consecutivo")+" \n <br>";
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*el consecutivo de la fecha_entrega no se encontro*");
										//System.out.println(inputParams.getValue("mensaje"));
										str = str + "*el consecutivo de la fecha_entrega no se encontro*\n <br>";
										bw.write(str);
										bw.flush();
										bw.newLine();
										getRequest().setAttribute("mensaje", str);
										return rc;
									}
									
									if(rsOferta_fecha.getString("ofe_cantidad") != null)
									{
										inputParams.setValue("ofe_cantidad", rsOferta_fecha.getDouble("ofe_cantidad"));
										str = str + "  Cantidad de la fecha_entrega de la oferta: "+ rsOferta_fecha.getDouble("ofe_cantidad")+" \n <br>";
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*la cantidad de la fecha_entrega no se encontro*");
										//System.out.println(inputParams.getValue("mensaje"));
										str = str + "*la cantidad de la fecha_entrega no se encontro*\n <br>";
										bw.write(str);
										bw.flush();
										bw.newLine();
										getRequest().setAttribute("mensaje", str);
										return rc;
									}
									
									if(rsOferta_fecha.getString("ofe_fecha_entrega") != null)
									{
										inputParams.setValue("ofe_fecha_entrega", rsOferta_fecha.getDate("ofe_fecha_entrega"));
										str = str + "  Fecha_entrega de la oferta: "+ rsOferta_fecha.getDate("ofe_fecha_entrega")+" \n <br>";
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*la fecha_entrega no se encontro*");
										//System.out.println(inputParams.getValue("mensaje"));
										str = str + "*la fecha_entrega no se encontro*\n <br>";
										bw.write(str);
										bw.flush();
										bw.newLine();
										getRequest().setAttribute("mensaje", str);
										return rc;
									}
									
									if(rsOferta_fecha.getString("ofe_status") != null)
									{
										inputParams.setValue("ofe_estatus", rsOferta_fecha.getString("ofe_status"));
										str = str + "  Estatus de Fecha_entrega de la oferta: "+ rsOferta_fecha.getString("ofe_status")+" \n <br>";
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*el estatus de la fecha_entrega no se encontro*");
										//System.out.println(inputParams.getValue("mensaje"));
										str = str + "*el estatus de la fecha_entrega no se encontro*\n <br>";
										bw.write(str);
										bw.flush();
										bw.newLine();
										getRequest().setAttribute("mensaje", str);
										return rc;
									}
									
									//---------------------------------------------------------------------------------------
									
									String sqlFecha_ocd    = getSQL(getResource("insert-fecha_entrega_ocd.sql"),inputParams);
									Recordset rsFecha_ocd  = db.get(sqlFecha_ocd); //registra la fecha de entrega de la orden de compra detalle
									
									str = str + "- Registramos la fecha de entrega de la orden de compra detalle. \n <br>";
									
									if(rsFecha_ocd.getRecordCount()>0)
									{
										rc = 0;
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*no se registro la fecha de entrega*");
										str = str + "*no se registro la fecha de entrega*\n <br>";
									}
								}
									
								sql_ocd  = getSQL(getResource("select-ocd.sql"),inputParams);
								rs_ocd   = db.get(sql_ocd); //consulta el detalle de la orden de compra.
								str = str + "- Consultamos el detalle de la orden de compra detalle. \n <br>";
								
								double monto_base = 0.0;
										
								if(rs_ocd.getRecordCount()>0)
								{
									rs_ocd.first();
											
									if(rs_ocd.getString("ocd_monto_bruto_ml") != null)
									{
										inputParams.setValue("oci_monto_base_ml", rs_ocd.getDouble("ocd_monto_bruto_ml"));
										monto_base = rs_ocd.getDouble("ocd_monto_bruto_ml");
										
										str = str + "Monto Bruto: "+ rs_ocd.getDouble("ocd_monto_bruto_ml") +" \n <br>";
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*el monto no se encontro*");
										//System.out.println(inputParams.getValue("mensaje"));
										str = str + "*el monto del detalle no se encontro*\n <br>";
										bw.write(str);
										bw.flush();
										bw.newLine();
										getRequest().setAttribute("mensaje", str);
										return rc;
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
									rc = 1;
									//inputParams.setValue("mensaje","*ocurrio un error, no se encontro el detalle creado*");
									//System.out.println(inputParams.getValue("mensaje"));
									str = str + "ocurrio un error, no se encontro el detalle creado\n <br>";
									bw.write(str);
									bw.flush();
									bw.newLine();
									getRequest().setAttribute("mensaje", str);
									return rc;
								}			
										
								String sqlCodImpuesto    = getSQL(getResource("select-cod_impuesto.sql"),inputParams);
								Recordset rsCodImpuesto  = db.get(sqlCodImpuesto); //consulta el impuesto deduccion oferta.
								
								str = str + "- Consultamos el impuesto deduccion oferta. \n <br>";
								if(rsCodImpuesto.getRecordCount()>0)
								{	
									double porcentaje_base = 0.0;
									
									while(rsCodImpuesto.next() != false)
									{
										if(rsCodImpuesto.getString("smn_codigos_impuestos_id") != null)
										{
											inputParams.setValue("smn_cod_impuesto_deduc_rf", rsCodImpuesto.getInt("smn_codigo_impuesto_rf"));
											str = str + "Impuesto deduccion oferta: "+rsCodImpuesto.getInt("smn_codigo_impuesto_rf")+" \n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*el codigo de impuesto deduccion esta vacio*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "el codigo de impuesto deduccion esta vacio\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										
										
										if(rsCodImpuesto.getString("imp_porcentaje_base") != null)
										{
											inputParams.setValue("imp_porcentaje_base", rsCodImpuesto.getDouble("imp_porcentaje_base"));
											porcentaje_base = rsCodImpuesto.getDouble("imp_porcentaje_base");
											str = str + "Porcentaje Base: "+rsCodImpuesto.getDouble("imp_porcentaje_base")+" \n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*el porcentaje base no se encontro*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*el porcentaje base no se encontro*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										if(rsCodImpuesto.getString("imp_ui_sustraendo") != null)
											inputParams.setValue("imp_ui_sustraendo", rsCodImpuesto.getDouble("imp_ui_sustraendo"));
										else
											inputParams.setValue("imp_ui_sustraendo", 0.0);
										
										if(rsCodImpuesto.getString("imp_tipo_codigo") != null)
										{
											inputParams.setValue("imp_tipo_codigo", rsCodImpuesto.getString("imp_tipo_codigo"));
											str = str + "Tipo de Codigo: "+rsCodImpuesto.getString("imp_tipo_codigo")+" \n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*El tipo de codigo no se encontro*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*El tipo de codigo no se encontro*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										
										String sqlTasa   = getSQL(getResource("select-tasa.sql"),inputParams);
										Recordset rsTasa = db.get(sqlTasa); //consulta la tasa de cambio.
										
										double tasa;
										str = str + "- Consultamos la tasa de cambio. \n <br>";
										
										if(rsTasa.getRecordCount()>0)
										{
											rsTasa.first();
											tasa = rsTasa.getDouble("tca_tasa_cambio");
											inputParams.setValue("oci_monto_impuesto_ma",(monto_base*porcentaje_base)/tasa);
											str = str + "Tasa de cambio: "+rsTasa.getDouble("tca_tasa_cambio")+"\n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*ocurrio un error, no se encontro la tasa de cambio*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*ocurrio un error, no se encontro la tasa de cambio*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										inputParams.setValue("oci_monto_impuesto_ml",monto_base*porcentaje_base);
										
										String sqlImpuesto   = getSQL(getResource("insert-orden_compra_impuesto.sql"),inputParams);
										Recordset rsImpuesto = db.get(sqlImpuesto);
										
										str = str + "Se hace el registro de los impuestos de la orden de compra \n <br>";
										if(rsImpuesto.getRecordCount()<1)
										{
											rc = 1;
											//inputParams.setValue("mensaje","*ocurrio un error, al registrar los impuestos de la orden de compra*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*ocurrio un error, al registrar los impuestos de la orden de compra*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
									} //endwhile
								}		
										
								String sqlDescuento_oferta   = getSQL(getResource("select-descuento_oferta.sql"),inputParams);
								Recordset rsDescuento_oferta = db.get(sqlDescuento_oferta); //consulta los descuentos de la oferta
								
								str = str + "- Se consultan los descuentos de la oferta \n <br>";
								if(rsDescuento_oferta.getRecordCount()>0)
								{
									while(rsDescuento_oferta.next() != false)
									{
										if(rsDescuento_oferta.getString("rio_cod_descuento") != null)
										{
											inputParams.setValue("smn_codigo_descuento_rf", rsDescuento_oferta.getInt("rio_cod_descuento"));
											str = str + "Descuento: "+rsDescuento_oferta.getInt("rio_cod_descuento")+"\n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*El codigo del descuento no se encontro*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*El codigo del descuento no se encontro*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										if(rsDescuento_oferta.getString("rio_monto_base_ml") != null)
										{
											inputParams.setValue("ocd_monto_base", rsDescuento_oferta.getDouble("rio_monto_base_ml"));
											str = str + "Monto Base ML: "+rsDescuento_oferta.getInt("rio_cod_descuento")+"\n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*El monto base no se encontro*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*El monto base no se encontro*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										if(rsDescuento_oferta.getString("rio_porcentaje_descuento") != null)
										{
											inputParams.setValue("ocd_porcentaje", rsDescuento_oferta.getDouble("rio_porcentaje_descuento"));
											str = str + "Porcentaje de Descuento: "+rsDescuento_oferta.getInt("rio_cod_descuento")+"\n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*El porcentaje del descuento no se encontro*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*El porcentaje del descuento no se encontro*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										if(rsDescuento_oferta.getString("rio_monto_descuento_ml") != null)
										{
											inputParams.setValue("ocd_monto_descuento", rsDescuento_oferta.getDouble("rio_monto_descuento_ml"));
											str = str + "Monto Descuento ML: "+rsDescuento_oferta.getInt("rio_cod_descuento")+"\n <br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*El monto del descuento no se encontro*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*El monto del descuento no se encontro*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
										
										if(rsDescuento_oferta.getString("rio_moneda_rf") != null)
											inputParams.setValue("rio_moneda_rf", rsDescuento_oferta.getInt("rio_moneda_rf"));
										else
											inputParams.setValue("rio_moneda_rf", 0);
										
										if(rsDescuento_oferta.getString("rio_tasa_rf") != null)
											inputParams.setValue("rio_tasa_rf", rsDescuento_oferta.getInt("rio_tasa_rf"));
										else
											inputParams.setValue("rio_tasa_rf", 0);
										
										if(rsDescuento_oferta.getString("rio_monto_base_ma") != null)
											inputParams.setValue("ocd_monto_base_ma", rsDescuento_oferta.getDouble("rio_monto_base_ma"));
										else
											inputParams.setValue("ocd_monto_base_ma", 0.0);
										
										if(rsDescuento_oferta.getString("rio_monto_descuento_ma") != null)
											inputParams.setValue("ocd_monto_descuento_ma", rsDescuento_oferta.getDouble("rio_monto_descuento_ma"));
										else
											inputParams.setValue("ocd_monto_descuento_ma", 0.0);
										
										String sqlDescuento_oc   = getSQL(getResource("insert-descuento_oc.sql"),inputParams);
										Recordset rsDescuento_oc = db.get(sqlDescuento_oc); //Se insertan los descuentos de la oferta
										
										str = str + "- Se insertan los descuentos de la oferta \n <br>";
										if(rsDescuento_oc.getRecordCount()<1)
										{
											rc = 1;
											//inputParams.setValue("mensaje","*Error al registrar el descuento de la orden de compra*");
											//System.out.println(inputParams.getValue("mensaje"));
											str = str + "*Error al registrar el descuento de la orden de compra*\n <br>";
											bw.write(str);
											bw.flush();
											bw.newLine();
											getRequest().setAttribute("mensaje", str);
											return rc;
										}
									}//end while
								}		
										
								String sqlImpuestos   = getSQL(getResource("select-impuestos.sql"),inputParams);
								Recordset rsImpuestos = db.get(sqlImpuestos); //consulta los impuestos
								
								str = str + "- Se hace calculan totales por impuestos de la Orden de Compra \n <br>";
								
								if(rsImpuestos.getRecordCount()>0)
								{
									rsImpuestos.first();
									
									if(rsImpuestos.getString("monto_impuesto_ml") != null)
									{
										inputParams.setValue("oci_monto_impuesto_ml", rsImpuestos.getDouble("monto_impuesto_ml"));
										str = str + "Monto Impuesto ML: "+ rsImpuestos.getDouble("monto_impuesto_ml") +"\n <br>";
									}
									else
									{
										inputParams.setValue("oci_monto_impuesto_ml", 0.0);
										str = str + "Monto Impuesto ML: 0 \n <br>";
									}
									if(rsImpuestos.getString("monto_impuesto_ma") != null)
									{
										inputParams.setValue("oci_monto_impuesto_ma", rsImpuestos.getDouble("monto_impuesto_ma"));
										str = str + "Monto Impuesto MA: "+ rsImpuestos.getDouble("monto_impuesto_ma")+"\n <br>";
									}
									else
									{	
										inputParams.setValue("oci_monto_impuesto_ma", 0.0);
										str = str + "Monto Impuesto MA: 0 \n <br>";
									}								
								}
								
								String sqlDescuentos   = getSQL(getResource("select-descuentos.sql"),inputParams);
								Recordset rsDescuentos = db.get(sqlDescuentos); //consulta los descuentos
								
								str = str + "- Se hace calculan los totales por descuento de la Orden de Compra \n <br>";
								if(rsDescuentos.getRecordCount()>0)
								{
									rsDescuentos.first();
									
									if(rsDescuentos.getString("monto_descuento_ml") != null)
									{
										inputParams.setValue("ocd_monto_descuento_ml", rsDescuentos.getDouble("monto_descuento_ml"));
										str = str + "Monto Descuento ML: "+ rsDescuentos.getDouble("monto_descuento_ml") +"\n <br>";
									}
									else
									{	
										inputParams.setValue("ocd_monto_descuento_ml", 0.0);
										str = str + "Monto Descuento ML: 0 \n <br>";
									}
									
									if(rsDescuentos.getString("monto_descuento_ma") != null)
									{	
										inputParams.setValue("ocd_monto_descuento_ma", rsDescuentos.getDouble("monto_descuento_ma"));
										str = str + "Monto Descuento MA: "+ rsDescuentos.getDouble("monto_descuento_ma") +"\n <br>";
									}
									else
									{
										inputParams.setValue("ocd_monto_descuento_ma", 0.0);
										str = str + "Monto Descuento MA: 0 \n <br>";
									}
								}		
										
								double monto_neto_ml = inputParams.getDouble("ofe_monto_ml")+inputParams.getDouble("oci_monto_impuesto_ml")-inputParams.getDouble("ocd_monto_descuento_ml");
								double monto_neto_ma = inputParams.getDouble("ofe_monto_ma")+inputParams.getDouble("oci_monto_impuesto_ma")-inputParams.getDouble("ocd_monto_descuento_ma");
								
								inputParams.setValue("ocd_monto_neto_ml", monto_neto_ml);
								inputParams.setValue("ocd_monto_neto_ma", monto_neto_ma);
								
								String sqlActualizar_ocd   = getSQL(getResource("update-actualizar_ocd.sql"),inputParams);
								Recordset rsActualizar_ocd = db.get(sqlActualizar_ocd); //Actualiza los campos calculados del detalle de la orden de compra
								
								if(rsActualizar_ocd.getRecordCount()>0)
								{
									str = str + "Se actualizaron los totales del detalle de la orden de compra \n <br>";
									
									String sqlActualizar_occ   = getSQL(getResource("update-actualizar_occ.sql"),inputParams);
									Recordset rsActualizar_occ = db.get(sqlActualizar_occ); //Actualiza los campos calculados de la orden de compra
									
								
									if(rsActualizar_occ.getRecordCount()>0)
									{
										str = str + "Se actualizaron los totales de la orden de compra cabecera\n <br>";
										String sqlUpdate = getSQL(getResource("update-oferta.sql"),inputParams);
										Recordset rsUpdate = db.get(sqlUpdate); //actualiza el estatus de la oferta a generada.
										
										
										if(rsUpdate.getRecordCount()>0)
										{
											rc = 0;
											//inputParams.setValue("mensaje","*Oferta Procesada exitosamente*");
											str = str + "<br>*Oferta Procesada exitosamente* \n<br>";
										}
										else
										{
											rc = 1;
											//inputParams.setValue("mensaje","*ocurrio un error al generar la oferta*");
											str = str + "*ocurrio un error al procesar la oferta* \n <br>";
											
										}	
									}
									else
									{
										rc = 1;
										//inputParams.setValue("mensaje","*ocurrio un error al actualizar los campos calculados de la orden de compra*");
										str = str + "*ocurrio un error al actualizar los campos calculados de la orden de compra* \n <br>";
									}
								}
								else
								{
									rc = 1;
									//inputParams.setValue("mensaje","*ocurrio un error al actualizar los campos calculados de la orden de compra detalle*");
									str = str + "*ocurrio un error al actualizar los campos calculados de la orden de compra detalle* \n <br>";
								}		
							}									
							else
							{
								rc = 1;
								//inputParams.setValue("mensaje","*no se registro el detalle de la orden de compra*");
								str = str + "*no se registro el detalle de la orden de compra* \n <br>";
							}							
					
					}
					else
					{
						rc = 1;
						//inputParams.setValue("mensaje","*no se encontro la oferta*");
						str = str + "<br>*no se encontro la oferta* \n <br>";
					}
					
				}//endwhile	
			}
			else
			{				
				rc = 1;
				//inputParams.setValue("mensaje","*no se encontraron ofertas aprobadas*");
				str = str + "<br>*no se encontraron ofertas aprobadas* \n <br>";
			
			}
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		
		finally
		{		
			if(rc==0)
			{	
				conn.commit();
				str = str + "<br>*Se genero correctamente la orden de compra*\n<br>";
				bw.write(str);
				bw.flush();
				bw.newLine();
				getRequest().setAttribute("mensaje", str);
			}
			else
			{
				conn.rollback();
				str = str + "*No se pudo procesar la(s) orden(es) de compra*\n <br>";
				bw.write(str);
				bw.flush();
				bw.newLine();
				getRequest().setAttribute("mensaje", str);
			}
			
			if(conn!=null)
				conn.close();
		}
		
		/*if(rc>0)
				//System.out.println("** ERROR **: " + inputParams.getValue("mensaje"));
			str = str + "*Error al registrar el descuento de la orden de compra*\n <br>";
			bw.write(str);
			bw.flush();
			bw.newLine();
			getRequest().setAttribute("mensaje", str);
		*/
		return rc;
	}
}
