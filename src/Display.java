import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display {

    /** Field used to store the controller that is connected to the display */
    private Controller controller;
    /** Field used to access the window component of the GUI
     * Window size is initially set to width = 450 and height = 550 */
    private JFrame window;
    /** Index 0 and 1 and temperatureSetpoint and deadband fields respectively
     *  indices 2 to 9 are temperature inputs (function 2 temperature inputs)
     *  index 10 is used for temperature input 0 (function 1 temperature input) */
    private JTextField[] inputs;
    /** A size 3 array used to display the output values */
    private JTextField[] outputs;
    /** A size 3 array to store the labels of the 3 output textfields */
    private JLabel[] outputLabels;
    /** Used to access the ImageIcon of the cooling status */
    private JTextField coolingIcon;

    /** Field used to store the onButton graphic */
    private ImageIcon onButton;
    /** Field used to store the offButton Graphic */
    private ImageIcon offButton;

    /** Constructor function for the Display class, sets the window size to width = 450, height = 550 with no layout manager.
     * Initialises the sizes of all textfields and labels to width = 150 and height = 20.
     * Uses the setBounds function to set all the locations of the textfields and labels.
     * Sets the window closing event to terminate the program.
     * @param cont is copied into this.controller in order to access control logic methods and data. */
    public Display(Controller cont){
        controller = cont;

        //setting up the window
        window = new JFrame();
        window.setSize(450,550);
        window.setLayout(null);

        //initialising the input textfields
        inputs = new JTextField[11];

        //temperature setpoint textfield
        JLabel tempSetpointLabel = new JLabel("Temperature Setpoint:");
        tempSetpointLabel.setBounds(50,40,150,20);
        tempSetpointLabel.setToolTipText("If temperature input 0 is above this value cooling will be set to on");
        window.add(tempSetpointLabel);
        inputs[0] = new JTextField();
        inputs[0].setBounds(50,60,150,20);
        window.add(inputs[0]);

        //function 1 temperature input textfield
        JLabel coolingTempLabel = new JLabel("Temperature input 0:");
        coolingTempLabel.setBounds(50,80, 150,20);
        window.add(coolingTempLabel);
        inputs[10] = new JTextField();
        inputs[10].setBounds(50,100,150,20);
        window.add(inputs[10]);

        //deadband textfield
        JLabel deadbandLabel = new JLabel("Deadband:");
        deadbandLabel.setBounds(250,40,150,20);
        deadbandLabel.setToolTipText("Inputs lower than (setpoint - deadband) will not be considered for output");
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
        coolingLabel.setBounds(250,80,100,20);
        window.add(coolingLabel);
        coolingIcon = new JTextField("OFF");
        coolingIcon.setBackground(Color.red);
        coolingIcon.setBounds(250,100,100,20);
        coolingIcon.setEditable(false);
        window.add(coolingIcon);

        //setting initial values in textfields
        for(int i = 0; i < 11; i++ ){
            inputs[i].setText(String.valueOf(controller.getInput(i)));
        }

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

    /** This function sets up a specified textfield to listen for an "enter" key press.
     * If the key press event is detected, the Update() function from the Controller class will be called.
     * @param index specifies the textfield to setup.
     * this function should be ran 11 times by the main function to set up every single textfield. */
    public void initInputs(int index){
        inputs[index].addActionListener(e -> {
            String text = inputs[index].getText();
            double inputTemp;
            //handle empty and invalid inputs
            try {
                inputTemp = Double.parseDouble(text);
                //if user input is below absolute 0, reset the field
                if( inputTemp < -273 ){
                    JOptionPane.showMessageDialog(null,"Invalid input: value below absolute zero.");
                    displayTemperatureInputs(index,controller.getInput(index));
                }
                //if user input is valid, set the temperature input to the input value and run update()
                else {
                    controller.setInput(inputTemp, index);
                    controller.Update();
                }
            }catch(NumberFormatException f){
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid numeric value and make sure that there are no empty fields.");
                displayTemperatureInputs(index,controller.getInput(index));
            }
        });
    }

    /** This function is used to set the controller variable to a specified controller object in order to access
     * the control logic functions and data */
    public void connect(Controller cont){
        controller = cont;
    }

    /** This function is used by a controller object to set a specified input to a value.
     * @param index is used to specify the index of the inputs[] array that needs to be changed.
     * @param value is the new value that should be displayed. */
    public void displayTemperatureInputs(int index, double value){
        inputs[index].setText(String.valueOf(value));
    }

    /** This function is called by the Update() function of a Controller object to set the label
     * and value of a output textfield to a desired value.
     * @param label is a string which contains the desired text to be displayed at outputLabels[index]
     * @param index is an integer value that dictates which outputs and outputLabels element will be changed
     * @param value is a double value which contains the new value to be displayed */
    public void displayOutputs(String label, double value, int index){
        outputLabels[index].setText(label);
        outputs[index].setText(String.valueOf(value));
    }

    /** This function sets the icon of the cooling display to on or off based on the input.
     * @param input is a boolean value, if input = true, cooling is set to ON
     *                                  else cooling is set to OFF */
    public void setCoolingIcon(boolean input){
        if(input == true){
            coolingIcon.setBackground(Color.green);
            coolingIcon.setText("ON");
        }else{
            coolingIcon.setBackground(Color.red);
            coolingIcon.setText("OFF");
        }
    }
}
