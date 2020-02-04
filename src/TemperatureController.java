class TemperatureController {

    public static void main(String args[]){

        //setting up the controller and display
        Controller cont = new Controller();
        Display disp = new Display(cont);
        cont.connect(disp);

        //setup textfields
        for( int i = 0; i < 11; i++ ){
            disp.initInputs(i);
        }
    }
}
