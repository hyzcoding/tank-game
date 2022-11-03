package com.hyzcoding.tankgame.component;

import com.hyzcoding.tankgame.TankGame;

import java.io.InputStream;
import java.util.Objects;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class FileUtils {
    public static InputStream getFilePath(String name){
        return Objects.requireNonNull(TankGame.class.getClassLoader().getResourceAsStream(name));
    }
}
