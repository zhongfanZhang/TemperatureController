import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: testing new version where all inputs will be in the same JTextField array to make initialisation more concise

public class Display {
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
    public Display(){
        //setting up the window
        GridLayout windowLayout = new GridLayout(2,4);
        window = new JFrame();
        window.setSize(1920,1080);
        window.setLayout(null);

        //initialising the input textfields
        inputs = new JTextField[10];

        //temperature setpoint textfield
        JLabel tempSetpointLabel = new JLabel("Temperature Setpoint:");
        tempSetpointLabel.setBounds(100,80,150,20);
        window.add(tempSetpointLabel);
        inputs[0] = new JTextField();
        inputs[0].setBounds(100,100,150,20);
        window.add(inputs[0]);

        //deadband textfield
        JLabel deadbandLabel = new JLabel("Deadband:");
        deadbandLabel.setBounds(300,80,150,20);
        window.add(deadbandLabel);
        inputs[1] = new JTextField();
        inputs[1].setBounds(300,100,150,20);
        window.add(inputs[1]);

        //temperature inputs textfields
        for(int i = 2; i < 10; i++ ){
            JLabel tempInputLabel = new JLabel("Temperature input " + (i-1) + ":");
            tempInputLabel.setBounds(100,180 + i*40,150,20);
            window.add(tempInputLabel);
            inputs[i] = new JTextField();
            inputs[i].setBounds(100,200 + i*40,150,20);
            window.add(inputs[i]);
        }

        //initialising the output textfields
        outputs = new JTextField[3];
        outputLabels = new JLabel[3];
        for(int i = 0; i < 3; i++ ){
            outputLabels[i] = new JLabel("Output off");
            outputLabels[i].setBounds(800,80 + i*40,150,20);
            window.add(outputLabels[i]);
            outputs[i] = new JTextField();
            outputs[i].setBounds(800,100 + i*40,150,20);
            outputs[i].setEditable(false);
            window.add(outputs[i]);
        }

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
        inputs[index].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputs[index].getText();
                //handle inputs other than numbers
                try{
                    controller.setInput(Double.parseDouble(text),index);
                    controller.Update();
                }catch(NumberFormatException f){
                    JOptionPane.showMessageDialog(null,
                            "Error: Please enter a valid numeric value.");
                }
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
}
