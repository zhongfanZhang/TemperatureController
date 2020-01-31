import java.util.Timer;
import java.util.TimerTask;

class TemperatureController {

    public static void main(String args[]){

        //setting up the controller and display
        Controller cont = new Controller();
        Display disp = new Display();
        disp.connect(cont);
        cont.connect(disp);
        disp.init();

        //setup textfields
        for( int i = 0; i < 8; i++ ){
            disp.initTemperatureInputs(i);
        }

        Timer t = new Timer();
        Ticker ticker = new Ticker(cont);
        t.scheduleAtFixedRate(ticker,0,100);
    }

    private static class Ticker extends TimerTask{
        Controller controller;

        public Ticker(Controller cont){
            this.controller = cont;
        }

        public void run(){
            controller.displayData();
        }
    }

}
