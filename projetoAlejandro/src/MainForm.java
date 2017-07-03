import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * Created by Marcelus on 21/03/2017.
 */
public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JButton buttonSend;
    private JCheckBox checkBoxADMCapcode;
    private JCheckBox checkBoxDeviceNumber;
    private JCheckBox checkBoxRepeatCall;
    private JCheckBox checkBoxServiceMember1;
    private JCheckBox checkBoxServiceMember2;
    private JCheckBox checkBoxServiceMember3;
    private JCheckBox checkBoxServiceMember4;
    private JComboBox comboBoxADMDelayTime;
    private JFormattedTextField textADMCapcode;
    private JFormattedTextField textFrequency;
    private JLabel labelADMDelayTime;
    private JLabel labelBTN1;
    private JLabel labelBTN2;
    private JLabel labelBTN3;
    private JLabel labelBTN4;
    private JLabel labelCAPCODE;
    private JLabel labelFrequency;
    private JLabel labelFrequencyValue;
    private JLabel labelMSG1;
    private JLabel labelMSG2;
    private JLabel labelMSG3;
    private JLabel labelMSG4;
    private JPanel panelBPS;
    private JRadioButton radioBPS1200;
    private JRadioButton radioBPS2400;
    private JRadioButton radioBPS512;
    private JRadioButton radioButtonMesgAlpha;
    private JRadioButton radioButtonMesgNumeric;
    private JFormattedTextField textBTN1;
    private JFormattedTextField textBTN2;
    private JFormattedTextField textBTN3;
    private JFormattedTextField textBTN4;
    private JFormattedTextField textDeviceNumber;
    private JTextField textMSG1;
    private JTextField textMSG2;
    private JTextField textMSG3;
    private JTextField textMSG4;
    private JFormattedTextField textRepeatCall;
    private JFormattedTextField textServiceMember1;
    private JFormattedTextField textServiceMember3;
    private JFormattedTextField textServiceMember4;
    private JFormattedTextField textServiceMember2;
    private JList listUSBDevices;
    private JLabel labelDevices;
    private JTabbedPane tabbedPane;
    private JPanel panelMain;
    private JPanel panelUSB;
    private JButton buttonFindDevices;
    private JPanel panelUSBDevices;
    private JPanel panelDevices;
    private JButton buttonRead;
    private JButton buttonOpenPort;
    private JButton buttonClosePort;
    private JPanel panelBottom;
    private JPanel panelMesgType;
    private JPanel panelUpRight;
    private JPanel panelMainButtons;
    private JRadioButton radioButtonUsage1;
    private JRadioButton radioButtonUsage2;
    private JRadioButton radioButtonUsage3;
    private JPanel panelUpLeft;
    private JLabel labelCurrentDevice;
    private MaskFormatter mask1Dec;
    private MaskFormatter mask3Bin;
    private MaskFormatter mask7Dec;
    private MaskFormatter mask6Dec;
    private int BPS = 1;
    private int MSGType = 1;
    private int ADMDelayTime = 1;
    private SerialPort[] ports;
    private SerialPort serialPortOpened;


    public MainForm() {
        super("RF Options");
        super.setResizable(false);
        setContentPane(tabbedPane);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        comboBoxADMDelayTime.addItem("1");
        comboBoxADMDelayTime.addItem("2");
        comboBoxADMDelayTime.addItem("3");
        comboBoxADMDelayTime.addItem("4");
        comboBoxADMDelayTime.addItem("5");
        comboBoxADMDelayTime.addItem("6");
        comboBoxADMDelayTime.addItem("7");
        comboBoxADMDelayTime.addItem("8");
        comboBoxADMDelayTime.addItem("9");

        //textADMCapcode = new JFormattedTextField(mask);

        checkBoxADMCapcode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textADMCapcode.setEnabled(checkBoxADMCapcode.isSelected());
            }
        });
        checkBoxRepeatCall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textRepeatCall.setEnabled(checkBoxRepeatCall.isSelected());
            }
        });
        checkBoxDeviceNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textDeviceNumber.setEnabled(checkBoxDeviceNumber.isSelected());
            }
        });
        checkBoxServiceMember1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textServiceMember1.setEnabled(checkBoxServiceMember1.isSelected());
            }
        });
        checkBoxServiceMember2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textServiceMember2.setEnabled(checkBoxServiceMember2.isSelected());
            }
        });
        checkBoxServiceMember3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textServiceMember3.setEnabled(checkBoxServiceMember3.isSelected());
            }
        });
        checkBoxServiceMember4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textServiceMember4.setEnabled(checkBoxServiceMember4.isSelected());
            }
        });

        radioBPS512.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BPS = 1;
            }
        });
        radioBPS1200.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BPS = 2;
            }
        });
        radioBPS2400.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BPS = 3;
            }
        });

        radioButtonMesgAlpha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MSGType = 1;
            }
        });
        radioButtonMesgNumeric.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MSGType = 2;
            }
        });

        comboBoxADMDelayTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMDelayTime = Integer.valueOf(comboBoxADMDelayTime.getSelectedItem().toString());
            }
        });

        buttonFindDevices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel listModel = (DefaultListModel) listUSBDevices.getModel();
                listModel.removeAllElements();
                String element;

                ports = SerialPort.getCommPorts();

                if (ports.length == 0) {
                    JOptionPane.showMessageDialog(MainForm.this, "No USB connections found.");
                    return;
                }

                for (SerialPort port : ports) {
                    element = port.getSystemPortName() + " - " + port.getDescriptivePortName();
                    listModel.addElement(element);
                }
            }
        });
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                long start = System.currentTimeMillis();
                String dataOutput = "";
                int frequency = Integer.valueOf(textFrequency.getValue().toString());
                String ADMCapcode = textADMCapcode.getValue().toString();
                String repeatCall = textRepeatCall.getValue().toString();
                String deviceNumber = textDeviceNumber.getValue().toString();

                String btn1 = textBTN1.getValue().toString();
                String btn2 = textBTN2.getValue().toString();
                String btn3 = textBTN3.getValue().toString();
                String btn4 = textBTN4.getValue().toString();

                String serviceMember1 = textServiceMember1.getValue().toString();
                String serviceMember2 = textServiceMember2.getValue().toString();
                String serviceMember3 = textServiceMember3.getValue().toString();
                String serviceMember4 = textServiceMember4.getValue().toString();

                String MSG1 = normalizeMSG(textMSG1.getText());
                String MSG2 = normalizeMSG(textMSG2.getText());
                String MSG3 = normalizeMSG(textMSG3.getText());
                String MSG4 = normalizeMSG(textMSG4.getText());

                String checksum = "";

                if (serialPortOpened != null) {
                    if (frequency < 420000 || frequency > 525999) {
                        JOptionPane.showMessageDialog(MainForm.this, "Frequency must be between 420000 and 525999.");
                    } else {
                        dataOutput += "#:;" + textFrequency.getValue() + ";" + BPS + ";" + MSGType + ";" + ADMCapcode + ";" + repeatCall + ";" + ADMDelayTime + ";" + deviceNumber + ";" + btn1 + ";" + serviceMember1 + ";" + MSG1 + ";" + btn2 + ";" + serviceMember2 + ";" + MSG2 + ";" + btn3 + ";" + serviceMember3 + ";" + MSG3 + ";" + btn4 + ";" + serviceMember4 + ";" + MSG4 + ";";
                        checksum = getAdlerChecksum(dataOutput.substring(1));
                        //checksum = "00000000000";
                        dataOutput += "!" + checksum + "@";
                        System.out.println("snt:" + dataOutput);
                        try {
                            serialPortOpened.getOutputStream().write(dataOutput.getBytes());
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(MainForm.this, "Error while sending.");
                            e1.printStackTrace();
                            return;
                        }
//                        System.out.println(System.currentTimeMillis() - start);
                        JOptionPane.showMessageDialog(MainForm.this, "Sent.");
                    }
                } else {
                    tabbedPane.setSelectedIndex(1);
                    JOptionPane.showMessageDialog(MainForm.this, "Device not communicating.");
                }
            }
        });
        buttonRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                long start = System.currentTimeMillis();

                if (serialPortOpened == null) {
                    tabbedPane.setSelectedIndex(1);
                    JOptionPane.showMessageDialog(MainForm.this, "Device not communicating.");
                    return;
                }

                //Solicitação de Leitura do Device
                try {
                    serialPortOpened.getOutputStream().write(63);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //Leitura do Write do Device
                byte[] b = new byte[254];
                try {
                    serialPortOpened.getInputStream().read(b);
                } catch (IOException f) {
                    JOptionPane.showMessageDialog(MainForm.this, "Read error.");
                    f.printStackTrace();
                }

                //Transforma a saída de bytes em uma string
                String output = "";
                for (byte aB : b) {
                    output += Character.toString((char) aB);
                }
                System.out.println("out: " + output);

                //Separa o checksum recebido pelo Serial e calcula o checksum das configurações localmente
                String chkSum1, chkSum2;
                chkSum1 = output.substring(output.lastIndexOf('!') + 1, output.lastIndexOf('@'));
                chkSum2 = getAdlerChecksum(output.substring(0, output.lastIndexOf('!')));
                //chkSum2 = "00000000000";

                //Verifica se os checksums são diferentes
                if (!chkSum1.equals(chkSum2)) {
                    JOptionPane.showMessageDialog(MainForm.this, "Checksum error!");
                    return;
                }

                //Atualiza valores lidos do Device
                String[] readValues = output.split(";");
                textFrequency.setValue(readValues[1]);
                switch (readValues[2]) {
                    case "1":
                        radioBPS1200.setSelected(false);
                        radioBPS2400.setSelected(false);
                        radioBPS512.setSelected(true);
                        break;
                    case "2":
                        radioBPS512.setSelected(false);
                        radioBPS2400.setSelected(false);
                        radioBPS1200.setSelected(true);
                        break;
                    case "3":
                        radioBPS512.setSelected(false);
                        radioBPS1200.setSelected(false);
                        radioBPS2400.setSelected(true);
                        break;
                }
                if (readValues[3].equals("1")) {
                    radioButtonMesgAlpha.setSelected(true);
                    radioButtonMesgNumeric.setSelected(false);
                } else {
                    radioButtonMesgAlpha.setSelected(false);
                    radioButtonMesgNumeric.setSelected(true);
                }
                textADMCapcode.setValue(readValues[4]);
                textRepeatCall.setValue(readValues[5]);
                comboBoxADMDelayTime.getModel().setSelectedItem(readValues[6]);
                textDeviceNumber.setValue(readValues[7]);
                textBTN1.setValue(readValues[8]);
                textServiceMember1.setValue(readValues[9]);
                textMSG1.setText(readValues[10]);
                textBTN2.setValue(readValues[11]);
                textServiceMember2.setValue(readValues[12]);
                textMSG2.setText(readValues[13]);
                textBTN3.setValue(readValues[14]);
                textServiceMember3.setValue(readValues[15]);
                textMSG3.setText(readValues[16]);
                textBTN4.setValue(readValues[17]);
                textServiceMember4.setValue(readValues[18]);
                textMSG4.setText(readValues[19]);

//                System.out.println(System.currentTimeMillis() - start);
                JOptionPane.showMessageDialog(MainForm.this, "Done.");
            }
        });
        buttonOpenPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Checa a porta COM selecionada
                try {
                    serialPortOpened = ports[listUSBDevices.getSelectedIndex()];
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(MainForm.this, "Select device.");
                    return;
                }

                if (serialPortOpened.isOpen()) {
                    JOptionPane.showMessageDialog(MainForm.this, "Port is already open.");
                    return;
                }

                //Abre a porta selecionada
                if (serialPortOpened.openPort()) {
                    labelCurrentDevice.setText("Current Device: " + serialPortOpened.getSystemPortName() + " - " + serialPortOpened.getDescriptivePortName());
                    JOptionPane.showMessageDialog(MainForm.this, "Port opened successfully.");
                } else {
                    JOptionPane.showMessageDialog(MainForm.this, "Unable to open the port.");
                    return;
                }

                //Definição de parâmetros
                serialPortOpened.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
                serialPortOpened.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);

            }
        });
        buttonClosePort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tenta fechar a porta, se não encontrar porta para fechar, lança erro.
                try {
                    serialPortOpened.closePort();
                    serialPortOpened = null;
                    labelCurrentDevice.setText("Current Device: null");
                    JOptionPane.showMessageDialog(MainForm.this, "Port closed.");
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(MainForm.this, "Couldn't find an open port.");
                    e1.printStackTrace();
                }
            }
        });

        setVisible(true);
        getAdlerChecksum("");

    }

    private String normalizeMSG(String str) {
        if (str.length() < 40) {
            for (int i = str.length(); i < 40; i++) {
                str += " ";
            }
        } else if (str.length() > 40)
            str = str.substring(0, 40);
        return str;
    }

    private void createUIComponents() {

        try {
            mask7Dec = new MaskFormatter("#######");
            mask7Dec.setPlaceholderCharacter('_');
            mask6Dec = new MaskFormatter("######");
            mask6Dec.setPlaceholderCharacter('_');
            mask1Dec = new MaskFormatter("#");
            mask1Dec.setPlaceholderCharacter('_');
            mask3Bin = new MaskFormatter("###");
            mask3Bin.setPlaceholderCharacter('_');
            mask3Bin.setValidCharacters("01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textADMCapcode = new JFormattedTextField(mask7Dec);
        textRepeatCall = new JFormattedTextField(mask1Dec);
        textDeviceNumber = new JFormattedTextField(mask3Bin);
        textFrequency = new JFormattedTextField(mask6Dec);
        textBTN1 = new JFormattedTextField(mask7Dec);
        textBTN2 = new JFormattedTextField(mask7Dec);
        textBTN3 = new JFormattedTextField(mask7Dec);
        textBTN4 = new JFormattedTextField(mask7Dec);
        textServiceMember1 = new JFormattedTextField(mask3Bin);
        textServiceMember2 = new JFormattedTextField(mask3Bin);
        textServiceMember3 = new JFormattedTextField(mask3Bin);
        textServiceMember4 = new JFormattedTextField(mask3Bin);
        textMSG1 = new JTextField();
        textMSG2 = new JTextField();
        textMSG3 = new JTextField();
        textMSG4 = new JTextField();

        textADMCapcode.setValue("0000000");
        textRepeatCall.setValue("0");
        textDeviceNumber.setValue("000");
        textFrequency.setValue("420000");
        textBTN1.setValue("0000000");
        textBTN2.setValue("0000000");
        textBTN3.setValue("0000000");
        textBTN4.setValue("0000000");
        textServiceMember1.setValue("000");
        textServiceMember2.setValue("000");
        textServiceMember3.setValue("000");
        textServiceMember4.setValue("000");
    }

    private String getSimpleChecksum(String dataOutput) {
//        System.out.println(dataOutput);
        long chkSum = 0;
        for (int i = 0; i < dataOutput.length(); i++)
            chkSum += ((int) dataOutput.charAt(i) * i);

        NumberFormat formatter = new DecimalFormat("00000000000");
        String s = formatter.format(chkSum);

//        System.out.println("Checksum: "+s);
        return s;
    }

    private String getAdlerChecksum(String dataOutput) {
        long a = 1, b = 0;
        for (int i = 0; i < dataOutput.length(); i++) {
            a = (a + (int) dataOutput.charAt(i)) % 65521;
            b = (b + a) % 65521;
        }

        NumberFormat formatter = new DecimalFormat("00000000000");
        String s = formatter.format(b * 65536 + a);

        return s;
    }
}