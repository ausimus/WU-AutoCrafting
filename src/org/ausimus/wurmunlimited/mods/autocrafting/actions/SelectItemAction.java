package org.ausimus.wurmunlimited.mods.autocrafting.actions;

import com.wurmonline.server.*;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.*;
import com.wurmonline.server.questions.AutoCraftSelectItemQuestion;
import org.ausimus.wurmunlimited.mods.autocrafting.config.AusConstants;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class SelectItemAction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer
{

    private static short actionIdNews;
    private static ActionEntry actionEntryNews;

    public SelectItemAction()
    {
        actionIdNews = (short) ModActions.getNextActionId();
        actionEntryNews = ActionEntry.createEntry(actionIdNews, "Select Items to Create", "Selecting", new int[]{
        });
        ModActions.registerAction(actionEntryNews);
    }

    @Override
    public BehaviourProvider getBehaviourProvider()
    {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer()
    {
        return this;
    }

    @Override
    public short getActionId()
    {
        return actionIdNews;
    }


    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target)
    {
        return getBehavioursFor(performer, target);
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target)
    {
        if (target.getTemplateId() == AusConstants.CraftingWorkBenchTemplateID)
        {
            return Collections.singletonList(actionEntryNews);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        return action(act, performer, target, action, counter);
    }

    @Override
    public boolean action(Action act, Creature performer, Item target, short action, float counter)
    {
        if (target.getTemplateId() == AusConstants.CraftingWorkBenchTemplateID)
        {
            AutoCraftSelectItemQuestion q = new AutoCraftSelectItemQuestion(performer, "Auto Craft Menu", "Which item shall be created?", target.getWurmId());
            q.sendQuestion();
        }
        return true;
    }
}