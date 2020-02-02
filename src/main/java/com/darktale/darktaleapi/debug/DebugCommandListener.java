package com.darktale.darktaleapi.debug;

import com.darktale.darktaleapi.event.command.APIRegisterCommandEvent;
import com.darktale.darktaleapi.listener.APIListener;

/**
 *
 * @author Ryan
 */
public class DebugCommandListener implements APIListener {

    public void onAPIRegisterCommand(APIRegisterCommandEvent event) {
        System.out.println("hello.");
    }
}
