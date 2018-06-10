package org.ausimus.wurmunlimited.mods.autocrafting.db;

import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DBInit
{
    private static boolean initialized = false;
    private static Connection connection;
    static void init()
    {
        if (initialized)
        {
            return;
        }
        initialized = true;
        connection = AusDBSupport.getAusDB();
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try
            {
                connection.close();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                AusLogger.WriteLog(ex.getMessage(), AusConstants.logFile);
            }
        }));
    }

    @SuppressWarnings("SameParameterValue")
    static void execSQL(String statement) throws SQLException
    {
        try (PreparedStatement ps = connection.prepareStatement(statement))
        {
            ps.execute();
        }
    }

    static Connection getConnection()
    {
        return connection;
    }
}