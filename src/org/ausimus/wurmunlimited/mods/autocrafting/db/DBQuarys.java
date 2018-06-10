package org.ausimus.wurmunlimited.mods.autocrafting.db;

import com.wurmonline.server.utils.DbUtilities;

import java.sql.*;

public class DBQuarys
{
    private static Connection connection;
    public static void init() throws SQLException
    {
        DBInit.init();
        connection = DBInit.getConnection();
        DBInit.execSQL("CREATE TABLE IF NOT EXISTS WORKBENCHES (WurmID LONG NOT NULL, Stored_Matter LONG NOT NULL, PRIMARY KEY(WurmID))");
    }

    public static void setStoredMatter(long wurmID, long value) throws SQLException
    {
        try (PreparedStatement st = connection.prepareStatement("INSERT OR REPLACE INTO WORKBENCHES (WurmID, Stored_Matter) VALUES (?,?)"))
        {
            st.setLong(1, wurmID);
            st.setLong(2, value);
            st.execute();
        }
    }

    public static long getStoredMatter(long itemID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        int storedMatter = 0;
        try
        {
            Connection dbcon = AusDBSupport.getAusDB();
            stmt = dbcon.createStatement();
            rs = stmt.executeQuery("select * from WORKBENCHES where WURMID = " + itemID);
            if (rs.next())
            {
                storedMatter = rs.getInt("Stored_Matter");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DbUtilities.closeDatabaseObjects(stmt, rs);
        }
        return storedMatter;
    }
}