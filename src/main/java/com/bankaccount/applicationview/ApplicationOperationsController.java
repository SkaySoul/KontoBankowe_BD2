package com.bankaccount.applicationview;

import com.bankaccount.applicationsource.Account;
import com.bankaccount.applicationsource.OperationHistoryUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ApplicationOperationsController extends ApplicationRootController{

    Account account;


    @FXML
    TableColumn<OperationHistoryUnit, String> operationColumn;
    @FXML
    TableColumn<OperationHistoryUnit, Float> valueColumn;
    @FXML
    TableView<OperationHistoryUnit> tableView;
    @FXML
    Button closeButton;

    public ApplicationOperationsController(){

    }

    @FXML
    void initialize(){
        closeButton.setOnAction(event->{
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    public void initData() {
        ObservableList<OperationHistoryUnit> operationList = FXCollections.observableArrayList(account.getOperationList());
        valueColumn.setCellValueFactory(new PropertyValueFactory<OperationHistoryUnit, Float>("operationType"));
        operationColumn.setCellValueFactory(new PropertyValueFactory<OperationHistoryUnit, String>("value"));
        tableView.setItems(operationList);
    }
}
