package at.htl.contact_manager.model;

public enum ContactType {
    BUSINESS,
    PRIVATE,
    NONE;

    public static ContactType fromString(String type) {
        try {
            return ContactType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return NONE;
        }
    }
}
