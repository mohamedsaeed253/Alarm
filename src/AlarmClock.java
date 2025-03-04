import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class AlarmClock  implements Runnable{

    private final LocalTime alarmTime;
    private final String filePath;
    private final Scanner scanner;

    AlarmClock(LocalTime alarmTime, String filePath, Scanner scanner){
        this.alarmTime = alarmTime;
        this.filePath =filePath;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (LocalTime.now().isBefore(alarmTime)){
            try {
                Thread.sleep(1000);
                LocalTime now = LocalTime.now();
                System.out.printf("\r%02d:%02d:%02d",
                                    now.getHour(),
                                    now.getMinute(),
                                    now.getSecond());
            } catch (InterruptedException e) {
                System.out.println("Some thing has interrupted");
            }
        }


        System.out.println("\n*Alarm Noises*");
        soundPlay(filePath);
    }

    private void soundPlay(String filePath){
        File file = new File(filePath);
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)){
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(3);
            System.out.println("Press \"Enter\" to close tha alarm.");
            scanner.nextLine();
            clip.close();
            scanner.close();
        }
        catch (UnsupportedAudioFileException e){
            System.out.println("The audio file is not supported.");
        }catch (LineUnavailableException e){
            System.out.println("The file is not available");
        }
        catch (IOException e){
            System.out.println("Error reading audio file");
        }
    }
}