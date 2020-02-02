import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display {

    //TODO: scalable window
    //TODO: other forms of output
    //TODO: cooling display
    //TODO: check for possible exploits

    private Controller controller;
    private JFrame window;
    /** Index 0 and 1 and temperatureSetpoint and deadband fields respectively
     *  indices 2 to 9 are temperature inputs */
    private JTextField[] inputs;
    /** */
    private JTextField[] outputs;
    /** */
    private JLabel[] outputLabels;
    /** */
    private JLabel coolingIcon;

    /** */
    public Display(){
        //setting up the window
        GridLayout windowLayout = new GridLayout(2,4);
        window = new JFrame();
        window.setSize(450,550);
        window.setLayout(null);

        //initialising the input textfields
        inputs = new JTextField[10];

        //temperature setpoint textfield
        JLabel tempSetpointLabel = new JLabel("Temperature Setpoint:");
        tempSetpointLabel.setBounds(50,40,150,20);
        window.add(tempSetpointLabel);
        inputs[0] = new JTextField();
        inputs[0].setBounds(50,60,150,20);
        window.add(inputs[0]);

        //deadband textfield
        JLabel deadbandLabel = new JLabel("Deadband:");
        deadbandLabel.setBounds(250,40,150,20);
        window.add(deadbandLabel);
        inputs[1] = new JTextField();
        inputs[1].setBounds(250,60,150,20);
        window.add(inputs[1]);

        //temperature inputs textfields
        for(int i = 2; i < 10; i++ ){
            JLabel tempInputLabel = new JLabel("Temperature input " + (i-1) + ":");
            tempInputLabel.setBounds(50,60 + i*40,150,20);
            window.add(tempInputLabel);
            inputs[i] = new JTextField();
            inputs[i].setBounds(50,80 + i*40,150,20);
            window.add(inputs[i]);
        }

        //initialising the output temperature textfields
        outputs = new JTextField[3];
        outputLabels = new JLabel[3];
        for(int i = 0; i < 3; i++ ){
            outputLabels[i] = new JLabel("Output off");
            outputLabels[i].setBounds(250,140 + i*40,150,20);
            window.add(outputLabels[i]);
            outputs[i] = new JTextField();
            outputs[i].setBounds(250,160 + i*40,150,20);
            outputs[i].setEditable(false);
            window.add(outputs[i]);
        }

        //cooling output intially set to off
        JLabel coolingLabel = new JLabel("Cooling status:");
        coolingLabel.setBounds(250,340,100,20);
        window.add(coolingLabel);
        coolingIcon = new JLabel(new ImageIcon("src/resources/OffButton.png"));
        coolingIcon.setBounds(250,360,100,100);
        window.add(coolingIcon);

        //setup window closing event
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
        });

        window.setTitle("Temperature Controller");
        window.setVisible(true);
    }

    public void init(){
        //setting initial values in textfields
        for(int i = 0; i < 10; i++ ){
            inputs[i].setText(String.valueOf(controller.getInput(i)));
        }
    }

    /** */
    public void initInputs(int index){
        inputs[index].addActionListener(e -> {
            String text = inputs[index].getText();
            double inputTemp;
            try {
                inputTemp = Double.parseDouble(text);
                controller.setInput(inputTemp,index);
                controller.Update();
            }catch(NumberFormatException f){
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid numeric value and make sure that there are no empty fields.");
            }
        });
    }

    /** */
    public void connect(Controller cont){
        controller = cont;
    }

    /** */
    public void displayTemperatureInputs(int index, double value){
        inputs[index].setText(String.valueOf(value));
    }

    /** */
    public void displayOutputs(String label, double value, int index){
        outputLabels[index].setText(label);
        outputs[index].setText(String.valueOf(value));
    }

    public void turnOffOutput(int index){
        outputLabels[index].setText("Output off");
        outputs[index].setText(null);
    }

    /** */
    public void setCoolingIcon(boolean input){
        if(input == true){
            try {
                coolingIcon.setIcon(new ImageIcon("src/resources/OnButton.png"));
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"src/resources/OnButton.png not found.");
            }
        }else{
            try{
                coolingIcon.setIcon(new ImageIcon("src/resources/OffButton.png"));
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"src/resources/OffButton.png not found.");
            }
        }
    }
}
