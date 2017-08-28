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

import com.wurmonline.server.items.AusItemTemplateCreator;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.ausimus.wurmunlimited.mods.autocrafting.poller.PollWorkBench;
import org.gotti.wurmunlimited.modloader.interfaces.*;

import java.util.Properties;

public class Initiator implements WurmServerMod, ItemTemplatesCreatedListener, Initable, Configurable, ServerPollListener{

    private long lastPollWorkBench = 0;

    /**
     * @param properties properties
     */
    @Override
    public void configure(Properties properties) {
        AusConstants.CraftingWorkBenchTemplateID = Integer.parseInt(properties.getProperty("CraftingWorkBenchTemplateID", Integer.toString(AusConstants.CraftingWorkBenchTemplateID)));
        AusConstants.InputTemplateID = Integer.parseInt(properties.getProperty("InputTemplateID", Integer.toString(AusConstants.InputTemplateID)));
        AusConstants.OutputTemplateID = Integer.parseInt(properties.getProperty("OutputTemplateID", Integer.toString(AusConstants.OutputTemplateID)));
        AusConstants.pollInterval = Integer.parseInt(properties.getProperty("pollInterval", Integer.toString(AusConstants.pollInterval)));
        AusConstants.debug = Boolean.parseBoolean(properties.getProperty("debug", Boolean.toString(AusConstants.debug)));
    }

    @Override
    public void onItemTemplatesCreated() {
        new AusItemTemplateCreator();
    }

    @Override
    public void init() {

    }

    @Override
    public void preInit() {

    }

    @Override
    public void onServerPoll() {
        long second = 1000L;
        if (System.currentTimeMillis() - second * AusConstants.pollInterval > lastPollWorkBench)
        {
            new PollWorkBench();
            lastPollWorkBench = System.currentTimeMillis();
        }
    }
}
