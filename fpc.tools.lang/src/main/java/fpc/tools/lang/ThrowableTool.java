package fpc.tools.lang;


import fpc.tools.fp.FPUtils;
import fpc.tools.fp.Function1;
import lombok.NonNull;

import java.util.Optional;


/**
 * @author Bastien Aracil
 */
public class ThrowableTool {

    public static final Function1<Throwable,String> ONE_LINE_MESSAGE_EXTRACTOR = ThrowableTool::oneLineMessage;

    public static boolean isCausedByInterruption(@NonNull Throwable throwable) {
        return FPUtils.isCausedByInterruption(throwable);
    }

    public static void interruptIfCausedByInterruption(@NonNull Throwable throwable) {
        FPUtils.interruptIfCausedByInterruption(throwable);
    }

    public static String oneLineMessage(@NonNull Throwable throwable) {
        final String classType = throwable.getClass().getSimpleName();
        final String message = throwable.getMessage();
        StringBuilder sb = new StringBuilder();
        sb.append(classType);
        if (message != null && !message.isEmpty()) {
            sb.append('(').append(message).append(')');
        }
        Optional.ofNullable(throwable.getCause())
                .ifPresent(c -> sb.append(" > ").append(oneLineMessage(c)));
        return sb.toString();
    }

    public static Object oneLineMessageLazy(@NonNull Throwable throwable) {
        return StringTool.toStringLazy(ThrowableTool::oneLineMessage, throwable);
    }


}
