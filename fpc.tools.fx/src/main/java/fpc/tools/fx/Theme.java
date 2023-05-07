package fpc.tools.fx;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Theme {

    public static final Theme EMPTY = new Theme("-","");

    public static Theme create(String name, String themeUrl) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid theme name '"+name+"'");
        }
        return new Theme(name,themeUrl);
    }

    String name;

    String themeUrl;

    public boolean isEmpty() {
        return themeUrl.isEmpty();
    }

    @Override
    public String toString() {
        return isEmpty()?"Default":name;
    }
}
