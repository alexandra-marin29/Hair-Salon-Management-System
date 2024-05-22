public class Role {
    private int idRole;
    private String name;
    private boolean active;

    public Role(int idRole, String name, boolean active) {
        this.idRole = idRole;
        this.name = name;
        this.active = active;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }
}
