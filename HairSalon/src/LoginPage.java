import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class LoginPage extends JFrame implements ActionListener {
    private JTextField txfUsername;
    private JPasswordField psfPassword;
    private JButton btnLogin;

    Border thickBorder = new LineBorder(Color.BLACK, 1);
    public LoginPage() {
        super(Language.get("LP_SUPER_TITLE"));
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        ImageIcon imgBanner = new ImageIcon(Language.get("LP_BANNER_PATH"));
        Image image = imgBanner.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);
        add(backgroundPanel, BorderLayout.CENTER);


        JPanel pnlInputs = new JPanel(new GridBagLayout());
        pnlInputs.setBackground(new Color(255, 255, 255, 200));
        pnlInputs.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlInputs.add(new JLabel(Language.get("LP_LBL_UNAME")), gbc);

        gbc.gridx = 1;
        txfUsername = new JTextField();
        txfUsername.setBorder(thickBorder);
        txfUsername.setPreferredSize(new Dimension(200, 30));
        pnlInputs.add(txfUsername, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlInputs.add(new JLabel(Language.get("LP_LBL_PASS")), gbc);

        gbc.gridx = 1;
        psfPassword = new JPasswordField();
        psfPassword.setPreferredSize(new Dimension(200, 30));
        psfPassword.setBorder(thickBorder);
        psfPassword.addActionListener(this);
        pnlInputs.add(psfPassword, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnLogin = new JButton(Language.get("LP_BTN_LOGIN"));
        btnLogin.addActionListener(this);
        btnLogin.setBackground(new Color(255, 255, 255));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setBorder(thickBorder);
        btnLogin.setPreferredSize(new Dimension(80, 40));

        pnlInputs.add(btnLogin, gbc);


        backgroundPanel.add(pnlInputs);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLogin || e.getSource() == psfPassword){
            Staff staff = DbManager.login(txfUsername.getText(), psfPassword.getText());

            if(staff == null){
                JOptionPane.showMessageDialog(this, Language.get("LP_ERR_WRNGCRED_CONTENT"), Language.get("LP_ERR_WRNGCRED_TITLE"), JOptionPane.ERROR_MESSAGE);
                return;
            }

            MainPage mainPage = new MainPage(staff);
            mainPage.setVisible(true);
            setVisible(false);
        }
    }
}
