import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

//TODO: testing new version where all inputs will be in the same JTextField array to make initialisation more concise

public class Display {
    private Controller controller;
    private JFrame window;
    /** Index 0 and 1 and temperatureSetpoint and deadband fields respectively
     *  indices 2 to 9 are temperature inputs */
    private JTextField[] inputs;

    /** */
    public Display(){
        //setting up the window
        GridLayout windowLayout = new GridLayout(2,4);
        window = new JFrame();
        window.setSize(1920,1080);
        window.setLayout(null);

        //initialising the textfields
        inputs = new JTextField[10];
        for(int i = 0; i < 10; i++ ){
            inputs[i] = new JTextField();
            inputs[i].setBounds(100,100 + i*20,100,20);
            window.add(inputs[i]);
        }

        window.setVisible(true);
    }

    public void init(){
        //setting initial values in textfields
        for(int i = 0; i < 10; i++ ){
            inputs[i].setText(String.valueOf(controller.getInput(i)));
        }
    }

    public void initInputs(int index){
        inputs[index].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if( checkForCharacters() ) {
                    controller.setInput(Double.parseDouble(inputs[index].getText()), index);
                    controller.Update();
                }else{
                    inputs[index].setText(controller.getInput(index));
                }
            }

            //returns true if no characters are found and false if a character other than digits and '.' is found
            public boolean checkForCharacters(){
                String text = inputs[index].getText();
                char ctext[] = text.toCharArray();
                for( int i = 0; i < text.length(); i++ ){
                    if(Character.isDigit(ctext[i]) != true || ctext[i] != '.'){
                        JOptionPane.showMessageDialog(null,
                                "Error: Please enter a valid numeric value.");
                        return false;
                    }
                }
                return true;
            }
        });
    }

    /** */
    public void connect(Controller cont){
        controller = cont;
    }

    public void displayTemperatureInputs(int index, double value){
        inputs[index].setText(String.valueOf(value));
    }
}
