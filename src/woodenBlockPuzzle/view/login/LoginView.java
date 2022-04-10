package woodenBlockPuzzle.view.login;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * In deze klasse wordt het login-scherm opgesteld
 *
 * @author Michel Matthe
 * @version 1.1
 */
public class LoginView extends GridPane {
    private Label lblUsername;
    private Label lblPassword;
    private TextField tfUsername;
    private TextField tfPassword;
    private Button btnLogin;
    private Button btnSignUp;

    public LoginView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    /**
     * Initialiseert alle nodes.
     */
    private void initialiseNodes() {
        lblUsername = new Label("Username: ");
        lblPassword = new Label("Password: ");
        tfUsername = new TextField();
        tfPassword = new TextField();
        btnLogin = new Button("Log In");
        btnSignUp = new Button("Sign up");
    }

    /**
     * In deze methode krijgen de nodes hun layout
     */
    private void layoutNodes() {
        this.add(lblUsername, 0, 0);
        this.add(tfUsername, 1, 0);
        this.add(lblPassword, 0, 1);
        this.add(tfPassword, 1, 1);
        this.add(btnLogin, 1, 2);
        this.add(btnSignUp, 1, 3);

        btnLogin.setPrefSize(200, 20);
        btnSignUp.setPrefSize(200, 20);

        ColumnConstraints columns = new ColumnConstraints(200);
        this.getColumnConstraints().addAll(columns, columns);

        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
    }

    Button getBtnLogin() {
        return btnLogin;
    }

    Button getBtnSignUp() {
        return btnSignUp;
    }

    TextField getTfUsername() {
        return tfUsername;
    }

    TextField getTfPassword() {
        return tfPassword;
    }
}
