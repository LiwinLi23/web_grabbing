package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mysql.jdbc.Statement;

import my.function.mylog;

public class mysql {
	
	//返回自增id
	public static int insert(String sdatabase,String stable,Map rmap)
	{
		Connection con=C3P0Factory.getC3P0Connection(sdatabase);
		if (con!=null)
		{
			String head="";
			String value="";
			for (Object o : rmap.keySet()) 
	    	{
				 if (head.length()==0) 
				 {
					 head=o.toString(); 
					 value="?";
				 }
				 else
				 {
					 head=head+","+o.toString();
					 value=value=value+",?";
				 }	 
	    	}
			
			String sSql="insert into "+stable+"("+head+")values("+value+")";
			
			PreparedStatement pstmt=null;
			try {
				pstmt = con.prepareStatement(sSql,Statement.RETURN_GENERATED_KEYS) ;
				int i=1;
				for (Object o : rmap.keySet()) 
		    	{
					pstmt.setObject(i,rmap.get(o));
					i++;
		    	}
				
				int t = pstmt.executeUpdate() ; // 执行更新 
				if (t>0)
				{
					int iid = -1;  
					ResultSet rs = pstmt.getGeneratedKeys(); //获取结果     
					if (rs.next()) {
						iid = rs.getInt(1);//取得ID 
						return iid;
					} 
					else
					{
						return 1;
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mylog.ErrStackTrace("mysql",e);
				return -1;
			}
			finally
			{
				if (pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
				
			}
		}
		return 0;
	}

}
