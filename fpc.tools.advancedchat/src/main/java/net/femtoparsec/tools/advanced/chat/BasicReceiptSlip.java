package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.advanced.chat.Request;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class BasicReceiptSlip<A> implements ReceiptSlip<A> {

    @NonNull Instant dispatchingTime;

    @NonNull Instant receptionTime;

    @NonNull Request<A> sentRequest;

    @NonNull A answer;

}
