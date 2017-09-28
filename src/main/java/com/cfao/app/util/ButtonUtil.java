package com.cfao.app.util;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ResourceBundle;

/**
 * Created by JP on 7/13/2017.
 */
public class ButtonUtil {
    public static void edit(Button... buttons) {
        for (Button button : buttons) {
            /*GlyphsDude.setIcon(button, FontAwesomeIcon.PENCIL);*/
            button.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
            button.setGraphic(new ImageView(new Image("images/icons/edit.png")));
        }
    }

    public static void delete(Button... buttons) {
        for (Button button : buttons) {
            /*GlyphsDude.setIcon(button, FontAwesomeIcon.TRASH);*/
            button.setText(ResourceBundle.getBundle("Bundle").getString("button.delete"));
            button.setGraphic(new ImageView(new Image("images/icons/cross.png")));
        }
    }

    public static void next(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.ARROW_RIGHT);
        }
    }

    public static void print(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.PRINT);
        }
    }

    public static void previous(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.ARROW_LEFT);
        }
    }

    public static void cancel(Button... buttons) {
        for (Button button : buttons) {
            button.setText(ResourceBundle.getBundle("Bundle").getString("button.cancel"));
            GlyphsDude.setIcon(button, FontAwesomeIcon.SHARE_SQUARE);
        }
    }

    public static void add(Button... buttons) {
        for (Button button : buttons) {
            /*GlyphsDude.setIcon(button, FontAwesomeIcon.FILE);*/
            button.setText(ResourceBundle.getBundle("Bundle").getString("button.add"));
            button.setGraphic(new ImageView(new Image("images/icons/add.png")));
        }
    }

    public static void save(Button... buttons) {
        for (Button button : buttons) {
            /*GlyphsDude.setIcon(button, FontAwesomeIcon.FILE);*/
            button.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            button.setGraphic(new ImageView(new Image("images/icons/save.png")));
        }
    }

    public static void angle_up(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.ANGLE_UP);
        }
    }

    public static void angle_down(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.ANGLE_DOWN);
        }
    }

    public static void angle_double_up(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.ANGLE_DOUBLE_UP);
        }
    }

    public static void angle_double_down(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.ANGLE_DOUBLE_DOWN);
        }
    }

    public static void excel(Button... buttons) {
        for (Button button : buttons) {
            GlyphsDude.setIcon(button, FontAwesomeIcon.FILE_EXCEL_ALT);
        }
    }

    public static void excel(Hyperlink... hyperlinks) {
        for (Hyperlink hyperlink : hyperlinks) {
            GlyphsDude.setIcon(hyperlink, FontAwesomeIcon.FILE_EXCEL_ALT);
        }
    }

    public static void detailsTab(Tab... tabs) {
        for (Tab tab : tabs) {
            GlyphsDude.setIcon(tab, FontAwesomeIcon.BUILDING_ALT);
        }
    }

    public static void plusIcon(Button... buttons) {
        for (Button button : buttons) {
            //GlyphsDude.setIcon(button, FontAwesomeIcon.PLUS_SQUARE);
            button.setGraphic(new ImageView(new Image("images/icons/plus.png")));
        }
    }

    public static void minusIcon(Button... buttons) {
        for (Button button : buttons) {
            //GlyphsDude.setIcon(button, FontAwesomeIcon.MINUS_SQUARE);
            button.setGraphic(new ImageView(new Image("images/icons/minus.png")));
        }
    }
}
