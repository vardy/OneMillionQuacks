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

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public enum Database {

    /**
     * Singleton instance of the database.
     */
    INSTANCE;

    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> sync;

    public void init() {
        if(connection != null) {
            return;
        }

        // Building URI and client for Redis connection
        String mainDbPassword = "";
        String dbIP = "db";
        int dbNum = 0;
        RedisClient client = RedisClient.create(String.format("redis://%s@%s/%d", mainDbPassword, dbIP, dbNum));

        connection = client.connect(); //Establishing the connection
        sync = connection.sync();

        if(sync.exists("quacks") == 0) {
            sync.set("quacks", "0");
        }
    }

    public void registerQuack() {
        sync.incr("quacks");

        if(getQuacks() % 100 == 0) {
            Quacks.INSTANCE.updateStatus(getQuacks());
        }
    }

    public int getQuacks() {
        return Integer.parseInt(sync.get("quacks"));
    }
}
