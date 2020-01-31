import java.util.Arrays;

public class Controller {

    //-------------------------CLASS VARIABLES---------------------------------------
    /** indices 0 and 1 are used for the temperature setpoint and deadband values respectively,
     * indices 2 to 9 are used for input temperatures */
    private double inputs[];
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

        //setup input temperatures, initialise values to 0
        inputs = new double[10];
        inputs[0] = 22.0;
        inputs[1] = 2.0;
        Arrays.fill(inputs,2,9,0);
    }

    public void connect(Display disp){
        display = disp;
    }

    /** */
    public void Update(){
        //this function will be run at regular intervals by the main function


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
