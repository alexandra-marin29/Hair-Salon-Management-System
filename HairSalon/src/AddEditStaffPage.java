import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;

public class AddEditStaffPage extends JFrame implements ActionListener {
    private Staff staff;
    private StaffPage staffPage;
    private JComboBox<Role> cbRoles;

    private JTextField txfFirstName, txfLastName, txfUsername, txfTel, txfNote;
    private JPasswordField psfPassword;

    private JButton btnConfirmation;
    private JButton btnCancel;
    private JButton btnGenerateUsername;

    private boolean objectContructionFinished=false;

    public AddEditStaffPage(int idStaff) {
        this(idStaff, null);
    }
    public AddEditStaffPage(int idStaff, StaffPage staffPage){
        super("Staff");

        setSize(513,433);
        setResizable(false);
        setLayout(null);

        if(idStaff == -1){
            staff = new Staff();
        }
        else{
            staff = DbManager.getStaffById(idStaff);
        }


        JPanel frame = new JPanel();
        frame.setLayout(new GridLayout(7,2, 5,5));
        frame.setLocation(20, 10);
        frame.setSize(460, 300);

        JLabel lblFirstName = new JLabel("Edit firstName: ");
        frame.add(lblFirstName);
        txfFirstName = new JTextField();
        txfFirstName.addActionListener(this);
        frame.add(txfFirstName);

        JLabel lblLastName = new JLabel("Edit lastname: ");
        frame.add(lblLastName);
        txfLastName = new JTextField();
        txfLastName.addActionListener(this);
        frame.add(txfLastName);

        JPanel pnlUsername = new JPanel();
        pnlUsername.setLayout(new GridLayout(1,2,20,20));
        JLabel lblUsername = new JLabel("Edit nickname: ");
        pnlUsername.add(lblUsername);
        btnGenerateUsername = new JButton("Gen.");
        btnGenerateUsername.addActionListener(this);
        pnlUsername.add(btnGenerateUsername);
        frame.add(pnlUsername);
        txfUsername = new JTextField();
        txfUsername.addActionListener(this);
        frame.add(txfUsername);

        JLabel lblDate = new JLabel("Telephone number: ");
        frame.add(lblDate);
        txfTel = new JTextField();
        txfTel.addActionListener(this);
        frame.add(txfTel);

        JLabel lblNote = new JLabel("Note: ");
        frame.add(lblNote);
        txfNote = new JTextField();
        txfNote.addActionListener(this);
        frame.add(txfNote);

        JLabel lblPassword = new JLabel("New password : ");
        frame.add(lblPassword);
        psfPassword = new JPasswordField();
        psfPassword.addActionListener(this);
        frame.add(psfPassword);

        JLabel lblRole = new JLabel("Select Role: ");
        frame.add(lblRole);
        cbRoles = new JComboBox<>();
        loadRolesIntoComboBox();
        frame.add(cbRoles);

        add(frame);
        if(idStaff!=-1){
            populateFieldsOnUpdate();
        }

        createButtons();
        objectContructionFinished = true;
        this.staffPage=staffPage;
    }
    private void loadRolesIntoComboBox() {
        ArrayList<Role> roles = DbManager.getAllActiveRoles();
        for (Role role : roles) {
            cbRoles.addItem(role);
        }
    }

    private void createButtons(){
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1,3, 50, 10));
        pnlButtons.setLocation(20, 355);
        pnlButtons.setSize(460, 20);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        pnlButtons.add(btnCancel);

        btnConfirmation = new JButton("Confirm");
        btnConfirmation.addActionListener(this);
        pnlButtons.add(btnConfirmation);

        add(pnlButtons);
    }

    private void populateFieldsOnUpdate(){
        txfFirstName.setText(staff.getFirstName());
        txfLastName.setText(staff.getLastName());
        txfUsername.setText(staff.getUsername());
        txfTel.setText(staff.getTel());
        txfNote.setText(staff.getInfo());
    }

    void updateInMemoryStaff(){
        staff.setFirstName(txfFirstName.getText());
        staff.setLastName(txfLastName.getText());
        staff.setUsername(txfUsername.getText());
        staff.setTel(txfTel.getText());
        staff.setInfo(txfNote.getText());
        Role selectedRole = (Role) cbRoles.getSelectedItem();
        staff.setIdRole(selectedRole.getIdRole());

        String password = new String(psfPassword.getPassword());
        if (password != null && !password.trim().isEmpty()) {
            staff.setPassword(password);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(!objectContructionFinished)
            return;

        updateInMemoryStaff();

        if(e.getSource() == btnCancel){
            dispose();
            staffPage.refreshTable();;
        }

        if(e.getSource() == btnConfirmation){
            if(validateErrors())
                return;

            boolean success = DbManager.insertOrUpdateStaff(staff);
            if(success){
                JOptionPane.showMessageDialog(this, "Success");
                dispose();
                staffPage.refreshTable();

            }
            else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        }

        if(e.getSource() == btnGenerateUsername){
            txfUsername.setText(generateUsername());
        }
    }
    private String generateUsername() {
        String firstName = txfFirstName.getText().trim().toLowerCase();
        String lastName = txfLastName.getText().trim().toLowerCase();

        String generated = firstName + "_" + lastName;

        Customer other = DbManager.getCustomerByUsername(generated);
        if (other == null) {
            return generated;
        }
        Random rng = new Random();
        while (true) {
            String withSuffix = generated + rng.nextInt(10000);
            other = DbManager.getCustomerByUsername(withSuffix);
            if (other == null) {
                return withSuffix;
            }
        }
    }

    private boolean validateErrors(){
        Staff other = DbManager.getStaffByUsername(txfUsername.getText());
        if(other!=null && other.getId() != staff.getId()){
            JOptionPane.showMessageDialog(this, "Username already taken. Pick another option.");
            return true;
        }

        String password = psfPassword.getText();
        if(password != null && password.length() > 0){
            if(password.length()<6) {
                JOptionPane.showMessageDialog(this, "Password too simple. Pick another option.");
                return true;
            }
        }

        return false;
    }

}
