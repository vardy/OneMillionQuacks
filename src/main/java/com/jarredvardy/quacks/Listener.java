/**
 * Copyright 2019 Jarred Vardy
 * <p>
 * This file is part of OneMillionQuacks.
 * <p>
 * OneMillionQuacks is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * OneMillionQuacks is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with OneMillionQuacks. If not, see http://www.gnu.org/licenses/.
 */

package com.jarredvardy.quacks;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public final class Listener extends ListenerAdapter  {

    private String prefix = System.getenv("prefix");
    private String invite = System.getenv("invite");

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot()
                || event.getAuthor().isFake()
                || !event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
            return;
        }

        String raw = event.getMessage().getContentRaw();

        if(raw.toLowerCase().contains("quack") && !raw.equals(prefix + "quacks")) {
            Database.INSTANCE.registerQuack();
        }

        if(raw.equals(prefix + "quacks")) {
            event.getChannel().sendMessage(String.format("%d quacks", Database.INSTANCE.getQuacks())).queue();
        }

        if(raw.equals(prefix + "invite")) {
            event.getChannel().sendMessage(String.format("`%s`", invite)).queue();
        }
    }
}
