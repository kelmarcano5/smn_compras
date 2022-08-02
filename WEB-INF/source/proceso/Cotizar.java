package proceso;

import dinamica.*;
import java.sql.*;
import javax.sql.DataSource;

public class Cotizar extends GenericTransaction
{

	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
		
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlProducto   = getSQL(getResource("select-producto_requisicion_detalle.sql"),inputParams);
			Recordset rsProducto = db.get(sqlProducto); //CONSULTA EL PRODUCTO BIEN SEA IT,SE,AF
			
			if(rsProducto.getRecordCount()>0)
			{
				rsProducto.first();
				
				if(rsProducto.getString("producto") != null)
					inputParams.setValue("rpp_id_producto", rsProducto.getInt("producto"));
				else
					inputParams.setValue("rpp_id_producto", 0);
				
				if(rsProducto.getString("smn_requisicion_detalle_id") != null)
					inputParams.setValue("smn_requisicion_detalle_id", rsProducto.getInt("smn_requisicion_detalle_id"));
				else
					inputParams.setValue("smn_requisicion_detalle_id", 0);
				
				if(rsProducto.getString("rrs_fecha_de_requerido") != null)
					inputParams.setValue("rrs_fecha_de_requerido", rsProducto.getDate("rrs_fecha_de_requerido"));
				else
					inputParams.setValue("rrs_fecha_de_requerido", "0000-00-00");
				
				String sqlProveedores   = getSQL(getResource("select-proveedor_producto.sql"),inputParams);
				Recordset rsProveedores = db.get(sqlProveedores); //CONSULTA LOS POSIBLES PROVEEDORES DEL PRODUCTO.
				
				if(rsProveedores.getRecordCount()>0)
				{
					while(rsProveedores.next() != false)
					{
						if(rsProveedores.getString("smn_proveedor_id") != null)
							inputParams.setValue("smn_proveedor_id", rsProveedores.getInt("smn_proveedor_id"));
						else
							inputParams.setValue("smn_proveedor_id", 0);
						
						String sqlCotizacion   = getSQL(getResource("insert-cotizacion.sql"), inputParams);
						Recordset rsCotizacion = db.get(sqlCotizacion);
						
						if(rsCotizacion.getRecordCount()>0)
						{
							rsCotizacion.first();
							
							System.out.println("*cotizacion_id* = " + rsCotizacion.getInt("smn_cotizacion_id"));
							rc = 0;
						}
						else
						{
							rc = 3; //no se registro la cotizacion. 
						}
					}
				}
				else
				{
					rc = 2; //no se encontro posibles proveedores de ese producto consultado.
				}
			}
			else
			{
				rc = 1; //no se encontro el producto en la tabla smn_compras.smn_requisicion_detalle
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		finally
		{
			if(conn!=null)
				conn.close();
		}
		
		if(rc>0)
			System.out.print("** ERROR **: " + rc);
		
		return rc;
	}

}
