import java.util.Arrays;

public class Controller {

    //-------------------------CLASS VARIABLES---------------------------------------
    /** indices 0 and 1 are used for the temperature setpoint and deadband values respectively,
     * indices 2 to 9 are used for input temperatures */
    private double[] inputs;
    /** */
    private double[] outputs;
    /** */
    private int[] outputIndex;
    /** Used for the cooling output, initially set to false by the constructor function.
     * Set cooling = true if the desired output value is ON
     * Set cooling = false if OFF */
    private boolean cooling;

    private Display display;

    //--------------------------CONTROL FUNCTIONS------------------------------------
    /**
     * Constructor function for the Controller class.
     * Sets the default temperature setpoint to 22 degrees and temperature setpoint
     * deadband to 2 degrees.
     * Initialises all input temperatures to 0 degrees.
     * cooling is initially set to false (OFF)
     */
    public Controller() {
        //setting up variables
//        temperatureSetpoint = 22.0;
//        deadband = 2.0;
        cooling = false;

        //setup initial inputs and outputs values
        inputs = new double[10];
        inputs[0] = 22.0;
        inputs[1] = 2.0;
        Arrays.fill(inputs,2,9,0);
        outputs = new double[3];
        Arrays.fill(outputs,0);
        outputIndex = new int[3];
        Arrays.fill(outputIndex,0);
    }

    public void connect(Display disp){
        display = disp;
    }

    /** */
    public void Update(){
        //fill the output array with the three highest values
        fillOutputArray();

        //check if the values are valid for output, i.e. above the deadband threshold
        for( int i = 0; i < outputs.length; i++ ){
            //if temp is greater than the threshold, display
            if( outputs[i] > (inputs[0] - inputs[1]) ){
                display.displayOutputs("Input " + outputIndex[i] + ":", outputs[i], i);
            }else{
                display.turnOffOutput(i);
            }
        }
    }

    /** */
    private void fillOutputArray(){
        double[] temp = new double[8];
        for( int i = 0; i < 8; i++ ){
            temp[i] = inputs[i+2];
        }
        for(int i = 0; i < 3; i++ ){
            //only output if above deadband threshold
            double max = inputs[0] - inputs[1];
            int maxIndex = 0;
            for( int j = 0; j < temp.length; j++ ){
                if(temp[j] > max){
                    max = temp[j];
                    maxIndex = j;
                }
            }
            //set the previous max to an improbable value so it wont selected for max again
            temp[maxIndex] = -100000;
            outputs[i] = max;
            outputIndex[i] = maxIndex;
        }
    }

    //-------------------------------GET/SET FUNCTIONS---------------------------------
    /** Sets a specified input to a new temperature.
     * @param newTemp is a double value representing temperature
     * @param index is an integer value which dictates which input is being changed */
    public void setInput(double newTemp, int index){
        this.inputs[index] = newTemp;
    }

    public String getInput(int index) {
        return String.valueOf(inputs[index]);
    }
}
