import java.util.Arrays;

public class Controller {

    //-------------------------CLASS VARIABLES---------------------------------------
    /**
     * Temperature setpoint, set to 22 degrees by default by the constructor
     */
    private double temperatureSetpoint;
    /** Temperature setpoint deadband, set to 2 degrees by the constructor class
     *  if an input temperature is lower than (setpoint - deadband) the output
     *  will be turned off */
    private double deadband;
    /** Array used to store all 8 of the input temperatures, all 8 values are initially
     * set to 0 by the constructor function */
    private double inputTemperatures[];
    /** Used for the cooling output, initially set to false by the constructor function.
     * Set cooling = true if the desired output value is ON
     * Set cooling = false if OFF */
    private boolean cooling;

    //--------------------------CONTROL FUNCTIONS------------------------------------
    /**
     * Constructor function for the Controller class.
     * Sets the default temperature setpoint to 22 degrees and temperature setpoint
     * deadband to 2 degrees.
     * Initialises all input temperatures to 0 degrees.
     */
    public Controller() {
        //setting up variables
        this.temperatureSetpoint = 22.0;
        this.deadband = 2.0;
        this.cooling = false;

        //setup input temperatures, initialise values to 0
        this.inputTemperatures = new double[8];
        Arrays.fill(inputTemperatures,0);
    }


    //-------------------------------GET/SET FUNCTIONS---------------------------------
    /** */
    public double getTemperatureSetpoint(){
        return temperatureSetpoint;
    }

    /** Gets the deadband temperature below which outputs will be turned off
     * @return temperatureSetpoint - deadband */
    public double getDeadBand(){
        return temperatureSetpoint - deadband;
    }

    /** Sets a new temperature setpoint, overwriting the previous value
     * @param newSetpoint This should be a double value representing the
     *                    new temperature setpoint. */
    public void setTemperatureSetpoint(double newSetpoint){
        temperatureSetpoint = newSetpoint;
    }

    /** */
    public void setDeadband(double newDeadband){
        deadband = newDeadband;
    }

    /** Sets a specified input temperature to a new temperature.
     * @param newTemp is a double value representing temperature
     * @param index is an integer value which dictates which input is being changed */
    public void setInputTemperatures(double newTemp, int index){
        this.inputTemperatures[index] = newTemp;
    }
}
