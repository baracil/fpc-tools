package fpc.tools.fx.dialog;

import fpc.tools.i18n.Dictionary;
import fpc.tools.validation.ValidationError;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Builder;
import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Builder
public class ControlInfo {

    private final Dictionary dictionary;

    private final Control control;

    private final ValidationDecoration validationDecoration = new CompoundValidationDecoration(new GraphDeco(), new StyleClassValidationDecoration());

    public void updateDecoration(List<ValidationError> errors) {
        final Optional<ValidationMessage> message = errors.stream()
                                                          .findFirst()
                                                          .map(ValidationError::getErrorType)
                                                          .map(t -> "error-type." + t)
                                                          .map(dictionary::value)
                                                          .map(m -> ValidationMessage.error(control, m));

        try {
            validationDecoration.removeDecorations(control);

            message.ifPresent(validationDecoration::applyValidationDecoration);
        } catch (Exception e) {
            throw e;
        }
    }

    private static class GraphDeco extends GraphicValidationDecoration {
        @Override
        protected Collection<Decoration> createValidationDecorations(ValidationMessage message) {
            final Node node=  createDecorationNode(message);
            final Image image = extractImage(node);
            final double xOffset;
            final double yOffset;
            if (image != null) {
                final double imageHeight = image.getHeight();
                final double imageWidth = image.getWidth();
                xOffset = -imageHeight/4;
                yOffset = +imageWidth/4;
            } else {
                xOffset = 0;
                yOffset = 0;
            }

            return List.of(new GraphicDecoration(node, Pos.TOP_RIGHT, xOffset, yOffset));
        }

        private @Nullable Image extractImage(@Nullable Node node) {
            if (node == null) {
                return null;
            }
            if (node instanceof ImageView) {
                return ((ImageView) node).getImage();
            }
            if (node instanceof Labeled) {
                return extractImage(((Labeled) node).getGraphic());
            }
            return null;
        }

    }

}
