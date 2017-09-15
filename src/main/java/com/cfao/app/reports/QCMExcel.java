package com.cfao.app.reports;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.PersonneQcm;
import com.cfao.app.beans.Qcm;
import com.cfao.app.util.Constante;

import java.util.Date;

/**
 * Created by JP on 9/14/2017.
 */
public class QCMExcel extends ExcelRapport {
    public QCMExcel() {
        super();
    }

    public void printDetails(Qcm qcm) throws Exception {
        short line = 0;
        row = sheet.createRow(line);
        createCell(1, "Test : " + qcm.getTitre(), headerStyle);
        //sheet.addMergedRegion(new CellRangeAddress(line, line, 1, 4));
        createCell(2, new Date(), shortDateStyle);
        line++;
        row = sheet.createRow(line++);
        createCell(1, "Type : " + qcm.getQcmType().getLibelle(), headerStyle);
        row = sheet.createRow(line++);
        createCell(1, "Base : " + qcm.getBase(), headerStyle);
        row = sheet.createRow(line++);
        row = sheet.createRow(line++);
        createCell(1, "Compétences possédées par le test", headerStyle);
        row = sheet.createRow(line++);
        createCell(0, "N°", headerStyle);
        createCell(1, "Description", headerStyle);
        createCell(2, "Compétence", headerStyle);
        createCell(3, "Connaissance", headerStyle);
        int i = 1;
        for (Competence c : qcm.getCompetences()) {
            row = sheet.createRow(line++);
            createCell(0, i, defaultStyle);
            createCell(1, c.getDescription(), defaultStyle);
            if (c.getType().equals(Constante.COMPETENCE) || c.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                createCell(2, "x", defaultStyle);
            } else {
                createCell(2, "", defaultStyle);
            }
            if (c.getType().equals(Constante.CONNAISSANCE) || c.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                createCell(3, "x", defaultStyle);
            } else {
                createCell(3, "", defaultStyle);
            }
            i++;
        }
        row = sheet.createRow(line++);
        row = sheet.createRow(line++);
        createCell(1, "Civilités ayant passées le test", headerStyle);
        row = sheet.createRow(line++);
        createCell(0, "N°", headerStyle);
        createCell(1, "Noms et Prénoms", headerStyle);
        createCell(2, "Note", headerStyle);
        createCell(3, "Société", headerStyle);
        createCell(4, "Pays", headerStyle);
        i = 1;
        for (PersonneQcm pq : qcm.getPersonneQcms()) {
            row = sheet.createRow(line++);
            createCell(0, i, defaultStyle);
            createCell(1, pq.getPersonne().getNom() + " " + pq.getPersonne().getPrenom(), defaultStyle);
            createCell(2, pq.getNote(), defaultStyle);
            createCell(3, pq.getPersonne().getSociete().getNom(), defaultStyle);
            createCell(4, pq.getPersonne().getPays().getNamefr(), defaultStyle);
            i++;
        }

        /*List<Competence> competences = qcm.getCompetences();
        List<Personne> personneDeja = new ArrayList<>();
        for (PersonneQcm pq : qcm.getPersonneQcms()) {
            personneDeja.add(pq.getPersonne());
        }
        List<Personne> personnes = new PersonneModel().getList();
        ObservableList<Personne> data = FXCollections.observableArrayList();
        for (Personne p : personnes) {
            boolean aBesoin = false;
            Iterator<PersonneCompetence> iterator = p.getPersonneCompetences().iterator();
            while (!aBesoin && iterator.hasNext()) {
                PersonneCompetence pc = iterator.next();
                Competence c = pc.getCompetence();
                if (!personneDeja.contains(p)) {
                    if (competences.contains(c) && pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                        aBesoin = true;
                    }
                }
            }
            if (aBesoin) {
                data.add(p);
            }
        }
        XSSFSheet sheet2 = workbook.createSheet("Civilité potentiel");
        XSSFRow row2 = sheet2.createRow(line++);
        row2 = sheet2.createRow(line++);
        createCell(1, "Civilités potentiel pour le test", headerStyle, row2);
        createCell(0, "N°", headerStyle, row2);
        createCell(1, "Noms et Prénoms", headerStyle, row2);
        createCell(2, "Société", headerStyle, row2);
        createCell(3, "Pays", headerStyle, row2);
        for (Personne p : data) {
            row2 = sheet2.createRow(line++);
            createCell(0, i + "", defaultStyle, row2);
            createCell(1, p.getNom() + " " + p.getPrenom(), defaultStyle, row2);
            createCell(2, p.getSociete().getNom(), defaultStyle, row2);
            createCell(3, p.getPays().getNamefr(), defaultStyle, row2);
        }*/
        terminer(true);
    }
}
