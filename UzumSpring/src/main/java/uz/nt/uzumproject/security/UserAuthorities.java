package uz.nt.uzumproject.security;

public enum UserAuthorities {
    /**
     * Start of Objects list
     */
    READ("READ"),
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");
    /**
     * End of Objects list
     */
    
    UserAuthorities(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}
