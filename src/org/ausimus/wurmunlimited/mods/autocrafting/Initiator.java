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
import org.gotti.wurmunlimited.modloader.interfaces.*;

import java.util.Properties;

public class Initiator implements WurmServerMod, ItemTemplatesCreatedListener, Initable, Configurable, ServerPollListener{
    /**
     * @param properties properties
     */
    @Override
    public void configure(Properties properties) {

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

    // Timed logic depends on this.
    @Override
    public void onServerPoll() {

    }


}
