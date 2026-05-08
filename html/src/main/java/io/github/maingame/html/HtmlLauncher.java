package io.github.maingame.html;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import io.github.maingame.core.Main;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Canvas matches itch.io iframe — FitViewport handles scaling to virtual 1920x1080
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(960, 540);
        config.antialiasing = true;
        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        // Hide loading overlay once the game starts
        Element loading = Document.get().getElementById("loading");
        if (loading != null) {
            loading.getStyle().setDisplay(Style.Display.NONE);
        }
        return new Main();
    }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        return new Preloader.PreloaderCallback() {
            @Override
            public void update(Preloader.PreloaderState state) {
                // Update the progress bar in index.html
                Element progress = Document.get().getElementById("progress");
                if (progress != null) {
                    float percent = state.getProgress() * 100f;
                    progress.getStyle().setWidth(percent, Style.Unit.PCT);
                }
            }

            @Override
            public void error(String file) {
                GWT.log("Preloader error loading: " + file);
            }
        };
    }
}
