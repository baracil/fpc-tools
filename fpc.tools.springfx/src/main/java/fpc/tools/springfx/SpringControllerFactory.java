package fpc.tools.springfx;

import fpc.tools.fx.ControllerFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

@RequiredArgsConstructor
@Slf4j
public class SpringControllerFactory implements ControllerFactory {

    private final BeanFactory beanFactory;

    @Override
    public Object getController(Class<?> controllerType) throws Exception {
        try {
            return beanFactory.getBean(controllerType);
        } catch (NoSuchBeanDefinitionException e) {
            try {
                return controllerType.getConstructor().newInstance();
            } catch (NoSuchMethodException nme) {
                throw new IllegalArgumentException("It seems that the controller "+controllerType+" should be a Spring bean. Forgot the @FXComponent annotation ?",nme);
            }
        }
    }
}
