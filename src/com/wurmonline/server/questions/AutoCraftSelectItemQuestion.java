//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wurmonline.server.questions;

import com.wurmonline.server.*;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Change;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.items.*;
import com.wurmonline.server.players.PlayerInfoFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;

public final class AutoCraftSelectItemQuestion extends Question
{
    private LinkedList<ItemTemplate> itemplates = new LinkedList();
    private final String filter;

    public AutoCraftSelectItemQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget)
    {
        super(aResponder, aTitle, aQuestion, 5, aTarget);
        this.filter = "*";
    }

    public AutoCraftSelectItemQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget, String aFilter)
    {
        super(aResponder, aTitle, aQuestion, 5, aTarget);
        this.filter = aFilter;
    }

    public void answer(Properties answers)
    {
        this.setAnswer(answers);
        String val = this.getAnswer().getProperty("filterme");
        if (val != null && val.equals("true"))
        {
            val = this.getAnswer().getProperty("filtertext");
            if (val == null || val.length() == 0)
            {
                val = "*";
            }

            AutoCraftSelectItemQuestion icq = new AutoCraftSelectItemQuestion(this.getResponder(), this.title, this.question, this.target, val);
            icq.sendQuestion();
        } else
        {
            parseItemCreationQuestion(this);
        }

    }

    public void sendQuestion()
    {
        int height = 225;
        this.itemplates = new LinkedList();
        StringBuilder buf = new StringBuilder(this.getBmlHeader());
        buf.append("harray{label{text=\"List shows name -material\"}}");
        ItemTemplate[] templates = ItemTemplateFactory.getInstance().getTemplates();
        Arrays.sort(templates);

        int x;
        for (x = 0; x < templates.length; ++x)
        {
            if (!templates[x].isNoCreate() && (this.getResponder().getPower() == 5 || !templates[x].unique && !templates[x].isPuppet() && templates[x].getTemplateId() != 175 && templates[x].getTemplateId() != 654 && templates[x].getTemplateId() != 738 && templates[x].getTemplateId() != 972 && templates[x].getTemplateId() != 1032 && templates[x].getTemplateId() != 1297 && !templates[x].isRoyal && !templates[x].isUnstableRift()) && (this.getResponder().getPower() >= 2 || templates[x].getTemplateId() == 781 || templates[x].isBulk() && !templates[x].isFood() && templates[x].getTemplateId() != 683 && templates[x].getTemplateId() != 737 && templates[x].getTemplateId() != 175 && templates[x].getTemplateId() != 654 && templates[x].getTemplateId() != 738 && templates[x].getTemplateId() != 972 && templates[x].getTemplateId() != 1032) && PlayerInfoFactory.wildCardMatch(templates[x].getName().toLowerCase(), this.filter.toLowerCase()))
            {
                this.itemplates.add(templates[x]);
            }
        }

        if (this.itemplates.size() != 1)
        {
            this.itemplates.add(0, null);
        }

        buf.append("harray{label{text=\"Item\"};dropdown{id=\"data1\";options=\"");

        for (x = 0; x < this.itemplates.size(); ++x)
        {
            if (x > 0)
            {
                buf.append(",");
            }

            ItemTemplate tp = (ItemTemplate) this.itemplates.get(x);
            if (tp == null)
            {
                buf.append("Nothing");
            } else if (!tp.isMetal() && !tp.isWood() && !tp.isOre && !tp.isShard)
            {
                if (tp.bowUnstringed)
                {
                    buf.append(tp.getName() + " - " + tp.sizeString + " [unstringed]");
                } else
                {
                    buf.append(tp.getName() + (tp.sizeString.isEmpty() ? "" : " - " + tp.sizeString));
                }
            } else
            {
                buf.append(tp.getName() + " - " + tp.sizeString + Item.getMaterialString(tp.getMaterial()) + " ");
            }
        }

        buf.append("\"}}");
        buf.append("harray{button{text=\"Filter list\";id=\"filterme\"};label{text=\" using \"};input{maxchars=\"30\";id=\"filtertext\";text=\"" + this.filter + "\";onenter=\"filterme\"}}");
        buf.append("harray{label{text=\"Material\"};dropdown{id=\"material\";options=\"");

        for (x = 0; x <= 92; ++x)
        {
            if (x == 0)
            {
                buf.append("standard");
            } else
            {
                buf.append(",");
                buf.append(Item.getMaterialString((byte) x));
            }
        }

        buf.append("\"}}");
        buf.append("harray{label{text=\"Number of items   \"};input{maxchars=\"3\"; id=\"number\"; text=\"1\"}}");
        buf.append("harray{label{text=\"Item qualitylevel \"};input{maxchars=\"2\"; id=\"data2\"; text=\"1\"}}");
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
        } else
        {
            buf.append("passthrough{id=\"rare\";text=\"0\"}");
        }

        buf.append(this.createAnswerButton2());
        this.getResponder().getCommunicator().sendBml(250, height, true, true, buf.toString(), 200, 200, 200, this.title);
    }

    ItemTemplate getTemplate(int aTemplateId)
    {
        return (ItemTemplate) this.itemplates.get(aTemplateId);
    }

    static void parseItemCreationQuestion(AutoCraftSelectItemQuestion question)
    {
        int type = question.getType();
        Creature responder = question.getResponder();
        long target = question.getTarget();
        if (type == 0)
        {
            logger.log(Level.INFO, "Received answer for a question with NOQUESTION.");
        } else
        {
            if (type == 5 && (WurmId.getType(target) == 2 || WurmId.getType(target) == 19 || WurmId.getType(target) == 20))
            {
                String d1 = question.getAnswer().getProperty("data1");
                String d2 = question.getAnswer().getProperty("data2");
                String sm = question.getAnswer().getProperty("sizemod");
                String number = question.getAnswer().getProperty("number");
                String materialString = question.getAnswer().getProperty("material");
                String rareString = question.getAnswer().getProperty("rare");
                byte material = 0;

                try
                {
                    Item item = Items.getItem(target);
                    long now = System.currentTimeMillis();
                    int data1 = Integer.parseInt(d1);
                    int data2 = Integer.parseInt(d2);
                    int num = Integer.parseInt(number);
                    byte rare = Byte.parseByte(rareString);
                    String name = question.getAnswer().getProperty("itemName");
                    String red = question.getAnswer().getProperty("c_red");
                    String green = question.getAnswer().getProperty("c_green");
                    String blue = question.getAnswer().getProperty("c_blue");
                    int colour = -1;
                    int x;
                    if (red != null && green != null && blue != null && red.length() > 0 && green.length() > 0 && blue.length() > 0)
                    {
                        try
                        {
                            int r = Integer.parseInt(red);
                            int g = Integer.parseInt(green);
                            x = Integer.parseInt(blue);
                            colour = WurmColor.createColor(r < 0 ? 0 : r, g < 0 ? 0 : g, x < 0 ? 0 : x);
                        } catch (NullPointerException | NumberFormatException var48)
                        {
                            logger.log(Level.WARNING, "Bad colours:" + red + "," + green + "," + blue);
                        }
                    }

                    try
                    {
                        material = Byte.parseByte(materialString);
                    } catch (NumberFormatException var47)
                    {
                        logger.log(Level.WARNING, "Material was " + materialString);
                    }

                    ItemTemplate template = question.getTemplate(data1);
                    if (template == null)
                    {
                        responder.getCommunicator().sendNormalServerMessage("You decide not to create anything.");
                        return;
                    }

                    float sizemod = 1.0F;
                    if (sm != null && sm.length() > 0)
                    {
                        try
                        {
                            sizemod = Math.abs(Float.parseFloat(sm));
                            if (template.getTemplateId() != 995)
                            {
                                sizemod = Math.min(5.0F, sizemod);
                            }
                        } catch (NumberFormatException var46)
                        {
                            responder.getCommunicator().sendAlertServerMessage("The size mod " + sm + " is not a float value.");
                        }
                    }

                    if (template.getTemplateId() != 179 && template.getTemplateId() != 386)
                    {
                        for (x = 0; x < num; ++x)
                        {
                            data2 = Math.min(100, data2);
                            data2 = Math.max(1, data2);
                            Item newItem = null;
                            if (!Servers.localServer.testServer && responder.getPower() <= 3)
                            {
                                if (material == 0)
                                {
                                    material = template.getMaterial();
                                }

                                newItem = ItemFactory.createItem(387, (float) data2, material, rare, responder.getName());
                                newItem.setRealTemplate(template.getTemplateId());
                                if (template.getTemplateId() == 729)
                                {
                                    newItem.setName("This cake is a lie!");
                                } else
                                {
                                    newItem.setName("weird " + ItemFactory.generateName(template, material));
                                }
                            } else
                            {
                                int t = template.getTemplateId();
                                int color = colour;
                                if (template.isColor)
                                {
                                    t = 438;
                                    if (colour == -1)
                                    {
                                        color = WurmColor.getInitialColor(template.getTemplateId(), (float) Math.max(1, data2));
                                    }
                                }

                                if (material != 0)
                                {
                                    newItem = ItemFactory.createItem(t, (float) data2, material, rare, responder.getName());
                                } else
                                {
                                    newItem = ItemFactory.createItem(t, (float) data2, rare, responder.getName());
                                }

                                if (t != -1 && color != -1)
                                {
                                    newItem.setColor(color);
                                }

                                if (name != null && name.length() > 0)
                                {
                                    newItem.setName(name, true);
                                }

                                if (template.getTemplateId() == 175)
                                {
                                    newItem.setAuxData((byte) 2);
                                }
                            }

                            if (newItem.getTemplateId() == 995 && sizemod > 0.0F)
                            {
                                newItem.setAuxData((byte) ((int) sizemod));
                                if (newItem.getAuxData() > 4)
                                {
                                    newItem.setRarity((byte) 2);
                                }

                                newItem.fillTreasureChest();
                            }

                            if (newItem.isCoin())
                            {
                                long val = (long) Economy.getValueFor(template.getTemplateId());
                                if (val * (long) num > 500000000L)
                                {
                                    responder.getCommunicator().sendNormalServerMessage("You aren't allowed to create that amount of money.");
                                    responder.getLogger().log(Level.WARNING, responder.getName() + " tried to create " + num + " " + newItem.getName() + " but wasn't allowed to.");
                                    return;
                                }

                                Change change = new Change(val);
                                long newGold = change.getGoldCoins();
                                long newCopper;
                                if (newGold > 0L)
                                {
                                    newCopper = Economy.getEconomy().getGold();
                                    Economy.getEconomy().updateCreatedGold(newCopper + newGold);
                                }

                                newCopper = change.getCopperCoins();
                                long newSilver;
                                if (newCopper > 0L)
                                {
                                    newSilver = Economy.getEconomy().getCopper();
                                    Economy.getEconomy().updateCreatedCopper(newSilver + newCopper);
                                }

                                newSilver = change.getSilverCoins();
                                long newIron;
                                if (newSilver > 0L)
                                {
                                    newIron = Economy.getEconomy().getSilver();
                                    Economy.getEconomy().updateCreatedSilver(newIron + newSilver);
                                }

                                newIron = change.getIronCoins();
                                if (newIron > 0L)
                                {
                                    long oldIron = Economy.getEconomy().getIron();
                                    Economy.getEconomy().updateCreatedIron(oldIron + newIron);
                                }
                            }

                            if (responder.getLogger() != null)
                            {
                                responder.getLogger().log(Level.INFO, responder.getName() + " created item " + newItem.getName() + ", WurmID: " + newItem.getWurmId() + ", QL: " + newItem.getQualityLevel());
                            } else if (responder.getPower() != 0)
                            {
                                logger.log(Level.INFO, responder.getName() + " created item " + newItem.getName() + ", WurmID: " + newItem.getWurmId() + ", QL: " + newItem.getQualityLevel());
                            }

                            if (sizemod != 1.0F && template.getTemplateId() != 995)
                            {
                                newItem.setWeight((int) Math.max(1.0F, sizemod * (float) template.getWeightGrams()), false);
                                newItem.setSizes(newItem.getWeightGrams());
                            }

                            Item inventory = responder.getInventory();
                            if (newItem.isKingdomMarker() || newItem.isWind() && template.getTemplateId() == 579 || template.getTemplateId() == 578 || template.getTemplateId() == 999)
                            {
                                newItem.setAuxData(responder.getKingdomId());
                            }

                            Item container;
                            if (newItem.isLock() && newItem.getTemplateId() != 167)
                            {
                                try
                                {
                                    container = ItemFactory.createItem(168, newItem.getQualityLevel(), responder.getName());
                                    inventory.insertItem(container);
                                    newItem.addKey(container.getWurmId());
                                    container.setLockId(newItem.getWurmId());
                                } catch (NoSuchTemplateException | FailedException var44)
                                {
                                    logger.log(Level.WARNING, responder.getName() + " failed to create key: " + var44.getMessage(), var44);
                                }
                            }
                            item.setData1(newItem.getTemplateId());
                            responder.getCommunicator().sendNormalServerMessage("The workbench will now automatically create " + newItem.getTemplate().getName() + "'s.");
                        }
                    } else
                    {
                        responder.getCommunicator().sendAlertServerMessage("Don't create these. They will lack important data.");
                    }

                    if (responder.loggerCreature1 > 0L)
                    {
                        responder.getCommunicator().sendNormalServerMessage("That took " + (System.currentTimeMillis() - now) + " ms.");
                    }
                } catch (NoSuchItemException var49)
                {
                    logger.log(Level.WARNING, "Failed to locate item with id=" + target + " for which name change was intended.");
                    responder.getCommunicator().sendNormalServerMessage("Failed to locate that item.");
                } catch (NumberFormatException var50)
                {
                    responder.getCommunicator().sendNormalServerMessage("You realize that " + d1 + ", " + d2 + " or " + number + " doesn't match your requirements.");
                    if (logger.isLoggable(Level.FINE))
                    {
                        logger.log(Level.FINE, responder.getName() + " realises that " + d1 + ", " + d2 + " or " + number + " doesn't match their requirements. " + var50, var50);
                    }
                } catch (FailedException var51)
                {
                    responder.getCommunicator().sendNormalServerMessage("Failed!: " + var51.getMessage());
                    if (logger.isLoggable(Level.FINE))
                    {
                        logger.log(Level.FINE, "Failed to create an Item " + var51, var51);
                    }
                } catch (NoSuchTemplateException var52)
                {
                    responder.getCommunicator().sendNormalServerMessage("Failed!: " + var52.getMessage());
                    if (logger.isLoggable(Level.FINE))
                    {
                        logger.log(Level.FINE, "Failed to create an Item " + var52, var52);
                    }
                }
            }

        }
    }
}