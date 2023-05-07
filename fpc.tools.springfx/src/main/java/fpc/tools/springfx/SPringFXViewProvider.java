package fpc.tools.springfx;

import fpc.tools.fxmodel.EmptyFXView;
import fpc.tools.fxmodel.FXView;
import fpc.tools.fxmodel.FXViewProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class SPringFXViewProvider implements FXViewProvider {

    private final ApplicationContext applicationContext;

    @Override
    public <C extends FXView> Optional<FXView> findFXView(Class<? extends C> fxViewType) {
        if (fxViewType.equals(EmptyFXView.class)) {
            return Optional.of(EmptyFXView.create());
        }
        try {
            return Optional.of(applicationContext.getBean(fxViewType));
        } catch (BeansException e) {
            LOG.error("Could not find any FXView of type "+fxViewType,e);
            return Optional.empty();
        }
    }
}
