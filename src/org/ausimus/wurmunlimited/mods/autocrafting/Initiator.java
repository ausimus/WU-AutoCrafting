package org.ausimus.wurmunlimited.mods.autocrafting;

/*
     ___          ___          ___                     ___          ___          ___
    /\  \        /\__\        /\  \         ___       /\__\        /\__\        /\  \
   /::\  \      /:/  /       /::\  \       /\  \     /::|  |      /:/  /       /::\  \
  /:/\:\  \    /:/  /       /:/\ \  \      \:\  \   /:|:|  |     /:/  /       /:/\ \  \
 /::\~\:\  \  /:/  /  ___  _\:\~\ \  \     /::\__\ /:/|:|__|__  /:/  /  ___  _\:\~\ \  \
/:/\:\ \:\__\/:/__/  /\__\/\ \:\ \ \__\ __/:/\/__//:/ |::::\__\/:/__/  /\__\/\ \:\ \ \__\
\/__\:\/:/  /\:\  \ /:/  /\:\ \:\ \/__//\/:/  /   \/__/~~/:/  /\:\  \ /:/  /\:\ \:\ \/__/
     \::/  /  \:\  /:/  /  \:\ \:\__\  \::/__/          /:/  /  \:\  /:/  /  \:\ \:\__\
     /:/  /    \:\/:/  /    \:\/:/  /   \:\__\         /:/  /    \:\/:/  /    \:\/:/  /
    /:/  /      \::/  /      \::/  /     \/__/        /:/  /      \::/  /      \::/  /
    \/__/        \/__/        \/__/                   \/__/        \/__/        \/__/
*/

import com.wurmonline.server.TimeConstants;
import com.wurmonline.server.items.AusItemTemplateCreator;
import org.ausimus.wurmunlimited.mods.autocrafting.actions.SelectItemAction;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.ausimus.wurmunlimited.mods.autocrafting.db.DBQuarys;
import org.ausimus.wurmunlimited.mods.autocrafting.poller.pollInputs;
import org.ausimus.wurmunlimited.mods.autocrafting.poller.pollOutputs;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Initiator implements PreInitable, WurmServerMod, ItemTemplatesCreatedListener, Initable, Configurable, ServerPollListener, ServerStartedListener
{
    private long lastPollWorkBenchin;
    private long lastPollWorkBenchout;
    /**
     * @param properties properties
     */
    @Override
    public void configure(Properties properties)
    {
        AusConstants.CraftingWorkBenchTemplateID = Integer.parseInt(properties.getProperty("CraftingWorkBenchTemplateID", Integer.toString(AusConstants.CraftingWorkBenchTemplateID)));
        AusConstants.InputTemplateID = Integer.parseInt(properties.getProperty("InputTemplateID", Integer.toString(AusConstants.InputTemplateID)));
        AusConstants.OutputTemplateID = Integer.parseInt(properties.getProperty("OutputTemplateID", Integer.toString(AusConstants.OutputTemplateID)));
        AusConstants.pollInterval = Integer.parseInt(properties.getProperty("pollInterval", Integer.toString(AusConstants.pollInterval)));
        AusConstants.useWeight = Boolean.parseBoolean(properties.getProperty("useWeight", Boolean.toString(AusConstants.useWeight)));
        AusConstants.debug = Boolean.parseBoolean(properties.getProperty("debug", Boolean.toString(AusConstants.debug)));
    }

    @Override
    public void onItemTemplatesCreated()
    {
        new AusItemTemplateCreator();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void init()
    {
        // Create ausDB files if they do not already exist, these are just plain text files.
        try
        {
            File db = new File(AusConstants.dbFile);
            File logger = new File(AusConstants.logFile);
            if (!db.exists())
            {
                db.getParentFile().mkdirs();
                db.createNewFile();
            }
            if (!logger.exists())
            {
                logger.getParentFile().mkdirs();
                logger.createNewFile();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void preInit()
    {
        ModActions.init();
    }

    @Override
    public void onServerPoll()
    {
        long second = TimeConstants.SECOND_MILLIS;
        if (System.currentTimeMillis() - second * AusConstants.pollInterval > lastPollWorkBenchin)
        {
            new pollInputs();
            lastPollWorkBenchin = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - second * AusConstants.pollInterval > lastPollWorkBenchout)
        {
            new pollOutputs();
            lastPollWorkBenchout = System.currentTimeMillis();
        }
    }

    @Override
    public void onServerStarted()
    {
        try
        {
            DBQuarys.init();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        ModActions.registerAction(new SelectItemAction());
    }
}