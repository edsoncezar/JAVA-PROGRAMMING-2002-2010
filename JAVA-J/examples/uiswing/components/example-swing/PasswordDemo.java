import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordDemo {
    public static void main(String[] argv) {
        final JFrame f = new JFrame("PasswordDemo");

        JLabel label = new JLabel("Enter the password: ");
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setEchoChar('#');
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPasswordField input = (JPasswordField)e.getSource();
                char[] password = input.getPassword();
                if (isPasswordCorrect(password)) {
                    JOptionPane.showMessageDialog(f,
                        "Success! You typed the right password.");
                } else {
                    JOptionPane.showMessageDialog(f,
                        "Invalid password. Try again.",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.add(label, BorderLayout.WEST);
        contentPane.add(passwordField, BorderLayout.CENTER);

        f.setContentPane(contentPane);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        f.pack();
        f.setVisible(true);
    }

    private static boolean isPasswordCorrect(char[] input) {
        char[] correctPassword = { 'b', 'u', 'g', 'a', 'b', 'o', 'o' };
        if (input.length != correctPassword.length)
            return false;
        for (int i = 0;  i < input.length; i ++)
            if (input[i] != correctPassword[i])
                return false;
        return true;
    }
}
