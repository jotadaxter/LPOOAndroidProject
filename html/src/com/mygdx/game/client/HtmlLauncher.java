package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * The Class HtmlLauncher.
 */
public class HtmlLauncher extends GwtApplication {

        /**
         * Gets the config.
         *
         * @return the config
         */
        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        /**
         * Creates the application listener.
         *
         * @return the application listener
         */
        @Override
        public ApplicationListener createApplicationListener () {
                return new MyGame(false);
        }
}