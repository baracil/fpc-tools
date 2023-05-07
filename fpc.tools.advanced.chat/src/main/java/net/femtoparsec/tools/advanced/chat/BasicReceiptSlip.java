package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.advanced.chat.Request;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class BasicReceiptSlip<A> implements ReceiptSlip<A> {

    Instant dispatchingTime;

    Instant receptionTime;

    Request<A> sentRequest;

    A answer;

}
