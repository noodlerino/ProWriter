package view.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.DAOManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Libro;
import model.Proyecto;
import view.Main;

/**
 * Controlador de la vista que permite acceder a los libros que hay en un proyecto
 * 
 * @author Albert Araque, Francisco Jos� Ruiz
 * @version 1.0
 */
public class InsideProjectViewController implements Initializable {

	private static final int MAX_LENGTH = 20;
	private static final int[] PANE_SIZE = { 290, 340 };
	private static final int[] IMAGE_FIT = { 200, 230 };
	private static final int IMAGE_LAYOUT[] = { 45, 22 };
	private static final int FONT_SIZE = 14;
	private static final int LABEL_XLAY = 32;
	private static final int NLABEL_YLAY = 275;
	private static final int[] FLOWPANE_MARGIN = { 10, 8, 20, 8 };

	@FXML public FlowPane bookFlowPane;
	@FXML public Button addBookButton;
	@FXML public Button updateBookButton;
	@FXML public Button deleteBookButton;
	@FXML public Button displayBookButton;
	@FXML public Button backButton;
	@FXML public Label errorLabel;
	@FXML public Label selectedBookLabel;
	@FXML public Label projectNameDisplay;

	private MainViewController mainViewController;
	private Proyecto project;
	private Libro selectedBook;
	private Pane bookPane;

