package com.newera.docs.entity.animation;

public interface INEAnimal {
    default byte getBodyState() { return 0; }
    default void setBodyState(byte state) {}

    default java.util.UUID getNEParentUUID() { return null; }
    default void setNEParentUUID(java.util.UUID uuid) {}

    default java.util.List<java.util.UUID> getNEChildrenUUIDs() { return new java.util.ArrayList<>(); }
    default void addNEChild(java.util.UUID uuid) {}
    default void removeNEChild(java.util.UUID uuid) {}
    default boolean isArmadilloSleeping() { return false; }
}

