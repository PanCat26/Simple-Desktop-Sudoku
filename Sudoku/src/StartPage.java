import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPage implements ActionListener
{
    public static JFrame startFrame;
    private JLabel label;
    private JButton button;

    StartPage()
    {
        startFrame=new JFrame();

        startFrame.setTitle("Sudoku");
        ImageIcon smallLogo=new ImageIcon("SmallLogo.png");
        startFrame.setIconImage(smallLogo.getImage());
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        startFrame.setMinimumSize(new Dimension(1000, 1200));
        startFrame.setLayout(new BoxLayout(startFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        startFrame.getContentPane().setBackground(new Color(0Xd6d6d6));

        startFrame.add(Box.createRigidArea(new Dimension(0, 50)));

        label=new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setText("Sudoku");
        label.setFont(new Font("Oswald", Font.BOLD, 80));
        label.setForeground(new Color(0X373748));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        ImageIcon largeLogo=new ImageIcon("LargeLogo.png");
        label.setIcon(largeLogo);
        label.setOpaque(false);
        startFrame.add(label);

        startFrame.add(Box.createRigidArea(new Dimension(0, 50)));

        button=new JButton();
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 100));
        button.setFocusPainted(false);
        button.setText("Play");
        button.setFont(new Font("Oswald", Font.BOLD, 60));
        button.setForeground(new Color(0X373748));
        button.setBackground(new Color(0Xffe891));
        UIManager.put("Button.select", new Color(0Xffd42a));
        button.setBorder(BorderFactory.createLineBorder(new Color(0X373748), 6, true));
        startFrame.add(button);

        startFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        startFrame.setVisible(false);
        GamePage.gameFrame.setVisible(true);
    }

}
