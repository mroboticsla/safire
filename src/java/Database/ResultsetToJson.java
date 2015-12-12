package Database;

import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.sql.ResultSetMetaData;

public class ResultsetToJson {
    public ResultsetToJson() {
        super();
    }
    
    public static JSONArray convert( ResultSet rs )
    throws SQLException, Exception
    {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();
            
            for (int i=1; i<numColumns+1; i++) {
                String type = "Generic";
                String column_name = rsmd.getColumnName(i);
                
                if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                    obj.put(column_name, rs.getArray(column_name));
                    type = "Array";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    obj.put(column_name, rs.getString(column_name));
                    type = "BigInt";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    obj.put(column_name, rs.getBoolean(column_name));
                    type = "Bool";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    obj.put(column_name, rs.getBlob(column_name));
                    type = "Blob";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    obj.put(column_name, rs.getString(column_name)); 
                    type = "Double";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    obj.put(column_name, rs.getString(column_name));
                    type = "Float";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    obj.put(column_name, rs.getString(column_name));
                    type = "Int";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    obj.put(column_name,  convert(rs.getString(column_name)));
                    type = "NVarchar";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                    obj.put(column_name,  convert(rs.getString(column_name)));
                    type = "Varchar";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    obj.put(column_name, rs.getString(column_name));
                    type = "TinyInt";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    obj.put(column_name, rs.getString(column_name));
                    type = "SmallInt";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    obj.put(column_name, rs.getString(column_name));
                    type = "Date";
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    obj.put(column_name, rs.getString(column_name));
                    type = "TimeStamp";
                }
                else{
                    obj.put(column_name, rs.getObject(column_name));
                }
                //System.out.println(column_name + ": " + "(" + type + ")" +  rs.getObject(column_name));
            }
            json.add(obj);
        }
        
        return json;
    }
    
    public static String convert(String str)
    {
        if (str == null) str = "";
        StringBuffer ostr = new StringBuffer();
        for(int i=0; i<str.length(); i++) 
        {
            char ch = str.charAt(i);
            if ((ch >= 0x0020) && (ch <= 0x007e))   // Does the char need to be converted to unicode? 
            {
                    ostr.append(ch);                                        // No.
            } else                                                                  // Yes.
            {
            ostr.append("\\" + "u") ;                            // standard unicode format.
                String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);       // Get hex value of the char. 
                for(int j=0; j<4-hex.length(); j++)     // Prepend zeros because unicode requires 4 digits
                        ostr.append("0");
                ostr.append(hex.toLowerCase());         // standard unicode format.
                //ostr.append(hex.toLowerCase(Locale.ENGLISH));
            }
        }
        //System.out.println(ostr);
        return (new String(ostr));              //Return the stringbuffer cast as a string.
    }
}
