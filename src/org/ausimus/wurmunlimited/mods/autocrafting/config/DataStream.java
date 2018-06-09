package org.ausimus.wurmunlimited.mods.autocrafting.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataStream
{
    public static void SendMatter(long ident, long data, String dir)
    {
        try
        {
            FileInputStream in = new FileInputStream(dir);
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(dir);
            props.setProperty(String.valueOf(ident), String.valueOf(data));
            props.store(out, null);
            out.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
