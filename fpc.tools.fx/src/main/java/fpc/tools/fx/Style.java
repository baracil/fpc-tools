package fpc.tools.fx;

import lombok.NonNull;
import lombok.Value;

@Value
public class Style {
    @NonNull String name;
    @NonNull String sheetStylePath;
}
