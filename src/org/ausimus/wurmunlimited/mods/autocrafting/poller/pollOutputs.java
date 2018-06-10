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
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.ausimus.wurmunlimited.mods.autocrafting.db.DBQuarys;

public class pollOutputs
{
    public pollOutputs()
    {
        try
        {
            for (Item item : Items.getAllItems())
            {
                if (item.getTemplateId() == AusConstants.OutputTemplateID)
                {
                    Item parent = Items.getItem(item.getTopParent());
                    Item[] subItems = item.getAllItems(true);
                    if (subItems.length <= 99)
                    {
                        if (parent.getData1() != -1)
                        {
                            Item toCreate = ItemFactory.createItem(parent.getData1(), parent.getQualityLevel(), null);
                            boolean hasMatter;
                            long value;
                            if (AusConstants.useWeight)
                            {
                                hasMatter = DBQuarys.getStoredMatter(parent.getWurmId()) >= toCreate.getWeightGrams();
                                value = DBQuarys.getStoredMatter(parent.getWurmId()) - toCreate.getWeightGrams();

                            }
                            else
                            {
                                hasMatter = DBQuarys.getStoredMatter(parent.getWurmId()) >= toCreate.getValue();
                                value = DBQuarys.getStoredMatter(parent.getWurmId()) - toCreate.getValue();
                            }
                            if (hasMatter)
                            {
                                item.insertItem(toCreate);
                                DBQuarys.setStoredMatter(parent.getWurmId(), value);
                                parent.setName(parent.getTemplate().getName() + " [" + String.valueOf(value) + "]");
                                parent.updateName();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}