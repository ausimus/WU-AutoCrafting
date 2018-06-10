package org.ausimus.wurmunlimited.mods.autocrafting.db;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusLogger;
import java.sql.*;
class AusDBSupport
{
    private static final String LITE_DB_DRIVER = "org.sqlite.JDBC";

    private static String getDbConnectionString()
    {
        return "jdbc:sqlite:./mods/AutoCrafting/AutoCrafting.db";
    }

    static Connection getAusDB()
    {
        String dbConnection = getDbConnectionString();
        try
        {
            Class.forName(LITE_DB_DRIVER);
            return DriverManager.getConnection(dbConnection);
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            AusLogger.WriteLog(ex.getMessage(), AusConstants.logFile);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}