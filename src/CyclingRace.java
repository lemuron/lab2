import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Cyclist extends Thread implements Comparable<Cyclist>{

    private int timeOfStart;
    private int time;
    private String name;

    private LoggingTester log;

    Cyclist(int timeOfStart, LoggingTester log){
        this.timeOfStart = timeOfStart;
        this.log = log;
        setName();
        setTime();

    }

    private void setTime() {
        Random generator = new Random();

        time = (int)(generator.nextGaussian()*3000 + 30000);

        if (time < 25000){
            this.time = 25000;
        }
        else if (time > 37000){
            this.time = 37000;
        }
    }

    private int getTime() {
        return time;
    }


    private void setName() {
        try {
            URL url = new URL("https://gist.githubusercontent.com/jdudek/732279/raw/39e0ac7e7614857bf3759593d074131d605a71af/last.txt");
            Scanner scanner = null;
            try {
                scanner = new Scanner(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Random generator = new Random();
            String name = "NN";
            int line = generator.nextInt(94);
            for(int i=0;i<94;i++){
                if (scanner != null) {
                    name = scanner.nextLine();
                }

                if (i == line) {
                    this.name = name;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String startInfo = name + " started in " + this.timeOfStart + " s of race \n";
        String endInfo = name + " at the finish line in " + (timeOfStart + time) + " s of race \n";

        System.out.println(startInfo);
        log.doLogging(startInfo);

        try {
            sleep(this.time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(endInfo);
        log.doLogging(endInfo);
    }


    public String toString(){
        return name + " " + String.valueOf(time);
    }

    void addToTimetable(PriorityQueue<Cyclist> timetable) {
        timetable.add(this);
    }

    @Override
    public int compareTo(Cyclist o) {
        Integer firstTime = this.getTime();
        Integer secondTime = o.getTime();

        return firstTime.compareTo(secondTime);
    }
}


public class CyclingRace {

    public static void main(String args[]){

        TimerTask race = new TimerSchedulePeriod();
        Timer timer = new Timer();

        timer.schedule(race, 40, 40);

    }


    private static class TimerSchedulePeriod extends TimerTask {
        LoggingTester log = new LoggingTester();

        PriorityQueue<Cyclist> timetable = new PriorityQueue<>();

        int second = 0;
        int nextCyclist = 1;

        void showTimetable(PriorityQueue<Cyclist> timetable){
            Iterator itr = timetable.iterator();
            String timetableOut = "Timetable: ";
            if (itr.hasNext()){
                timetableOut += (" 1. " + itr.next());
                if (itr.hasNext()){
                    timetableOut += ("  2. " + itr.next());
                    if (itr.hasNext()){
                        timetableOut += ("  3. " + itr.next());
                    }
                }
            }

            System.out.println(timetableOut);
            log.doLogging(timetableOut);
        }

        @Override
        public void run() {
            second++;
            if(second == nextCyclist && nextCyclist < 900){
                Cyclist cyclist = new Cyclist(second, log);
                cyclist.start();
                cyclist.addToTimetable(timetable);
                nextCyclist+=60;
                showTimetable(timetable);
            }
            else if(second > 4000){
                System.out.print("Race ended -- ");
                showTimetable(timetable);
                System.exit(0);
            }
        }


    }
}

class LoggingTester {
    private final Logger logger = Logger.getLogger(LoggingTester.class
            .getName());
    private FileHandler fh = null;

    LoggingTester() {
        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
        try {
            fh = new FileHandler("CyclingRaceLogFile_"
                    + format.format(Calendar.getInstance().getTime()) + ".log");
        } catch (Exception e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    void doLogging(String usual_info) {
        logger.info(usual_info);
    }
}