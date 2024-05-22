import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServicePage extends JPanel implements ActionListener {
    private JButton btnAddHairSalonService;
    private JButton btnEditHairSalonService;
    private JButton btnDeleteHairSalonService;


    private JPanel pnlTableWrap;
    private JScrollPane scpScrollPane;
    private JTable table;

    private ListSelectionModel listSelectionModel;
    private ArrayList<Service> services;

    public ServicePage(){
        super();
        setLayout(null);

        createButtons();

        createTable();

        refreshTable();

    }

    private void createButtons(){
        JPanel pnlBtns = new JPanel();
        pnlBtns.setLayout(new GridLayout(1,3,15,15));
        pnlBtns.setLocation(20,65);
        pnlBtns.setSize(760,25);

        btnAddHairSalonService = new JButton(Language.get("BP_BTN_ADD"));
        btnAddHairSalonService.addActionListener(this);
        pnlBtns.add(btnAddHairSalonService);

        btnEditHairSalonService = new JButton(Language.get("BP_BTN_EDIT"));
        btnEditHairSalonService.addActionListener(this);
        pnlBtns.add(btnEditHairSalonService);

        btnDeleteHairSalonService = new JButton(Language.get("BP_BTN_DELETE"));
        btnDeleteHairSalonService.addActionListener(this);
        pnlBtns.add(btnDeleteHairSalonService);

        add(pnlBtns);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnAddHairSalonService){
            AddEditServicePage addEditHairSalonServicePage = new AddEditServicePage(-1,this);
            addEditHairSalonServicePage.setVisible(true);
        }

        if(e.getSource() == btnEditHairSalonService){
            if(listSelectionModel.getSelectedIndices().length == 0)
                return;
            int selectedIndex = listSelectionModel.getSelectedIndices()[0];

            AddEditServicePage addEditHairSalonServicePage = new AddEditServicePage(services.get(selectedIndex).getId_service(),this);
            addEditHairSalonServicePage.setVisible(true);
        }

        if(e.getSource() == btnDeleteHairSalonService){
            if(listSelectionModel.getSelectedIndices().length == 0)
                return;
            int selectedIndex = listSelectionModel.getSelectedIndices()[0];

            boolean response = DbManager.deleteService(services.get(selectedIndex));

            if(response)
                JOptionPane.showMessageDialog(this, Language.get("BP_MSG_DELETE_SUCCES"));
            else
                JOptionPane.showMessageDialog(this, Language.get("BP_MSG_DELETE_SUCCES"));

            refreshTable();
        }

    }

    void createTable(){
        pnlTableWrap = new JPanel();
        pnlTableWrap.setLocation(20, 100);
        pnlTableWrap.setSize(760, 480);
        add(pnlTableWrap);

        class NonEditableTableModel extends DefaultTableModel{
            NonEditableTableModel(Object[] objects, int lines){
                super(objects, lines);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }

        table = new JTable(new NonEditableTableModel(new Object[]{Language.get("BP_TBL_HEAD_NAME"), Language.get("BP_TBL_HEAD_PRICE"), Language.get("BP_TBL_HEAD_DURATION")}, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = table.getSelectionModel();

        scpScrollPane = new JScrollPane(table);
        scpScrollPane.setPreferredSize(new Dimension(760,460));
        scpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlTableWrap.add(scpScrollPane);
        add(pnlTableWrap);
    }


    public void refreshTable(){
        services = DbManager.getAllServices();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while(model.getRowCount()>0)
            model.removeRow(0);

        for (Service service : services) {
            model.addRow(new Object[]{
                    service.getName(),
                    service.getPrice(),
                    service.getMinutes()
            });
        }
    }
}

