package com.wurmonline.server.questions;

import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.players.PlayerInfoFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;

public final class AutoCraftSelectItemQuestion extends Question
{

    private final String filter;

    public AutoCraftSelectItemQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget)
    {
        super(aResponder, aTitle, aQuestion, 5, aTarget);
        filter = "*";
    }

    private AutoCraftSelectItemQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget, String aFilter)
    {
        super(aResponder, aTitle, aQuestion, 5, aTarget);
        filter = aFilter;
    }

    public void answer(Properties answers)
    {
        setAnswer(answers);
        String val = getAnswer().getProperty("filterme");
        if (val != null && val.equals("true"))
        {
            val = getAnswer().getProperty("filtertext");
            if (val == null || val.length() == 0)
            {
                val = "*";
            }

            AutoCraftSelectItemQuestion q = new AutoCraftSelectItemQuestion(getResponder(), title, question, target, val);
            q.sendQuestion();
        }
        else
        {
            parseItemDataQuestion(this);
        }

    }

    public void sendQuestion()
    {
        int height = 225;
        LinkedList<ItemTemplate> itemplates = new LinkedList<>();
        StringBuilder buf = new StringBuilder(getBmlHeader());
        buf.append("harray{label{text=\"List shows name -material\"}}");
        ItemTemplate[] templates = ItemTemplateFactory.getInstance().getTemplates();
        Arrays.sort(templates);

        int x;
        for (x = 0; x < templates.length; ++x)
        {
            if (!templates[x].isNoCreate() && (getResponder().getPower() == 5 || !templates[x].unique && !templates[x].isPuppet() && templates[x].getTemplateId() != 175 && templates[x].getTemplateId() != 654 && templates[x].getTemplateId() != 738 && templates[x].getTemplateId() != 972 && templates[x].getTemplateId() != 1032 && templates[x].getTemplateId() != 1297 && !templates[x].isRoyal && !templates[x].isUnstableRift()) && (getResponder().getPower() >= 2 || templates[x].getTemplateId() == 781 || templates[x].isBulk() && !templates[x].isFood() && templates[x].getTemplateId() != 683 && templates[x].getTemplateId() != 737 && templates[x].getTemplateId() != 175 && templates[x].getTemplateId() != 654 && templates[x].getTemplateId() != 738 && templates[x].getTemplateId() != 972 && templates[x].getTemplateId() != 1032) && PlayerInfoFactory.wildCardMatch(templates[x].getName().toLowerCase(), filter.toLowerCase()))
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
                if (tp.bowUnstringed)
                {
                    buf.append(tp.getName()).append(" - ").append(tp.sizeString).append(" [unstringed]");
                }
                else
                {
                    buf.append(tp.getName()).append(tp.sizeString.isEmpty() ? "" : " - " + tp.sizeString);
                }
            }
            else
            {
                buf.append(tp.getName()).append(" - ").append(tp.sizeString).append(Item.getMaterialString(tp.getMaterial())).append(" ");
            }
        }

        buf.append("\"}}");
        buf.append("harray{button{text=\"Filter list\";id=\"filterme\"};label{text=\" using \"};input{maxchars=\"30\";id=\"filtertext\";text=\"").append(this.filter).append("\";onenter=\"filterme\"}}");
        buf.append("harray{label{text=\"Material\"};dropdown{id=\"material\";options=\"");

        for (x = 0; x <= 92; ++x)
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
        buf.append("harray{label{text=\"Number of items   \"};input{maxchars=\"3\"; id=\"number\"; text=\"1\"}}");
        buf.append("harray{label{text=\"Custom size mod (float.eg. 0.3)\"};input{maxchars=\"4\"; id=\"sizemod\"; text=\"\"}}");
        if (this.getResponder().getPower() >= 4)
        {
            buf.append("table{rows=\"1\";cols=\"8\";");
            buf.append("radio{group=\"rare\";id=\"0\";selected=\"true\"};label{text=\"Common\"};");
            buf.append("radio{group=\"rare\";id=\"1\"};label{text=\"Rare\"};");
            buf.append("radio{group=\"rare\";id=\"2\"};label{text=\"Supreme\"};");
            buf.append("radio{group=\"rare\";id=\"3\"};label{text=\"Fantastic\"};");
            buf.append("}");
            buf.append("harray{label{text='Item Actual Name';hover=\"leave blank to use its base name\"};input{id='itemName'; maxchars='60'; text=''}}");
            buf.append("harray{label{text=\"Colour:\";hover=\"leave blank to use default\"};label{text='R'};input{id='c_red'; maxchars='3'; text=''}label{text='G'};input{id='c_green'; maxchars='3'; text=''}label{text='B'};input{id='c_blue'; maxchars='3'; text=''}}");
            height += 50;
        }
        else
        {
            buf.append("passthrough{id=\"rare\";text=\"0\"}");
        }

        buf.append(createAnswerButton2());
        getResponder().getCommunicator().sendBml(250, height, true, true, buf.toString(), 200, 200, 200, title);
    }

    private void parseItemDataQuestion(AutoCraftSelectItemQuestion question)
    {
        Creature performer = getResponder();
        long target = question.getTarget();
        String d1 = question.getAnswer().getProperty("data1");
        try
        {
            Item item = Items.getItem(target);
            int data1 = d1 == null ? -1 : Integer.parseInt(d1);
            if (item.hasData())
            {
                item.setData1(data1);
                performer.getCommunicator().sendNormalServerMessage("You choose " + data1 + ".");
            }
        }
        catch (NoSuchItemException ex)
        {
            ex.printStackTrace();
        }
    }
}