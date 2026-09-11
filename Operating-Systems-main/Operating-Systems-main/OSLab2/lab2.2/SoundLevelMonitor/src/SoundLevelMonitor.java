import java.io.*;
import java.util.concurrent.TimeUnit;

public class SoundLevelMonitor {
    public static class Range {
        private static final int LOW_MIN = 40;
        private static final int LOW_MAX = 60;
        private static final int MEDIUM_MIN = 60;
        private static final int MEDIUM_MAX = 80;

        public String checkLevel(double number) {
            if (number >= LOW_MIN && number < LOW_MAX) {
                return "Low";
            } else if (number >= LOW_MAX && number < MEDIUM_MAX) {
                return "Medium";
            } else {
                return "High";
            }
        }
    }

    public static void main(String[] args) {
        BufferedWriter writer = null;
        BufferedReader reader = null;
        try {
            writer = new BufferedWriter(new FileWriter("C:/Users/user/noisepollution.txt"));

            while (true) {
                reader = new BufferedReader(new FileReader("C:/Users/user/soundlevel.txt"));
                int sum = 0;
                int counter = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] levels = line.split(" ");
                    for (String level : levels) {
                        sum += Integer.parseInt(level);
                        counter++;
                    }
                }
                double avg = (double) sum / counter;

                Range range = new Range();
                String level = range.checkLevel(avg);

                writer.write(level);
                writer.newLine();
                writer.flush();
                TimeUnit.SECONDS.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}