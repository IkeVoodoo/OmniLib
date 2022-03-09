package me.ikevoodoo.omnilib.timed;

import me.ikevoodoo.omnilib.OmniLIB;
import org.bukkit.Bukkit;

public class TimedActionManager {

    public void schedule(TimedAction action) {
        schedule(action, action.delay());
    }

    private void schedule(TimedAction action, long delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OmniLIB.getInstance(), () -> {
                if(action.async())
                    Bukkit.getScheduler().runTaskAsynchronously(OmniLIB.getInstance(), action::execute);
                else
                    action.execute();

                if(action.reschedule())
                    schedule(action, action.delay());
        }, delay);
    }

}
