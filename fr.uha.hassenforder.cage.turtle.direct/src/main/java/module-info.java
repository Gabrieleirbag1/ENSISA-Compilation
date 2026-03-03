module fr.uha.hassenforder.cage.turtle.direct {
    requires fr.uha.page.runtime;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires javafx.base;
	requires java.desktop;

    opens fr.uha.hassenforder.cage.turtle.direct.view to javafx.fxml;
    exports fr.uha.hassenforder.cage.turtle.direct;
}
