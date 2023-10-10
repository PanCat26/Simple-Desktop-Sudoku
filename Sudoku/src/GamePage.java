import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class GamePage implements ActionListener, FocusListener
{
    public static JFrame gameFrame;
    private JPanel panel;
    private JTextField[][] cells;
    private int values[][];
    private JLabel alert;
    private JButton submitButton, restartButton;
    GamePage()
    {
        gameFrame=new JFrame();

        gameFrame.setTitle("Sudoku");
        ImageIcon smallLogo=new ImageIcon("SmallLogo.png");
        gameFrame.setIconImage(smallLogo.getImage());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setMinimumSize(new Dimension(1000, 1200));
        gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        gameFrame.getContentPane().setBackground(new Color(0Xd6d6d6));

        //rigid area
        gameFrame.add(Box.createRigidArea(new Dimension(0, 50)));

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(9, 9));
        panel.setMaximumSize(new Dimension(540, 540));
        cells=new JTextField[9][9];
        values=new int[9][9];
        generateValues();
        for (int i=0;i<9;++i)
            for (int j=0;j<9;++j)
            {
                cells[i][j]=new JTextField();
                cells[i][j].addFocusListener(this);
                cells[i][j].setActionCommand(Integer.toString(i*10+j));
                cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cells[i][j].setFont(new Font("Oswald", Font.BOLD, 25));
                cells[i][j].setForeground(new Color(0X373748));
                cells[i][j].setDisabledTextColor(new Color(0X285582));
                cells[i][j].setBackground(new Color(0Xffe891));
                if (values[i][j]!=0)
                {
                    cells[i][j].setText(Integer.toString(values[i][j]));
                    cells[i][j].setEnabled(false);
                }
                panel.add(cells[i][j]);
            }
        cells[0][0].setBorder(BorderFactory.createMatteBorder(4, 4, 1, 1, new Color(0X373748)));
        cells[0][8].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 4, new Color(0X373748)));
        cells[8][0].setBorder(BorderFactory.createMatteBorder(1, 4, 4, 1, new Color(0X373748)));
        cells[8][8].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 4, new Color(0X373748)));
        for (int i=2;i<=5;i+=3)
        {
            cells[0][i].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 2, new Color(0X373748)));
            cells[8][i].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 2, new Color(0X373748)));
            cells[i][0].setBorder(BorderFactory.createMatteBorder(1, 4, 2, 1, new Color(0X373748)));
            cells[i][8].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 4, new Color(0X373748)));

            for (int j=2;j<=5;j+=3)
                cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, new Color(0X373748)));
            for (int j=3;j<=6;j+=3)
                cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 2, 2, 1, new Color(0X373748)));
        }
        for (int i=3;i<=6;i+=3)
        {
            cells[0][i].setBorder(BorderFactory.createMatteBorder(4, 2, 1, 1, new Color(0X373748)));
            cells[8][i].setBorder(BorderFactory.createMatteBorder(1, 2, 4, 1, new Color(0X373748)));
            cells[i][0].setBorder(BorderFactory.createMatteBorder(2, 4, 1, 1, new Color(0X373748)));
            cells[i][8].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 4, new Color(0X373748)));

            for (int j=2;j<=5;j+=3)
                cells[i][j].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 2, new Color(0X373748)));
            for (int j=3;j<=6;j+=3)
                cells[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 1, 1, new Color(0X373748)));
        }
        for (int i=1;i<=7;i+=3)
        {
            cells[0][i].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 1, new Color(0X373748)));
            cells[8][i].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, new Color(0X373748)));
            cells[i][0].setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, new Color(0X373748)));
            cells[i][8].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 4, new Color(0X373748)));
            for (int j=1;j<=7;j+=3)
                cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0X373748)));
            for (int j=2;j<=5;j+=3)
            {
                cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, new Color(0X373748)));
                cells[j][i].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, new Color(0X373748)));
            }
            for (int j=3;j<=6;j+=3)
            {
                cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 2, 1, 1, new Color(0X373748)));
                cells[j][i].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 1, new Color(0X373748)));
            }
        }
        gameFrame.add(panel);

        gameFrame.add(Box.createRigidArea(new Dimension(0, 10)));
        alert=new JLabel();
        alert.setAlignmentX(Component.CENTER_ALIGNMENT);
        alert.setHorizontalTextPosition(JLabel.CENTER);
        alert.setVerticalTextPosition(JLabel.CENTER);
        alert.setText(" ");
        alert.setFont(new Font("Oswald", Font.BOLD, 20));
        alert.setOpaque(false);
        gameFrame.add(alert);
        gameFrame.add(Box.createRigidArea(new Dimension(0, 10)));

        submitButton=new JButton();
        submitButton.addActionListener(this);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setMaximumSize(new Dimension(200, 80));
        submitButton.setFocusPainted(false);
        submitButton.setText("Submit");
        submitButton.setFont(new Font("Oswald", Font.BOLD, 40));
        submitButton.setForeground(new Color(0X373748));
        submitButton.setBackground(new Color(0Xffe891));
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(0X373748), 6, true));
        gameFrame.add(submitButton);

        gameFrame.add(Box.createRigidArea(new Dimension(0, 25)));

        restartButton=new JButton();
        restartButton.addActionListener(this);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setMaximumSize(new Dimension(200, 80));
        restartButton.setFocusPainted(false);
        restartButton.setText("Restart");
        restartButton.setFont(new Font("Oswald", Font.BOLD, 40));
        restartButton.setForeground(new Color(0X373748));
        restartButton.setBackground(new Color(0Xffe891));
        restartButton.setBorder(BorderFactory.createLineBorder(new Color(0X373748), 6, true));
        gameFrame.add(restartButton);
    }

    private void generateValues()// throws FileNotFoundException
    {
        File file=new File("PossibleValues.txt");
        Scanner scanner;
        try
        {
            scanner=new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        int n=new Random().nextInt(50);
        for (int i=1;i<=n;++i)
            scanner.nextLine();
        String s=scanner.nextLine();
        int k=-1;
        for (int i=0;i<9;++i)
            for (int j=0;j<9;++j)
            {
                ++k;
                if (s.charAt(k)=='.')
                    values[i][j]=0;
                else
                    values[i][j]=s.charAt(k)-'0';
            }
    }

    boolean isComplete()
    {
        for (int i=0;i<9;++i)
            for (int j=0;j<9;++j)
                if (cells[i][j].getText().isBlank())
                    return  false;
        return true;
    }
    private boolean hasOnlyDigits()
    {
        for (int i=0;i<9;++i)
            for (int j=0;j<9;++j)
            {
                for (char ch:cells[i][j].getText().toCharArray())
                    if ("123456789 ".indexOf(ch)==-1)
                        return false;
            }
        return true;
    }

    private void Assign()
    {
        for (int i=0;i<9;++i)
            for (int j=0;j<9;++j)
                values[i][j]=Integer.parseInt(cells[i][j].getText());
    }
    private boolean isCorrect()
    {
        for (int k=1;k<=9;++k)
        {
            for (int i=0;i<9;++i)
            {
                int cnt=0;
                for (int j=0;j<9;++j)
                    if (values[i][j]==k)
                        ++cnt;
                if (cnt!=1)
                    return false;
                cnt=0;
                for (int j=0;j<9;++j)
                    if (values[j][i]==k)
                        ++cnt;
                if (cnt!=1)
                    return false;
            }
            for (int i=0;i<=6;i+=3)
                for (int j=0;j<=6;j+=3)
                {
                    int cnt=0;
                    for (int l=i;l<=i+2;++l)
                        for (int c=j;c<=j+2;++c)
                            if (values[l][c]==k)
                                ++cnt;
                    if (cnt!=1)
                        return false;
                }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==submitButton)
        {
            if (isComplete())
            {
                if (hasOnlyDigits())
                {
                    Assign();
                    if (isCorrect())
                    {
                        alert.setForeground(new Color(0X61a03F));
                        alert.setText("Congratulations!");
                    }
                    else
                    {
                        alert.setForeground(new Color(0X9a0000));
                        alert.setText("Incorrect. Try again!");
                    }
                }
                else
                {
                    alert.setForeground(new Color(0X9a0000));
                    alert.setText("Invalid values!");
                }
            }
            else
            {
                alert.setForeground(new Color(0X9a0000));
                alert.setText("Incomplete!");
            }
        }
        else if (e.getSource()==restartButton)
        {
            alert.setText(" ");
            generateValues();
            for (int i=0;i<9;++i)
                for (int j=0;j<9;++j)
                {
                    if (values[i][j]!=0)
                    {
                        cells[i][j].setText(Integer.toString(values[i][j]));
                        cells[i][j].setEnabled(false);
                    }
                    else
                    {
                        cells[i][j].setText("");
                        cells[i][j].setEnabled(true);
                    }
                }
        }
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        int line=e.getComponent().getY()/60, column=e.getComponent().getX()/60;
        for (int i=0;i<9;++i)
        {
            cells[line][i].setBackground(new Color(0Xffd42a));
            cells[i][column].setBackground(new Color(0Xffd42a));
        }
        for (int i=line-line%3;i<=line-line%3+2;++i)
            for (int j=column-column%3;j<=column-column%3+2;++j)
                cells[i][j].setBackground(new Color(0Xffd42a));
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        int line=e.getComponent().getY()/60, column=e.getComponent().getX()/60;
        for (int i=0;i<9;++i)
        {
            cells[line][i].setBackground(new Color(0Xffe891));
            cells[i][column].setBackground(new Color(0Xffe891));
        }
        for (int i=line-line%3;i<=line-line%3+2;++i)
            for (int j=column-column%3;j<=column-column%3+2;++j)
                cells[i][j].setBackground(new Color(0Xffe891));
    }
}