	/**
	 * M�todo para inicializar la clase
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		bookFlowPane.prefWidthProperty().bind(Main.getStage().widthProperty());
		bookFlowPane.prefHeightProperty().bind(Main.getStage().heightProperty());

		// Evento al hacer click al bot�n a�adir
		addBookButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Stage addBookDialog = new Stage();

				addBookDialog.initModality(Modality.APPLICATION_MODAL);
				addBookDialog.initStyle(StageStyle.UNDECORATED);
				addBookDialog.initOwner(Main.getStage());

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddBookView.fxml"));
				BorderPane dialogRoot = null;

				try {
					dialogRoot = fxmlLoader.load();
				} catch (IOException e) {
				}

				AddBookViewController addController = fxmlLoader.getController();
				addController.setProject(project);

				Scene dialogScene = new Scene(dialogRoot, 400, 750);
				addBookDialog.setScene(dialogScene);
				addBookDialog.showAndWait();
				selectedBook = null;
				selectedBookLabel.setText("Ning�n libro seleccionado");
				loadBooks();
			}
		});

		// Evento al hacer click al bot�n actualizar
		updateBookButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				if (selectedBook == null) {
					errorLabel.setVisible(true);
					return;
				}

				Stage updateProjectDialog = new Stage();

				updateProjectDialog.initModality(Modality.APPLICATION_MODAL);
				updateProjectDialog.initStyle(StageStyle.UNDECORATED);
				updateProjectDialog.initOwner(Main.getStage());

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UpdateBookView.fxml"));
				BorderPane dialogRoot = null;

				try {
					dialogRoot = fxmlLoader.load();
				} catch (IOException e) {
				}

				UpdateBookViewController updateController = fxmlLoader.getController();
				updateController.setBook(selectedBook);

				Scene dialogScene = new Scene(dialogRoot, 400, 750);
				updateProjectDialog.setScene(dialogScene);
				updateProjectDialog.showAndWait();
				selectedBook = null;
				selectedBookLabel.setText("Ning�n libro seleccionado");
				loadBooks();
			}
		});

		// Evento que borra el libro del set de libros del proyecto, y lo deja reflejado
		// en la base de datos
		deleteBookButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				if (selectedBook == null) {
					errorLabel.setVisible(true);
					return;
				}

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Eliminaci�n de libro");
				alert.setHeaderText("Est�s a punto de eliminar el libro");
				alert.setContentText("�Est�s seguro?");

				Optional<ButtonType> resultado = alert.showAndWait();
				if (resultado.get() == ButtonType.OK) {

					// Esta l�nea, si estuviera descomentada,
					// borrar�a el libro de la base de datos
					// DAOManager.getLibroDAO().removeLibro(selectedBook.getId());

					// Esta l�nea elimina el libro seleccionado del set de libros del proyecto
					project.getLibros().remove(selectedBook);

					// Ahora solo queda reflejarlo en la base de datos, con un update
					DAOManager.getProyectoDAO().updateProyecto(project);

					selectedBook = null;
					selectedBookLabel.setText("Ning�n libro seleccionado");
					loadBooks();

				}
			}
		});

		displayBookButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (selectedBook == null) {
					errorLabel.setVisible(true);
					return;
				}

				Stage displayBookDialog = new Stage();

				displayBookDialog.initModality(Modality.APPLICATION_MODAL);
				displayBookDialog.initStyle(StageStyle.UNDECORATED);
				displayBookDialog.initOwner(Main.getStage());

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DisplayBookView.fxml"));
				BorderPane dialogRoot = null;

				try {
					dialogRoot = fxmlLoader.load();
				} catch (IOException e) {
				}

				DisplayBookViewController displayController = fxmlLoader.getController();
				displayController.setBook(selectedBook);

				Scene dialogScene = new Scene(dialogRoot, 600, 770);
				displayBookDialog.setScene(dialogScene);
				displayBookDialog.showAndWait();
				selectedBook = null;
				selectedBookLabel.setText("Ningun libro seleccionado");
			}
		});

		backButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProjectView.fxml"));
				BorderPane borderPane = null;
				try {
					borderPane = fxmlLoader.load();
				} catch (IOException e) {
				}

				ProjectViewController projectViewController = fxmlLoader.getController();
				projectViewController.setController(mainViewController);

				mainViewController.setView(borderPane);

			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				projectNameDisplay.setText(project.getNombre());
				loadBooks();
			}
		});

	}

	/**
	 * M�todo para mostrar el libro en el panel
	 * 
	 * @param l Libro de entrada
	 */
	public void convertBookToPane(Libro l) {

		if (l == null)
			return;

		// Crea el contenedor (Pane) donde va la informaci�n
		bookPane = new Pane();

		// Si el libro tiene una imagen, la a�ade, si no, coge una por defecto
		ImageView projectImage;
		File imageFile = null;

		try {
			imageFile = new File(l.getImagen());
		} catch (Exception e) {
		}
		if (l.getImagen() == null || l.getImagen().equals("") || !imageFile.exists())
			projectImage = new ImageView("resources/libro.png");
		else {
			projectImage = new ImageView(new Image(imageFile.toURI().toString()));
		}

		Label nameLabel = new Label();
		Label characterLabel = new Label();

		// Establece el margen de cada contenedor
		FlowPane.setMargin(bookPane,
				new Insets(FLOWPANE_MARGIN[0], FLOWPANE_MARGIN[1], FLOWPANE_MARGIN[2], FLOWPANE_MARGIN[3]));

		// Establece las medidas del contenedor y todo lo que haya dentro de �ste
		bookPane.setPrefSize(PANE_SIZE[0], PANE_SIZE[1]);
		bookPane.getStyleClass().add("pane");

		projectImage.setFitHeight(IMAGE_FIT[0]);
		projectImage.setFitWidth(IMAGE_FIT[1]);
		projectImage.setLayoutX(IMAGE_LAYOUT[0]);
		projectImage.setLayoutY(IMAGE_LAYOUT[1]);
		projectImage.setPickOnBounds(true);
		projectImage.setPreserveRatio(true);

		if (l.getNombre().length() > MAX_LENGTH)
			nameLabel.setText("Nombre: " + l.getNombre().substring(0, MAX_LENGTH) + "...");
		else
			nameLabel.setText("Nombre: " + l.getNombre());
		nameLabel.setLayoutX(LABEL_XLAY);
		nameLabel.setLayoutY(NLABEL_YLAY);
		nameLabel.setFont(new Font(FONT_SIZE));

		characterLabel.setText("Numero de personajes: " + l.getPersonajes().size());
		characterLabel.setLayoutX(LABEL_XLAY);
		characterLabel.setLayoutY(NLABEL_YLAY + 10);

		bookPane.getChildren().add(nameLabel);
		bookPane.getChildren().add(projectImage);
		bookFlowPane.getChildren().add(bookPane);

		// Evento para seleccionar el objeto
		bookPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (event.getClickCount() == 1) {
					selectedBook = l;
					selectedBookLabel.setText("Libro seleccionado: " + l.getNombre());
					if (errorLabel.isVisible())
						errorLabel.setVisible(false);
				}

				if (event.getClickCount() == 2) {

					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/InsideBookView.fxml"));
					BorderPane borderPane = null;
					try {
						borderPane = fxmlLoader.load();
					} catch (IOException e) {
					}

					InsideBookViewController insideBookViewController = fxmlLoader.getController();
					insideBookViewController.setProject(project);
					insideBookViewController.setBook(selectedBook);
					insideBookViewController.setController(mainViewController);

					mainViewController.setView(borderPane);
				}
			}
		});
	}

	/**
	 * M�todo para cargar los libros en el panel
	 */
	private void loadBooks() {
		bookFlowPane.getChildren().removeAll(bookFlowPane.getChildren());

		for (Libro l : project.getLibros()) {
			convertBookToPane(l);
		}
	}

	/**
	 * M�todo para seleccionar el controlador
	 * 
	 * @param controller Controlador de entrada
	 */
	public void setController(MainViewController controller) {
		mainViewController = controller;
	}

	/**
	 * M�todo para seleccionar el proyecto
	 * 
	 * @param p Proyecto de entrada
	 */
	public void setProyecto(Proyecto p) {
		project = p;
	}

}
