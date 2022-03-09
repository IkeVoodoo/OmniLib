package me.ikevoodoo.omnilib.events.items;

public interface ItemEventHandler {

    void handleRightClick(ItemEvent event);

    void handleLeftClick(ItemEvent event);

    void handleDrop(ItemEvent event);

}
