package com.cfao.app.reports;

import com.cfao.app.beans.*;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.Constante;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.time.Period;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JP on 9/10/2017.
 */
public class CiviliteExcel extends ExcelRapport {
    public CiviliteExcel() {
        super();
    }

    public void printListe() throws Exception {
        short line = 0; // La ligne en cours de traitement
        /**
         * ENTETE
         */
        row = sheet.createRow(line);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("LISTE DES CIVILITES");
        cell.setCellStyle(headerStyle);

        sheet.addMergedRegion(new CellRangeAddress(line, line, 0, 4));
        cell = row.createCell(6);
        cell.setCellStyle(dateStyle);
        cell.setCellValue(new Date());
        sheet.addMergedRegion(new CellRangeAddress(line, line, 6, 9));

        line++;
        row = sheet.createRow(line);
        String[] titres = {"N°", "Pays", "Filiale", "Matric", "Prénom", "Nom", "Fonction", "Profil", "Groupe", "Section", "Potentiel",
                "Postes", "Passport", "Expire", "Téléphone", "MEMO"};
        for (int i = 0; i < titres.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(titres[i]);
            cell.setCellStyle(headerStyle);
        }

        line++;
        int col;
        int i = 1;
        List<Personne> personnes = new PersonneModel().getList();

        for (Personne p : personnes) {
            col = 0;
            row = sheet.createRow(line);
            row.createCell(col++).setCellValue(i);
            /** Pays */
            cell = row.createCell(col++);
            cell.setCellStyle(defaultStyle);
            cell.setCellValue(p.getPays().getNamefr());
            /** Filiale */
            cell = row.createCell(col++);
            cell.setCellValue(p.getSociete().getNom());
            cell.setCellStyle(defaultStyle);
            /** Matric */
            cell = row.createCell(col++);
            cell.setCellValue(p.getMatricule());
            cell.setCellStyle(defaultStyle);
            /** Prenom */
            cell = row.createCell(col++);
            cell.setCellValue(p.getPrenom());
            cell.setCellStyle(defaultStyle);
            /** Nom */
            cell = row.createCell(col++);
            cell.setCellValue(p.getNom());
            cell.setCellStyle(defaultStyle);
            /** Fonction */
            cell = row.createCell(col++);
            cell.setCellValue(p.getFonction());
            cell.setCellStyle(defaultStyle);
            /** Profil */
            cell = row.createCell(col++);
            String profils = "";
            if (p.getProfilPersonnes() != null) {
                Iterator<ProfilPersonne> iterator = p.getProfilPersonnes().iterator();
                while (iterator.hasNext()) {
                    ProfilPersonne profil = iterator.next();
                    profils += profil.getProfil().getLibelle() + " (" + profil.getNiveau().getLibelle() + ")";
                    if (iterator.hasNext()) {
                        System.err.println("Je suis entree");
                        profils += "\n";
                    }
                }
            }
            cell.setCellValue(profils);
            cell.setCellStyle(wrapStyle);
            /** Groupe */
            cell = row.createCell(col++);
            if (p.getGroupe() != null) {
                cell.setCellValue(p.getGroupe().getLibelle());
            }
            cell.setCellStyle(defaultStyle);
            /** Section */
            cell = row.createCell(col++);
            cell.setCellValue("");
            if (p.getSection() != null) {
                cell.setCellValue(p.getSection().getLibelle());
            }
            cell.setCellStyle(defaultStyle);
            /** Potentiel */
            cell = row.createCell(col++);
            cell.setCellValue("");
            if (p.getPotentiel() != null) {
                cell.setCellValue(p.getPotentiel().getLibelle());
            }
            cell.setCellStyle(defaultStyle);
            /** Postes */
            cell = row.createCell(col++);
            cell.setCellStyle(wrapStyle);
            String postes = "";
            /*if (p.getPostes() != null) {
                for (Poste poste : p.getPostes()) {
                    postes += poste.getTitre() + " ";
                }
            }
            if (!postes.isEmpty()) {
                //postes.substring(0, postes.lastIndexOf("\n"));
            }*/
            cell.setCellValue(postes);
            /** Passport */
            cell = row.createCell(col++);
            cell.setCellStyle(defaultStyle);
            if (p.getPassport() != null) {
                cell.setCellValue(p.getPassport().substring(0, p.getPassport().lastIndexOf(".")));
            }
            /** Expire */
            cell = row.createCell(col++);
            cell.setCellStyle(shortDateStyle);
            if (p.getExpirationPassport() != null) {
                cell.setCellValue(p.getExpirationPassport());
                Period diff = Period.between(p.expirationPassportProperty().get(), new java.sql.Date(new Date().getTime()).toLocalDate());
                if (diff.getMonths() < 6) {
                    cell.setCellStyle(redBackground);
                    cell.getCellStyle().setDataFormat(
                            createHelper.createDataFormat().getFormat("dd-mmm-yy"));
                }
            }

            /** Telephone */
            cell = row.createCell(col++);
            cell.setCellValue(p.getTelephone());
            cell.setCellStyle(defaultStyle);
            /** MEMO */
            cell = row.createCell(col++);
            cell.setCellValue(p.getMemo());
            cell.setCellStyle(defaultStyle);
            line++;
            i++;
        }

        sheet.createFreezePane(0, 2);
        AreaReference source = new AreaReference(new CellReference(1, 1), new CellReference(sheet.getLastRowNum(), titres.length - 1));

        CellReference position = new CellReference("A6");
        XSSFSheet pivotSheet = workbook.createSheet("Nbre de civilités par pays");
        XSSFPivotTable pivotTable = pivotSheet.createPivotTable(source, position, sheet);
        pivotTable.addRowLabel(0); // Pays
        pivotTable.addColumnLabel(DataConsolidateFunction.COUNT, 3, "Nombre de civilité");
        terminer();
    }

