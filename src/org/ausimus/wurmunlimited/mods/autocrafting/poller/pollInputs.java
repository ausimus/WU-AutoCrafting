package org.ausimus.wurmunlimited.mods.autocrafting.poller;

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

import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.items.Item;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusLogger;
import org.ausimus.wurmunlimited.mods.autocrafting.db.DBQuarys;

import java.sql.SQLException;

public class pollInputs
{
    public pollInputs()
    {
        Item[] items = Items.getAllItems();
        for (Item item : items)
        {
            if (item.getTemplateId() == AusConstants.InputTemplateID)
            {
                Item[] itemsInsideInput = item.getAllItems(true);
                {
                    for (Item anItemsInsideInput : itemsInsideInput)
                    {
                        try
                        {
                            Item parent = Items.getItem(anItemsInsideInput.getTopParent());
                            try
                            {
                                DBQuarys.setStoredMatter(parent.getWurmId(), anItemsInsideInput.getWeightGrams() + DBQuarys.getStoredMatter(parent.getWurmId()));
                                parent.setName(parent.getTemplate().getName() + " [" + String.valueOf(DBQuarys.getStoredMatter(parent.getWurmId())) + "]");
                                parent.updateName();
                                // Destroy item last, always last.
                                Items.destroyItem(anItemsInsideInput.getWurmId());
                            }
                            catch (SQLException ex)
                            {
                                ex.printStackTrace();
                                AusLogger.WriteLog(ex.getMessage(), AusConstants.logFile);
                            }
                        }
                        catch (NoSuchItemException ex)
                        {
                            ex.printStackTrace();
                            AusLogger.WriteLog(ex.getMessage(), AusConstants.logFile);
                        }
                    }
                }
            }
        }
    }
}