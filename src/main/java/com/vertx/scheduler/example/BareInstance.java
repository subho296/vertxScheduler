package com.vertx.scheduler.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class BareInstance extends AbstractVerticle{

    public static void main(String[] args) {
        Launcher.main(new String[]{"-ha"});
    }
}