    public void printCompetence(Personne personne) throws Exception {
        short line = 0;
        XSSFCell cell;
        row = sheet.createRow(line++);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Liste et état des compétences");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(personne.getNom() + " " + personne.getPrenom());
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
        cell = row.createCell(5);
        cell.setCellStyle(shortDateStyle);
        cell.setCellValue(new Date());

        row = sheet.createRow(line++);
        String[] titres = {"N°", "Description", "Niveau", "Competence", "Connaissance", "Certification"};
        row = sheet.createRow(line++);
        for (int i = 0; i < titres.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(titres[i]);
        }

        getPrintCompetence(personne, line, false);
        sheet.createFreezePane(0, 3);
        terminer();
    }

    public void getPrintCompetence(Personne personne, short line, boolean merged) {
        XSSFCell cell;
        row = sheet.createRow(line++);
        String[] titres = {"N°", "Description", "Niveau", "Competence", "Connaissance", "Certification"};
        row = sheet.createRow(line);
        for (int i = 0; i < titres.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(titres[i]);
        }
        if (merged) {
            sheet.addMergedRegion(new CellRangeAddress(line, line, 1, 2));
        }
        line++;
        short col;
        int i = 1;
        List<PersonneCompetence> competences = personne.getPersonneCompetences();
        for (PersonneCompetence pc : competences) {
            col = 0;
            Competence c = pc.getCompetence();
            row = sheet.createRow(line);
            cell = row.createCell(col++);
            cell.setCellValue(i);
            cell.setCellStyle(defaultStyle);
            /** Description */
            cell = row.createCell(col++);
            cell.setCellValue(c.getDescription());
            cell.setCellStyle(defaultStyle);
            if (merged) {
                sheet.addMergedRegion(new CellRangeAddress(line, line, 1, 2));
            }
            /** Niveau */
            cell = row.createCell(col++);
            cell.setCellValue(c.getNiveau().getLibelle());
            cell.setCellStyle(defaultStyle);
            /** Competence */
            cell = row.createCell(col++);
            if (c.getType().equals(Constante.COMPETENCE) || c.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                cell.setCellValue("x");
            }
            cell.setCellStyle(defaultStyle);
            /** Connaissance */
            cell = row.createCell(col++);
            if (c.getType().equals(Constante.CONNAISSANCE) || c.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                cell.setCellValue("x");
            }
            cell.setCellStyle(defaultStyle);
            /** Certification */
            cell = row.createCell(col++);
            switch (pc.getCompetenceCertification().getCertification()) {
                case "CE":
                    cell.setCellValue("Certifiée");
                    break;
                case "AC":
                    cell.setCellValue("A Certifier");
                    break;
                case "EN":
                    cell.setCellValue("En cours");
                    break;
            }
            cell.setCellStyle(defaultStyle);
            line++;
            i++;
        }
    }

