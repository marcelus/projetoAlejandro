import javax.swing.*;

/**
 * Created by Marcelus on 21/03/2017.
 */

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm form = new MainForm();
            }
        });

    }
}