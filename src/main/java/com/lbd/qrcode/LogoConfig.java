package com.lbd.qrcode;

import java.awt.*;

/**
 * @Author wenzhd
 * @Date 2018/5/9
 * @Description
 */
public class LogoConfig {
    /**
     * logo默认边框颜色
     */
    public static final Color DEFAULT_BORDER_COLOR = Color.WHITE;
    /**
     * logo默认边框宽度
     */
    public static final int DEFAULT_BORDER = 2;
    /**
     * logo大小默认为照片的1/6
     */
    public static final int DEFAULT_LOGO_PART = 6;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    /**
     * Creates a default config with on color {@link #BLACK} and off color
     * {@link #WHITE}, generating normal black-on-white barcodes.
     */
    public LogoConfig() {
        this(DEFAULT_BORDER_COLOR, DEFAULT_LOGO_PART);
    }

    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}
