package org.ausimus.wurmunlimited.mods.autocrafting.config;

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

import java.util.Locale;

@SuppressWarnings({"WeakerAccess", /* Who are you to tell me how to spell Penus. */"SpellCheckingInspection"})
public class AusConstants
{
    public static int CraftingWorkBenchTemplateID/* = 16000*/;
    public static int InputTemplateID/* = 16001*/;
    public static int OutputTemplateID/* = 16002*/;
    public static int pollInterval;
    public static int BIG = Integer.MAX_VALUE;
    public static int SMALL = 1;
    public static int NONE = 0;
    public static float HARD = 100.0F;
    public static float MEDIUM = HARD / 2;
    public static float EASY = MEDIUM / 2;
    public static int NOSKILL = -10;
    public static boolean debug;
    public static String logFile = "./mods/AutoCrafting/AutoCrafting".toLowerCase(Locale.ENGLISH) + ".log";
    public static String dbFile = "./mods/AutoCrafting/AutoCrafting".toLowerCase(Locale.ENGLISH) + ".db";
}