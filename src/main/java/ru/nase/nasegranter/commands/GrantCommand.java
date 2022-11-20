package ru.nase.nasegranter.commands;

import org.bukkit.command.*;
import ru.nase.nasegranter.*;
import java.text.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import ru.nase.nasegranter.grant.*;
import ru.nase.nasegranter.utils.*;
import ru.nase.nasegranter.Main;
import ru.nase.nasegranter.grant.Group;
import ru.nase.nasegranter.grant.Storage;
import ru.nase.nasegranter.utils.Config;
import ru.nase.nasegranter.utils.CooldownUtil;
import ru.nase.nasegranter.utils.Lang;
import ru.nase.nasegranter.utils.TitleUtil;

public class GrantCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0) {
            for (final String help : Main.config.getStringList("settings.help")) {
                sender.sendMessage(help);
            }
            return true;
        }
        final String lowerCase = args[0].toLowerCase();
        final String s;
        switch (s = lowerCase) {
            case "reload": {
                if (!sender.hasPermission("nasegranter.reload")) {
                    return true;
                }
                final long time = System.currentTimeMillis();
                this.reload();
                sender.sendMessage(MessageFormat.format(Lang.getString("cmd.reload_access"), System.currentTimeMillis() - time));
                return false;
            }
            case "give": {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Lang.getString("cmd.only_players"));
                    return true;
                }
                if (!sender.hasPermission("nasegranter.grant")) {
                    sender.sendMessage(Lang.getString("cmd.no_permissions"));
                    return true;
                }
                if (args.length > 3) {
                    for (final String help2 : Main.config.getStringList("settings.help")) {
                        sender.sendMessage(help2);
                    }
                    return true;
                }
                if (args.length == 1) {
                    sender.sendMessage(Lang.getString("cmd.need_target_name"));
                    return true;
                }
                final Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(MessageFormat.format(Lang.getString("cmd.target_offline"), args[1]));
                    return true;
                }
                if (args.length == 2) {
                    sender.sendMessage(Lang.getString("cmd.need_group_id"));
                    return true;
                }
                final String group = args[2];
                if (Main.config.getString("priorities." + group) == null) {
                    sender.sendMessage(Lang.getString("cmd.group_not_exist"));
                    return true;
                }
                final String player_group = Main.getPermission().getPlayerGroups((Player)sender)[0];
                if (Main.config.getString("limits." + player_group + "." + group) == null) {
                    sender.sendMessage(Lang.getString("cmd.cant_give_group"));
                    return true;
                }
                final String target_group = Main.getPermission().getPlayerGroups(target)[0];
                final int target_group_priori = Main.config.getInt("priorities." + target_group);
                final int give_group_priority = Main.config.getInt("priorities." + group);
                if (target_group_priori > give_group_priority) {
                    sender.sendMessage(Lang.getString("cmd.higher_group"));
                    return true;
                }
                if (target_group_priori == give_group_priority) {
                    sender.sendMessage(Lang.getString("cmd.this_group"));
                    return true;
                }
                if (!Storage.containsStorage((Player)sender, group)) {
                    final int gg = Group.getGiveGroups((Player)sender, group);
                    Storage.setAvailableGroupGrants((Player)sender, group, gg);
                }
                if (Storage.getAvailableGroupGrants((Player)sender, group) == 0) {
                    sender.sendMessage(Lang.getString("cmd.limit_riched"));
                    return true;
                }
                if (CooldownUtil.has(sender.getName())) {
                    sender.sendMessage(MessageFormat.format(Lang.getString("\u0441md.has_cooldown"), CooldownUtil.left(sender.getName())));
                    return true;
                }
                final int tryes = Storage.getAvailableGroupGrants((Player)sender, group);
                final String groupName = Group.getGroupName(group);
                Storage.setAvailableGroupGrants((Player)sender, group, tryes - 1);
                CooldownUtil.set(sender.getName(), 1000 * Main.config.getInt("settings.cooldown"));
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), Main.config.getString("settings.command").replace("{player}", target.getName()).replace("{group}", group));
                for (final Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendMessage(MessageFormat.format(Lang.getString("cmd.players_notify"), sender.getName(), target.getName(), groupName).split("\\n"));
                    if (Main.config.getBoolean("settings.titles.enabled")) {
                        TitleUtil.sendTitle(pl, MessageFormat.format(Main.config.getString("settings.titles.title"), sender.getName(), target.getName(), groupName), MessageFormat.format(Main.config.getString("settings.titles.subtitle"), sender.getName(), target.getName(), groupName), Main.config.getInt("settings.titles.fadeIn"), Main.config.getInt("settings.titles.stay"), Main.config.getInt("settings.titles.fadeOut"));
                    }
                }
                return false;
            }
            case "fix": {
                if (!sender.hasPermission("nasegranter.fix")) {
                    sender.sendMessage(Lang.getString("cmd.no_permissions"));
                    return true;
                }
                if (args.length > 2) {
                    for (final String help2 : Main.config.getStringList("settings.help")) {
                        sender.sendMessage(help2);
                    }
                    return true;
                }
                if (args.length == 1) {
                    sender.sendMessage(Lang.getString("cmd.need_target_name"));
                    return true;
                }
                final String player = args[1];
                if (!Main.dataConfig.contains("storage." + player)) {
                    sender.sendMessage(Lang.getString("cmd.no_limits"));
                    return true;
                }
                Main.dataConfig.set("storage." + player, (Object)null);
                Main.getInstance().saveData();
                sender.sendMessage(MessageFormat.format(Lang.getString("cmd.fix_access"), player));
                return false;
            }
            default:
                break;
        }
        for (final String help2 : Main.config.getStringList("settings.help")) {
            sender.sendMessage(help2);
        }
        return false;
    }
    
    private void reload() {
        Main.config = Config.get("config.yml");
        Main.getInstance().saveData();
        Lang.init();
    }
}
