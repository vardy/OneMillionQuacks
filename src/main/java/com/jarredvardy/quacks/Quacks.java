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

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public enum Quacks {

    /**
     * Singleton instance of bot.
     */
    INSTANCE;

    private static JDA jda;

    public static void main(String[] args) {

        Thread.currentThread().setName("Main");
        Database.INSTANCE.init();
        Quacks.INSTANCE.init();
    }

    public void init() {
        String token = System.getenv("token");

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .setGame(Game.playing(String.format("%07d Quacks", Database.INSTANCE.getQuacks())))
                    .addEventListener(new Listener())
                    .build();
            jda.awaitReady();
            jda.setAutoReconnect(true);

        } catch (LoginException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void updateStatus(int newQuacks) {
        jda.getPresence().setGame(Game.playing(String.format("%07d Quacks", newQuacks)));
    }
}
