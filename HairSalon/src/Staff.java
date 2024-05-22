public class Staff extends Person{

    protected int idRole;
    protected String password;

    public Staff(){
        this(-1,"","","","",true,"","");
    }
    public Staff(int id, String firstName, String lastName, String tel, String info, boolean active, String username,String password) {
        super(id, username, firstName, lastName, tel, info, active);
        this.password = password;
        this.idRole=-1;

    }
    public Staff(int id, String firstName, String lastName, String tel, String info, boolean active, String username, int idRole) {
        super(id, username, firstName, lastName, tel, info, active);
        this.idRole=idRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRole(){return idRole; }

    public void setIdRole(int idRole){this.idRole=idRole; }

    @Override
    public String toString() {
        return username;
    }
}