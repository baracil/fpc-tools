package fpc.tools.fx.dialog;

import fpc.tools.fp.Consumer1;
import fpc.tools.i18n.Dictionary;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DialogInfoExtractor<O> {

    public static <O> DialogInfo<O> extract(Dictionary dictionary,
                                            DialogController<?, O> controller) {
        return new DialogInfoExtractor<>(dictionary, controller, controller).extract();
    }

    private final Dictionary dictionary;

    private final DialogController<?, O> dialogController;

    private final DialogResultHandler<O> resultHandler;

    private final DialogInfo.Builder<O> builder = DialogInfo.<O>builder();

    private DialogInfo<O> extract() {
        final Field[] fields = dialogController.getClass().getDeclaredFields();
        Arrays.stream(fields)
              .filter(f -> Node.class.isAssignableFrom(f.getType()))
              .forEach(this::parseField);

        return builder.dialogController(dialogController)
                      .build();
    }

    private void parseField(Field f) {
        this.checkButton(f, CancelButton.class, builder::cancelButton);
        this.checkButton(f, ApplyButton.class, builder::applyButton);
        this.checkButton(f, ValidateButton.class, builder::validateButton);

        this.checkValidatable(f);
    }

    private void checkValidatable(Field f) {
        final Optional<String> name = getValidatableFieldName(f);
        name.ifPresent(n -> getControlInfoFromField(f).ifPresent(c -> builder.validatableField(n, c)));
    }

    private Optional<String> getValidatableFieldName(Field field) {
        final ValidatableField annotation = field.getAnnotation(ValidatableField.class);
        if (annotation == null) {
            return Optional.empty();
        }
        final String name = annotation.value();
        if (name.equals(ValidatableField.NO_VALUE)) {
            return Optional.of(field.getName());
        }
        return Optional.of(name);
    }


    private void checkButton(Field field, Class<? extends Annotation> annotation,
                             Consumer1<Button> consumer) {
        if (field.isAnnotationPresent(annotation)) {
            getButtonFromField(field).ifPresent(consumer);
        }
    }

    private Optional<Button> getButtonFromField(Field f) {
        return getValueFromField(f, Button.class);
    }

    private Optional<ControlInfo> getControlInfoFromField(Field f) {
        return getValueFromField(f, Control.class)
                .map(c -> new ControlInfo(dictionary, c));
    }

    private <T> Optional<T> getValueFromField(Field f, Class<T> expectedType) {
        if (expectedType.isAssignableFrom(f.getType())) {
            boolean accessible = f.canAccess(dialogController);
            try {
                if (!accessible) {
                    f.setAccessible(true);
                }
                return Optional.of(expectedType.cast(f.get(dialogController)));
            } catch (Exception e) {
                return Optional.empty();
            } finally {
                if (!accessible) {
                    f.setAccessible(false);
                }
            }
        }
        return Optional.empty();
    }

}
