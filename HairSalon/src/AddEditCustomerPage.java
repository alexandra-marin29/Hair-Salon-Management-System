import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AddEditCustomerPage extends JFrame implements ActionListener {
    private Customer customer;
    private CustomerPage customerPage;
    private JTextField txfFirstName, txfLastName, txfUsername, txfTel, txfNote;

    private JButton btnConfirmation;
    private JButton btnCancel;
    private JButton btnGenerateUsername;

    private boolean objectContructionFinished=false;
    public AddEditCustomerPage(int idCustomer) {
        this(idCustomer, null);
    }
    public AddEditCustomerPage(int idCustomer, CustomerPage  customerPage){
        super("Customer");

        setSize(513,433);
        setResizable(false);
        setLayout(null);

        if(idCustomer == -1){
            customer = new Customer();
        }
        else{
            customer = DbManager.getCustomerById(idCustomer);
        }

        JPanel frame = new JPanel();
        frame.setLayout(new GridLayout(5,2, 5,5));
        frame.setLocation(20, 10);
        frame.setSize(460, 200);

        JLabel lblFirstName = new JLabel("Edit firstName: ");
        frame.add(lblFirstName);
        txfFirstName = new JTextField();
        txfFirstName.addActionListener(this);
        frame.add(txfFirstName);

        JLabel lblLastName = new JLabel("Edit lastName: ");
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

        JLabel lblHour = new JLabel("Note: ");
        frame.add(lblHour);
        txfNote = new JTextField();
        txfNote.addActionListener(this);
        frame.add(txfNote);

        add(frame);
        if(idCustomer!=-1){
            populateFieldsOnUpdate();
        }

        createButtons();
        objectContructionFinished = true;
        this.customerPage=customerPage;

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
        txfFirstName.setText(customer.getFirstName());
        txfLastName.setText(customer.getLastName());
        txfUsername.setText(customer.getUsername());
        txfTel.setText(customer.getTel());
        txfNote.setText(customer.getInfo());
    }

    void updateInMemoryCustomer(){
        customer.setFirstName(txfFirstName.getText());
        customer.setLastName(txfLastName.getText());
        customer.setUsername(txfUsername.getText());
        customer.setTel(txfTel.getText());
        customer.setInfo(txfNote.getText());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(!objectContructionFinished)
            return;

        updateInMemoryCustomer();

        if(e.getSource() == btnCancel){
            dispose();
            customerPage.refreshTable();
        }

        if(e.getSource() == btnConfirmation){
            if(validateErrors())
                return;

            boolean success = DbManager.insertOrUpdateCustomer(customer);
            if(success){
                JOptionPane.showMessageDialog(this, "Success");
                dispose();
                customerPage.refreshTable();

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
        Customer other = DbManager.getCustomerByUsername(txfUsername.getText());
        if(other!=null && other.getId() != customer.getId()){
            JOptionPane.showMessageDialog(this, "Username already taken. Pick another option.");
            return true;
        }

        return false;
    }

}
