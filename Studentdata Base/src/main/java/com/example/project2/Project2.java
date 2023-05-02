package com.example.project2;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class Project2 extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb_majrashi_alsudais", "root", "azooz1422");//creat a conniction;
        PreparedStatement stmt;// creat a Prepared Statement

        stmt = con.prepareStatement("select * from studentstbl_rayan_abdulazziz;");//creat the read cpmmand from sql
        ResultSet RS = stmt.executeQuery();//creat a result set
        stmt = con.prepareStatement("insert into studentstbl_rayan_abdulazziz(FullName,DateOfBirth,GPA) values(?,?,?)q");//creat the insert cpmmand from sql

        BorderPane pane = new BorderPane();//creat a borderPane
        TabPane tabPane  = new TabPane();//creat a TabPane

        pane.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(pane, 800, 600);
        Tab tab1 = new Tab("Add Student");//give the tab a title
        tabPane.getTabs().add(tab1);//inseert the new tab in the TabPane
        pane.setCenter(tabPane);
        tab1.setClosable(false);


        GridPane pane1 = new GridPane();//creat a gridPane
        pane1.setAlignment(Pos.CENTER);//center the gridpane
        pane1.setHgap(5);
        pane1.setVgap(5);

        GridPane pane2 = new GridPane();//creat a gridPane
        pane2.setAlignment(Pos.CENTER);//center the gridpane
        pane2.setHgap(5);
        pane2.setVgap(5);

        tab1.setContent(pane2);//adding the gridPane into the tab

        Tab tab2 = new Tab("Search/Data Table");//set a title for the tab
        tabPane.getTabs().add(tab2);//adding it to the tab pane
        pane.setCenter(tabPane);
        tab2.setClosable(false);

        tab2.setContent(pane1);//insert the gridPane into the tab

        stage.setTitle("Abdulaziz Alsidais ("+441010036+") Rayan Majrashi ("+441009006+") Group:1");//set the tital of the window

        Label insert = new Label("Insert A Student");
        Label Serach = new Label("Search For AStudent");


        insert.setFont(Font.font("itaic", FontWeight.BOLD,16));


        Serach.setFont(Font.font("itaic", FontWeight.BOLD,16));


        pane2.setFillWidth(insert, true);
        pane2.add(insert,0,0,2,1);
        pane1.add(Serach,0,0,2,1);


        TableView tableView = new TableView<Student>();//creat a tableView

        TableColumn id = new TableColumn<Student,Integer>("Student ID");//creat a column and set it's title
        id.setCellValueFactory(new PropertyValueFactory<Student,Integer>("ID"));//set the type of data in the column from class student

        TableColumn name =new TableColumn<Student,String>("Student Name");//creat a column and set it's title
        name.setCellValueFactory(new PropertyValueFactory<Student,String>("Name"));//set the type of data in the column from class student

        TableColumn birthDay = new TableColumn<Student, Date>("Date of Birth");//creat a column and set it's title
        birthDay.setCellValueFactory(new PropertyValueFactory<Student,Date>("BH"));//set the type of data in the column from class student

        TableColumn gpa =new TableColumn<Student, Double>("GPA");//creat a column and set it's title
        gpa.setCellValueFactory(new PropertyValueFactory<Student,Float>("GPA"));//set the type of data in the column from class student

        tableView.getColumns().add(id);//inserting the column into the table
        tableView.getColumns().add(name);//inserting the column into the table
        tableView.getColumns().add(birthDay);//inserting the column into the table
        tableView.getColumns().add(gpa);//inserting the column into the table
        pane1.add(tableView,0,4,2,1);//insert the table into the gridPane with a column span

        Label lb = new Label("Search by Name");
        TextField sc = new TextField();


        pane1.add(lb,0,2);//insert the label into the gridpane
        pane1.add(sc,1,2);//insert the label into the gridpane

        HBox hBox = new HBox();

        Button scb = new Button("Search");

        Button ref = new Button("Refreash");

        Button exit = new Button("Exit");//creating three buttons

        hBox.getChildren().add(scb);
        hBox.getChildren().add(ref);
        hBox.getChildren().add(exit);//adding the buttons into the hbox

        pane1.add(hBox,0,3,2,1);//adding the hbox into the gridpane with column span
        hBox.setSpacing(100);//set spacing for the hbox

        Label resalt = new Label("");//creatinng the result lable
        pane1.add(resalt,0,5,2,1);//inserting the lable into the gridPane with column span


        while (RS.next()) {//creating a while loop that ends once the result set is empty and inside the loop we insert every student record into the table
            tableView.getItems().add(new Student(RS.getInt(1), RS.getString(2), RS.getDate(3), RS.getFloat(4)));

        }

            scb.setOnAction(e->{//handling the search button

            try {
                Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb_majrashi_alsudais", "root", "azooz1422");//creating a new connection becuse of the try
            String sname = sc.getText();//ectract the text from the text feild
            String x = "%"+sname+"%";//put the name the user enter into th rihght format

                PreparedStatement stmt1;

                stmt1 = con1.prepareStatement("select * from studentstbl_rayan_abdulazziz where FullName LIKE ?;");//pcreating the sql command

            stmt1.setString(1,x);//put the String in

                ResultSet rs = stmt1.executeQuery();//execute the query

            if (rs.next()){
                tableView.getItems().clear();//clear the table to avoid duplicat
                tableView.getItems().add(new Student(rs.getInt(1),rs.getString(2),rs.getDate(3),rs.getFloat(4)));
                //insert the first record

                while (rs.next()) {//insert in all the records in the data base into the table
                    tableView.getItems().add(new Student(rs.getInt(1),rs.getString(2),rs.getDate(3),rs.getFloat(4)));

                }

                resalt.setText("This is the result found on your search");//set the result lable for success
                resalt.setFont(Font.font("itaic", FontWeight.BOLD,16));//set style
                resalt.setTextFill(Color.GREEN);


            }else {

                resalt.setText("");
            resalt.setText("There is no record with this Name");//set the lable for error
            resalt.setFont(Font.font("itaic", FontWeight.BOLD,16));//set style
            resalt.setTextFill(Color.RED);


            }
            sc.setText("");//clear the text feild
            rs.close();//close conection and resultSet
            con1.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        ref.setOnAction(e->{
            tableView.getItems().clear();//clear the table to avoid duplicat
            resalt.setText("");
            resalt.setText("Refresh successfully ");
            resalt.setFont(Font.font("itaic", FontWeight.BOLD,16));
            resalt.setTextFill(Color.GREEN);


            try {
                Connection con1= DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb_majrashi_alsudais", "root", "azooz1422");//creat a conniction;
                PreparedStatement stmt2;//open connection and preparStatment
                stmt2 = con1.prepareStatement("select * from studentstbl_rayan_abdulazziz;");//setting the qury
                ResultSet RS2 = stmt2.executeQuery();
            while (RS2.next()) {
                    //inserting the recordes frrom the dataBAse
                    tableView.getItems().add(new Student(RS2.getInt(1), RS2.getString(2), RS2.getDate(3), RS2.getDouble(4)));
                }
                stmt2.close();//close al conniction with the database
                RS2.close();
                con1.close();
            }catch (SQLException ex) {
                throw new RuntimeException(ex);
            }



        });

        PreparedStatement finalStmt = stmt;
        exit.setOnAction(e->{//exite hanndler
            try {
            finalStmt.close();
            RS.close();
                con.close();//close every connections
                System.exit(0);//close the system
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Label NAME = new Label("Name:");
        TextField TN = new TextField();
        pane2.add(NAME,0,2);//adding the label and the text feild into the gridpane
        pane2.add(TN,1,2);
        Label bd = new Label("Date of Birth :");
        DatePicker datePicker = new DatePicker();
        pane2.add(bd,0,3);
        pane2.add(datePicker,1,3);//adding the label and the Date Picker into the gridpane

        Label lgpa = new Label("GPA");
        Slider slider = new Slider(0,5,0);//creating a slider
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(0.001);//set the block increment to 0.001
        Label sv = new Label(" ");//setting the lable that will show the vaule the the slider at

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sv.setText(String.valueOf(Double.parseDouble(String.format("%.3f",slider.getValue()))));
                //set the text vaule an set it to show onle three decimal points
            }
        });


        HBox h = new HBox();//creating an hbox
        h.getChildren().add(lgpa);
        h.getChildren().add(slider);
        h.getChildren().add(sv);//inset the lable and the slider and the lable for the slider value onto th hBox
        h.setSpacing(55);//set spacing for the Hbox

        pane2.add(h,0,4,2,1);//inserting the Hbox into the gridPane

        Button save = new Button("Save");
        Button exb = new Button("Exit");//crreating the save button and the exite button

        HBox H = new HBox();

        H.getChildren().add(save);
        H.getChildren().add(exb);//adding the into a new Hbox
        H.setSpacing(100);//set spacing for the Hbox

        pane2.add(H,0,5,2,1);//insert the Hbx into the GridPane

        exb.setOnAction(e->{//exite handler
            try {
                finalStmt.close();
                RS.close();
                con.close();//clsoe all conniction with the dataBase
                System.exit(0);//close the system
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        Label re = new Label("");//creating a result lable
        re.setFont(Font.font("itaic", FontWeight.BOLD,16));//set style
        pane2.add(re,0,6,2,1);//insert the result into the gridPane with column span

        save.setOnAction(e->{//save Handler
            LocalDate Date = null;
            try {
            Connection con1 =  DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb_majrashi_alsudais", "root", "azooz1422");//creat a conniction;
            PreparedStatement stmt1;//creating connicton and preparedStatment

                stmt1 = con1.prepareStatement("insert into studentstbl_rayan_abdulazziz(FullName,DateOfBirth,GPA) values(?,?,?)");//make the qury

                System.out.println(datePicker.getValue());
                String x =TN.getText();//getting the text from the name field
                if (x.length()<=0){//if statment to check if the name field is empty
                re.setText("The name can not be empty ");//setting the result lable
                re.setTextFill(Color.RED);//and color to red
            }else{
            try {
                Date = datePicker.getValue();

            }catch (NullPointerException nul){
                re.setTextFill(Color.RED);//setting the result text and color for an error
                re.setText("Please pick a date before you save ");
            }
            double g= Double.valueOf(sv.getText());//gitting the value from the slider
                    System.out.println(g);
                    stmt1.setString(1,x);
            stmt1.setDate(2, java.sql.Date.valueOf(Date));
            stmt1.setDouble(3,g);//inset all the data into the dataBase
                re.setTextFill(Color.GREEN);
                re.setText("Recored inserted succsfliy ");//setting the text color to green and sitting the text to succs
                stmt1.execute();
                stmt1.close();
                con1.close();//close al dataBase connction

        }} catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });




        stage.setScene(scene);

        stage.show();
    }
    public static java.sql.Date date(String d) {
        try {
            DateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date dobj = dformat.parse(d);
            long timeInMilli = dobj.getTime();
            //create sql Data object
            java.sql.Date dob = new java.sql.Date(timeInMilli);
            return dob;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }

    }



    public static void main(String[] args) {
        launch();
    }
}