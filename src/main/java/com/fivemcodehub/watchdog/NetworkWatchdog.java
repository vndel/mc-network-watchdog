package com.fivemcodehub.watchdog;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NetworkWatchdog extends JavaPlugin {
    private int samplesOverLimit;

    @Override public void onEnable() {
        saveDefaultConfig();
        long interval = Math.max(20L, getConfig().getLong("sampling.interval-ticks", 100));
        getServer().getScheduler().runTaskTimer(this, this::sample, interval, interval);
    }

    private void sample() {
        double mspt = Bukkit.getAverageTickTime();
        double critical = getConfig().getDouble("sampling.critical-mspt", 48.0);
        int required = Math.max(1, getConfig().getInt("sampling.sustained-samples", 3));
        samplesOverLimit = mspt >= critical ? samplesOverLimit + 1 : 0;
        if (samplesOverLimit == required) {
            getLogger().warning(String.format("Sustained tick pressure: %.2f mspt, %.2f tps, %d players",
                    mspt, Bukkit.getTPS()[0], Bukkit.getOnlinePlayers().size()));
        }
    }
}
