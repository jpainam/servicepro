package com.cfao.app.reports;

import com.cfao.app.beans.Formation;
import com.cfao.app.model.FormationModel;

import java.util.HashMap;

/**
 * Created by JP on 7/13/2017.
 */
public class PrintFormation extends Report {
    FormationModel formationModel;
    HashMap<String, Object> parameters;
    public PrintFormation(){
        super();
        formationModel = new FormationModel();
        parameters = new HashMap<>();
        parameters.put("logo", logo);
    }
    public void showReport(Formation formation) {}
}