    public void printDetails(Personne p) throws Exception {
        short line = 0;
        row = sheet.createRow(line++);
        createCell(0, "Information Générale", headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 0, 3));
        createCell(4, new Date(), shortDateStyle);
        row = sheet.createRow(line++);
        createCell(1, "Matricule", headerStyle);
        createCell(2, "Nom & Prénom", headerStyle);
        createCell(3, "Date Naiss.", headerStyle);
        createCell(4, "Email", headerStyle);
        row = sheet.createRow(line++);
        createCell(1, p.getMatricule(), defaultStyle);
        createCell(2, p.getNom() + " " + p.getPrenom(), defaultStyle);
        createCell(3, p.getDatenaiss(), shortDateStyle);
        createCell(4, p.getEmail(), defaultStyle);
        row = sheet.createRow(line++);
        row = sheet.createRow(line++);
        createCell(1, "Groupe", headerStyle);
        createCell(2, "Société", headerStyle);
        createCell(3, "Section", headerStyle);
        createCell(4, "Ambition", headerStyle);
        row = sheet.createRow(line++);
        createCell(1, p.getGroupe() != null ? p.getGroupe().getLibelle() : "", defaultStyle);
        createCell(2, p.getSociete() != null ? p.getSociete().getNom() : "", defaultStyle);
        createCell(3, p.getSection() != null ? p.getSection().getLibelle() : "", defaultStyle);
        createCell(4, p.getAmbition() != null ? p.getAmbition().getLibelle() : "", defaultStyle);
        row = sheet.createRow(line++);
        row = sheet.createRow(line++);
        createCell(1, "Date Contrat", headerStyle);
        createCell(2, "Pays", headerStyle);
        createCell(3, "Passport", headerStyle);
        createCell(4, "Langue", headerStyle);
        row = sheet.createRow(line++);
        createCell(1, p.getDatecontrat(), shortDateStyle);
        createCell(2, p.getPays().getNamefr(), defaultStyle);
        createCell(3, p.getPassport() != null ? p.getPassport().
                substring(0, p.getPassport().lastIndexOf(".")) : "", defaultStyle);
        row = sheet.createRow(line++);
        createCell(0, "Langues parlées : " + p.getLangues().toString(), defaultStyle);
        sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 0, 3));

        List<ProfilPersonne> profils = p.getProfilPersonnes();
        if (null != profils) {
            row = sheet.createRow(line++);
            createCell(0, "Profil", headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 0, 3));
            row = sheet.createRow(line++);
            createCell(0, "N°", headerStyle);
            createCell(1, "Abbréviation", headerStyle);
            createCell(2, "Description", headerStyle);
            int i = 1;
            for (ProfilPersonne pp : profils) {
                row = sheet.createRow(line++);
                Profil profil = pp.getProfil();
                createCell(0, i + "", defaultStyle);
                createCell(1, profil.getAbbreviation(), defaultStyle);
                createCell(2, profil.getLibelle(), defaultStyle);
                i++;
            }
        }

        List<Poste> postes = p.getPostes();
        if (null != postes) {
            row = sheet.createRow(line++);
            createCell(0, "Postes", headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 0, 3));
            row = sheet.createRow(line++);
            createCell(0, "N°", headerStyle);
            createCell(1, "Titre du poste", headerStyle);
            createCell(2, "Société", headerStyle);
            createCell(3, "Date début", headerStyle);
            createCell(4, "Date fin", headerStyle);
            int i = 1;
            for (Poste poste : postes) {
                row = sheet.createRow(line++);
                createCell(0, i + "", defaultStyle);
                createCell(1, poste.getTitre(), defaultStyle);
                createCell(2, poste.getSociete().getNom(), defaultStyle);
                createCell(3, poste.getDatedebut(), shortDateStyle);
                createCell(4, poste.getDatefin(), shortDateStyle);
            }
        }
        row = sheet.createRow(line++);
        row = sheet.createRow(line++);
        createCell(0, "Compétences", headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(line - 1, line - 1, 0, 3));
        getPrintCompetence(p, (short) (line - 1), true);

        terminer();
    }
}
