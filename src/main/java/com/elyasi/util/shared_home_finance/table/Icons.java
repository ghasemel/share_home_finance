package com.elyasi.util.shared_home_finance.table;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import lombok.extern.slf4j.Slf4j;


class Icons {



    static final Icons INSTANCE = new Icons();

    private Icons() {
        /*ERROR = createIcon(Icons525.STOP_SIGN);
        ERROR.getStyleClass().add("error.error");

        WARNING = createIcon(EmojiOne.WARNING);
        WARNING.getStyleClass().add("warning");

        INFO = createIcon(Icons525.INFO_CIRCLE2);
        INFO.getStyleClass().add("info");*/
    }

    public GlyphIcon createIcon(GlyphIcons icon) {
        try {
            if (icon instanceof MaterialDesignIcon) {
                return new MaterialDesignIconView((MaterialDesignIcon) icon);
            } else if (icon instanceof FontAwesomeIcon) {
                return new FontAwesomeIconView((FontAwesomeIcon) icon);
            } else if (icon instanceof MaterialIcon) {
                return new MaterialIconView((MaterialIcon) icon);
            } else if (icon instanceof OctIcon) {
                return new OctIconView((OctIcon) icon);
            }

            throw new IllegalArgumentException(icon + " icon is not defined");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }
}
