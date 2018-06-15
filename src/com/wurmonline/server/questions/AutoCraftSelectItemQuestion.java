package com.wurmonline.server.questions;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.*;
import com.wurmonline.shared.constants.ItemMaterials;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;

public final class AutoCraftSelectItemQuestion extends Question
{
    private LinkedList<ItemTemplate> itemplates = new LinkedList<>();
    public AutoCraftSelectItemQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget)
    {
        super(aResponder, aTitle, aQuestion, 5, aTarget);
    }

    public void answer(Properties answers)
    {
        setAnswer(answers);
        parseItemCreationQuestion(this);
    }

    public void sendQuestion()
    {
        int width = 225;
        int height = 225;
        itemplates = new LinkedList<>();
        StringBuilder buf = new StringBuilder(getBmlHeader());
        ItemTemplate[] templates = ItemTemplateFactory.getInstance().getTemplates();
        Arrays.sort(templates);

        int x;
        for (x = 0; x < templates.length; ++x)
        {
            if (templates[x].isBulk())
            {
                itemplates.add(templates[x]);
            }
        }

        if (itemplates.size() != 1)
        {
            itemplates.add(0, null);
        }

        buf.append("harray{label{text=\"Item\"};dropdown{id=\"data1\";options=\"");

        for (x = 0; x < itemplates.size(); ++x)
        {
            if (x > 0)
            {
                buf.append(",");
            }

            ItemTemplate tp = itemplates.get(x);
            if (tp == null)
            {
                buf.append("Nothing");
            }
            else if (!tp.isMetal() && !tp.isWood() && !tp.isOre && !tp.isShard)
            {
                buf.append(tp.getName()).append(tp.sizeString.isEmpty() ? "" : " - " + tp.sizeString);
            }
            else
            {
                buf.append(tp.getName()).append(" - ").append(tp.sizeString).append(Item.getMaterialString(tp.getMaterial())).append(" ");
            }
        }
        buf.append("\"}}");
        buf.append("harray{label{text=\"Material\"};dropdown{id=\"aux\";options=\"");
        for(x = 0; x <= ItemMaterials.MATERIAL_MAX; ++x)
        {
            if (x == 0)
            {
                buf.append("standard");
            }
            else
            {
                buf.append(",");
                buf.append(Item.getMaterialString((byte) x));
            }
        }
        buf.append("\"}}");
        buf.append(createAnswerButton2());
        getResponder().getCommunicator().sendBml(width, height, true, true, buf.toString(), 200, 200, 200, title);
    }

    private ItemTemplate getTemplate(int aTemplateId)
    {
        return itemplates.get(aTemplateId);
    }

    private static void parseItemCreationQuestion(AutoCraftSelectItemQuestion question)
    {
        Creature responder = question.getResponder();
        long target = question.getTarget();
        String d1 = question.getAnswer().getProperty("data1");
        String aux = question.getAnswer().getProperty("aux");
        try
        {
            Item item = Items.getItem(target);
            int data1 = Integer.parseInt(d1);
            ItemTemplate template = question.getTemplate(data1);
            byte material = Byte.parseByte(aux);
            Item newItem;
            {
                int t = template.getTemplateId();
                newItem = ItemFactory.createItem(t, 1.0F, material, (byte) 0, responder.getName());
            }
            item.setData1(newItem.getTemplateId());
            item.setAuxData(material);
            responder.getCommunicator().sendNormalServerMessage("The workbench will now automatically create " + newItem.getTemplate().getName() + "'s.");
        }
        catch (FailedException | NoSuchItemException | NoSuchTemplateException | NumberFormatException e)
        {
            e.printStackTrace();
        }
    }
}