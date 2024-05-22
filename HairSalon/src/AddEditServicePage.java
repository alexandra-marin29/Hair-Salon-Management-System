import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditServicePage extends JFrame implements ActionListener {
    private Service service;
    private ServicePage servicePage;
    private JTextField txfName, txfPrice, txfDuration;

    private JButton btnConfirmation;
    private JButton btnCancel;

    private boolean objectContructionFinished=false;

    public AddEditServicePage(int idService) {
        this(idService, null);
    }
    public AddEditServicePage(int idService, ServicePage servicePage){
        super("Customer");

        setSize(513,433);
        setResizable(false);
        setLayout(null);

        this.servicePage = servicePage;
        if(idService == -1){
            service = new Service();
        }
        else{
            service = DbManager.getServiceById(idService);
        }

        JPanel frame = new JPanel();
        frame.setLayout(new GridLayout(5,2, 5,5));
        frame.setLocation(20, 10);
        frame.setSize(460, 200);

        JLabel lblName = new JLabel("Edit name: ");
        frame.add(lblName);
        txfName = new JTextField();
        txfName.addActionListener(this);
        frame.add(txfName);

        JLabel lblPrice = new JLabel("Edit Price: ");
        frame.add(lblPrice);
        txfPrice = new JTextField();
        txfPrice.addActionListener(this);
        frame.add(txfPrice);

        JLabel lblDuration = new JLabel("Duration (minutes) : ");
        frame.add(lblDuration);
        txfDuration = new JTextField();
        txfDuration.addActionListener(this);
        frame.add(txfDuration);

        add(frame);
        if(idService!=-1){
            populateFieldsOnUpdate();
        }

        createButtons();
        objectContructionFinished = true;
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
        txfName.setText(service.getName());
        txfPrice.setText( String.valueOf(service.getPrice()));
        txfDuration.setText(String.valueOf(service.getMinutes()));
    }

    void updateInMemoryService(){
        service.setName(txfName.getText());
        service.setPrice(Double.parseDouble(txfPrice.getText()));
        service.setMinutes(Integer.parseInt(txfDuration.getText()));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(!objectContructionFinished)
            return;

        try{
            updateInMemoryService();
        } catch (Exception ex){}

        if(e.getSource() == btnCancel){
            dispose();
            servicePage.refreshTable();

        }

        if(e.getSource() == btnConfirmation){
            if(validateErrors())
                return;

            boolean success = DbManager.insertOrUpdateService(service);
            if(success){
                JOptionPane.showMessageDialog(this, "Success");
                dispose();
                servicePage.refreshTable();

            }
            else {
                JOptionPane.showMessageDialog(this, "Error");
            }

        }
    }

    private boolean validateErrors(){
        Service other = DbManager.getServiceByName(txfName.getText());
        if(other!=null && other.getId_service() != service.getId_service()){
            JOptionPane.showMessageDialog(this, "Name already taken. Pick another option.");
            return true;
        }

        try{
            int duration = Integer.parseInt(txfDuration.getText());

            if(duration < 0) {
                JOptionPane.showMessageDialog(this, "Duration can't be negative.");
                return true;
            }

        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid duration.");
            return true;
        }

        try{
            double price = Double.parseDouble(txfPrice.getText());

            if(price < 0) {
                JOptionPane.showMessageDialog(this, "Price can't be negative.");
                return true;
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid price.");
            return true;
        }

        return false;
    }

}
