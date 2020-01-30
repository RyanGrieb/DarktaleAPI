package com.darktale.darktaleapi.listener;

import com.darktale.darktaleapi.event.APIEvent;
import java.lang.reflect.Method;

/**
 *
 * @author Ryan
 */
public class APIListenerWrapper {

    private APIListener listener;
    private String name;

    public APIListenerWrapper(String name, APIListener listener) {
        this.name = name;
        this.listener = listener;
    }

    public void onEventCall(APIEvent event) {
        //Call all methods with their parameter being an instanceof the event
        Method[] methods = listener.getClass().getMethods();
        for (Method m : methods) {
            if (m.getParameterCount() <= 0 || m.getParameterCount() > 1) {
                continue;
            }

            //If the methods paramater matches the event, call it!
            if (m.getParameterTypes()[0].isInstance(event)) {
                try {
                    m.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getName() {
        return name;
    }
}
