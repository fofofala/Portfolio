import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class gameOver extends JPanel{
    int score; //displayText stores the label used to display game over text. score stores score for initializer.
    File file = new File("res\\score.txt"); //gets the score file.
    JLabel displayText;
    void drawText(){ //draws our text
        //uses html to display text in nice format
        //uses html to display text in nice format
        displayText = new JLabel("<html><center>GAME OVER!</center></html>");
        displayText.setForeground(Color.black);
        displayText.setFont(new Font("Calibri", Font.BOLD,50));
        setLayout(null);
        displayText.setBounds(constants.DIM_WIDTH/3,constants.DIM_HEIGHT/2,300,100);
        add(displayText);

    }
    void saveData() throws IOException{ //this saves our data into a text file.
        FileWriter write = new FileWriter(file,true);
        try {
            String name = JOptionPane.showInputDialog(this, "Your initials please?(2 characters):", //gets input from user from dialog
                    "Save your score!", JOptionPane.INFORMATION_MESSAGE);
            String line = name.toUpperCase() + "\t" + score + "\n";
            if (name.length() == 2) { //makes sure name is of length 2 otherwise doesn't save it
                write.write(line);
                JOptionPane.showMessageDialog(this, "Score saved!", //shows confirmation message
                        "Success!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Score was not saved :(",
                        "Failed to save", JOptionPane.INFORMATION_MESSAGE); //shows failure to save message.
            }
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(this,"You have to enter a name! Failed to save!");
        }
        write.close();
        remove(this);setVisible(false);
        //soundManager.play(soundManager.waitingMusic,true);
        MissileCommand.main.mainMenu();
    }

    public gameOver(int score) { //creates our window with the settings below, and draws our text, + calls saveData()
        setBackground(Color.blue);
        setSize(new Dimension(800, 600));
        setVisible(true);
        this.score =score;
        System.out.println(score);
        drawText();
        Timer timer = new Timer(3200, e-> {
            try {
                saveData();
            } catch (IOException ioException) { //added a timer here because JOptionPane overrides JPanel and this results in a blank frame
                ioException.printStackTrace();  //hence we need a little time for the Panel to set first for a quick fix.
            }
        });
        timer.start();timer.setRepeats(false);
        soundManager.play(soundManager.gameOver,false);
        Timer alternateColor= new Timer(750, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getBackground().equals(Color.red)){
                    setBackground(Color.blue);
                    displayText.setForeground(Color.black);
                }else{
                    setBackground(Color.red);
                    displayText.setForeground(Color.white);

                }
                int randomX = (int) (Math.random()*500);
                int randomY = (int) (Math.random()*400);
                displayText.setBounds(randomX,randomY,displayText.getWidth(),displayText.getHeight());
            }
        });alternateColor.start();

    }
}