package com.cfao.app.util;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;

/**
 * Created by JP on 7/13/2017.
 */
public class ButtonUtil {
    public static void edit(Button... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.PENCIL);
        }
    }

    public static  void delete(Button ... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.TRASH);
        }
    }

    public static  void next(Button ... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.ARROW_RIGHT);
        }
    }

    public static  void print(Button ... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.PRINT);
        }
    }

    public static  void previous(Button ... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.ARROW_LEFT);
        }
    }

    public static  void cancel(Button ... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.SHARE_SQUARE);
        }
    }
    public static void add(Button ... buttons){
        for(Button button : buttons){
            GlyphsDude.setIcon(button, FontAwesomeIcon.FILE);
        }
    }
}
