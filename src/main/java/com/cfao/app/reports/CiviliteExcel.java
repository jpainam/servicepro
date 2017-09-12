package com.cfao.app.reports;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.ProfilPersonne;
import com.cfao.app.model.PersonneModel;
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
                while (iterator.hasNext()){
                    ProfilPersonne profil = iterator.next();
                    profils += profil.getProfil().getLibelle() + " (" + profil.getNiveau().getLibelle() + ")";
                    if(iterator.hasNext()){
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
            if(p.getPassport() != null) {
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
}
