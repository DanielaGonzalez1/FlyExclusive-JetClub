package appium.utils;

public enum SQLConstants {
    UPDATE_USER("UPDATE dbo.AbpUsers SET UserName = 'USERNAME' WHERE id = 'ID'");

    public String query;
    SQLConstants(String query) {
        this.query = query;
    }
}
