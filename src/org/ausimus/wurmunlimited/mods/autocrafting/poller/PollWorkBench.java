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

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.Items;
import com.wurmonline.server.Server;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.utils.DbUtilities;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PollWorkBench {
    public PollWorkBench() {
        try {
            Item[] itemWB = Items.getAllItems();
            for (Item iwb : itemWB) {
                if (iwb.getTemplateId() == AusConstants.CraftingWorkBenchTemplateID) {
                    Item[] itemInputs = iwb.getAllItems(false);
                    for (Item input : itemInputs) {
                        if (input.getTemplateId() == AusConstants.InputTemplateID) {
                            Item[] itemInternal = input.getAllItems(false);
                            for (Item internal : itemInternal) {
                                if (internal.getTemplateId() > 0) {
                                    iwb.setData1(internal.getWeightGrams());
                                    Items.destroyItem(internal.getWurmId());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Server.getInstance().broadCastAlert("Poll Ran");
    }

    /**
     * @param item The item being called, usually derived from {@link ItemList} for this were using {@link AusConstants} as its a custom Item.
     * @return getNumItems.
     */
    @SuppressWarnings({"unused"})
    private int getWorkBenchItems(int item) {
        Statement stmt = null;
        ResultSet rs = null;
        int getNumItems = 0;
        try {
            Connection dbcon = DbConnector.getItemDbCon();
            stmt = dbcon.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM ITEMS WHERE (TEMPLATEID) = " + item);
            if (rs.next()) {
                getNumItems = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Server.getInstance().broadCastAlert("Failed to count items:" + ex.getMessage());
        } finally {
            DbUtilities.closeDatabaseObjects(stmt, rs);
        }
        return getNumItems;
    }
}
