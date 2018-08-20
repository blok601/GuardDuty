package me.blok.guard.data;

import me.blok.guard.Files;

import static me.blok.guard.utils.ChatUtils.format;

/**
 * Created by Blok on 6/3/2018.
 */
public class Messages {

    public static final String BROADCAST_ON_DUTY =  format(Files.getInstance().getMessages().getString("broadcastonduty"));
    public static final String BROADCAST_OFF_DUTY =  format(Files.getInstance().getMessages().getString("broadcastoffduty"));
    public static final String ALREADY_ON_DUTY = format(Files.getInstance().getMessages().getString("already-on-duty"));
    public static final String NOW_OFF_DUTY = format(Files.getInstance().getMessages().getString("now-off-duty"));
    public static final String NOW_ON_DUTY = format(Files.getInstance().getMessages().getString("now-on-duty"));
    public static final String SET_JAIL = format(Files.getInstance().getMessages().getString("saved-jail"));
    public static final String JAILED = format(Files.getInstance().getMessages().getString("sent-to-jail"));
    public static final String JAIL_NOT_SET = format(Files.getInstance().getMessages().getString("jail-not-set"));
    public static final String SENT_TO_JAIL = format(Files.getInstance().getMessages().getString("sent-player-to-jail"));
    public static final String NOT_ALLOWED_COMMAND = format(Files.getInstance().getMessages().getString("not-allowed-command"));
    public static final String BYPASSED_PLAYER = format(Files.getInstance().getMessages().getString("bypassed-player"));
    public static final String FINISHED_JAILTIME = format(Files.getInstance().getMessages().getString("jail-time-finished"));
    public static final String UNJAILED_PLAYER = format(Files.getInstance().getMessages().getString("unjailed-player"));
    public static final String ALREADY_IN_JAIL = format(Files.getInstance().getMessages().getString("already-in-jail"));

}
