
package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller extends Application {
    private Stage stage;
    @FXML
    private Pane pane1;
    @FXML
    TextArea CONSOLA;
    CodeArea codeArea = new CodeArea();

    public Controller() {
    }

    @FXML
    public void initialize() {
        this.codeArea.setParagraphGraphicFactory(LineNumberFactory.get(this.codeArea));
        this.codeArea.replaceText(0, 0, Configs.sampleCode);
        this.codeArea.setPrefSize(800.0D, 600.0D);
        Subscription cleanupWhenNoLongerNeedIt = this.codeArea.multiPlainChanges().successionEnds(Duration.ofMillis(500L)).subscribe((ignore) -> {
            this.codeArea.setStyleSpans(0, Configs.computeHighlighting(this.codeArea.getText()));
        });
        this.pane1.getChildren().add(this.codeArea);
    }

    public void evtSalir(ActionEvent event) {
        System.exit(0);
    }

    public void evtAbrir(ActionEvent evento) {
        FileChooser openfile = new FileChooser();
        openfile.setTitle("Abrir archivos");
        ExtensionFilter filtro = new ExtensionFilter("Archivos .dng", new String[]{"*.dng"});
        openfile.getExtensionFilters().add(filtro);
        openfile.showOpenDialog(this.stage);
    }

    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
    }

    public void ejecutar(ActionEvent e) {
        this.compilar();
    }

    public void compilar() {
        this.CONSOLA.setText("");
        long tInicial = System.currentTimeMillis();
        String texto = this.codeArea.getText();
        String[] renglones = texto.split("\\n");

        for(int x = 0; x < renglones.length; ++x) {
            boolean bandera = false;
            if (!renglones[x].trim().equals("")) {
                for(int y = 0; y < Configs.EXPRESIONES.length && !bandera; ++y) {
                    Pattern patron = Pattern.compile(Configs.EXPRESIONES[y]);
                    Matcher matcher = patron.matcher(renglones[x]);
                    if (matcher.matches()) {
                        bandera = true;
                    }
                }

                if (!bandera) {
                    this.CONSOLA.setText(this.CONSOLA.getText() + "\nNo existe ese instrumento o nota en línea " + (x + 1));
                }
            }
        }

        long tFinal = System.currentTimeMillis() - tInicial;
        this.CONSOLA.setText(this.CONSOLA.getText() + "\nCompilado en: " + tFinal + " milisegundos");
    }
}
