import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    //-------------------------CLASS VARIABLES---------------------------------------
    /** Indices 0 and 1 are used for the temperature setpoint and deadband values respectively (initially set to 22 and 2),
     * indices 2 to 9 are used for input temperatures (function 2 temperature inputs, initially all set to 0)
     * index 10 is used for temperature input 0 which dictates cooling status (function 1 temperature input, initially set to 0) */
    private double[] inputs;

    /** ArrayList used to store the 3 highest values from the "inputs" array
     * This list should be cleared after every call of the Update() function */
    private ArrayList<Double> outputs;

    /** Array used to store the original indices of the 3 highest values of the "inputs" array
     * This list should be cleared after every call of the Update() function */
    private ArrayList<Integer> outputIndex;

    /** Field used to access a Display object which contains the functions to interact with the GUI */
    private Display display;

    /** Field used to set the cooling status
     * Initially set to false in the constructor function */
    private boolean cooling;

    //--------------------------CONTROL FUNCTIONS------------------------------------
    /**
     * Constructor function for the Controller class.
     * Sets the default temperature setpoint to 22 degrees and temperature setpoint
     * deadband to 2 degrees.
     * Initialises all input temperatures to 0 degrees.
     * cooling is initially set to false (OFF)
     */
    public Controller() {
        //setup initial inputs and outputs values
        inputs = new double[11];
        inputs[0] = 22.0;
        inputs[1] = 2.0;
        Arrays.fill(inputs,2,9,0);
        inputs[10] = 0.0;
        cooling = false;

        //setup output arraylists
        outputs = new ArrayList<>();
        outputIndex = new ArrayList<>();
    }

    /** Function used to set the display field
     * @param disp is copied into this.display */
    public void connect(Display disp){
        display = disp;
    }

    /** This function is called after every valid input event that the GUI detects.
     * Sets the function 2 outputs to the 3 highest values by calling the fillOutputArray() method
     * Refills all unchanged textfields if some are cleared
     * Checks the temperature input 0 value to determine the cooling status */
    public void Update(){
        //fill the output array with the three highest values
        fillOutputArray();

        //display function 2 outputs
        for( int i = 0; i < outputs.size(); i++ ){
            display.displayOutputs("Input " + ((outputIndex.get(i))+1) + ":", outputs.get(i), i);
        }
        //empty the output arraylist
        outputs.clear();
        outputIndex.clear();

        //refill unchanged values
        for(int i = 2; i < 10; i++){
            display.displayTemperatureInputs(i,inputs[i]);
        }

        //function 1: cooling display
        if( inputs[10] > inputs[0] ){
            cooling = true;
        }else if( inputs[10] < (inputs[0] - inputs[1])){
            cooling = false;
        }
        display.setCoolingIcon(cooling);
    }

    /** This function fills the outputs arraylist with the 3 highest values from inputs[2-9] (i.e function 2 inputs) */
    private void fillOutputArray(){
        //setting up a temp arraylist to contain the current inputs
        ArrayList<Double> temp = new ArrayList<>();
        for( int i = 2; i < 10; i++ ){
            temp.add(inputs[i]);
        }

        //find the top 3 max values and remove them from the temp arraylist
        for( int i = 0; i < 3; i++ ){
            double max = -1000000;
            int max_index = 0;
            for( int j = 0; j < temp.size(); j++ ){
                if( temp.get(j) >= max && temp.get(j) < 100 ){
                    max = temp.get(j);
                    max_index = j;
                }
            }
            temp.remove(max_index);
            outputs.add(max);
            outputIndex.add(max_index);
        }
    }

    //-------------------------------GET/SET FUNCTIONS---------------------------------
    /** Sets a specified input to a new temperature.
     * @param newTemp is a double value representing temperature
     * @param index is an integer value which dictates which input is being changed */
    public void setInput(double newTemp, int index){
        this.inputs[index] = newTemp;
    }

    /** @return a specified value from the inputs array
     * @param index is used to specify the desired index */
    public double getInput(int index) {
        return inputs[index];
    }
}
