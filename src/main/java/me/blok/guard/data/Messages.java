package me.blok.guard.data;

import me.blok.guard.Guard;

import static me.blok.guard.utils.ChatUtils.format;

/**
 * Created by Blok on 6/3/2018.
 */
public class Messages {

    public static String BROADCAST_ON_DUTY;
    public static String BROADCAST_OFF_DUTY;
    public static String ALREADY_ON_DUTY;
    public static String ALREADY_OFF_DUTY;
    public static String NOW_OFF_DUTY;
    public static String NOW_ON_DUTY;
    public static String SET_JAIL;
    public static String JAILED;
    public static String JAIL_NOT_SET;
    public static String SENT_TO_JAIL;
    public static String NOT_ALLOWED_COMMAND;
    public static String BYPASSED_PLAYER;
    public static String FINISHED_JAILTIME;
    public static String UNJAILED_PLAYER;
    public static String ALREADY_IN_JAIL;

    public static void reload() {
        BROADCAST_ON_DUTY = format(Guard.getInstance()
                .getConfig()
                .getString("broadcastonduty"));
        BROADCAST_OFF_DUTY = format(Guard.getInstance().getConfig().getString("broadcastoffduty"));
        ALREADY_ON_DUTY = format(Guard.getInstance().getConfig().getString("already-on-duty"));
        ALREADY_OFF_DUTY = format(Guard.getInstance().getConfig().getString("already-off-duty"));
        NOW_OFF_DUTY = format(Guard.getInstance().getConfig().getString("now-off-duty"));
        NOW_ON_DUTY = format(Guard.getInstance().getConfig().getString("now-on-duty"));
        SET_JAIL = format(Guard.getInstance().getConfig().getString("saved-jail"));
        JAILED = format(Guard.getInstance().getConfig().getString("sent-to-jail"));
        JAIL_NOT_SET = format(Guard.getInstance().getConfig().getString("jail-not-set"));
        SENT_TO_JAIL = format(Guard.getInstance().getConfig().getString("sent-player-to-jail"));
        NOT_ALLOWED_COMMAND = format(Guard.getInstance().getConfig().getString("not-allowed-command"));
        BYPASSED_PLAYER = format(Guard.getInstance().getConfig().getString("bypassed-player"));
        FINISHED_JAILTIME = format(Guard.getInstance().getConfig().getString("jail-time-finished"));
        UNJAILED_PLAYER = format(Guard.getInstance().getConfig().getString("unjailed-player"));
        ALREADY_IN_JAIL = format(Guard.getInstance().getConfig().getString("already-in-jail"));
    }

}
