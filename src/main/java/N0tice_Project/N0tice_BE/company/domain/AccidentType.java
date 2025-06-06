package N0tice_Project.N0tice_BE.company.domain;

public enum AccidentType {
    CONCEALMENT("은폐"), // 은폐
    DEATH("사망"),       // 사망
    INJURY("부상");      // 부상

    private final String displayName;

    AccidentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
