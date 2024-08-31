package android.os;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(Looper.class)
public class LooperHidden {
    public static void setObserver(Observer observer) {
        throw new RuntimeException("Stub!");
    }

    public interface Observer {
        Object messageDispatchStarting();
        void messageDispatched(Object token, Message msg);
        void dispatchingThrewException(Object token, Message msg, Exception exception);
    }
}
