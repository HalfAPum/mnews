package com.narvatov.mnews.utils;

public class ServerResponse {

    public static String getCreatedMessage(Object object) {
        return getSuccessfulMessage(object, CREATED);
    }

    public static String getUpdatedMessage(Object object) {
        return getSuccessfulMessage(object, UPDATED);
    }

    public static String getDeletedMessage(int id) {
        return getSuccessfulMessage("Item at index " + id, DELETED);
    }

    private static String getSuccessfulMessage(Object object, String status) {
        return object.getClass().getSimpleName() + HAS_BEEN_SUCCESSFULLY + status + ".";
    }

    private final static String CREATED = "created";
    private final static String UPDATED = "updated";
    private final static String DELETED = "deleted";

    private final static String HAS_BEEN_SUCCESSFULLY = " has been successfully ";

}
