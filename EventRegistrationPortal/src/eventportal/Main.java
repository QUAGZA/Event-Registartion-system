package eventportal;

import eventportal.gui.RegistrationFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Try to set Nimbus LAF for nicer look
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // ignore
        }

        // Dark theme tweaks
        UIManager.put("control", new Color(43, 43, 43));
        UIManager.put("info", new Color(43, 43, 43));
        UIManager.put("nimbusBase", new Color(18, 30, 49));
        UIManager.put("nimbusFocus", new Color(115,164,209));
        UIManager.put("nimbusLightBackground", new Color(60,63,65));
        UIManager.put("text", new Color(220,220,220));
        UIManager.put("Label.foreground", new Color(220,220,220));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));

        SwingUtilities.invokeLater(() -> {
            RegistrationFrame rf = new RegistrationFrame();
            rf.setVisible(true);
        });
    }
}
