package com.wurmonline.server.items;

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

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.TimeConstants;
import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.combat.CombatConstants;
import com.wurmonline.server.economy.MonetaryConstants;
import com.wurmonline.shared.constants.IconConstants;
import com.wurmonline.shared.constants.ItemMaterials;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;

import java.io.IOException;

import static org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants.*;

public class AusItemTemplateCreator
{
    public AusItemTemplateCreator()
    {
        try
        {
            // Machine
            ItemTemplateCreator.createItemTemplate(
                    AusConstants.CraftingWorkBenchTemplateID,
                    "Auto WorkBench",
                    "WorkBenches",
                    "excellent",
                    "good",
                    "ok",
                    "poor",
                    "An item to breakdown and create items.",
                    new short[]
                            {
                                    ItemTypes.ITEM_TYPE_NAMED,
                                    ItemTypes.ITEM_TYPE_METAL,
                                    ItemTypes.ITEM_TYPE_MISSION,
                                    ItemTypes.ITEM_TYPE_COOKER,
                                    ItemTypes.ITEM_TYPE_DECORATION,
                                    ItemTypes.ITEM_TYPE_USE_GROUND_ONLY,
                                    ItemTypes.ITEM_TYPE_ALWAYSPOLL,
                                    ItemTypes.ITEM_TYPE_HOLLOW,
                                    ItemTypes.ITEM_TYPE_REPAIRABLE,
                                    ItemTypes.ITEM_TYPE_NEVER_SHOW_CREATION_WINDOW_OPTION,
                                    ItemTypes.ITEM_TYPE_PLANTABLE,
                                    ItemTypes.ITEM_TYPE_USES_SPECIFIED_CONTAINER_VOLUME,
                                    ItemTypes.ITEM_TYPE_NOBANK,
                                    ItemTypes.ITEM_TYPE_HASDATA
                            },
                    (short) IconConstants.ICON_COOKER_FORGE,
                    BehaviourList.fireBehaviour,
                    CombatConstants.FIGHTNORMAL,
                    TimeConstants.DECAYTIME_STEEL,
                    BIG,
                    BIG,
                    BIG,
                    NOSKILL,
                    MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
                    // ToDo Make a perdy model.
                    "model.container.still.copper.",
                    EASY,
                    SMALL,
                    ItemMaterials.MATERIAL_IRON,
                    MonetaryConstants.COIN_SILVER,
                    false)
                    .setContainerSize(NONE, NONE, NONE)
                    .setInitialContainers(new InitialContainer[]
                            {
                                    new InitialContainer(AusConstants.InputTemplateID, "Input"),
                                    new InitialContainer(AusConstants.OutputTemplateID, "Output")
                            });

            // Input
            ItemTemplateCreator.createItemTemplate(
                    AusConstants.InputTemplateID,
                    "Input",
                    "Inputs",
                    "superb",
                    "good",
                    "ok",
                    "poor",
                    "Input",
                    new short[]
                            {
                                    ItemTypes.ITEM_TYPE_METAL,
                                    ItemTypes.ITEM_TYPE_HOLLOW,
                                    ItemTypes.ITEM_TYPE_NOTAKE,
                                    ItemTypes.ITEM_TYPE_NORENAME,
                                    ItemTypes.ITEM_TYPE_COMPONENT_ITEM,
                                    ItemTypes.ITEM_TYPE_PARENT_ON_GROUND,
                                    ItemTypes.ITEM_TYPE_NEVER_SHOW_CREATION_WINDOW_OPTION,
                                    ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                                    ItemTypes.ITEM_TYPE_NOBANK,
                                    ItemTypes.ITEM_TYPE_NOT_MISSION
                            },
                    (short) IconConstants.ICON_COPPER_STILL,
                    BehaviourList.itemBehaviour,
                    CombatConstants.FIGHTNORMAL,
                    TimeConstants.DECAYTIME_NEVER,
                    BIG,
                    BIG,
                    BIG,
                    AusConstants.NOSKILL,
                    MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
                    "model.container.still.boiler.",
                    HARD,
                    SMALL,
                    ItemMaterials.MATERIAL_IRON,
                    MonetaryConstants.COIN_SILVER,
                    false);

            // Output
            ItemTemplateCreator.createItemTemplate(
                    AusConstants.OutputTemplateID,
                    "Output",
                    "Outputs",
                    "superb",
                    "good",
                    "ok",
                    "poor",
                    "Output",
                    new short[]
                            {
                                    ItemTypes.ITEM_TYPE_METAL,
                                    ItemTypes.ITEM_TYPE_HOLLOW,
                                    ItemTypes.ITEM_TYPE_NOTAKE,
                                    ItemTypes.ITEM_TYPE_NORENAME,
                                    ItemTypes.ITEM_TYPE_COMPONENT_ITEM,
                                    ItemTypes.ITEM_TYPE_PARENT_ON_GROUND,
                                    ItemTypes.ITEM_TYPE_NEVER_SHOW_CREATION_WINDOW_OPTION,
                                    ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                                    ItemTypes.ITEM_TYPE_NOBANK,
                                    ItemTypes.ITEM_TYPE_NOT_MISSION,
                                    ItemTypes.ITEM_TYPE_NOPUT
                            },
                    (short) IconConstants.ICON_COPPER_STILL,
                    BehaviourList.itemBehaviour,
                    CombatConstants.FIGHTNORMAL,
                    TimeConstants.DECAYTIME_NEVER,
                    BIG,
                    BIG,
                    BIG,
                    AusConstants.NOSKILL,
                    MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
                    "model.container.still.boiler.",
                    HARD,
                    SMALL,
                    ItemMaterials.MATERIAL_IRON,
                    MonetaryConstants.COIN_SILVER,
                    false);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}