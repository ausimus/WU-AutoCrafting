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
import com.wurmonline.server.items.ItemList;
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
                    Item toCreate = ItemFactory.createItem(ItemList.stoneBrick, parent.getQualityLevel(), null);
                    if (DBQuarys.getStoredMatter(parent.getWurmId()) >= toCreate.getWeightGrams())
                    {
                        long value = DBQuarys.getStoredMatter(parent.getWurmId()) - toCreate.getWeightGrams();
                        item.insertItem(toCreate);
                        DBQuarys.setStoredMatter(parent.getWurmId(), value);
                        parent.setName(parent.getTemplate().getName() + " [" + String.valueOf(value) + "]");
                        parent.updateName();
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