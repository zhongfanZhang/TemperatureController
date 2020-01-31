import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

//TODO: testing new version where all inputs will be in the same JTextField array to make initialisation more concise

public class Display {
    private Controller controller;
    private JFrame window;

    private JTextField temperatureSetPoint;
    private JTextField deadband;
    private JTextField temperatureInputs[];

    /** */
    public Display(){
        //setting up the window
        GridLayout windowLayout = new GridLayout(2,4);
        window = new JFrame();
        window.setLayout(windowLayout);

        //initialising the textfields
        temperatureInputs = new JTextField[8];
        temperatureSetPoint = new JTextField();
        deadband = new JTextField();

        //adding textfields to window
        window.add(temperatureSetPoint);
        window.add(deadband);
    }

    public void init(){
        //setting initial values in textfields
        temperatureSetPoint.setText(String.valueOf(controller.getTemperatureSetpoint()));
        deadband.setText(String.valueOf(controller.getDeadBand()));
        for(int i = 0; i < 8; i++ ){
            temperatureInputs[i].setText(String.valueOf(controller.getTemperature(i)));
        }
    }

    public void initTemperatureInputs(int index){
        temperatureInputs[index].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if( checkForCharacters() ) {
                    controller.setInputTemperatures(Double.parseDouble(temperatureInputs[index].getText()), index);
                    controller.displayData();
                }else{
                    temperatureInputs[index].setText(controller.getTemperature(index));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                temperatureInputs[index].setText(controller.getTemperature(index));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if( checkForCharacters() ) {
                    controller.setInputTemperatures(Double.parseDouble(temperatureInputs[index].getText()), index);
                    controller.displayData();
                }else{
                    temperatureInputs[index].setText(controller.getTemperature(index));
                }
            }

            public boolean checkForCharacters(){
                String text = temperatureInputs[index].getText();
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
        temperatureInputs[index].setText(String.valueOf(value));
    }
}
