package fpc.tools.springfx;

import fpc.tools.fxmodel.EmptyFXView;
import fpc.tools.fxmodel.FXView;
import fpc.tools.fxmodel.FXViewProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class SPringFXViewProvider implements FXViewProvider {

    @NonNull
    private final ApplicationContext applicationContext;

    @Override
    public @NonNull <C extends FXView> Optional<FXView> findFXView(@NonNull Class<? extends C> fxViewType) {
        if (fxViewType.equals(EmptyFXView.class)) {
            return Optional.ofNullable(EmptyFXView.create());
        }
        try {
            return Optional.of(applicationContext.getBean(fxViewType));
        } catch (BeansException e) {
            LOG.error("Could not find any FXView of type "+fxViewType,e);
            return Optional.empty();
        }
    }
}
