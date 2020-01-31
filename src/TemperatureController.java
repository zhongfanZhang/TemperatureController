class TemperatureController {

    public static void main(String args[]){

        //setting up the controller and display
        Controller cont = new Controller();
        Display disp = new Display();
        disp.connect(cont);
        cont.connect(disp);
        disp.init();

        //setup textfields
        for( int i = 0; i < 10; i++ ){
            disp.initInputs(i);
        }
    }
}
