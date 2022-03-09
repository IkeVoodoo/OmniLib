package me.ikevoodoo.omnilib.timed;

public interface TimedAction {

    /**
     * Process the action.
     * */
    void execute();

    /**
     * Check if the action should be rescheduled.
     *
     * If this is enabled:<br>
     * The action will be rescheduled with the delay returned by {@link #delay()}
     *
     * @return True if the action should be rescheduled.
     *         Otherwise, the action will be executed once and then removed from the scheduler.
     * */
    default boolean reschedule() {
        return false;
    }

    /**
     * Delay in ticks between executions.
     *
     * If rescheduling is enabled:<br>
     *  The first execution will be instantly executed.<br>
     *  Then any subsequent executions will be delayed by the return value of this method.
     *
     * @return Delay in ticks between executions.
     * */
    default long delay() {
        return 20;
    }

    /**
     * Check if the action should be executed asynchronously.
     *
     * @return True if the action should be executed asynchronously.
     * */
    default boolean async() {
        return false;
    }

}
